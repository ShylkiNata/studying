package win_logic;

import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;

import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Shape;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;


import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;


import win_model.ColorPanelData;
import win_model.ServerData;
import win_view.*;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {
	
    private Point startPoint, endPoint;  
    private Shape shape;
    
	private Path2D path;
    private Line2D line;
    private Rectangle2D rect;
    private Ellipse2D elli;
    private Polygon poly;
    
    
    public static Point startPoint_client, endPoint_client, startPoint_server, endPoint_server;
    public static Shape shape_client, shape_server;
    
	public static Color fillColor_client, color_client, fillColor_server, color_server;
	public static int mode_client, mode_server;
	public static float width_client, width_server;
    
    double t; int r,R;
	static int InvTextField=0;
    
    public static JPanel jp;

	static JPanel newjp; static BufferedImage img;
    static TextField tf = new TextField(); 
    
    public static ArrayList<Point[]> FigureOrder;
    
    public static ArrayList <Integer> ModeOrder = new ArrayList <Integer>();
    public static ArrayList <Color> ColorOrder = new ArrayList <Color>();
    public static ArrayList <Float> WidthOrder = new ArrayList <Float>();
    
    public static ArrayList <Color> ColorFillOrder = new ArrayList <Color>();
    
    public static ArrayList <Shape> ShapeOrder = new ArrayList <Shape>(); 
    
    public static ArrayList <String> StringOrder = new ArrayList <String>();  
    public static ArrayList <Font> FontOrder = new ArrayList <Font>();
   //public static ArrayList <String> StringOrder = new ArrayList <String>();  
    public static ArrayList <Color> FontColorOrder = new ArrayList <Color>();
    
    int ModeOrderSocket=-100; Color ColorOrderSocket, ColorFillOrderSocket;
    
    static int i=0, f=0, topf=-1,x=0,y=0;
	int shape_i=0;
    
    public DrawPanel() {
    	setBackground(Color.white);
    	
        FigureOrder = new ArrayList<>(25);
        
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                AWTKeyStroke ak = AWTKeyStroke.getAWTKeyStrokeForEvent(e);
                
                if(topf!=-1 && ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK))){             
              	  Color b = JColorChooser.showDialog(null, "JColorChooser", ColorPanelData.textColor);
			      ColorFillOrder.set(topf, b);
			      repaint();
              }
            }
        });
        
        MouseAdapter ma = new MouseAdapter() {
        	
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(new ToolsLogic().get_objColor()==1) { //picker
            		try{
            		   Robot robot=new Robot();
            		   Color color = robot.getPixelColor((int)MouseInfo.getPointerInfo().getLocation().getX(),(int)MouseInfo.getPointerInfo().getLocation().getY());//new Color(img.getRGB(e.getPoint().x,e.getPoint().y));
            			
            		   switch(ColorPanelData.colorMode){
            		     case 1: ColorPanelData.lineColor=color; break;
            		     case 2: ColorPanelData.fillColor=color; break;
            		     case 3: ColorPanelData.textColor=color; break;
                       }
            		}
            		catch(AWTException ae) { ae.printStackTrace(); }
            	}
            }

            @Override
            public void mousePressed(MouseEvent e) {				  
            	if(InvTextField==1) InvTextField=0; 
            	requestFocusInWindow();
            	
                if (SwingUtilities.isRightMouseButton(e)) {
                	InvTextField=1;
                	jp.setLayout(null);
                	TextField tf = new TextField();
                    tf.setLocation(e.getPoint());
                    
                    jp.add(tf);
                    
                    tf.requestFocusInWindow(); 
                }
            	else if(new ToolsLogic().get_drawMode()>=0){
		            	if(new ToolsLogic().get_objColor()!=1) { // если выбран не пикер
			                startPoint = e.getPoint();
			                		                    
				            if(ServerData.GotAServerFLAG==1) { 
				                try {
				                	ServerLogic.outS.writeObject(ToolsLogic.drawMode);	ServerLogic.outS.flush();
				                	ServerLogic.outS.writeObject(e.getPoint());	ServerLogic.outS.flush();
				                	ServerLogic.outS.writeObject(ToolsLogic.lineWidth);	ServerLogic.outS.flush();
				                	ServerLogic.outS.writeObject(ColorPanelData.lineColor);	ServerLogic.outS.flush();
				                	ServerLogic.outS.writeObject(ColorPanelData.fillColor);	ServerLogic.outS.flush();
				                	} 
				                catch (IOException e1) { e1.printStackTrace(); } 
				            }
				            if(ServerData.GotAClientFLAG==1) { 
				                try {
				                	ClientLogic.outC.writeObject(ToolsLogic.drawMode);	ClientLogic.outC.flush();
				                	ClientLogic.outC.writeObject(e.getPoint());	ClientLogic.outC.flush();
				                	ClientLogic.outC.writeObject(ToolsLogic.lineWidth);	ClientLogic.outC.flush();
				                	ClientLogic.outC.writeObject(ColorPanelData.lineColor);	ClientLogic.outC.flush();
				                	ClientLogic.outC.writeObject(ColorPanelData.fillColor);	ClientLogic.outC.flush();
				                	} 
				                catch (IOException e1) { e1.printStackTrace(); } 
				            }
			                
			                if(ToolsLogic.drawMode<3 && ToolsLogic.drawMode>=0){ // если режим рисования - ластик, карандаш или ксить
			                	path = new Path2D.Double();
			                    shape = path;
			                }
		            	}
			     }
            	
	            else {
	                x = e.getX(); y = e.getY();
	            	for(int find=0; find<ShapeOrder.size(); find++) { 
	            		if(ShapeOrder.get(find).getBounds2D().contains(e.getPoint())) topf=find;
	            	}
            }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            	topf=-1; x=0; y=0;
	            if (InvTextField==0 && new ToolsLogic().get_drawMode()>=0){
	                	
	                if(new ToolsLogic().get_objColor()!=1) {
	    	           endPoint = e.getPoint();
	    	                
	   		           if(ServerData.GotAServerFLAG==1) { 
			                try { ServerLogic.outS.writeObject(-1000); ServerLogic.outS.flush();
			                	  ServerLogic.outS.writeObject(e.getPoint()); ServerLogic.outS.flush(); } 
			                catch (IOException e1) { e1.printStackTrace(); } 
			           }
	   		           if(ServerData.GotAClientFLAG==1) { 
			                try { ClientLogic.outC.writeObject(-1000); ClientLogic.outC.flush();
			                	  ClientLogic.outC.writeObject(e.getPoint()); ClientLogic.outC.flush(); } 
			                catch (IOException e1) { e1.printStackTrace(); } 
			           }
	    	           
	    	           if( new ToolsLogic().get_drawMode()<3  ){
	    	               path.moveTo(startPoint.x, startPoint.y);
	    	               path.lineTo(endPoint.x, endPoint.y);
	    	               shape = path; 
	    	           }
	    	           
		               else getShape(startPoint, endPoint);
	    	                
							                Point[] points = new Point[]{startPoint, endPoint};
							                FigureOrder.add(points); 
							                
								            startPoint = null;
								            endPoint = null;
							                
											if(ToolsLogic.drawMode==0) WidthOrder.add((float) 1);
											else WidthOrder.add(ToolsLogic.lineWidth);
											if(ToolsLogic.drawMode==2) ColorOrder.add(Color.white);
											else ColorOrder.add(ColorPanelData.lineColor);
											
								            ShapeOrder.add(shape);	
								            ModeOrder.add(new ToolsLogic().get_drawMode());               
							                ColorFillOrder.add(ColorPanelData.fillColor); // цвета закрашенного
							                
							                ToolsLogic.createAlternateArray=1;
							                
							                repaint(); }   
            	}
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            	
            if(new ToolsLogic().get_objColor()!=1 && InvTextField==0 && new ToolsLogic().get_drawMode()>=0) {	
                endPoint = e.getPoint(); 
                
	            if(ServerData.GotAServerFLAG==1) { 
	                try { ServerLogic.outS.writeObject(1000);  ServerLogic.outS.flush();
	                	  ServerLogic.outS.writeObject(e.getPoint());  ServerLogic.outS.flush(); } 
	                catch (IOException e1) { e1.printStackTrace(); } 
	            }
	            if(ServerData.GotAClientFLAG==1) { 
	                try { ClientLogic.outC.writeObject(1000);  ClientLogic.outC.flush();
	                	  ClientLogic.outC.writeObject(e.getPoint());  ClientLogic.outC.flush(); } 
	                catch (IOException e1) { e1.printStackTrace(); } 
	            }
                
                if(new ToolsLogic().get_drawMode()<3){
                    path.moveTo(startPoint.x, startPoint.y);
                    path.lineTo(endPoint.x, endPoint.y);
                    shape = path;
                    startPoint = endPoint;
                }
                
	            else getShape(startPoint, endPoint);
                
                repaint();
            }
            else if(ToolsLogic.drawMode<0){ findFigure(e.getPoint()); }
           }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
     }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // сглаживание
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // сглаживание
        //((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        shape_i=0; f=0; i=0;
        
        for (shape_i=0; shape_i<ShapeOrder.size(); shape_i++) {

        	g2.setColor(ColorFillOrder.get(shape_i));
        	g2.setStroke(new BasicStroke(WidthOrder.get(shape_i)));
        	g.setColor(ColorFillOrder.get(shape_i));
			((Graphics2D) g).setStroke(new BasicStroke(WidthOrder.get(shape_i)));
						
			g2.fill(ShapeOrder.get(shape_i));
			g2.setColor(ColorOrder.get(shape_i));
			g2.draw(ShapeOrder.get(shape_i));
			
        }
               
        if (startPoint != null && endPoint != null) { 	
            g2.setColor(ColorPanelData.fillColor);
            g.setColor(ColorPanelData.fillColor);
        	g2.setStroke(new BasicStroke(ToolsLogic.lineWidth));
			((Graphics2D) g).setStroke(new BasicStroke(ToolsLogic.lineWidth));
			
			g2.fill(shape);
			g2.setColor(ColorPanelData.lineColor);
			if(ToolsLogic.drawMode==0) g2.setStroke(new BasicStroke(1));
			if(ToolsLogic.drawMode==2) g2.setColor(Color.white);
			g2.draw(shape);		
        }
        
        if (startPoint_client != null && endPoint_client != null) { 	
            g2.setColor(fillColor_client);
            g.setColor(fillColor_client);
        	g2.setStroke(new BasicStroke(width_client));
			((Graphics2D) g).setStroke(new BasicStroke(width_client));
			
			g2.fill(shape_client);
			g2.setColor(color_client);
			if(mode_client==0) g2.setStroke(new BasicStroke(1));
			if(mode_client==2) g2.setColor(Color.white);
			g2.draw(shape_client);		
        }
        if (startPoint_server != null && endPoint_server != null) { 	
            g2.setColor(fillColor_server);
            g.setColor(fillColor_server);
        	g2.setStroke(new BasicStroke(width_server));
			((Graphics2D) g).setStroke(new BasicStroke(width_server));
			
			g2.fill(shape_server);
			g2.setColor(color_server);
			if(mode_server==0) g2.setStroke(new BasicStroke(1));
			if(mode_server==2) g2.setColor(Color.white);
			g2.draw(shape_server);		
        }
        
        jp=this;
        //g2.dispose(); g.dispose();
    }
     
    void getShape(Point startPoint, Point endPoint){
    	int x0=0, y0=0, x1=0, y1=0, t, r, R; 
    	
    	switch(new ToolsLogic().get_drawMode()) {
       	case 10: line = new Line2D.Double(startPoint.x, startPoint.y, endPoint.x, endPoint.y); shape=line; break;
       	case 11: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=Math.abs(endPoint.y-startPoint.y); }
       			 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
    				 
       			 elli = new Ellipse2D.Double(x0, y0, x1, y1); shape=elli; 
       			 break;
       	case 12: if((endPoint.x>startPoint.x) && (endPoint.y>startPoint.y)) { x0=startPoint.x; y0=startPoint.y; x1=endPoint.x-startPoint.x; y1=endPoint.y-startPoint.y; }
				 else if((endPoint.x<startPoint.x) && (endPoint.y<startPoint.y)) { x0=endPoint.x; y0=endPoint.y; x1=startPoint.x-endPoint.x; y1=startPoint.y-endPoint.y; }
    			 else if((endPoint.x<startPoint.x) && (endPoint.y>startPoint.y)) { x0=endPoint.x; y0=startPoint.y; x1=startPoint.x-endPoint.x; y1=endPoint.y-startPoint.y; }
    			 else { x0=startPoint.x; y0=endPoint.y; x1=endPoint.x-startPoint.x; y1=startPoint.y-endPoint.y; }
				 
				 rect = new Rectangle2D.Double(x0, y0, x1, y1); shape=rect; 
				 break;
       	case 13: poly = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y}, 3); shape=poly; 
       	         break;
       	case 14: poly = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, startPoint.x}, new int [] {endPoint.y, startPoint.y, endPoint.y, 2*endPoint.y-startPoint.y}, 4); shape=poly; 
       			 break;
       	case 15: 				
               	 t = (int) Math.sqrt(Math.pow(endPoint.x-startPoint.x, 2)+Math.pow(endPoint.y-startPoint.y, 2));
				 r = (int) ( ( (Math.sqrt(5)*( Math.sqrt(5+2*Math.sqrt(5)) ))/10) *t );	 
				 R = (int) (Math.sqrt(5)-1)*r;
				 
				 if(endPoint.y>startPoint.y) poly = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y+r+R), (int) (startPoint.y+r+R)}, 5); 
				 else poly = new Polygon(new int [] {2*startPoint.x-endPoint.x, startPoint.x, endPoint.x, (int) ((startPoint.x+endPoint.x)/2), (int) ((startPoint.x+2*startPoint.x-endPoint.x)/2)}, new int [] {endPoint.y, startPoint.y, endPoint.y, (int) (startPoint.y-r-R), (int) (startPoint.y-r-R)}, 5);
				 shape=poly; break;
        }
    }
    
        
    /* Очистка всех массивов и создание пустого холста */    
    static void clearArea(){
    	FigureOrder.clear();
        ModeOrder.clear();
        ColorOrder.clear();
        WidthOrder.clear();
        ColorFillOrder.clear();
        ShapeOrder.clear(); 
        StringOrder.clear();  
        FontOrder.clear();
        i=0; 
        jp.removeAll();
        jp.validate();
    }
    
   
    // Делаем изображение из панели рисование
    public static BufferedImage getImg (){  	
		img = new BufferedImage(jp.getWidth(), jp.getHeight(), BufferedImage.TYPE_INT_RGB);
		jp.paint(img.getGraphics());
    	return img;
    }
    
    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    
  
	void findFigure(Point p){
    	int dx=p.x-x, dy=p.y-y;
    	int x0, y0, w, h;

    	switch(ModeOrder.get(topf)) {
    	case 10: x0= FigureOrder.get(topf)[0].x;
	     		 y0= FigureOrder.get(topf)[0].y;
	     		 w = FigureOrder.get(topf)[1].x;
	     		 h = FigureOrder.get(topf)[1].y;
			   
			    FigureOrder.set(topf,new Point []{new Point(x0+dx,y0+dy), new Point(w+dx,h+dy)});
			    ShapeOrder.set(topf,new Line2D.Float(x0+dx,y0+dy,w+dx,h+dy));
			    break;
    	case 11: x0= (int)ShapeOrder.get(topf).getBounds().getX();
			     y0=(int)ShapeOrder.get(topf).getBounds().getY();
			     w= (int)ShapeOrder.get(topf).getBounds().getWidth();
			     h=(int)ShapeOrder.get(topf).getBounds().getHeight();

				FigureOrder.set(topf,new Point []{new Point(FigureOrder.get(topf)[0].x+dx,FigureOrder.get(topf)[0].y+dy), new Point(FigureOrder.get(topf)[1].x+dx,FigureOrder.get(topf)[1].y+dy)});
			    ShapeOrder.set(topf,new Ellipse2D.Float(x0+dx,y0+dy,w,h));
			    break;
    	case 12: x0= (int)ShapeOrder.get(topf).getBounds().getX();
		    	 y0=(int)ShapeOrder.get(topf).getBounds().getY();
		    	 w= (int)ShapeOrder.get(topf).getBounds().getWidth();
		    	 h=(int)ShapeOrder.get(topf).getBounds().getHeight();
		    	
		    	FigureOrder.set(topf,new Point []{new Point(FigureOrder.get(topf)[0].x+dx,FigureOrder.get(topf)[0].y+dy), new Point(FigureOrder.get(topf)[1].x+dx,FigureOrder.get(topf)[1].y+dy)});
		    	ShapeOrder.set(topf,new Rectangle2D.Float(x0+dx,y0+dy,w,h));
		    	break;
    	}
    	x+=dx; y+=dy;
    	repaint();    	
    }
}



