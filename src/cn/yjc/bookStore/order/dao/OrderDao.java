package cn.yjc.bookStore.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.yjc.bookStore.book.domain.Book;
import cn.yjc.bookStore.order.domain.Order;
import cn.yjc.bookStore.order.domain.OrderItem;
import cn.yjc.bookStore.pager.Expression;
import cn.yjc.bookStore.pager.PageBean;
import cn.yjc.bookStore.pager.PageConstants;

public class OrderDao {
	QueryRunner qr = new TxQueryRunner();
	/**
	 * 按状态查询订单
	 * @param status
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(int status, int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status", "=", status + ""));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 查询所有订单
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList, pc);
	}
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public int findStatus(String oid) throws SQLException{
		String sql = "select status from t_order where oid=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), oid);
		return number.intValue();
	}
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String oid, int status) throws SQLException{
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql, status, oid);
	}
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 * @throws SQLException 
	 */
	public Order load(String oid) throws SQLException{
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		loadOrderItem(order);//为当前订单加载他的所用订单条目
		return order;
	}
	/**
	 * 生成订单
	 * @param order
	 * @throws SQLException 
	 */
	public void add(Order order) throws SQLException{
		/*
		 * 插入订单
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrdertime(), 
				order.getTotal(), order.getStatus(), order.getAddress(), 
				order.getOwner().getUid()};
		qr.update(sql, params);
		/*
		 * 循环遍历订单的所有条目，让每个条目生成一个Object[]
		 * 多个条目就对应Object[][]
		 * 执行批处理，完成插入条目
		 */
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for (int i = 0; i < len; i++) {
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[]{item.getOrderItemId(), item.getQuantity(),
					item.getSubtotal(), item.getBook().getBid(),
					item.getBook().getBname(), item.getBook().getCurrPrice(),
					item.getBook().getImage_b(), order.getOid()};
		}
		qr.batch(sql, objs);
	}
	/**
	 * 按用户查询订单
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid, int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid","=",uid));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByCriteria(List<Expression> exprList, int pc) throws SQLException{
		/*
		 * 得到ps
		 */
		int ps = PageConstants.ORDER_PAGE_SIZE;//每页记录数
		/*
		 * 通过exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();//SQL中有问号，他是对应问号的值
		for(Expression expr : exprList){
			/*
			 * 添加一个条件
			 * 		以and开头
			 * 		条件的名称
			 * 		条件的运算符，可以是=、!=、>、< ... is null, is null没有值
			 */
			whereSql.append(" and ").append(expr.getName())
				.append(" ").append(expr.getOperator()).append(" ");
			// where 1=1 and bid = is null
			if(!expr.getOperator().equals("is null")){
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		/*
		 * 得到tr
		 */
		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
		int  tr = number.intValue();//得到总记录数
		/*
		 * 得到beanList，即当前页记录
		 */
		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1) * ps);//第一个问号：当前页首行记录的下标
		params.add(ps);//第二个问号：一共查询几行，就是每页记录数
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
		//虽然已经获取了所有订单，但是并没有订单条目
		//遍历每个订单，为其加载他的订单条目
		for (Order order : beanList) {
			loadOrderItem(order);
		}
		/*
		 * 创建PageBean,设置参数返回
		 * 其中PageBean没有url，这个任务由Servlet完成
		 */
		PageBean<Order> pb = new PageBean<Order>();
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}
	/*
	 * 为制定的order加载他的orderItem
	 */
	private void loadOrderItem(Order order) throws SQLException {
		/*
		 * 给出sql语句select * from t_orderItem where and 	oid=?	
		 * 直行之，的到List<OrderItem>
		 * 设置个Order对象
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
	}
	/**
	 * 把多个map转换成多个orderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	/**
	 * 把一个map转换成一个orderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
}
