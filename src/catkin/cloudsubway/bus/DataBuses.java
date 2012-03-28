package catkin.cloudsubway.bus;

import java.util.HashMap;

/**
 * @author Libitum<libitum@msn.com>
 * DataBuses handle Buses of different APP
 *
 */
public class DataBuses {
	private static DataBuses instance = null;
	private HashMap<String, Bus> buses = null;
	
	private DataBuses(){
		buses = new HashMap<String, Bus>();
	}
	
	public static synchronized DataBuses getInstance(){
		if(instance == null){
			instance = new DataBuses();
		}
		return instance;
	}
	
	/**
	 * @param appId The application identity for getting bus.
	 * @return Bus of the application. If not existed, new it.
	 */
	public Bus getBus(String appId){
		if(!buses.containsKey(appId)){
			buses.put(appId, new Bus());
		}
		return buses.get(appId);
	}
}