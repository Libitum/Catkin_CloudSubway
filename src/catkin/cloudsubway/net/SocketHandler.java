package catkin.cloudsubway.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import catkin.cloudsubway.bus.Bus;
import catkin.cloudsubway.bus.Data;
import catkin.cloudsubway.bus.DataBuses;
import catkin.cloudsubway.exception.RequestException;
import catkin.cloudsubway.exception.ResponseException;
import catkin.cloudsubway.exception.ServerException;
import catkin.cloudsubway.exception.TimeoutException;

import android.util.Log;

/**
 * @author Libitum<libitum@msn.com>
 * This class is a Thread class which runs a server for CloudSubway clients.
 *
 */
class SocketHandler extends Thread {
	private final String TAG = "catkin.cloudsubway.server.SocketHandler";
	
	private Socket s = null;
	
	public SocketHandler(Socket s, int num){
		super("SocketHandler"+num);
		this.s = s;
	}
	
	public void run(){	
		try{
			Log.i(TAG, "New connection established");
			
			ObjectInputStream input = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
			
			try{
				Request request = getRequest(input);
				Response response = null;
				
				Log.d(TAG, "Response received. AppId:" + request.getAppId());
				
				String method = request.getMethod();
				if(method.equals("PUT")){
					response = putData(request);
				}
				else if(method.equals("GET")){
					response = getData(request);
				}
			
				output.writeBytes(response.getHeader());
				output.flush();
				for(Object obj : response.getBody()){
					output.writeObject(obj);
					output.flush();
				}
			} catch(ServerException e){
				output.writeBytes(e.getCode());
				output.writeByte('\n');
				output.flush();
				output.writeObject(e.getMessage());
				Log.e(TAG, e.getMessage());
			} catch(ClassNotFoundException e){
				output.writeBytes("CODE:" + StateCode.NOT_FOUND + '\n');
				output.writeByte('\n');
				output.flush();
				output.writeObject(e.getMessage());
				Log.e(TAG, e.toString());
			} catch (InterruptedException e) {
				output.writeBytes("CODE:" + StateCode.SERVER_ERROR + '\n');
				output.writeByte('\n');
				output.flush();
				output.writeObject(e.getMessage());
				Log.e(TAG, e.toString());
			} finally{
				output.flush();
				input.close();
				output.close();
			}
		} catch (IOException e){
			Log.e(TAG, e.toString());
		} finally{
			try {
				s.close();
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}
		}
	}
	
	/**
	 * @param request put-data operation according to request
	 * @return response of put-data operation
	 */
	private Response putData(Request request) throws ResponseException{
		Log.i(TAG, "Method is putData");
		Response response = new Response();
		Bus bus = DataBuses.getInstance().getBus(request.getAppId());
		List<Object> values = request.getBody();
		
		int i = 0;
		for(String key : request.getKeys()){
			if(!bus.contains(key)){
				bus.put(key, new Data(values.get(i)));
			}
			else{
				//TODO put重复数据的策略
			}
			i++;
		}
		response.setCode(StateCode.ACCEPTED);
		response.check();
		return response;
	}
	
	/**
	 * @param request get-data operation according to request
	 * @return response of get-data operation
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	private Response getData(Request request) throws InterruptedException, TimeoutException, ResponseException{
		Log.i(TAG, "Method is getData");
		Bus bus = DataBuses.getInstance().getBus(request.getAppId());
		Response response = new Response();
		
		for(String key:request.getKeys()){
			response.addKey(key);
			Data data = bus.get(key);
			response.addBody(data.getData());
		}
		response.setCode(StateCode.OK);
		return response;
	}
	
	/**
	 * @param input ObjectInputStream which should be read.
	 * @return request of the client.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Request getRequest(ObjectInputStream input) throws IOException, ClassNotFoundException, RequestException{
		Request request = new Request();
		
		String head = input.readLine();
		while(head.length() != 0){
			Log.i(TAG, head);
			request.setHead(head);
			head = input.readLine();
		}
		if(request.getMethod().toUpperCase().equals("PUT")){
			for(int i=0; i<request.getKeyLength(); i++){
				request.addBody(input.readObject());
			}
		}
		request.check();
		return request;
	}
	
}