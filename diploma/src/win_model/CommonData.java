package win_model;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class CommonData {
	public String imgPath="/project/diploma/img/";
	public static String [] img={"connect.png","about.png","pencil.png","brush.png","eraser.png","diameter.png","picker.png","cursor.png"};
	public static String [] fgr={"f_line.png","f_circle.png","f_rectangle.png","f_triangle.png","f_rhombus.png","f_pentagon.png"};
	public static String [] tls={"back.png","tools.png","forth.png"};
	   
	public static String [] MenuFileItem = {"New", "Clear", "Open", "Save", "Save As", "Exit"};
	public static String [] MenuThemeItem = {"Black Star","Green Dream","Plain","Sky Metallic","Orange Metallic"};
	public static String [] jMenuName = {"File","Theme"};
	   
	public static JPanel panelCommon;
	public static JMenu [] jMenu = new JMenu[jMenuName.length];
	public static JMenuItem [] jMenuItem = new JMenuItem [MenuFileItem.length];
	public static JCheckBoxMenuItem [] jMenuThemeItem = new JCheckBoxMenuItem [MenuThemeItem.length];
	public static JButton [] jMenuBtn = new JButton[tls.length];
	   
	public static JButton [] Btn = new JButton[img.length];
	public static JButton [] inBtn = new JButton[fgr.length];
	
	public static Image image, nimage;
	public static ImageIcon themeBtnImg;
	
	public static int i, j, x=0, y=0, inPanAmount=3;
	public static boolean panelCommonFlag;
	
	
	public static int createFileChooserFlag=0;
	public static int fileHasBeenSavedFlag=0;
	public static int createOpenFileChooserFlag=0;
	public static File file, selectedFile, selectedOpenFile; 
	public static String figO="", createdFileName="";
	
	public static Runnable rClient, rServer;
	public static Thread tClient, tServer;
}
