package win_view;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import win_model.*;

public class MainMenuElements extends CommonData{

	
/* Создаём панель меню с основными пунктами */
   public JMenuBar createMenu() {
       JMenuBar menuBar = new JMenuBar();  
       
       for(i=0; i<jMenuName.length; i++){
    	   jMenu[i]=new JMenu(jMenuName[i]);
    	   menuBar.add(jMenu[i]); 
       }
       
       menuBar.add(Box.createHorizontalGlue());
       for(i=0; i<tls.length; i++) {
           themeBtnImg = new ImageIcon(imgPath+tls[i]);
           image = themeBtnImg.getImage();  
           nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
           themeBtnImg = new ImageIcon(nimage);
           jMenuBtn[i]=new JButton(themeBtnImg);
           jMenuBtn[i].setPreferredSize(new Dimension(30,15));
           jMenuBtn[i].setBorderPainted(false);
    	   menuBar.add(jMenuBtn[i]);
       }       
       menuBar.add(Box.createHorizontalGlue());      

       for(i=0; i<2; i++) {
				           themeBtnImg = new ImageIcon(imgPath+img[i]);
				           image = themeBtnImg.getImage();  
				           nimage = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);  
				           themeBtnImg = new ImageIcon(nimage);
				           Btn[i]=new JButton(themeBtnImg);
				           Btn[i].setPreferredSize(new Dimension(30,15));
				    	   menuBar.add(Btn[i]); 
       }
       
	   CreateMenuItems();
	   
       return menuBar;
   }

/* Создаём подпункты File */
   public void CreateMenuItems(){
	   for(i=0; i<MenuFileItem.length; i++){ 
		   									jMenuItem[i] = new JMenuItem(MenuFileItem[i]);  
		   									
		   									switch(i) {
		   									case 0: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		   										break;
		   									case 1: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		   										break;
		   									case 2: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		   										break;
		   									case 3: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		   										break;
		   									case 4: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		   										break;
		   									case 5: jMenuItem[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.CTRL_MASK));
		   										break;
		   									}
	   										jMenu[0].add(jMenuItem[i]); 
	   										if((i+1)%2!=0 && i!=0) jMenu[0].addSeparator();
	   										}
	   for(i=0; i<MenuThemeItem.length; i++){ 
		   		jMenuThemeItem[i] = new JCheckBoxMenuItem(MenuThemeItem[i]);  
		   		if(i==0) jMenuThemeItem[i].setSelected(true);
				jMenu[1].add(jMenuThemeItem[i]);
				}
   }  
}
