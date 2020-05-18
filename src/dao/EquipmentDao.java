package dao;

import java.sql.*;

public class EquipmentDao {
	private static Connection conn = null;

	//连接到数据库
	public static void initConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/labsystem?serverTimezone=UTC",
				"root","");
	}
	
	//（管理员业务）返回设备的种数
	public static int equipSpecies() throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM equipcatalog";
		ResultSet rs = stmt.executeQuery(sql);
		int amount = 0;
		while (rs.next()) {
			amount ++;
		}
		return amount;
	}
	
	//（管理员业务）给出id 返回该设备的id及设备名信息
	public static String[] equipView(int id) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM equipcatalog WHERE id = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			return new String[] {rs.getString(1),rs.getString(2)};
		} else {
			return new String[] {"",""};
		}
	}
	
	//（管理员业务）给出id 返回该设备总数目
	public static int equipAmount(int id) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM equipment WHERE afcatalogid = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		int amount = 0;
		while (rs.next()) {
			amount ++;
		}
		return amount;
	}
	
	//（管理员业务）给出设备种类及数量 添加该种类相应数量个设备
	public static void addEquipment(int eCID, int amount) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT id FROM equipcatalog WHERE id = " + eCID;
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			sql = "SELECT MAX(id) FROM equipment";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				int eID = rs.getInt(1) + 1;
				for (; amount > 0; amount--) {
					sql = "INSERT INTO equipment VALUES(" + eID + ", "+ eCID + ", 0)";
					stmt.executeUpdate(sql);
					eID++;
				}
			}
		}
		else
			System.out.println("此种设备暂未注册！");	//调试用语句
	}
	
	//（管理员业务）给出设备种类及数量 减少该种类相应数量个设备
	public static void delEquipment(int eCID, int amount) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT id FROM equipment WHERE afcatalogid = " + eCID + " AND aflabid = 0";
		ResultSet rs = stmt.executeQuery(sql);
		int restAmount = 0;
		while (rs.next()) {
			restAmount ++;
		}
		if (restAmount >= amount) {
			rs = stmt.executeQuery(sql);
			Statement stmt2 = conn.createStatement();
			for (; amount > 0; amount --) {
				rs.next();
				sql = "DELETE FROM equipment WHERE id = " + rs.getInt(1);
				stmt2.executeUpdate(sql);
			}
		}
		else
			System.out.println("没有这么多空闲设备！");	//调试用语句
	}
	
	//（管理员/研究员业务）给出设备种类ID、实验室ID 返回该实验室拥有的该种类设备个数
	public static int equipDistribute(int eCID, int lID) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT id FROM equipment "
				+ "WHERE afcatalogid = " + eCID + " AND aflabid = " + lID;
		ResultSet rs = stmt.executeQuery(sql);
		int amount = 0;
		while (rs.next()) {
			amount ++;
		}
		return amount;
	}
	
	//（管理员业务）给出设备种类、数量、两实验室ID 移动前者实验室相应数量个该种类设备至后者
	public static void setEquipment(int eCID, int amount, int fromLID, int toLID) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT id FROM equipment "
				+ "WHERE afcatalogid = " + eCID + " AND aflabid = " + fromLID;
		ResultSet rs = stmt.executeQuery(sql);
		int restAmount = 0;
		while (rs.next()) {
			restAmount ++;
		}
		if (restAmount >= amount) {
			rs = stmt.executeQuery(sql);
			Statement stmt2 = conn.createStatement();
			for (; amount > 0; amount --) {
				rs.next();
				sql = "UPDATE equipment SET aflabid = " + toLID + " WHERE id = " + rs.getInt(1);
				stmt2.executeUpdate(sql);
			}
		}
	}
	
	//（管理员业务）给出名字及初始数量 注册一个新的设备目录 自动生成一个ID和空闲的相应数量
	public static void registerEquip(String eCName, int amount) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		int eCID = 0;
		String sql = "SELECT MAX(id) FROM equipcatalog";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			eCID = rs.getInt(1) + 1;
		}
		if  (eCID != 0) {
			sql = "INSERT INTO equipcatalog VALUES(" + eCID + ", '" + eCName + "')";
			stmt.executeUpdate(sql);
		}
		addEquipment(eCID, amount);
	}
	
	//（管理员业务）给出设备目录ID 若没有一台设备属于该目录 则注销该设备目录并返回真 有则返回假
	public static boolean logoutEquip(int eCID) throws Exception {
		initConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT id FROM equipment WHERE afCatalogID = " + eCID;
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.next()) {
			sql = "DELETE FROM equipcatalog WHERE id = " + eCID;
			stmt.executeUpdate(sql);
			return true;
		} else {
			return false;
		}
	}
}