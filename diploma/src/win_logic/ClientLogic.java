package win_logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import win_model.*;

public class ClientLogic implements Runnable {
	String ip; int port;
	static ObjectOutputStream outC; static ObjectInputStream inC;
	
	private static Path2D path_client;
	private static Rectangle2D rect_client;
	private static Ellipse2D elli_client;
	private static Polygon poly_client;
	
	public ClientLogic(String ip, int port){
		this.ip=ip;
		this.port=port;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public void run() {
		
        try {
            InetAddress ipA = InetAddress.getByName(ip); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + ip + " and port " + port + "?");
            Socket socket = new Socket(ipA, port); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");
            
            ServerData.GotAClientFLAG=1;

	         outC = new ObjectOutputStream(socket.getOutputStream());
	         inC = new ObjectInputStream(socket.getInputStream());

        	 DrawPanel.clearArea();

        	 DrawPanel.ModeOrder= (ArrayList<Integer>) inC.readObject(); 
        	 DrawPanel.FigureOrder=(ArrayList<Point[]>) inC.readObject(); 
        	 DrawPanel.ColorOrder=(ArrayList<Color>) inC.readObject(); 
        	 DrawPanel.WidthOrder=(ArrayList<Float>) inC.readObject(); 
        	 DrawPanel.ColorFillOrder=(ArrayList<Color>) inC.readObject(); 
        	 DrawPanel.ShapeOrder=(ArrayList<Shape>) inC.readObject(); 
        	 DrawPanel.StringOrder=(ArrayList<String>) inC.readObject(); 
        	 DrawPanel.FontOrder=(ArrayList<Font>) inC.readObject();   	 
        	 
        	 DrawPanel.jp.repaint();
        	 
	         while(true){
	        	 getDataFromServer();
	         }        	 
	            
              
        } catch (Exception x) {
            x.printStackTrace();
        }	
	}
	
	void getDataFromServer(){
        try {  DrawPanel.mode_client=(int)inC.readObject();
 	   		   DrawPanel.startPoint_client=(Point) inC.readObject();
        	   DrawPanel.width_client=(float)inC.readObject();	
        	   DrawPanel.color_client=(Color)inC.readObject();	
        	   DrawPanel.fillColor_client=(Color)inC.readObject();	
 	   		   
        	   if(DrawPanel.mode_client<3 && DrawPanel.mode_client>=0) { 
        		   path_client = new Path2D.Double(); DrawPanel.shape_client = path_client; }
        	           	           	   
        	   if((int)inC.readObject()==-1000) getDataFromServerRealesed(); // проверить, точка поставлена или линию проводят	
        	   else getDataFromServerDragged(); //для кривых линий 
			} 
        catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
        catch (IOException e1) { e1.printStackTrace(); }     
	}
	
	void getDataFromServerDragged(){
		try{
			DrawPanel.endPoint_client=(Point) inC.readObject(); // получить конечную точку
		    
			if(DrawPanel.mode_client<3 && DrawPanel.mode_client>=0) {
	              path_client.moveTo(DrawPanel.startPoint_client.x, DrawPanel.startPoint_client.y);
	              path_client.lineTo(DrawPanel.endPoint_client.x, DrawPanel.endPoint_client.y);
	              DrawPanel.shape_client = path_client;
	              DrawPanel.startPoint_client=DrawPanel.endPoint_client; 
			}
			else  getShapeClient(DrawPanel.startPoint_client, DrawPanel.endPoint_client);
	              
				  DrawPanel.jp.repaint();
	              
	              if((int) inC.readObject()==1000) getDataFromServerDragged();
	              else getDataFromServerRealesed();
		 	   	
		    }
		 catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
		 catch (IOException e1) { e1.printStackTrace(); }
	}
	void getDataFromServerRealesed(){
		try{  DrawPanel.endPoint_client=(Point) inC.readObject();
		
			  if(DrawPanel.mode_client<3 && DrawPanel.mode_client>=0) {
		              path_client.moveTo(DrawPanel.startPoint_client.x, DrawPanel.startPoint_client.y);
		              path_client.lineTo(DrawPanel.endPoint_client.x, DrawPanel.endPoint_client.y);
		              DrawPanel.shape_client = path_client;
			  }
			  else    getShapeClient(DrawPanel.startPoint_client, DrawPanel.endPoint_client);
		              
			  		  DrawPanel.FigureOrder.add(new Point[]{DrawPanel.startPoint_client,DrawPanel.endPoint_client}); 
		              
		              DrawPanel.startPoint_client=DrawPanel.endPoint_client=null;
		              
					  if(DrawPanel.mode_client==0) DrawPanel.WidthOrder.add((float) 1);
					  else DrawPanel.WidthOrder.add(DrawPanel.width_client);
					  if(DrawPanel.mode_client==2) DrawPanel.ColorOrder.add(Color.white);
					  else DrawPanel.ColorOrder.add(DrawPanel.color_client);
						
					  DrawPanel.ShapeOrder.add(DrawPanel.shape_client);	
					  DrawPanel.ModeOrder.add(DrawPanel.mode_client);               
					  DrawPanel.ColorFillOrder.add(DrawPanel.fillColor_client); // цвета закрашенного
					  
		              DrawPanel.jp.repaint();  
		}
			 catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
			 catch (IOException e1) { e1.printStackTrace(); }
	}
	
	void getShapeClient(Point startPoint, Point endPoint){
    	int x0=0, y0=0, x1=0, y1=0, t, r, R; 
    	
    	switch(DrawPanel.mode_client) {
       	case 10: DrawPanel.shape_client = new Line2D.Double(startPoint.x, startPoint.y, endPoint.x, endPoint.y); break;
       	case 11: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=Math.abs(endPoint.y-startPoint.y); }
       			 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
    				 
       			 elli_client = new Ellipse2D.Double(x0, y0, x1, y1); DrawPanel.shape_client=elli_client; 
       			 break;
       	case 12: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=endPoint.y-startPoint.y; }
				 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
				 
				 rect_client = new Rectangle2D.Double(x0, y0, x1, y1); DrawPanel.shape_client=rect_client; 
				 break;
       	case 13: poly_client = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y}, 3); DrawPanel.shape_client=poly_client; 
       	         break;
       	case 14: poly_client = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, startPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y, 2*endPoint.y-startPoint.y}, 4); DrawPanel.shape_client=poly_client; 
       			 break;
       	case 15: 				
               	 t = (int) Math.sqrt(Math.pow(endPoint.x-startPoint.x, 2)+Math.pow(endPoint.y-startPoint.y, 2));
				 r = (int) ( ( (Math.sqrt(5)*( Math.sqrt(5+2*Math.sqrt(5)) ))/10) *t );	 
				 R = (int) (Math.sqrt(5)-1)*r;
				 
				 if(endPoint.y>startPoint.y) poly_client = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y+r+R), (int) (startPoint.y+r+R)}, 5); 
				 else poly_client = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y-r-R), (int) (startPoint.y-r-R)}, 5);
				 DrawPanel.shape_client=poly_client; break;
        }
	}
}
