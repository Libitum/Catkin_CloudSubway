package catkin.cloudsubway.exception;

import catkin.cloudsubway.net.StateCode;

public abstract class ServerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected StateCode code;
	
	public ServerException(String msg){
		super(msg);
	}
	
	public String getCode(){
		StringBuilder sb = new StringBuilder();
		sb.append("CODE:");
		sb.append(code);
		sb.append('\n');
		return sb.toString();
	}
}
