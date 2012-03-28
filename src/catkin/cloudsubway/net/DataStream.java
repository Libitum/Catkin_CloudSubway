package catkin.cloudsubway.net;

import java.util.ArrayList;
import java.util.List;

/**
 * @author libitum<libitum@msn.com>
 * This is a abstract class for Request and Response.
 */
abstract class DataStream {
	protected String appId;
	protected List<String> keys;
	protected List<Object> body;
	
	public DataStream(){
		keys = new ArrayList<String>();
		body = new ArrayList<Object>();
	}
	
	abstract public void setHead(String head);
	abstract public void check();
	abstract public String getHeader();
	
	public void addBody(Object obj){
		this.body.add(obj);
	}
	
	public void addKey(String key){
		this.keys.add(key);
	}
	
	public int getKeyLength(){
		return this.keys.size();
	}
	
	public List<String> getKeys(){
		return this.keys;
	}
	
	public List<Object> getBody(){
		return this.body;
	}
	
	public String getAppId(){
		return this.appId;
	}
}