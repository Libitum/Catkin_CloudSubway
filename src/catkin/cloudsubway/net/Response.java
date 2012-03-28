package catkin.cloudsubway.net;

import catkin.cloudsubway.exception.ResponseException;

/**
 * @author Libitum<libitum@msn.com>
 * This class is for data writing to the output stream.
 *
 */
class Response extends DataStream {
	private StateCode code;

	@Override
	public void setHead(String head) {
		
	}
	
	@Override
	public String getHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("CODE:"); sb.append(code); sb.append('\n');
		for(String key : keys){
			sb.append("KEY:"); sb.append(key); sb.append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}
	
	@Override
	public void check() throws ResponseException{
		if(code == null){
			throw new ResponseException("No Response Code Finded!");
		}
		if(keys.size() != body.size()){
			throw new ResponseException("Size of kyes and size of body are not equation!");
		}
	}
	
	public void setCode(StateCode code){
		this.code = code;
	}
	
	public StateCode getCode(){
		return this.code;
	}
}
