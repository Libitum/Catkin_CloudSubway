package catkin.cloudsubway.exception;

import catkin.cloudsubway.net.StateCode;

public class ResponseException extends ServerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseException(String msg){
		super(msg);
		code = StateCode.SERVER_ERROR;
	}
	
}
