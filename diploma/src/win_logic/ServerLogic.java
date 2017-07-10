package win_logic;

	import java.net.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.*;

import win_model.ServerData;
	public class ServerLogic implements Runnable {
	int port;
	
    static ObjectOutputStream outS;
    static ObjectInputStream inS;
    
	private static Path2D path_server;
		
	public ServerLogic(int port){
       this.port=port;
	}
	
	@Override
	public void run() {
		// случайный порт (может быть любое число от 1025 до 65535)
	     
	       try {
	         @SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
	         System.out.println("Waiting for a client...");

	         Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
	         System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");

	         ServerData.GotAServerFLAG=1;

	 // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту. 
	         outS = new ObjectOutputStream(socket.getOutputStream());
	         inS = new ObjectInputStream(socket.getInputStream());

		         outS.writeObject(DrawPanel.ModeOrder); outS.flush(); 
		         outS.writeObject(DrawPanel.FigureOrder); outS.flush();
		         outS.writeObject(DrawPanel.ColorOrder); outS.flush();
		         outS.writeObject(DrawPanel.WidthOrder); outS.flush();
		         outS.writeObject(DrawPanel.ColorFillOrder); outS.flush();
		         outS.writeObject(DrawPanel.ShapeOrder); outS.flush();
		         outS.writeObject(DrawPanel.StringOrder); outS.flush();
		         outS.writeObject(DrawPanel.FontOrder); outS.flush();
		         
		      while(true){
		        	 getDataFromClient();
		      }
		         
	      } catch(Exception x) { x.printStackTrace(); } 
	}
	

	void getDataFromClient(){
        try {  DrawPanel.mode_server=(int)inS.readObject();
 	   		   DrawPanel.startPoint_server=(Point) inS.readObject();
        	   DrawPanel.width_server=(float) inS.readObject();	
        	   DrawPanel.color_server=(Color) inS.readObject();	
        	   DrawPanel.fillColor_server=(Color) inS.readObject();	
 	   		   
        	   if(DrawPanel.mode_server<3 && DrawPanel.mode_server>=0) { 
        		   path_server = new Path2D.Double(); DrawPanel.shape_server = path_server; }
        	           	           	   
        	   if((int) inS.readObject()==-1000) getDataFromClientRealesed(); // проверить, точка поставлена или линию проводят	
        	   else getDataFromClientDragged(); //для кривых линий 
			} 
        catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
        catch (IOException e1) { e1.printStackTrace(); }     
	}
	
	void getDataFromClientDragged(){
		try{
			DrawPanel.endPoint_server=(Point) inS.readObject(); // получить конечную точку
		    
			if(DrawPanel.mode_server<3 && DrawPanel.mode_server>=0) {
	              path_server.moveTo(DrawPanel.startPoint_server.x, DrawPanel.startPoint_server.y);
	              path_server.lineTo(DrawPanel.endPoint_server.x, DrawPanel.endPoint_server.y);
	              DrawPanel.shape_server = path_server;
	              DrawPanel.startPoint_server=DrawPanel.endPoint_server; 
			}
			else  getShapeServer(DrawPanel.startPoint_server, DrawPanel.endPoint_server);
	              
				  DrawPanel.jp.repaint();
	              
	              if((int) inS.readObject()==1000) getDataFromClientDragged();
	              else getDataFromClientRealesed();
		 	   	
		    }
		 catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
		 catch (IOException e1) { e1.printStackTrace(); }
	}
	void getDataFromClientRealesed(){
		try{  DrawPanel.endPoint_server=(Point) inS.readObject();
		
			  if(DrawPanel.mode_server<3 && DrawPanel.mode_server>=0) {
		              path_server.moveTo(DrawPanel.startPoint_server.x, DrawPanel.startPoint_server.y);
		              path_server.lineTo(DrawPanel.endPoint_server.x, DrawPanel.endPoint_server.y);
		              DrawPanel.shape_server = path_server;
			  }
			  else    getShapeServer(DrawPanel.startPoint_server, DrawPanel.endPoint_server);
		              
			  		  DrawPanel.FigureOrder.add(new Point[]{DrawPanel.startPoint_server,DrawPanel.endPoint_server}); 
		              
		              DrawPanel.startPoint_server=DrawPanel.endPoint_server=null;
		              
					  if(DrawPanel.mode_server==0) DrawPanel.WidthOrder.add((float) 1);
					  else DrawPanel.WidthOrder.add(DrawPanel.width_server);
					  if(DrawPanel.mode_server==2) DrawPanel.ColorOrder.add(Color.white);
					  else DrawPanel.ColorOrder.add(DrawPanel.color_server);
						
					  DrawPanel.ShapeOrder.add(DrawPanel.shape_server);	
					  DrawPanel.ModeOrder.add(DrawPanel.mode_server);               
					  DrawPanel.ColorFillOrder.add(DrawPanel.fillColor_server); // цвета закрашенного
					  
		              DrawPanel.jp.repaint();  
		}
			 catch (ClassNotFoundException e1) { e1.printStackTrace();	} 
			 catch (IOException e1) { e1.printStackTrace(); }
	}
	
	void getShapeServer(Point startPoint, Point endPoint){
    	int x0=0, y0=0, x1=0, y1=0, t, r, R; 
    	
    	switch(DrawPanel.mode_server) {
       	case 10: DrawPanel.shape_server = new Line2D.Double(startPoint.x, startPoint.y, endPoint.x, endPoint.y); break;
       	case 11: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=Math.abs(endPoint.y-startPoint.y); }
       			 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
    				 
       			 DrawPanel.shape_server = new Ellipse2D.Double(x0, y0, x1, y1); 
       			 break;
       	case 12: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=endPoint.y-startPoint.y; }
				 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
				 
       			 DrawPanel.shape_server = new Rectangle2D.Double(x0, y0, x1, y1);
				 break;
       	case 13: DrawPanel.shape_server = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y}, 3);
       	         break;
       	case 14: DrawPanel.shape_server = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, startPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y, 2*endPoint.y-startPoint.y}, 4);
       			 break;
       	case 15: 				
               	 t = (int) Math.sqrt(Math.pow(endPoint.x-startPoint.x, 2)+Math.pow(endPoint.y-startPoint.y, 2));
				 r = (int) ( ( (Math.sqrt(5)*( Math.sqrt(5+2*Math.sqrt(5)) ))/10) *t );	 
				 R = (int) (Math.sqrt(5)-1)*r;
				 
				 if(endPoint.y>startPoint.y) DrawPanel.shape_server = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y+r+R), (int) (startPoint.y+r+R)}, 5); 
				 else DrawPanel.shape_server = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y-r-R), (int) (startPoint.y-r-R)}, 5);
				 break;
        }
	}
}