package cn.yjc.bookStore.admin.order.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.order.domain.Order;
import cn.yjc.bookStore.order.service.OrderService;
import cn.yjc.bookStore.pager.PageBean;
import cn.yjc.bookStore.user.domain.User;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pc;
	}
	/**
	 * 截取URL，页面中的分页导航中需要使用它作为超链接的目标
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/jingchengStore.BookServlet?method=findByCategory&cid=xxx
	 * uri = jingchengStore/BookServlet + method=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req){
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，不存在就不用截取
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1){
			url = url.substring(0, index);
		}
		return url;
	}
	/**
	 * 查询所有订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 得到pc：如果页面传，使用页面传的，否则pc=1
		 */
		int pc  = getPc(req);
		/*
		 * 得到URL：。。。
		 */
		String url = getUrl(req);
		/*
		 * 使用pc和uid调用service#myOrder得到PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/order/orderList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/orderList.jsp";
	}
	/**
	 * 按状态查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 得到pc：如果页面传，使用页面传的，否则pc=1
		 */
		int pc  = getPc(req);
		/*
		 * 得到URL：。。。
		 */
		String url = getUrl(req);
		/*
		 * 获取链接参数
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/*
		 * 使用pc和uid调用service#myOrder得到PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/order/orderList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/orderList.jsp";
	}
	/**
	 * 查看订单详细信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");//btn说明了用户点击了那个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "f:/adminjsps/admin/order/orderDetail.jsp";
	}
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 1){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "订单已经取消！");
		return "f:/adminjsps/msg.jsp";
	}
	/**
	 * 发货功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if(status != 2){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "订单已经发货！");
		return "f:/adminjsps/msg.jsp";
	}
}
