package catkin.cloudsubway.exception;

import catkin.cloudsubway.net.StateCode;

public class TimeoutException extends ServerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TimeoutException(String msg, long time){
		super(msg + "\tTotal Time: " + time);
		code = StateCode.TIME_OUT;
	}
}
