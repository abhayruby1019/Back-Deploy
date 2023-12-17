MudraAPIModel.javapackage com.example.fn;

public class MudraAPIModel {

    private String password;
	private String userId;
	private String pan;

	/*
	 * MudraAPIModel(String password, String userId, String pan) { this.password =
	 * password; this.userId = userId; this.pan = pan; }
	 */
	
	public String getPassword() {
		return password;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getPan() {
		return pan;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setPan(String pan) {
		this.pan = pan;
	}
	
}
