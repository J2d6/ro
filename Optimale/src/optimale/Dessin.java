/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimale;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import optimale.Arc;
import optimale.Sommet;
/**
 *
 * @author BenjaRaym
 */
public class Dessin extends JPanel implements MouseListener,MouseMotionListener,ActionListener{
	int nombreSommet=0,nombreArc=0;
	int SELECTED=-1;
	Sommet  [] sommet;
	Arc [] arc;
	Ford frm;
	boolean Locked=false;
	boolean sommetActif=false,arcActif=false;
	public Dessin(){
		sommet=new Sommet[100];
		arc=new Arc[100];
		setLayout(null);
		setBackground(new Color(0xffffff));
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public Dessin(Sommet [] sm,Arc [] arcs,int nbrSommet,int nbrArc){
		sommet=sm;
		arc=arcs;
		nombreSommet=nbrSommet;
		nombreArc=nbrArc;
		setLayout(null);
		setBackground(new Color(0xffffff));
		addMouseListener(this);
		addMouseMotionListener(this);

		afficheTout();
	}
	public void afficheTout(){
    for (int i=0;i<nombreSommet;i++)add(sommet[i]);
    for (int i=0;i<nombreArc;i++)add(arc[i]);
  }
	public void setFrameOwner(Ford f){
		frm=f;
	}
	public void ajoutArc(){
	  if (arcActif && SELECTED>=0){
        arc[nombreArc]=new Arc(sommet[SELECTED].getLocation().x+15,sommet[SELECTED].getLocation().y+15,SELECTED);
        add(arc[nombreArc]);
        nombreArc++;
    }
	}
	public void annuleAjoutArc(){
	  if (arcActif && SELECTED>=0){
			if (nombreArc>0){
				remove(arc[nombreArc-1]);
        nombreArc--;
        arcActif=false;
        repaint();
      }
    }
	}
	public void mouseMoved(MouseEvent e){
		if (arcActif){
			arc[nombreArc-1].setDestinationX(e.getX());
    	arc[nombreArc-1].setDestinationY(e.getY());
    }
  }
	public void mouseDragged(MouseEvent e){
		if (!arcActif && SELECTED>-1){
			sommet[SELECTED].setLocation(e.getX()-15,e.getY()-15);
			for (int i=0;i<nombreArc;i++){
        if (arc[i].getOrigine()==SELECTED){
          arc[i].setOrigineX(e.getX()-(sommet[SELECTED].getWidth()/2)+15);
          arc[i].setOrigineY(e.getY()-(sommet[SELECTED].getHeight()/2)+15);
          }
        if (arc[i].getDestination()==SELECTED){
          arc[i].setDestinationX(e.getX()-(sommet[SELECTED].getWidth()/2)+15);
          arc[i].setDestinationY(e.getY()-(sommet[SELECTED].getHeight()/2)+15);
          }
      }
		}
	}
	public void deselectTout(){
		for (int i=0;i<nombreSommet;i++) sommet[i].setSelected(false);
		if (frm!=null) frm.setEnableArcButton(false);
		SELECTED=-1;
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if (!Locked && !arcActif){
			int x=e.getX(),y=e.getY();
			deselectTout();
			for (int i=0;i<nombreSommet;i++){
				Point coordSommet=sommet[i].getLocation();
				if (x>=coordSommet.x && x<=coordSommet.x+sommet[i].getWidth() && y>=coordSommet.y && y<=coordSommet.y+sommet[i].getHeight()){
					SELECTED=i;
					break;
				}
			}
			if (SELECTED>-1){
				sommet[SELECTED].setSelected(true);
				if (SELECTED<nombreSommet-1 && !sommetActif)if (frm!=null) frm.setEnableArcButton(true);
			}
		}
	}
    @SuppressWarnings("empty-statement")
	public void mouseClicked(MouseEvent e){
		// click souris
		if (sommetActif){
			deselectTout();
			add(sommet[nombreSommet]=new Sommet(e.getX()-15,e.getY()-15,"X"+Entier((nombreSommet+1))));
			sommet[nombreSommet].setSelected(true);
			SELECTED=nombreSommet;
			nombreSommet++;
			if (frm!=null) frm.setEnableValiderButton(false);
			repaint();
		}
		else
		if (arcActif){
			for (int a=0;a<nombreSommet;a++){
        if (sommet[a].getLocation().x<e.getX() && sommet[a].getLocation().x+sommet[a].getWidth()>e.getX() &&
        sommet[a].getLocation().y<e.getY() && sommet[a].getLocation().y+sommet[a].getHeight()>e.getY()
         && a!=SELECTED){

          arc[nombreArc-1].setDestinationX(sommet[a].getLocation().x+15 );
          arc[nombreArc-1].setDestinationY(sommet[a].getLocation().y+15 );
          arc[nombreArc-1].setDestination(a);

          boolean erreur=true;

          String st="";

          while (erreur){
          			erreur=false;
          			st=JOptionPane.showInputDialog(null,"Valeur de l'arc","Ford",JOptionPane.QUESTION_MESSAGE);
          			if (st==null) break;

          			char car[]=new char[st.length()];
      					st.getChars(0,st.length(),car,0);

      					if (st.length()==0)erreur=true;
      					else
          			for (int i=0;i<car.length;i++) if (car[i]<'0' || car[i]>'9'){erreur=true; break;};
          			if (erreur) JOptionPane.showMessageDialog(null,"Veuillez entrer une valeur valide","Erreur",JOptionPane.ERROR_MESSAGE);
          }

          if (st!=null){
						Integer XX=new Integer(st);
          	int valeurArc=XX.intValue();
          	arc[nombreArc-1].setValeur(valeurArc);
          }
          else
						annuleAjoutArc();

					arcActif=false;
					if (frm!=null){
						frm.resetMessage();
						frm.setEnableSommetButton(true);

						if (peutValider()) frm.setEnableValiderButton(true);
						else frm.setEnableValiderButton(false);
					}
        }
      }
		}
	}
	private String Entier(int i){
		return i<10?" "+i:""+i;
	}
	public void actionPerformed(ActionEvent e){
		Object Source=e.getSource();
	}
	public boolean getEtatDessinSommet(){
		return sommetActif;
	}
	public boolean getEtatDessinArc(){
		return arcActif;
	}
	public void changeEtatDessinSommet(){
		sommetActif=!sommetActif;
	}
	public void changeEtatDessinArc(){
		arcActif=!arcActif;
	}
	public void changeEtatDessinSommet(boolean b){
		sommetActif=b;
	}
	public void changeEtatDessinArc(boolean b){
		arcActif=b;
	}
	public boolean isAnyOneSelected(){
		return SELECTED>-1;
	}
	public int nombreSommet(){
		return nombreSommet;
	}
	public int getSelectedSommet(){
		return SELECTED;
	}
	public void setLocked(boolean b){
		Locked=b;
	}
	public boolean peutValider(){
		boolean okOrigine=false,okFin=false;
		for (int i=0;i<nombreArc;i++){
			if (arc[i].getOrigine()==0) okOrigine=true;
			if (arc[i].getDestination()==nombreSommet-1) okFin=true;
			if (okOrigine && okFin) break;
		}
		return okOrigine && okFin;
	}
	public Arc[] getArc(){
		Arc [] a=new Arc[nombreArc];
		for (int i=0;i<nombreArc;i++){
			a[i]=new Arc(arc[i].getOriginePoint().x,arc[i].getOriginePoint().y,arc[i].getOrigine());
			a[i].setDestination(arc[i].getDestination());
			a[i].setDestinationX(arc[i].getDestinationPoint().x);
			a[i].setDestinationY(arc[i].getDestinationPoint().y);
			a[i].setValeur(arc[i].getValeur());
		}
		return a;
  }
	public Sommet[] getSommet(){
		Sommet [] s=new Sommet[nombreSommet];
		for (int i=0;i<nombreSommet;i++) s[i]=new Sommet(sommet[i].getLocation().x,sommet[i].getLocation().y,sommet[i].label);
		return s;
  }
  public int getSommetCount(){
		return nombreSommet;
	}
	public int getArcCount(){
		return nombreArc;
	}
}
