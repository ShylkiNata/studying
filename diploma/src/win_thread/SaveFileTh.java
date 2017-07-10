package win_thread;

import java.util.ArrayList;

public class SaveFileTh implements Runnable {
	private String result="";
	private ArrayList al;
	
	@Override
	public void run() { for(int it=0; it<al.size(); it++) result+=al.get(it)+" "; }
	
	public SaveFileTh(ArrayList al){ this.al=al; }
	
	public String getResult(){ return result; }
}