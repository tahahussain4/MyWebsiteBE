package model;

public class RestAPIException extends Exception{
	int status;
	String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public RestAPIException(int i, String message) {
		super();
		this.status = i;
		this.message = message;
	}
}
