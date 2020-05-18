package vo;

public class Manager {
	private int managerID;
	private String managerPassword;
	private String managerName;
	public int getManagerID() {
		return managerID;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Manager() {
		;
	}
	public Manager(int mID, String mPassword, String mName) {
		this.managerID = mID;
		this.managerPassword = mPassword;
		this.managerName = mName;
	}
}
