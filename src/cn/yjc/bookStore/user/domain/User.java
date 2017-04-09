package cn.yjc.bookStore.user.domain;

/**
 * 用户模块实体类
 * @author 惊城
 * 属性来自数据库表和所有的表单
 *
 */
public class User {
	//数据库表中的属性
	private String uid;//用户ID,主键
	private String username;//登录名
	private String loginname;//登录密码
	private String loginpass;//登录密码
	private String email;//邮箱
	private boolean status;//激活状态
	private String activationCode;//激活码
	//注册表单中
	private String reloginpass;//确认密码
	private String verifyCode;//验证码
	//修改密码表单
	private String newpass;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpass() {
		return loginpass;
	}

	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getReloginpass() {
		return reloginpass;
	}

	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", loginname="
				+ loginname + ", loginpass=" + loginpass + ", email=" + email
				+ ", status=" + status + ", activationCode=" + activationCode
				+ ", reloginpass=" + reloginpass + ", verifyCode=" + verifyCode
				+ ", newpass=" + newpass + "]";
	}
	
}
