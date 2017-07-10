package win_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import win_model.*;

public class ColorPanel {
	public static JPanel lineColorPanel, fillColorPanel, commonColorPanel, textColorPanel;

	JButton lineColorBtn, fillColorBtn, textColorBtn;
			
	public JPanel CreateColorPanel(){
		commonColorPanel=new JPanel();
		commonColorPanel.setBackground(new Color(51,51,51));
		
		lineColorPanel=new JPanel();
		fillColorPanel=new JPanel();
		textColorPanel=new JPanel();
				
		ImageIcon themeBtnImg = new ImageIcon("/project/diploma/img/point.png");
        Image image = themeBtnImg.getImage();  
        Image nimage = image.getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH);  
        themeBtnImg = new ImageIcon(nimage);
        lineColorBtn=new JButton(themeBtnImg);
        lineColorBtn.setPreferredSize(new Dimension(20,20));
        lineColorBtn.setBorderPainted(false);
		
		themeBtnImg = new ImageIcon("/project/diploma/img/shapeColor.png");
        image = themeBtnImg.getImage();  
        nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
        themeBtnImg = new ImageIcon(nimage);
        fillColorBtn=new JButton(themeBtnImg);
        fillColorBtn.setPreferredSize(new Dimension(20,20));
        fillColorBtn.setBorderPainted(false);
        
		themeBtnImg = new ImageIcon("/project/diploma/img/text.png");
        image = themeBtnImg.getImage();  
        nimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);  
        themeBtnImg = new ImageIcon(nimage);
        textColorBtn=new JButton(themeBtnImg);
        textColorBtn.setPreferredSize(new Dimension(20,20));
        textColorBtn.setBorderPainted(false);
        
        ColorPanelData.lineColor=Color.blue; ColorPanelData.fillColor=Color.white; ColorPanelData.textColor=Color.gray;
        
		lineColorPanel.setBackground(ColorPanelData.lineColor);
		fillColorPanel.setBackground(ColorPanelData.fillColor);
		textColorPanel.setBackground(ColorPanelData.textColor);
		
		lineColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		fillColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		textColorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		commonColorPanel.add(lineColorBtn); 
		commonColorPanel.add(lineColorPanel);
		commonColorPanel.add(fillColorBtn);  
		commonColorPanel.add(fillColorPanel);
		commonColorPanel.add(textColorBtn); 
		commonColorPanel.add(textColorPanel); 
		
		lineColorBtn.addActionListener(new ActionListener() {           
			public void actionPerformed(ActionEvent e) {
				 ColorPanelData.lineColor = JColorChooser.showDialog(null, "JColorChooser", ColorPanelData.lineColor);
	             lineColorPanel.setBackground(ColorPanelData.lineColor);
				 }           
	       		});
		
		lineColorPanel.addMouseListener(new MouseAdapter() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				ColorPanelData.colorMode=1;
				}         
	    });
		fillColorPanel.addMouseListener(new MouseAdapter() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				ColorPanelData.colorMode=2;
				System.out.println("2");
				}         
	    });
		textColorPanel.addMouseListener(new MouseAdapter() {           
			@Override
			public void mouseClicked(MouseEvent e) {
				ColorPanelData.colorMode=3;
				}         
	    });
		
		fillColorBtn.addActionListener(new ActionListener() {           
			public void actionPerformed(ActionEvent e) {
				ColorPanelData.fillColor = JColorChooser.showDialog(null, "JColorChooser", ColorPanelData.fillColor);
	             fillColorPanel.setBackground(ColorPanelData.fillColor);
	             }           
	       		});
		
		textColorBtn.addActionListener(new ActionListener() {           
			public void actionPerformed(ActionEvent e) {
				ColorPanelData.textColor = JColorChooser.showDialog(null, "JColorChooser", ColorPanelData.textColor);
	             textColorPanel.setBackground(ColorPanelData.textColor);
	             }           
	       		});
		
		return commonColorPanel;
	}
}
