package win_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.*;

import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;

import win_logic.*;

public class WindowGUI {
	int  xPad;
    int  xf;
    int  yf;
    int  yPad;
    int  thickness;
    boolean pressed=false;
    // текущий цвет
    Color maincolor = Color.black;
    // поверхность рисования 
	
   public static String frameName="Jade", fileName="New Image.jade";
   Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
   public static JFrame frame;
   
/***********************************************КОНСТРУКТОР***********************************************/      
	public WindowGUI() {
	  frame = new JFrame(frameName+" | "+fileName); 
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setIconImage(new ImageIcon("/project/diploma/img/icon.png").getImage());
	    
	  frame.setJMenuBar(new MainMenuLogic().createMenu());  

	  frame.setSize(screen.width/2, screen.height/2);
	  frame.setLayout(new GridBagLayout());
	  
	  GridBagConstraints c = new GridBagConstraints();
	  
	  c.fill = GridBagConstraints.BOTH; 
	  c.insets = new Insets(1, 1, 1, 1);

	  c.gridx = 0;
	  c.gridy = 0;

	  c.weightx = c.weighty = 0.0;
	  frame.add(new ToolsElements().CreateToolPanel(),c);

	  c.gridx = 0;
	  c.gridy = 1;

	  c.weightx = c.weighty = 1.0;
	  frame.add(new DrawPanel(),c);

	  c.gridx = 0; // если в 0 - располагается по центру
	  c.gridy = 2;

	  c.weightx = c.weighty = 0.0;
	  frame.add(new ColorPanel().CreateColorPanel(),c);	   

	  frame.setLocationRelativeTo(null);
	  //frame.setResizable(false);

	  frame.setVisible(true);

	}

	
/***********************************************КОНСТРУКТОР***********************************************/  
	
	public static void main(String[] args) {
			  try { UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel()); } 
			  catch (Exception e) { e.printStackTrace(); }
			  SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            		new  WindowGUI();
		            }
			  });
	}
}

