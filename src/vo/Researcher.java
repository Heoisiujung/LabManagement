package vo;

public class Researcher {
	private int researcherID;
	private String researcherPassword;
	private String researcherName;
	public int getResearcherID() {
		return researcherID;
	}
	public String getResearcherPassword() {
		return researcherPassword;
	}
	public void setResearcherPassword(String researcherPassword) {
		this.researcherPassword = researcherPassword;
	}
	public String getResearcherName() {
		return researcherName;
	}
	public void setResearcherName(String researcherName) {
		this.researcherName = researcherName;
	}
	public Researcher() {
		;
	}
	public Researcher(int rID, String rPassword, String rName) {
		this.researcherID = rID;
		this.researcherPassword = rPassword;
		this.researcherName = rName;
	}
}
