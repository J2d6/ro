/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimale;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author BenjaRaym
 */
public class Ford extends JFrame implements ActionListener{
    JTabbedPane onglet;
    Dessin desPane;
    JButton tbNouveau,tbFermer,tbValider,tbArc,tbSommet;
String nomProjet="";
    boolean Locked=false;
    boolean zoneW=false;
    private JLabel Msg;
     public Ford() {
     super(" TP RO : Chemin Optimal Ford SIGTD2 2016");
           setSize(new Dimension(800, 600));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
		setResizable(false);
				getContentPane().add(makeToolBar(),BorderLayout.NORTH);
        getContentPane().add(BarDeMessage(),BorderLayout.SOUTH);
        onglet=new JTabbedPane();
        onglet.setVisible(false);
        getContentPane().add(onglet);
        //setResizable(false);
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((ScreenSize.width-getSize().width)/2,(ScreenSize.height-getSize().height)/2);
       
        //getContentPane().add(new dessin());
        
        activeBoutons(zoneW);
        setVisible(true);
    }
    private JToolBar BarDeMessage(){
    	JToolBar toolBar=new JToolBar();
    	toolBar.setFloatable(false);
      toolBar.addSeparator();
      toolBar.add(Msg=new JLabel(" "));
      return toolBar;
		}
		private JToolBar makeToolBar(){
				JToolBar toolBar=new JToolBar();
				toolBar.setFloatable(false);
        toolBar.add(tbNouveau=new JButton ("New"));
         toolBar.addSeparator();
        toolBar.add(tbSommet=new JButton ("Sommet"));
        toolBar.addSeparator();
        toolBar.add(tbArc=new JButton ("Arc"));
        toolBar.addSeparator();

        /*toolBar.add(TBLock=new JButton(new ImageIcon("lock.gif")));
        toolBar.addSeparator();*/
        toolBar.add(tbValider=new JButton ("Executer"));
        toolBar.addSeparator();
        toolBar.add(tbFermer=new JButton("Fermer"));

        tbNouveau.setToolTipText("Nouveau");
        tbFermer.setToolTipText("Fermer");
        tbSommet.setToolTipText("Sommet");
        tbArc.setToolTipText("Arc");
        //Lock.setToolTipText("Lecture seule");
        tbValider.setToolTipText("Valider");
        
        tbSommet.setEnabled(false);
		tbArc.setEnabled(false);
				//tbLock.setEnabled(false);
		tbValider.setEnabled(false);
			
        tbNouveau.setFocusPainted(false);
        tbFermer.setFocusPainted(false);
        tbSommet.setFocusPainted(false);
        tbArc.setFocusPainted(false);
        //tbLock.setFocusPainted(false);
        tbValider.setFocusPainted(false);
        
        tbFermer.addActionListener(this);
        tbNouveau.addActionListener(this);
        //tbLock.addActionListener(this);
        tbSommet.addActionListener(this);
        tbArc.addActionListener(this);
        tbValider.addActionListener(this);
        
        return toolBar;
		}

   public void NouveauProjet(){
			String s="";
			while (s!=null && s.equals("")){
				s=JOptionPane.showInputDialog(null,"Nom du zone de travail","Zone de Travail",JOptionPane.QUESTION_MESSAGE);
				if (s!=null && s.equals(""))
					showError(1);
			}
			if (s!=null){
				onglet.setVisible(true);
				nomProjet=s;
				nomProjet="Zone 1";
				setTitle("Ford ["+s+"]");
				onglet.add(s,desPane=new Dessin());
				desPane.setFrameOwner(this);
				tbSommet.setEnabled(true);
				//tbFermer.setEnabled(true);
				zoneW=true;
				activeBoutons(zoneW);
				//activeBoutons(true);
			}
		}
	public void FermeProjet(){
		while (onglet.getTabCount()>0){
			onglet.removeTabAt(0);
      	}
      	nomProjet="";
      	zoneW=false;
      	//Locked=false;
      	activeBoutons(zoneW);
    }
    private void activeBoutons(boolean b){
      tbFermer.setEnabled(b);
      tbNouveau.setEnabled(!b);
      tbSommet.setEnabled(b);
      tbValider.setEnabled(b);
      //tbLock.setEnabled(b);
      resetMessage();
    }
		public void showError(int i){
			String s="";
			switch (i){
				case 1	: s="Nom Zone Travail vide";break;
				case 2	: s="";break;
			}
			JOptionPane.showMessageDialog(null,s,"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		public void showMessage(int i){
			String s="";
			switch (i){
				case 1	: s="Veuillez selectionner un sommet";break;
				case 2	: s="Le dernier sommet ne doit pas avoir d'arc";break;
				case 3	: s="Veuillez placer un sommet";break;
			}
			JOptionPane.showMessageDialog(null,s,"Attention",JOptionPane.INFORMATION_MESSAGE);
		}
		public void setMessage(String s){
			Msg.setText(s);
		}
		public void setMessage(String s,Icon ic){
			Msg.setText(s);
			Msg.setIcon(ic);
		}
		public void resetMessage(){
			Msg.setText(" ");
			Msg.setIcon(null);
		}

		// desactivation des boutons
		public void setEnableArcButton(boolean b){
			tbArc.setEnabled(b);
		}
		public void setEnableSommetButton(boolean b){
			tbSommet.setEnabled(b);
		}
		public void setEnableValiderButton(boolean b){
			tbValider.setEnabled(b);
		}

    public void actionPerformed(ActionEvent e) {
       if ((e.getSource()==tbNouveau) && !zoneW) NouveauProjet();
       if (e.getSource()==tbFermer) FermeProjet();
       if (e.getSource()==tbSommet && desPane!=null){
				desPane.changeEtatDessinSommet();
				desPane.changeEtatDessinArc(false);
				//setDisabledIcon(Icon disabledIcon);
				//setFocusPainted(boolean b);
				if (desPane.getEtatDessinSommet()){
					setMessage("On peut placer les sommets",new ImageIcon("sary.gif"));
				}
				else resetMessage();
				tbArc.setEnabled(!desPane.getEtatDessinSommet());

				if (desPane.nombreSommet()-1==desPane.getSelectedSommet()) tbArc.setEnabled(false);
			}
			if (e.getSource()==tbArc && desPane!=null){
				if (!desPane.isAnyOneSelected() || desPane.nombreSommet()-1==desPane.getSelectedSommet()){
					int i=1;
					if (desPane.isAnyOneSelected()) i=2;
					if (desPane.nombreSommet()==0) i=3;
					showMessage(i);
					}
				else {
					boolean dessigneDeja=desPane.getEtatDessinArc();
					desPane.changeEtatDessinArc(true);
					desPane.changeEtatDessinSommet(false);
					resetMessage();
					//TBSommet.setEnabled(!DessinPane.getEtatDessinArc());
					if (!dessigneDeja){
						setMessage("On peut placer l'arc",new ImageIcon("arc.gif"));
						tbSommet.setEnabled(false);
						desPane.ajoutArc();
					}
					else {
						desPane.annuleAjoutArc();
						tbSommet.setEnabled(true);
					}
				}
			}
    }
   

}
