package cn.yjc.bookStore.cart.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.yjc.bookStore.book.domain.Book;
import cn.yjc.bookStore.cart.domain.CartItem;
import cn.yjc.bookStore.cart.service.CartItemService;
import cn.yjc.bookStore.user.domain.User;

public class CartItemServlet extends BaseServlet {
	private CartItemService cartItemService = new CartItemService();
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取cartItems参数
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		/*
		 * 通过service得到List<CartItem>
		 */
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 保存，然后转发到/cart/cartShowItem.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/cartShowItem.jsp";
	}
	/**
	 * 更新数量
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cartItemId = req.getParameter("cartItemId");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		//给客户端返回一个json对象
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		resp.getWriter().print(sb);
		return null;
	}
	/**
	 * 批量删除购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 获取cartItemIds参数
		 * 调用service方法完成删除
		 * 修改URL调用myCart()显示购物车条目
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		String url = req.getRequestURL() + "?method=myCart";
		resp.sendRedirect(url);
		return null;
	}
	/**
	 * 添加购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 封装表单数据到CartItem(bid, quantity)
		 */
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		/*
		 * 调用service完成添加
		 */
		cartItemService.add(cartItem);
		/*
		 * 查询出当前用户的所有条目，修改URL调用myCart()显示购物车条目
		 */
		String url = req.getRequestURL() + "?method=myCart";
		resp.sendRedirect(url);
		return null;
	}
	/**
	 * 我的购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 得到uid
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/*
		 * 通过service得到当前用户的所有购物车条目
		 */
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		/*
		 * 保存起来，转发到/cart/cartList.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		return "f:/jsps/cart/cartList.jsp";
	}

}
