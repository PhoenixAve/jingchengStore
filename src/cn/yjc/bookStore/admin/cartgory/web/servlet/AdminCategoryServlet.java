package cn.yjc.bookStore.admin.cartgory.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.book.service.BookService;
import cn.yjc.bookStore.category.domain.Category;
import cn.yjc.bookStore.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAll());
		req.getRequestDispatcher("/adminjsps/admin/category/categoryList.jsp").include(req, resp);
		return null;
	}
	/**
	 * 添加一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到Category中
		 * 调用service的add()方法完成添加、
		 * 调用findAll()，返回categoryList.jsp显示所有分类
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());//设置cid
		categoryService.add(parent);
		return findAll(req, resp);
	}
	/**
	 * 添加子分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到Category中
		 * 需要手动把表单中的pid映射到child对象中
		 * 调用service的add()方法完成添加
		 * 调用findAll()，返回categoryList.jsp显示所有分类
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());//设置cid
		//手动映射pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.add(child);
		return findAll(req, resp);
	}
	/**
	 * 添加二级分类第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取cid，当前点击的父分类id
		 */
		String pid = req.getParameter("pid");
		/*
		 * 获取所有的父分类
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/addTwo.jsp";
	}
	/**
	 * 修改一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到category中
		 * 调用service方法完成修改
		 * 转发到categoryList.jsp显示所有分类return findAll();
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(req, resp);
	}
	/**
	 * 修改一级分类第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取链接中的cid
		 * 使用cid加载category
		 * 保存category
		 * 转发到editOne.jsp页面显示category
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/editOne.jsp";
	}
	/**
	 * 修改二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单参数到category child
		 * 把表单中的pid封装到child...
		 * 调用service.edit()方法完成修改
		 * 返回到categoryList.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.edit(child);
		return findAll(req, resp);
	}
	/**
	 * 修改二级分类第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取连接中的cid，通过cid加载category，保存之
		 * 查询出所有一级分类，保存之
		 * 转发到edtiTwo.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());
		return "f:/adminjsps/admin/category/editTwo.jsp";
	}
	/**
	 * 删除一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取连接中的cid，他是一个1级分类的id
		 * 通过cid，差看该父类下子分类的个数
		 * 如果大于零，说明还有子分类，不能删除，保存错误信息，转发到msg.jsp
		 * 如果等于零，删除之，返回到categoryList.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryService.findChildrenCountByParent(cid);
		if(cnt > 0){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "该分类下还有子分类不能删除");
			return "f:/adminjsps/msg.jsp";
		}else{
			categoryService.delete(cid);
			return findAll(req, resp);
		}
	}
	/**
	 * 删除二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取cid，即二级分离的id
		 * 获取该分类下图书的总数
		 * 如果大于零，保存错误信息，转发到msg.jsp
		 * 如果等于零，删除之，返回到categoryList.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if (cnt > 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		}else{
			categoryService.delete(cid);
			return findAll(req, resp);
		}
	}
		
}
