package win_logic;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import win_view.*;

public class ToolsLogic extends MainMenuElements {
	
	String [] fgrf={"f_linef.png","f_circlef.png","f_rectanglef.png","f_trianglef.png","f_rhombusf.png","f_pentagonf.png"};

	public static int changeTextColor=0;
	
	/* учка массивов дл€ сохранени€ послед-ти элементов до нажати€ вперЄд/назад кнопок*/
    private static List<Point[]> FigureOrderTL;
    private static ArrayList <Integer> ModeOrderTL;
    private static ArrayList <Color> ColorOrderTL;
    private static ArrayList <Float> WidthOrderTL;
    private static ArrayList <Color> ColorFillOrderTL;
    private static ArrayList <Shape> ShapeOrderTL;  
	
	public static int drawMode=0, objColor=0, createAlternateArray=0;
	public static float lineWidth=1;
	public static Font textAreaFont=new Font("Trebuchet MS", Font.PLAIN, 12);
	
	public int get_drawMode(){  
	  return drawMode;
	} 
	public int get_objColor(){  
		  return objColor;
	} 
	public void set_objColor(int n){  
		  objColor=n;
	} 
	public float get_lineWidth(){  
		  return lineWidth;
	} 
	
/* нопка меню Tools - скрыть панель инструментов и открыть*/	  
	  public boolean ToolsBtn(){
		  jMenuBtn[1].addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            if(panelCommonFlag==true) { panelCommon.setVisible(false); panelCommonFlag=false; 
		            							ColorPanel.commonColorPanel.setVisible(false); 
			            themeBtnImg = new ImageIcon(imgPath+"toolsClose.png");
			            image = themeBtnImg.getImage();  
			            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
			            themeBtnImg = new ImageIcon(nimage);
			            jMenuBtn[1].setIcon(themeBtnImg);
		            }
		            else { panelCommon.setVisible(true); panelCommonFlag=true;
		            	   ColorPanel.commonColorPanel.setVisible(true); 
			            themeBtnImg = new ImageIcon(imgPath+"tools.png");
			            image = themeBtnImg.getImage();  
			            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
			            themeBtnImg = new ImageIcon(nimage);
			            jMenuBtn[1].setIcon(themeBtnImg);
		            }
		        }
		    });
		  
		  return panelCommonFlag;
	  } 
	  
/* нопки вперЄд/назад - отменить/сохранить действие*/
	  public void ToolsBackForthBtn(){
		  jMenuBtn[0].addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent e) {
		        	if(createAlternateArray==1)	{ 
			        	FigureOrderTL=(List<Point[]>) ((ArrayList<Point[]>) DrawPanel.FigureOrder).clone();
			        	ModeOrderTL=(ArrayList<Integer>) DrawPanel.ModeOrder.clone();
			        	ColorOrderTL=(ArrayList<Color>) DrawPanel.ColorOrder.clone();
			        	WidthOrderTL=(ArrayList<Float>)DrawPanel.WidthOrder.clone();
			        	ColorFillOrderTL=(ArrayList<Color>) DrawPanel.ColorFillOrder.clone();
			        	ShapeOrderTL=(ArrayList<Shape>) DrawPanel.ShapeOrder.clone();
		        	
		        	createAlternateArray=0;
		        	}
		        	
		            if(DrawPanel.ModeOrder.size()!=0) {
		            	int index = DrawPanel.ModeOrder.size()-1;
		            	if(DrawPanel.ModeOrder.get(index)<3) DrawPanel.ShapeOrder.remove(DrawPanel.ShapeOrder.size()-1);
		            	DrawPanel.FigureOrder.remove(index);
		            	DrawPanel.ModeOrder.remove(index);
		            	DrawPanel.ColorOrder.remove(index);
		            	DrawPanel.WidthOrder.remove(index);
		            	DrawPanel.ColorFillOrder.remove(index);

		            	DrawPanel.jp.repaint();
		            }
		        }
		    });
		  
		  jMenuBtn[2].addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
			            if(DrawPanel.ModeOrder.size()<ModeOrderTL.size()) {
			            	
			            	int  index = DrawPanel.ModeOrder.size();
			            	if(ModeOrderTL.get(index)<3) DrawPanel.ShapeOrder.add(ShapeOrderTL.get(DrawPanel.ShapeOrder.size()));
			            	DrawPanel.FigureOrder.add(FigureOrderTL.get(index));
			            	DrawPanel.ModeOrder.add(ModeOrderTL.get(index));
			            	DrawPanel.ColorOrder.add(ColorOrderTL.get(index));
			            	DrawPanel.WidthOrder.add(WidthOrderTL.get(index));
			            	DrawPanel.ColorFillOrder.add(ColorFillOrderTL.get(index));
	
			            	DrawPanel.jp.repaint();
			            }
		        }
		    });
	  }
	  
/*  нопка выбора режима рисовани€: карандаш, кисть, ластик, толщина линии */
	  public void ModeBtn(){
		  Btn[2].addActionListener(new ActionListener() { //выбрать карандаш
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=0;
		        	System.out.println("pen");
		        }
		    });
		  Btn[3].addActionListener(new ActionListener() { //выбрать кисть
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=1;
		        }
		    });
		  Btn[4].addActionListener(new ActionListener() { //выбрать ластик
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=2; 
		        }
		    });
		  Btn[5].addActionListener(new ActionListener() { //выбрать текст
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); 
		        	/*FontChooser fontChooser = new FontChooser(null, new Font("Times New Roman", Font.PLAIN, 12)); // http://java-articles.info/articles/?p=272
		        	 
		        	if (fontChooser.isOkPressed()) {
		        	  drawModeCheck();
		        	  textAreaFont = fontChooser.getSelectedFont(); // »зменили параметры текста
		        	  changeTextColor=1;
		             }	 */
		        	}
		        }
		    );
		  Btn[Btn.length-3].addActionListener(new ActionListener() { //выбрать толщину линии
		        public void actionPerformed(ActionEvent e) {
		        	
			       	JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
			       	slider.setMajorTickSpacing(1);
				    slider.setPaintTicks(true);
				    slider.setPaintLabels(true);
				    slider.setSnapToTicks(true);
				    slider.setValue((int)lineWidth);
				    
			        JPanel sliderPanel = new JPanel();
			        sliderPanel.add(slider);
			        	        
			        int result = JOptionPane.showConfirmDialog(null, sliderPanel, 
			                 "Choose the line width", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			        
			        float lineWidthBuffer = lineWidth;
			        lineWidth=slider.getValue();
			        
			        if (result == JOptionPane.OK_OPTION) {
			        	lineWidth=slider.getValue();
			         }
			        
			        if (result == JOptionPane.CANCEL_OPTION) {
			        	lineWidth=lineWidthBuffer;
			         }
			        }
			        });
		  Btn[Btn.length-2].addActionListener(new ActionListener() { //выбрать цвет (picker)
		        public void actionPerformed(ActionEvent e) {
		        	if(objColor!=1)  objColor=1;
		        	else objColor=0;
			        }
			        });
		  Btn[Btn.length-1].addActionListener(new ActionListener() { //выбрать интсрумент курсор
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck();
		        	drawMode=-1;
			        }
			        });
	  } 	  
	  
	  public void ModeinBtn(){
		  inBtn[0].addActionListener(new ActionListener() { //выбрать линию
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=10; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_linef.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[0].setIcon(themeBtnImg);
		        }
		    });
		  inBtn[1].addActionListener(new ActionListener() { //выбрать окружность
		        public void actionPerformed(ActionEvent e) { 
		        	drawModeCheck(); drawMode=11; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_circlef.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[1].setIcon(themeBtnImg);
		        }
		    });
		  inBtn[2].addActionListener(new ActionListener() { //выбрать квадрат
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=12; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_rectanglef.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[2].setIcon(themeBtnImg);
		        }
		    });
		  inBtn[3].addActionListener(new ActionListener() { //выбрать треугольник
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=13; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_trianglef.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[3].setIcon(themeBtnImg);
		        }
		    });
		  inBtn[4].addActionListener(new ActionListener() { //выбрать ромб
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=14; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_rhombusf.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[4].setIcon(themeBtnImg);
		        }
		    });
		  inBtn[5].addActionListener(new ActionListener() { //выбрать п€тиугольник
		        public void actionPerformed(ActionEvent e) {
		        	drawModeCheck(); drawMode=15; 
		        	
		            themeBtnImg = new ImageIcon(imgPath+"f_pentagonf.png");
		            image = themeBtnImg.getImage();  
		            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		            themeBtnImg = new ImageIcon(nimage);
		            inBtn[5].setIcon(themeBtnImg);
		        }
		    });
	  }
	  
	  public void ToolsActivation(){
		  ToolsBtn(); ModeBtn(); ModeinBtn();
		  ToolsBackForthBtn();
	  }
	  
	  void drawModeCheck(){
      	if(drawMode>3) {
            themeBtnImg = new ImageIcon(imgPath+fgr[drawMode%10]);
            image = themeBtnImg.getImage();  
            nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
            themeBtnImg = new ImageIcon(nimage);
            inBtn[drawMode%10].setIcon(themeBtnImg);		        		
    	}
	  }
}
