package cn.yjc.bookStore.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.yjc.bookStore.cart.dao.CartItemDao;
import cn.yjc.bookStore.cart.domain.CartItem;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	/**
	 * 加载多个cartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 更新数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity){
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 批量删除购物车条目
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds){
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	public void add(CartItem cartItem){
		/*
		 * 使用uid和bid去数据库中查询这个条目是否存在
		 */
		try {
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), 
					cartItem.getBook().getBid());
			if(_cartItem == null){//如果原来没有这个条目，添加条目
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			}else{//如果原来有这个条目，修改数量
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 我的购物车
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid){
		try {
			return cartItemDao.findByUser(uid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
