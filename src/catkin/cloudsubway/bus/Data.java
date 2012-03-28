package catkin.cloudsubway.bus;

public class Data {
	private final Object data;
	private short copyNum = 0;

	public Data(Object data){
		this.data = data;
	}
	
	public Object getData(){
		return this.data;
	}
	
	public short getCopyNum(){
		return this.copyNum;
	}
	
	public void setCopyNum(int n){
		this.copyNum = (short)n;
	}
	
	public void reduceCopyNum(){
		copyNum--;
	}
}