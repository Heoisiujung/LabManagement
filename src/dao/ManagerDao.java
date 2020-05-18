package dao;

import vo.Manager;

import java.sql.*;

public class ManagerDao {
	private static Connection conn = null;

	//连接到数据库
	public static void initConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/labsystem?serverTimezone=UTC",
				"root","");
	}

	//给出管理员ID 返回一个管理员对象 对象的密码、姓名与ID匹配；若无此ID 则返回一个空管理员对象
	public static Manager getManager(int mID) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT password, name FROM manager WHERE id = " + mID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String password = rs.getString("password");
			String name = rs.getString("name");
			return new Manager(mID,password,name);
		}
		return new Manager();
	}

	//（管理员登录）给出管理员ID、密码 验证是否匹配
	public static boolean mIdentify(int mID, String mPassword) throws Exception {
		if (mPassword.equals(getManager(mID).getManagerPassword()))
			return true;
		else
			return false;
	}
	
	//（管理员业务）给出管理员对象与新密码 将该管理员的密码修改为新密码
	public static void editPassword(Manager manager, String newPassword) throws Exception{
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "UPDATE manager SET password = '" + newPassword + "' "
				+ "WHERE id = " + manager.getManagerID();
		stmt.executeLargeUpdate(sql);
		manager.setManagerPassword(newPassword);
	}
}