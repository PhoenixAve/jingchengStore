package cn.yjc.bookStore.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.category.domain.Category;
import cn.yjc.bookStore.category.service.CategoryService;
/**
 * 分类模块WEB层
 * @author 惊城
 *
 */
public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 通过service得到所有分类
		 * 保存到request中，转发到body.jsp
		 */
		List<Category> parents = categoryService.findAll();
		req.getSession().setAttribute("parents", parents);
		req.getRequestDispatcher("/jsps/header1.jsp").include(req, resp);
		return null;
	}
}
