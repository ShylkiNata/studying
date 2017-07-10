package win_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import win_logic.*;
import win_model.*;

public class ToolsElements extends CommonData{
	/* Создаём панель инструментов */   
	   public JPanel CreateToolPanel() {
		   
		   panelCommon = new JPanel();
			   
		   panelCommon.setPreferredSize(new Dimension(100, 40));
		   panelCommon.setBackground(new Color(51,51,51));
		   panelCommon.setLayout(new BoxLayout(panelCommon, BoxLayout.LINE_AXIS));
	 	   
	 	   JPanel [] panelInner = new JPanel[inPanAmount];  // Создаём 3 внутренних панели
		   panelInner[0]=new JPanel(new FlowLayout(FlowLayout.LEFT));
		   panelInner[1]=new JPanel(new FlowLayout(FlowLayout.CENTER));
		   panelInner[2]=new JPanel(new FlowLayout(FlowLayout.RIGHT));	   
		   
		   //Создание панелей в панеле Tools
	 	   for(i=0; i<inPanAmount; i++){		                  
	 		   				  panelInner[i].setBackground(new Color(51,51,51));
	 		   				  if(i==0 || i==inPanAmount-1){
		 		   			 	   //panelInner[i].setPreferredSize(new Dimension(100, 40));
		 		   			 	   panelInner[i].setBackground(new Color(38, 38, 38));
	 		   				  }
	 	   }
	 	   
		   //Заполнение кнопками рисования первой (левой) панели в Tools 	   
	       for(i=2; i<Btn.length-3; i++) {
	           themeBtnImg = new ImageIcon(imgPath+img[i]);
	           image = themeBtnImg.getImage();  
	           nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
	           themeBtnImg = new ImageIcon(nimage);
	           Btn[i]=new JButton(themeBtnImg);
	           Btn[i].setPreferredSize(new Dimension(30,30));
	           panelInner[0].add(Btn[i]);   	   
	       }
	 	         
		   //Заполнение кнопками рисования второй (центральной) панели в Tools 	      
		   for(i=0; i<inBtn.length; i++) {
		       themeBtnImg = new ImageIcon(imgPath+fgr[i]);
		       image = themeBtnImg.getImage();  
		       nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
		       themeBtnImg = new ImageIcon(nimage);
		       inBtn[i]=new JButton(themeBtnImg);
		       inBtn[i].setPreferredSize(new Dimension(30,30));             
		       panelInner[1].add(inBtn[i]);   	   	
		   }      
		    
		     for(i=Btn.length-3; i<Btn.length; i++) {
			       themeBtnImg = new ImageIcon(imgPath+img[i]);
			       image = themeBtnImg.getImage();  
			       nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
			       themeBtnImg = new ImageIcon(nimage);
			       Btn[i]=new JButton(themeBtnImg);
			       Btn[i].setPreferredSize(new Dimension(30,30));             
			       panelInner[2].add(Btn[i]);  	   	
		     } 
		     

		     for(i=0; i<inPanAmount; i++) panelCommon.add(panelInner[i]);
		    	  
	         new MainMenuLogic().MenuActivation();
	         new ToolsLogic().ToolsActivation();
	         
	        panelCommonFlag=true;
	        return panelCommon;
	   }
}
