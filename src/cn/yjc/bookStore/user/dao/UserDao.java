package cn.yjc.bookStore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.yjc.bookStore.user.domain.User;

/**
 * 用户模块持久层
 * @author 惊城
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 按照uid和oldpass查询
	 * @param uid
	 * @param oldpass
	 * @return
	 * @throws SQLException 
	 */
	public boolean findByUidAndOldpass(String uid, String oldPass) throws SQLException{
		String sql = "select count(1) from t_user where uid=? and loginpass=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), uid, oldPass);
		return number.intValue() > 0;
	}
	
	/**
	 * 根据uid修改密码
	 * @param uid
	 * @param newPass
	 * @throws SQLException
	 */
	public void updatePass(String uid, String newPass) throws SQLException{
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql, newPass, uid);
		System.out.print(newPass);
	}
	/**
	 * 根据用户名和密码查询用户
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public User findByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException{
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}
	/**
	 * 校验用户名是否注册
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException{
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
	/**
	 * 校验email是否注册
	 * @param email
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException{
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException{
		String sql = "insert into t_user values(?,?,?,?,?,?)";
		Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(), 
				user.getEmail(), user.isStatus(),user.getActivationCode()};
		qr.update(sql,params);		
	}
	/**
	 * 通过激活码查询用户
	 * @param activationCode
	 * @return
	 * @throws SQLException
	 */
	public User findByActivationCode(String activationCode) throws SQLException {
		String sql = "select * from t_user where activationCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class), activationCode);	
	}
	/**
	 * 修改状态
	 * @param uid
	 * @param status
	 * @throws SQLException 
	 */
	public void updateStatus(String uid, boolean status) throws SQLException{
		String sql = "update t_user set status=? where uid=?";
		qr.update(sql, status, uid);
	}
}
