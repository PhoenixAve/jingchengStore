package cn.yjc.bookStore.book.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.book.domain.Book;
import cn.yjc.bookStore.book.service.BookService;
import cn.yjc.bookStore.category.domain.Category;
import cn.yjc.bookStore.pager.PageBean;

public class BookServlet extends BaseServlet {

	private BookService bookService = new BookService();
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
		return "f:/jsps/book/bookList.jsp";
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
		return "f:/jsps/book/bookList.jsp";
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
		return "f:/jsps/book/bookList.jsp";
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
		return "f:/jsps/book/bookList.jsp";
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
		return "f:/jsps/book/bookList.jsp";
	}
	/**
	 * 查询推荐图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findRecommend(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		bookService.findAll(1);
		int pc = getPc(req);
		pc = (int) (Math.random()*10);

		/*
		 * 2. 得到url：...
		 */
		String url = "";
		/*
		 * 3.调用service#findAll得到PageBean
		 */
		PageBean<Book> pb = bookService.findRecommend(pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("recommendPb", pb);
		req.getRequestDispatcher("/jsps/body1.jsp").include(req, resp);
		return null;
	}
	/**
	 * 首页根据分类按刷新时间查询图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPrinttime(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		PageBean<Book> pb = bookService.findByPrinttime(cid);
		pb.setUrl("/BookServlet?method=findLikeJava");
		req.setAttribute("javaPb", pb);
		req.getRequestDispatcher("/jsps/body2.jsp").include(req, resp);
		return null;
	}
	/**
	 * 查询所有图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3.调用service#findAll得到PageBean
		 */
		PageBean<Book> pb = bookService.findAll(pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/bookList.jsp";
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
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "f:/jsps/book/bookDetail.jsp";
	}
}
