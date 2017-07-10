package win_view;

import jFontChooser.FontChooser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

import win_logic.DrawPanel;
import win_logic.ToolsLogic;
import win_model.ColorPanelData;

@SuppressWarnings("serial")
public class TextField extends JTextArea implements FocusListener, MouseListener, DocumentListener{

	public static Point start=null;
	Border border;

	public TextField(){
		setDragEnabled(true);
		
		border = BorderFactory.createLineBorder(Color.gray);
		setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
        setOpaque(false);
        
        setCaretColor(new Color(121,180,42));
        setFont(ToolsLogic.textAreaFont);
        setSize(getPreferredSize());
        
        setForeground(ColorPanelData.textColor);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
                AWTKeyStroke ak = AWTKeyStroke.getAWTKeyStrokeForEvent(e);
                
                if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK))){
                	FontChooser fontChooser = new FontChooser(null, new Font("Times New Roman", Font.PLAIN, 12)); // http://java-articles.info/articles/?p=272
		        	 
		        	if (fontChooser.isOkPressed()) {
		        	  ToolsLogic.textAreaFont = fontChooser.getSelectedFont(); // Изменили параметры текста
		        	  setFont(ToolsLogic.textAreaFont);
		        	}
		        	
		        	updateSize();
                }
                
                if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK))){             
                	  ColorPanelData.textColor = JColorChooser.showDialog(null, "JColorChooser", ColorPanelData.textColor);
			          ColorPanel.textColorPanel.setBackground(ColorPanelData.textColor);
			          
			          setForeground(ColorPanelData.textColor);
                }
                if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK))){             
			        deleteField();
                }
                if(e.getKeyCode()==KeyEvent.VK_DELETE){             
                	setSize(getPreferredSize());
                }
            }
        });
        addFocusListener( this );
        addMouseListener( this );
        getDocument().addDocumentListener( this );
	}
    
    public void actionPerformed(ActionEvent e) { updateSize(); setEditable(true); setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(1, 1, 1, 1))); }
    public void focusLost(FocusEvent e) { setEditable(true); }
    public void insertUpdate(DocumentEvent e) { updateSize(); }
    
    public void updateSize() { setSize(getPreferredSize()); }
    //(int)getPreferredSize().getWidth()+Toolkit.getDefaultToolkit().getFontMetrics(ToolsLogic.textAreaFont).charWidth('i'), (int)getPreferredSize().getHeight());
    
    public void removeUpdate(DocumentEvent e) { updateSize(); }

    public void mouseClicked( MouseEvent e ) {}
    
    public void focusGained(FocusEvent e) { }
    public void mouseEntered(MouseEvent e){ border = BorderFactory.createLineBorder(Color.gray);
											setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(1, 1, 1, 1))); 
											updateSize();}        
    public void mouseExited( MouseEvent e ) {
    										updateSize(); setBorder(BorderFactory.createEmptyBorder()); 
	  										setEditable(true); 
	  										}
    public void mousePressed( MouseEvent e ) {}
    public void mouseReleased( MouseEvent e ) {}
    public void mouseDragged( MouseEvent e ) {}
    public void changedUpdate(DocumentEvent e) {}
    
    
    void deleteField(){
    	
    	if(getText()!="") {
    	setLocation((int) (getLocation().getX()+3),(int)(getLocation().getY()) /*(getLocation().getY()+ToolsLogic.textAreaFont.getSize()+2) */);
    	/*
        DrawPanel.FigureOrder.add(new Point[]{getLocation(), getLocation()}); 
        DrawPanel.ModeOrder.add(3);
              
        String[] s= getText().split("\n");
        String m = String.join("\n", getText());
        
        DrawPanel.StringOrder.add(m);
        DrawPanel.FontOrder.add(ToolsLogic.textAreaFont);
        DrawPanel.ColorOrder.add(ColorPanel.textColor);
        
        DrawPanel.WidthOrder.add(new ToolsLogic().get_lineWidth());
        DrawPanel.ObjOutlineOrder.add(new ToolsLogic().get_objOutline()); // закрашено 2/не закрашено 1
        DrawPanel.ColorFillOrder.add(new ColorPanel().get_fillColor()); // цвета закрашенного
    	*/}
    	DrawPanel.jp.remove(this);
    	DrawPanel.jp.repaint();
    }
}
