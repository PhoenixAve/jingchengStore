package cn.yjc.bookStore.admin.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.admin.admin.domain.Admin;
import cn.yjc.bookStore.admin.admin.service.AdminService;

public class AdminServlet extends BaseServlet {
	private AdminService adminService = new AdminService();
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
		return "r:/adminjsps/adminLogin.jsp";
	}
	/**
	 * 登录功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到admin
		 */
		Admin form = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.login(form);
		if(admin == null){
			req.setAttribute("msg", "用户名或密码错误！");
			return "f:adminjsps/adminLogin.jsp";
		}
		req.getSession().setAttribute("admin", admin);
		return "r:/adminjsps/admin/adminIndex.jsp";
	}

}
