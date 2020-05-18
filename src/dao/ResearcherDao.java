package dao;

import vo.Researcher;
import java.sql.*;

public class ResearcherDao {
	private static Connection conn = null;

	//连接到数据库
	public static void initConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/labsystem?serverTimezone=UTC",
				"root","");
	}
	
	//给出研究员ID 返回一个研究员对象 对象的密码、姓名与ID匹配；若无此ID 则返回一个空研究员对象
	public static Researcher getResearcher(int rID) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT password, name FROM researcher WHERE id = " + rID;
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String password = rs.getString("password");
			String name = rs.getString("name");
			return new Researcher(rID,password,name);
		}
		return new Researcher();
	}
	
	//（研究员登录）给出研究员ID、密码 验证是否匹配
	public static boolean rIdentify(int rID, String rPassword) throws Exception{
		if (rPassword.equals(getResearcher(rID).getResearcherPassword()))
			return true;
		else
			return false;
	}
	
	//（研究员业务）给出研究员对象与新密码 将该研究员的密码修改为新密码
	public static void editPassword(Researcher researcher, String newPassword) throws Exception{
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "UPDATE researcher SET password = '" + newPassword + "' "
				+ "WHERE id = " + researcher.getResearcherID();
		stmt.executeLargeUpdate(sql);
		researcher.setResearcherPassword(newPassword);
	}
	
	//（管理员业务）返回研究员的个数
	public static int researcherAmount() throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM researcher";
		ResultSet rs = stmt.executeQuery(sql);
		int amount = 0;
		while (rs.next()) {
			amount ++;
		}
		return amount;
	}
	
	//（管理员业务）给出id 返回该研究员id及姓名信息
	public static String[] researcherView(int id) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM researcher WHERE id = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			return new String[] {rs.getString(1),rs.getString(3)};
		} else {
			return new String[] {"",""};
		}
	}
	
	//（管理员业务）给出名字 新建研究员账号 自动生成一个ID及初始密码"123456"
	public static void addResearcher(String rName) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		int rID = 0;
		String sql = "SELECT MAX(id) FROM researcher";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			rID = rs.getInt(1) + 1;
		}
		if (rID!=0) {
			sql = "INSERT INTO researcher VALUES(" + rID + ", '123456', '" + rName + "')";
			stmt.executeUpdate(sql);
		}
	}
	
	//(管理员业务）给出ID 删除该研究员账号
	public static void delResearcher(int rID) throws Exception{
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "DELETE FROM researcher WHERE id = " + rID;
		stmt.executeUpdate(sql);
	}
}