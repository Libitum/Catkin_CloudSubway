package catkin.cloudsubway.bus;

import java.util.Hashtable;

import catkin.cloudsubway.exception.TimeoutException;

/**
 * @author Libitum<libitum@msn.com>
 *
 */
public class Bus extends Hashtable<String, Data> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final long SLEEPTIME = 500l;

	public Data get(String key) throws InterruptedException{
		return get(key, 10);
	}
	
	/**
	 * @param key key of the Data.
	 * @param maxTryTimes
	 * @return 
	 * @throws InterruptedException
	 * @throws TimeoutException
	 * Get Data from bus. if copyNum of the data reduced to 0, then remove it.
	 */
	public Data get(String key, int maxTryTimes) throws InterruptedException, TimeoutException{
		int time = 0;
		while(!super.containsKey(key)){
			Thread.sleep(SLEEPTIME);
			if(++time > maxTryTimes){
				throw new TimeoutException("Timeout in Bus.get(key)", time*SLEEPTIME);
			}
		}
		Data data = super.get(key);
		data.reduceCopyNum();
		if(data.getCopyNum() <= 0){
			super.remove(key);
		}
		
		return data;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
	 * Set the copyNum of data to 2 by default.
	 */
	@Override
	public Data put(String key, Data value){
		value.setCopyNum(2);
		return super.put(key, value);
	}
}
