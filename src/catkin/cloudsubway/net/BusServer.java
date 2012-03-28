package catkin.cloudsubway.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import catkin.cloudsubway.bus.DataBuses;

import android.util.Log;

public class BusServer extends Thread {
	private static final String TAG = "catkin.cloudsubway.BusServer";
	private static BusServer instance = null;
	
	private final int PORT;
	private ServerSocket ss = null;
	private boolean stop = false;
	
	private BusServer(int port){
		super("BusServer");
		PORT = port;
	}
	
	public static synchronized BusServer getInstance(int port){
		if(instance == null){
			instance = new BusServer(port);
		}
		return instance;
	}
	
	@Override
	public void run(){
		@SuppressWarnings("unused")
		DataBuses dataBuses = DataBuses.getInstance(); //Stop DataBuses instance from recycling by GC.
		try {
			ss = new ServerSocket(PORT);
			Log.i(TAG, "BusServer start at port " + PORT);
			int num = 0;
			while(!stop){
				Socket s = ss.accept();
				SocketHandler socket = new SocketHandler(s, num++);
				socket.start();
			}
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} finally{
			try {
				ss.close();
				Log.i(TAG, "BusServer stopped");
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}
		}
	}
	
	public void stopServer(){
		this.stop = true;
	}
}
