package cn.yjc.bookStore.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.yjc.bookStore.user.dao.UserDao;
import cn.yjc.bookStore.user.domain.User;
import cn.yjc.bookStore.user.service.exception.UserException;

/**
 * 用户模块业务层
 * @author 惊城
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 修改密码
	 * @param uid
	 * @param oldPass
	 * @param newPass
	 * @throws UserException
	 */
	public void updatePass(String uid, String oldPass, String newPass) throws UserException{
		try {
			/*
			 * 校验老密码
			 */
			boolean bool = userDao.findByUidAndOldpass(uid, oldPass);
			if(!bool){		//如果老密码错误
				throw new UserException("老密码错误!");
			}
			/*
			 * 修改密码
			 */
			userDao.updatePass(uid, newPass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	public User login(User user){
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 校验用户名是否注册
	 * @param loginname
	 * @return 
	 */
	public boolean ajaxValidateLoginname(String loginname){
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 校验email是否注册
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email){
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 注册功能
	 * @param user
	 */
	public void regist(User user){
		/*
		 * 1.补齐数据
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		/*
		 * 2.向数据库插入用户
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/*
		 * 3.发送邮件
		 */
		/*
		 * 登录邮件服务器得到session
		 */
		String host = prop.getProperty("host");//服务器主机名
		String name =prop.getProperty("username");//登录名
		String pass = prop.getProperty("password");//登录密码
		Session session = MailUtils.createSession(host, name, pass);
		/*
		 * 创建mail对象
		 */
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject =prop.getProperty("subject");
		//MessageFormat.format方法会把第一个参数的{n}，使用第二个参数替换
		//例如MessageFormat.format("你好{0}，你{1}","张三","去死");返回-----你好张三，你去死
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail =new Mail(from, to, subject, content);
		/*
		 * 发送邮件
		 */
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 验证激活码是否正确
	 * @param activationCode
	 * @throws UserException 
	 */
	public void activation(String activationCode) throws UserException {
		/*
		 * 使用激活码进行查询
		 * user是否为null,
		 * 是null，抛异常，添加异常信息，无效激活码
		 * 不是null,查看user的状态是否为true,
		 * 		如果为true，抛出异常，添加信息，账户已经激活，请勿多次激活
		 * 		如果不是true，修改用户的状态为true
		 */
		try {
			User user = userDao.findByActivationCode(activationCode);
			if(user == null){
				throw new UserException("无效的激活码！");
			}
			if(user.isStatus()){
				System.out.print("账户已经激活，请勿多次激活!");
				throw new UserException("账户已经激活，请勿多次激活!");
			}
			userDao.updateStatus(user.getUid(),true);
		} catch (SQLException e) { 
			throw new RuntimeException(e);
		}		
	}
}
