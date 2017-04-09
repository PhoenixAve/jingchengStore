package cn.yjc.bookStore.admin.book.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.book.domain.Book;
import cn.yjc.bookStore.book.service.BookService;
import cn.yjc.bookStore.category.domain.Category;
import cn.yjc.bookStore.category.service.CategoryService;
import cn.yjc.bookStore.pager.PageBean;

public class AdminBookServlet extends BaseServlet {
	public CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		//删除图片
		Book book = bookService.load(bid);
		String savepath = this.getServletContext().getRealPath("/img/");//获取真实路径
		new File(savepath, book.getImage_w()).delete();
		new File(savepath, book.getImage_b()).delete();
		//删除数据库数据
		bookService.delete(bid);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "删除图书成功！");
		return "f:/adminjsps/msg.jsp";
	}
	/**
	 * 修改图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 把表单数据封装到Book对象中
		 * 封装cid到category中
		 * 把category赋给book
		 * 调用service完成工作
		 * 保存成功信息，转发到msg.jsp
		 */
		String bid = req.getParameter("bid");
		String bname = new String(req.getParameter("bname").getBytes("ISO-8859-1"), "utf-8");
		String author = new String(req.getParameter("author").getBytes("ISO-8859-1"), "utf-8");
		double price = Double.parseDouble(req.getParameter("price"));
		double currPrice = Double.parseDouble(req.getParameter("currPrice"));
		double discount = Double.parseDouble(req.getParameter("discount"));
		String press = new String(req.getParameter("press").getBytes("ISO-8859-1"), "utf-8");
		String publishtime = new String(req.getParameter("publishtime").getBytes("ISO-8859-1"), "utf-8");
		int edition = Integer.parseInt(req.getParameter("edition"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int wordNum = Integer.parseInt(req.getParameter("wordNum"));
		String printtime = new String(req.getParameter("printtime").getBytes("ISO-8859-1"), "utf-8");
		int booksize = Integer.parseInt(req.getParameter("booksize"));
		String paper = new String(req.getParameter("paper").getBytes("ISO-8859-1"), "utf-8");
		String cid = new String(req.getParameter("cid").getBytes("ISO-8859-1"), "utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
//		Map map = req.getParameterMap();
		map.put("bid", bid);
		map.put("bname", bname);
		map.put("author", author);
		map.put("price", price);
		map.put("currPrice", currPrice);
		map.put("discount", discount);
		map.put("press", press);
		map.put("publishtime", publishtime);
		map.put("edition", edition);
		map.put("pageNum", pageNum);
		map.put("wordNum", wordNum);
		map.put("printtime", printtime);
		map.put("booksize", booksize);
		map.put("paper", paper);
		map.put("cid", cid);
		map.put("bid", bid);
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		bookService.edit(book);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
		
	}
	/**
	 * 加载图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取bid，得到Book对象，保存之
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		/*
		 * 获取所有一级分类，保存之
		 */
		req.setAttribute("parents", categoryService.findParents());
		/*
		 * 获取当前图书所属的一级分类下所有二级分类
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		/*
		 * 转发到bookDetaoil.jsp显示
		 */
		return "f:/adminjsps/admin/book/bookDetail.jsp";
	}
	/**
	 * 添加图书第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取所有一级分类
		 * 转发到add.jsp，改页面会在下拉列表中显示所有一级分类
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("parents", parents);
		req.getRequestDispatcher("/adminjsps/admin/book/bookAdd.jsp").include(req, resp);
		return null;
	}
	/**
	 * ajax异步查询指定父分类下的二级子分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取pid
		 * 通过pid查询出所有二级分类
		 * 把List<Category>转换成json，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		resp.getWriter().print(json);
		return null;
	}
	//{"cid":"sfasfasf", "cname":"sdfsadf"}
	private String toJson(Category category){
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	//[{"cid":"sfasfasf", "cname":"sdfsadf"},{"cid":"sfasfasf", "cname":"sdfsadf"}]
	private String toJson(List<Category> categoryList){
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 显示所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 通过service得到所有分类
		 * 保存到request中，转发到body.jsp
		 */
		List<Category> parents = categoryService.findAll();
		req.getSession().setAttribute("parents", parents);
		req.getRequestDispatcher("/adminjsps/admin/book/bookLeft.jsp").include(req, resp);
		return null;
	}
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
	 * 按分类查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
		 * 获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");
		/*
		 * 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/book/bookList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/bookList.jsp";
	}
	/**
	 * 按作者查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
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
		 * 获取查询条件，本方法就是author
		 */
		String author = new String(req.getParameter("author").getBytes("ISO-8859-1"), "utf-8");
		/*
		 * 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/book/bookList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/bookList.jsp";
	}
	/**
	 * 按出版社查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
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
		 * 获取查询条件，本方法就是author
		 */
		String press = new String(req.getParameter("press").getBytes("ISO-8859-1"), "utf-8");
		/*
		 * 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/book/bookList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/bookList.jsp";
	}
	/**
	 * 按书名查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
		 * 获取查询条件，本方法就是author
		 */
		String bname = req.getParameter("bname");
		/*
		 * 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/book/bookList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/bookList.jsp";
	}
	/**
	 * 多条件组合查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
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
		 * 获取查询条件，本方法就是author
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 给PageBean设置URL，保存PageBean，转发到/jsps/book/bookList.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/book/bookList.jsp";
	}

}
