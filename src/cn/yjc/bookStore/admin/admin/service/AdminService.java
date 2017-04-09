package cn.yjc.bookStore.admin.admin.service;

import java.sql.SQLException;

import cn.yjc.bookStore.admin.admin.dao.AdmindDao;
import cn.yjc.bookStore.admin.admin.domain.Admin;

public class AdminService {
	private AdmindDao adminDao = new AdmindDao();
	/**
	 * 登录功能
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin){
		try {
			return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
