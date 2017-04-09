package cn.yjc.bookStore.user.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.user.domain.User;
import cn.yjc.bookStore.user.service.UserService;
import cn.yjc.bookStore.user.service.exception.UserException;

/**
 * 用户模块WEB层
 * @author 惊城
 *
 */
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	
	/**
	 * 退出登录
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/jsps/user/login.jsp";
	}
	/**
	 * 修改密码
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePass(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到user中
		 * 从session中获取uid
		 * 使用uid和表单中的oldPass和newPass调用service
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		//如果用户没有登录，返回到登录页面，显示错误信息
		User user = (User) req.getSession().getAttribute("sessionUser");
		if(user == null){
			req.setAttribute("msg", "您还没有登录！");
			return "f:/jsps/user/login.jsp";
		}
		
		try {
			userService.updatePass(user.getUid(), formUser.getLoginpass(), formUser.getNewpass());
			req.setAttribute("msg", "修改密码成功！");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request
			req.setAttribute("user", formUser);//为了回显
			return "f:/jsps/user/pwd.jsp";
		}
	}
	/**
	 * 登录
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、封装表单数据到user
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2、校验表单数据
		 */
		Map<String, String> errors = validateLogin(formUser, req.getSession());
		if(errors.size() > 0){
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}
		/*
		 * 3、使用service查询，得到user
		 */
		User user = userService.login(formUser);
		/*
		 * 4、查看用户是否存在，如果不存在
		 * 		保存错误信息：用户名和密码
		 * 		保存用户数据：为了回显
		 * 5、如果存在，查看是否激活，如果为false
		 * 		保存错误信息：您没有激活
		 * 		保存表单：为了回显
		 * 		转发到login.jsp
		 * 6、如果登陆成功
		 * 		保存当前查询出的user到session中
		 * 		保存当前用户的名称到cookie中，注意中文需要编码处理
		 */
		if(user == null){
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		}else{
			if(!user.isStatus()){
				req.setAttribute("msg", "您还没有激活！");
				req.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";
			}else{
				req.getSession().setAttribute("sessionUser", user);
				//设置编码
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "UTF-8");
				//添加cookie，保存十天
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);
				resp.addCookie(cookie);
				return "r:/index.jsp";//重定向到主页
			}
		}
	}
	
	/*
	 * 登录表单校验
	 */
	private Map<String, String> validateLogin(User formUser, HttpSession session){
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * 1.校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()){
			errors.put("loginname", "用户名不能为空！");
		}else if(loginname.length() < 3 || loginname.length() > 20){
			errors.put("loginname", "用户名长度必须在3~20之间！");
		}
		/*
		 * 2.校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()){
			errors.put("loginpass", "密码不能为空！");
		}else if(loginpass.length() < 3 || loginpass.length() > 20){
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		/*
		 * 3.验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()){
			errors.put("verifyCode", "验证码不能为空！");
		}else if(!verifyCode.equalsIgnoreCase(vcode)){
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
	/**
	 * ajax 用户名是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateLoginname(loginname);
		/*
		 * 将校验结果发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * ajax Email是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到email
		 */
		String email = req.getParameter("email");
		/*
		 * 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateLoginname(email);
		/*
		 * 将校验结果发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * ajax 验证码是否正确校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.得到输入框中的验证码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2.获取图片上真实的验证码
		 */
		String vcode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3.忽略大小写进行比较验证码是否一致
		 */
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		/*
		 * 4.将校验结果发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 注册功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.封装表单数据到User对象
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2.校验之,如果校验失败，保存错误信息，返回到regist.jsp显示
		 */
		Map<String, String> errors = validateRegist(formUser, req.getSession());
		if(errors.size() > 0){
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}
		/*
		 * 3.使用service完成业务
		 */
		userService.regist(formUser);
		/*
		 * 4.保存成功信息，转发到msg.jsp
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜您，注册成功，请尽快到邮箱激活！");
		//转发到msg.jsp显示信息
		return "f:/jsps/msg.jsp";
	}
	/**
	 * 注册校验，对表单的字段进行逐个校验，
	 * 如果有错误，使用当前字段名称为key，错误信息为value，
	 * 保存到map中，返回map
	 * @param formUser
	 * @return
	 */
	private Map<String, String> validateRegist(User formUser, HttpSession session){
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * 1.校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()){
			errors.put("loginname", "用户名不能为空！");
		}else if(loginname.length() < 3 || loginname.length() > 20){
			errors.put("loginname", "用户名长度必须在3~20之间！");
		}else if(!userService.ajaxValidateLoginname(loginname)){
			errors.put("loginname", "用户名已注册！");
		}
		/*
		 * 2.校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()){
			errors.put("loginpass", "密码不能为空！");
		}else if(loginpass.length() < 3 || loginpass.length() > 20){
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		/*
		 * 3.确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()){
			errors.put("reloginpass", "确认密码不能为空！");
		}else if(!reloginpass.equals(loginpass)){
			errors.put("reloginpass", "两次输入不一致！");
		}
		/*
		 * 4.email校验
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "Email不能为空！");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("email", "Email格式错误！");
		}else if(!userService.ajaxValidateEmail(email)){
			errors.put("email", "Email已被注册！");
		}
		/*
		 * 5.验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()){
			errors.put("verifyCode", "验证码不能为空！");
		}else if(!verifyCode.equalsIgnoreCase(vcode)){
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取参数中的激活码
		 */
		String activationCode = req.getParameter("activationCode");
		/*
		 * 通过service校验激活码是否正确,
		 * 		service方法有可能抛出异常，把异常信息保存到request中，转发到msg.jsp显示
		 * 	如果没有异常，保存想成功信息，转发到msg.jsp显示
		 */
		try {
			userService.activation(activationCode);
			req.setAttribute("code", "success");
			req.setAttribute("msg", "恭喜您，账号激活成功,请马上登录！");
		} catch (UserException e) {
			// 说明service抛出了异常
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");
		}
		
		return "f:/jsps/msg.jsp";
	}
}
