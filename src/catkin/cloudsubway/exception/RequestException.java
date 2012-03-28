package catkin.cloudsubway.exception;

import catkin.cloudsubway.net.StateCode;

public class RequestException extends ServerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RequestException(String msg){
		super(msg);
		code = StateCode.BAD_REQUEST;
	}
}
