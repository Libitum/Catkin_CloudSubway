package catkin.cloudsubway.net;

public enum StateCode {
	OK(200),
	ACCEPTED(201),
	
	BAD_REQUEST(400),
	NOT_FOUND(404),
	
	
	SERVER_ERROR(500),
	TIME_OUT(504);
	
	private int code;
	private StateCode(int code){
		this.code = code;
	}
	@Override
	public String toString(){
		return String.valueOf(code);
	}
}
