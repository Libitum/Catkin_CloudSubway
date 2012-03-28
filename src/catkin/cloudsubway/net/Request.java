package catkin.cloudsubway.net;

import java.util.ArrayList;
import java.util.List;

import catkin.cloudsubway.exception.RequestException;

/**
 * @author Libitum<libitum@msn.com>
 * This class is for the data getting from input stream.
 *
 */
class Request extends DataStream {
	private String method;
	private String from;
	private String to;
	private List<String> files;
	
	public Request(){
		super();
		files = new ArrayList<String>();
	}
	
	@Override
	public void setHead(String head){
		String[] entry = head.split(":");
		if(entry[0].toUpperCase().equals("APPID"))
			appId = entry[1];
		else if(entry[0].trim().toUpperCase().equals("METHOD"))
			this.method = entry[1].trim().toUpperCase();
		else if(entry[0].trim().toUpperCase().equals("KEY"))
			this.keys.add(entry[1].trim());
		else if(entry[0].trim().toUpperCase().equals("FILE"))
			this.files.add(entry[1].trim());
		else if(entry[0].trim().toUpperCase().equals("FROM"))
			this.from = entry[1].trim();
		else if(entry[0].trim().toUpperCase().equals("TO"))
			this.to = entry[1].trim();
	}
	
	@Override
	public String getHeader() {
		return null;
	}
	
	@Override
	public void check() throws RequestException {
		if(files.size() > 0){
			if(from == null || to == null){
				throw new RequestException("No FROM or TO with FILE!");
			}
		}
		if(method.equals("PUT") && keys.size() != body.size()){
			throw new RequestException("Size of keys and size of values are not equtaion!");
		}
	}
	
	public String getMethod(){
		return this.method;
	}
	
	public String getFrom(){
		return this.from;
	}
	
	public String getTo(){
		return this.to;
	}
}
