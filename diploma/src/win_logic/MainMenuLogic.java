package win_logic;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel;

import win_view.*;

public class MainMenuLogic extends MainMenuElements {

/* кнопка для File -> New. Очистить холст */
	  public void fileMenu(){
		   jMenuItem[0].addActionListener(new ActionListener() { // New       
	           public void actionPerformed(ActionEvent e) {
	        	   DrawPanel.clearArea(); DrawPanel.jp.repaint();
	        	   createdFileName=""; fileHasBeenSavedFlag=0; createdFileName="";
	        	   WindowGUI.fileName="New Image.jade";
	        	   WindowGUI.frame.setTitle(WindowGUI.frameName+" | "+WindowGUI.fileName);
	           }           
	       });
		   jMenuItem[1].addActionListener(new ActionListener() { // Clear область рисования
	           public void actionPerformed(ActionEvent e) {
	        	   DrawPanel.clearArea(); DrawPanel.jp.repaint();
	           }           
	       });
		   jMenuItem[2].addActionListener(new ActionListener() { // Clear область рисования
	           public void actionPerformed(ActionEvent e) {
	        	   openFile();
	           }           
	       });
		   jMenuItem[jMenuItem.length-3].addActionListener(new ActionListener() { // Save          
	           public void actionPerformed(ActionEvent e) {
	        	   if(fileHasBeenSavedFlag==0) {
			        	   try { fileChooser(); } 
			        	   catch (FileNotFoundException e1) { e1.printStackTrace(); }
	        	   }
	        	   else saveExFile();
	           }           
	       });		
		   jMenuItem[jMenuItem.length-2].addActionListener(new ActionListener() { //Save as          
	           public void actionPerformed(ActionEvent e) {
	        	   try {
					fileChooser();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
	           }           
	       });		   
		   
			jMenuItem[jMenuItem.length-1].addActionListener(new ActionListener() {  //Exit         
		       public void actionPerformed(ActionEvent e) {
		           System.exit(0); }           
		    });
	  }
	
	  /* кнопка для Theme */
	  public void themeMenu(){
		  	jMenuThemeItem[0].addActionListener(new ActionListener() { // Black theme       
	           public void actionPerformed(ActionEvent e) {
	        	   for(int jm=0; jm<jMenuThemeItem.length; jm++) jMenuThemeItem[jm].setSelected(false);
	        	   jMenuThemeItem[0].setSelected(true);
		 			  try { UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel()); } 
					  catch (Exception exp) { exp.printStackTrace(); }
	           }           
	       });
		  	jMenuThemeItem[1].addActionListener(new ActionListener() { // Green theme       
		           public void actionPerformed(ActionEvent e) {
		        	   for(int jm=0; jm<jMenuThemeItem.length; jm++) jMenuThemeItem[jm].setSelected(false);
		        	   jMenuThemeItem[1].setSelected(true);
			 			  try { UIManager.setLookAndFeel(new SyntheticaGreenDreamLookAndFeel()); } 
						  catch (Exception exp) { exp.printStackTrace(); }
		           }           
		       });
		  	jMenuThemeItem[2].addActionListener(new ActionListener() { // Plain theme       
		           public void actionPerformed(ActionEvent e) {
		        	   for(int jm=0; jm<jMenuThemeItem.length; jm++) jMenuThemeItem[jm].setSelected(false);
		        	   jMenuThemeItem[2].setSelected(true);
			 			  try { UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel()); } 
						  catch (Exception exp) { exp.printStackTrace(); }
		           }           
		       });
		  	jMenuThemeItem[3].addActionListener(new ActionListener() { // Sky theme       
		           public void actionPerformed(ActionEvent e) {
		        	   for(int jm=0; jm<jMenuThemeItem.length; jm++) jMenuThemeItem[jm].setSelected(false);
		        	   jMenuThemeItem[3].setSelected(true);
			 			  try { UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel()); } 
						  catch (Exception exp) { exp.printStackTrace(); }
		           }           
		       });
		  	jMenuThemeItem[4].addActionListener(new ActionListener() { // Orange theme       
		           public void actionPerformed(ActionEvent e) {
		        	   for(int jm=0; jm<jMenuThemeItem.length; jm++) jMenuThemeItem[jm].setSelected(false);
		        	   jMenuThemeItem[4].setSelected(true);
			 			  try { UIManager.setLookAndFeel(new SyntheticaOrangeMetallicLookAndFeel()); } 
						  catch (Exception exp) { exp.printStackTrace(); }
		           }           
		       });
	  }
		  
	  
/*Кнопка подключения к серверу*/	  
	  public void ConnectBtn(){
		  Btn[0].addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	String[] options = new String[] {"Client", "Server", "Cancel"};
		        	
		            JTextField xField = new JTextField(10);
		            JTextField yField = new JTextField(10);
		            
		            JPanel myPanel = new JPanel();
		            myPanel.setLayout(new GridBagLayout());
		            
		            GridBagConstraints c = new GridBagConstraints();
		            	c.fill = GridBagConstraints.HORIZONTAL;
		            	c.gridx = 1;
		            	c.gridy = 0;
		            	c.insets = new Insets(5, 0, 5, 2);
		            	myPanel.add(new JLabel("Put the server IP address"), c);

		            	c.fill = GridBagConstraints.HORIZONTAL;
		            	c.gridx = 2;
		            	c.gridy = 0;
		            	c.insets = new Insets(5, 5, 5, 0);
		            	myPanel.add(xField,c);
		            	
		            	c.fill = GridBagConstraints.HORIZONTAL;
		            	c.gridx = 1;
		            	c.gridy = 1;
		            	c.insets = new Insets(5, 0, 5, 2);
		            	myPanel.add(new JLabel("Put the port of the socket  "), c);

		            	c.fill = GridBagConstraints.HORIZONTAL;
		            	c.gridx = 2;
		            	c.gridy = 1;
		            	c.insets = new Insets(5, 5, 5, 0);
		            	myPanel.add(yField,c);
		            
		           // int result = JOptionPane.showConfirmDialog(null, myPanel, "Connect to the server", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		            int result = JOptionPane.showOptionDialog(null, myPanel, "Connect to the server", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		            if (result == 0) {
		               
		               System.out.println("x value: " + xField.getText());
		               System.out.println("y value: " + yField.getText());
		              
			           rClient = new ClientLogic(xField.getText(),Integer.parseInt(yField.getText()));
			           tClient = new Thread(rClient);
			           tClient.start();
		            }
		            else if (result == 1) {
			           			           
			           rServer = new ServerLogic(Integer.parseInt(yField.getText()));
			           tServer = new Thread(rServer);
			           tServer.start();
			          
			               
			           System.out.println("y value: " + yField.getText());
			          }
		            }
		    });
	  }
	  
	  public void MenuActivation(){
		  fileMenu(); fileMenu(); themeMenu(); 
		  ConnectBtn();
	  }
	  
	  void fileChooser() throws FileNotFoundException{	  
		  createFileChooserFlag++; int errorFlag=0;
		  
		  if(MainMenuLogic.createFileChooserFlag==2) {
    	   String userDir = System.getProperty("user.home");
    	   JFileChooser fileChooser = new JFileChooser(userDir +"/Desktop");
    	   
    	   fileChooser.setDialogTitle("Save Image as");
    	   
    	   String [] filterArray={"jpg", "png", "gif", "jpeg", "jade"}; FileFilter filter;
    	   for(int i=0; i<filterArray.length; i++) {
	        	   filter = new FileNameExtensionFilter(filterArray[i], filterArray[i]);
	        	   fileChooser.setFileFilter(filter);
	        	   fileChooser.addChoosableFileFilter(filter);
    	   }

    	   
    	   if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
    		   fileHasBeenSavedFlag=1;
    			  
        	   selectedFile = fileChooser.getSelectedFile();

    		    if(selectedFile.getName().lastIndexOf(".")==-1) { // если не указано расширение пользователем

		    	   if(fileChooser.getFileFilter().getDescription().equals("All Files") || fileChooser.getFileFilter().getDescription().equals("jade")) 	{		    	 

		    		   saveAsJade(selectedFile.getAbsolutePath()+".jade");

			    	   createdFileName=selectedFile.getAbsolutePath()+".jade";
				       WindowGUI.fileName = selectedFile.getName()+".jade";
		    	   }
		    	   else { // если не указано расширение. И оно не явл All Files или jade
		    		 createdFileName=selectedFile.getAbsolutePath()+"."+fileChooser.getFileFilter().getDescription();
		    		 file = new File(createdFileName);
		    		 switch(fileChooser.getFileFilter().getDescription()){
				   		case "png":  try { ImageIO.write(DrawPanel.getImg(), "png", file); } 
						 			 catch (IOException e) { e.printStackTrace(); }
							break;
						case "gif":  try { ImageIO.write(DrawPanel.getImg(), "gif", file); } 
									 catch (IOException e) { e.printStackTrace(); }
							break;
						case "jpg":  try { ImageIO.write(DrawPanel.getImg(), "jpg", file); } 
							  		 catch (IOException e) { e.printStackTrace(); }
							break;
						case "jpeg": try { ImageIO.write(DrawPanel.getImg(), "jpeg", file); }
							  		 catch (IOException e) { e.printStackTrace(); }
							break;
		    	   		}
		    	       WindowGUI.fileName = selectedFile.getName()+"."+fileChooser.getFileFilter().getDescription();
		    	   }
    		    }
    		    	
    		    else { // если расширение пользователем указано
		    		createdFileName=selectedFile.getAbsolutePath();
    		    	file = new File(selectedFile.getAbsolutePath());
    		    	switch((selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")+1,selectedFile.getName().length())).toLowerCase()){
    		    		case "png":  try { ImageIO.write(DrawPanel.getImg(), "png", file); } 
   					 				 catch (IOException e) { e.printStackTrace(); }
    		    			break;
    		    		case "gif":  try { ImageIO.write(DrawPanel.getImg(), "gif", file); } 
    		    					 catch (IOException e) { e.printStackTrace(); }
    		    			break;
    		    		case "jpg":  try { ImageIO.write(DrawPanel.getImg(), "jpg", file); } 
			 				  		 catch (IOException e) { e.printStackTrace(); }
    		    			break;
    		    		case "jpeg": try { ImageIO.write(DrawPanel.getImg(), "jpeg", file); }
			 				  		 catch (IOException e) { e.printStackTrace(); }
    		    			break;
    		    		case "jade": saveAsJade(selectedFile.getAbsolutePath());
    		    			break;
    		    		default: JOptionPane.showMessageDialog(null, "Unsupported format!\nSave file using other extension.", "Error",
    		    			     JOptionPane.ERROR_MESSAGE); errorFlag=1;
    		    			break;
    		    	}
    		    	if(errorFlag==1) errorFlag=0; 
    		    	else WindowGUI.fileName = selectedFile.getName();
    		    }
    		    
    	   }	    		    
		    createFileChooserFlag=0;
		    
		    WindowGUI.frame.setTitle(WindowGUI.frameName+" | "+WindowGUI.fileName);
		 }
	}
	  
/* Сохранение в Jade формате */
	  void saveAsJade(String path){
 			ObjectOutputStream oos;
		     
			try
			{
			  oos = new ObjectOutputStream(new FileOutputStream(path));
			            
			  oos.writeObject(DrawPanel.FigureOrder); 
			  oos.writeObject(DrawPanel.ModeOrder);
			  oos.writeObject(DrawPanel.ColorOrder);
			  oos.writeObject(DrawPanel.WidthOrder);
			  oos.writeObject(DrawPanel.ColorFillOrder);  
			  oos.writeObject(DrawPanel.ShapeOrder);
			  oos.writeObject(DrawPanel.StringOrder);  
			  oos.writeObject(DrawPanel.FontOrder);  
			  
			  oos.flush();
			  oos.close();
			}
   			catch (IOException ex)
			{
			  System.out.println(ex.toString());
			}
	  }

/* Открытие в Jade формате */
	  @SuppressWarnings("unchecked")
	void openFile(){
		  createOpenFileChooserFlag++;
		  
		  if(createOpenFileChooserFlag==2) {			  
    	   String userDir = System.getProperty("user.home");
    	   JFileChooser fileChooser = new JFileChooser(userDir +"/Desktop");
    	   
    	   fileChooser.setDialogTitle("Open File");
    	   
    	   FileFilter openfilter = new FileNameExtensionFilter("jade", "jade");
	       fileChooser.setFileFilter(openfilter);
	       fileChooser.addChoosableFileFilter(openfilter);
	       
    	   if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	       
    		   fileHasBeenSavedFlag=1;
        	   selectedOpenFile = fileChooser.getSelectedFile();

    		   if(selectedOpenFile.getName().lastIndexOf(".")==-1) { // если не указано расширение пользователем
    			 JOptionPane.showMessageDialog(null, "File not found."+selectedOpenFile.getName()+"\nCheck its name and extension.", "Error", JOptionPane.ERROR_MESSAGE);
    		   }
    		   else {
    		    	switch((selectedOpenFile.getName().substring(selectedOpenFile.getName().lastIndexOf(".")+1,selectedOpenFile.getName().length())).toLowerCase()){
    	    		case "jade": 
    	    			ObjectInputStream ois;
    	    			try
    	    			{
    	    			  ois = new ObjectInputStream(new FileInputStream(selectedOpenFile.getAbsolutePath()));
    	    			    	    			  
    	    			  DrawPanel.FigureOrder =/* (ArrayList<Point[]>)*/ ((ArrayList<Point[]>)  ois.readObject());
    	    			  DrawPanel.ModeOrder = (ArrayList<Integer>) ois.readObject();
    	    			  DrawPanel.ColorOrder = (ArrayList<Color>) ois.readObject();
    	    			  DrawPanel.WidthOrder = (ArrayList<Float>) ois.readObject();
    	    			  DrawPanel.ColorFillOrder = (ArrayList<Color>) ois.readObject();
    	    			  DrawPanel.ShapeOrder = (ArrayList<Shape>) ois.readObject();
    	    			  DrawPanel.StringOrder = (ArrayList<String>) ois.readObject();
    	    			  DrawPanel.FontOrder = (ArrayList<Font>) ois.readObject();
    	    			  
    	    			  ois.close();
    	    			}
    	    			catch (IOException ex)
    	    			{
    	    			  System.out.println(ex.toString());
    	    			}
    	    			catch (ClassNotFoundException ex)
    	    			{
    	    			  System.out.println(ex.toString());
    	    			}
    	    			break;
    	    		default: JOptionPane.showMessageDialog(null, "Unsupported format!\nChoose *.jade file", "Error",
		    			     JOptionPane.ERROR_MESSAGE); 
    	    			break;
    	    	}  
    		   }
    	   }
	       
	       createOpenFileChooserFlag=0;
		  }
	  }
	  
	  void saveExFile(){
		  file = new File(createdFileName);
	    	switch((createdFileName.substring(createdFileName.lastIndexOf(".")+1,createdFileName.length())).toLowerCase()){
    		case "png":  try { ImageIO.write(DrawPanel.getImg(), "png", file); } 
			 				 catch (IOException e) { e.printStackTrace(); }
    			break;
    		case "gif":  try { ImageIO.write(DrawPanel.getImg(), "gif", file); } 
    					 catch (IOException e) { e.printStackTrace(); }
    			break;
    		case "jpg":  try { ImageIO.write(DrawPanel.getImg(), "jpg", file); } 
 				  		 catch (IOException e) { e.printStackTrace(); }
    			break;
    		case "jpeg": try { ImageIO.write(DrawPanel.getImg(), "jpeg", file); }
 				  		 catch (IOException e) { e.printStackTrace(); }
    			break;
    		case "jade": saveAsJade(createdFileName);
    			break;
    	}  
	  }
}
