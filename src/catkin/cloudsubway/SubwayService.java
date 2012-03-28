/**
 * 
 */
package catkin.cloudsubway;

import catkin.cloudsubway.net.BusServer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Libitum<libitum@msn.com>
 *
 */
public class SubwayService extends Service {
	private static final String TAG = "catkin.cloudsubway.SubwayService";
	private static final int PORT = 9527;
	BusServer busServer = null;

	/*
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.i(TAG, "SubwayService Creating...");
		busServer = BusServer.getInstance(PORT);
		busServer.start();
	}
	
	@Override
	public void onDestroy(){
		Log.i(TAG, "SubwayService destory...");
		busServer = BusServer.getInstance(PORT);
		busServer.stopServer();
	}
}
