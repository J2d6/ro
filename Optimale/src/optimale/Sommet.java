/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimale;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author BenjaRaym
 */
public class Sommet extends JLabel{
  Icon ic=new ImageIcon("src/images/boribory.png");
  Icon ic1=new ImageIcon("src/images/boribory1.png");
  String label;
  boolean mety=true;
  boolean selected;
  int Lambda=0;
  /*
	public sommet(){
    setIcon(ic);
  }
  public sommet(int x,int y){
    setIcon(ic);
    setBounds(x,y,ic.getIconWidth(),ic.getIconHeight());
  }*/
  public Sommet(int x,int y,String c){
    setIcon(ic);
    setLabel(c);
    setBounds(x,y,ic.getIconWidth(),ic.getIconHeight());
    //setToolTipText("Sommet "+c);
  }
  public Sommet(int x,int y,int c){
    setIcon(ic);
    setLabel("X"+Entier(c));
    setBounds(x,y,ic.getIconWidth(),ic.getIconHeight());
    //setToolTipText("Sommet "+c);
  }
  public void setMety(boolean b){
  	mety=b;
  	repaint();
	}
  public void setSelected(boolean b){
    selected=b;
    repaint();
  }
  public boolean getSelected(){
    return selected;
  }
  public void setLabel(String c){
    label=c;
  }
  public void setLambda(int L){
    Lambda=L;
    repaint();
  }
  public int getWidth(){
    return ic.getIconWidth();
  }
  public int getHeight(){
    return ic.getIconHeight();
  }
	public void paint(Graphics g){
    	Font Str=new Font("Times new roman",Font.BOLD,15);
    	if (mety){
            if (!selected) g.drawImage(getToolkit().getImage("src/images/boribory.png"),0,0,null);
    		else g.drawImage(getToolkit().getImage("src/images/boribory1.png"),0,0,null);
    	}else{
				g.setColor(new Color(0x999999));
				g.fillArc(2,2,28,28,0,360);
				g.setColor(new Color(0xcccccc));
				g.fillArc(0,0,28,28,0,360);
			}
      g.setFont(Str);
			g.setColor(Color.white);
      g.drawString(label,10,25);
      g.setColor(Color.white);
      if (Lambda>0){
        g.fillRect(0,0,getWidth(),10);
        g.setColor(Color.black);
        g.drawRect(0,0,getWidth()-1,10);
        g.drawString(Lambda+"",(getWidth()-((Lambda+"").length()*5))/2,9);
      }
	}
       private String Entier(int i){
		return i<10?" "+i:""+i;
    }
}