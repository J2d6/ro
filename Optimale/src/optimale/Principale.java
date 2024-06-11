/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimale;
import java.awt.*;
import javax.swing.*;
import java.lang.Math.*;
import java.awt.event.*;


/**
 *
 * @author BenjaRaym
 */
public  class Principale extends JFrame implements ActionListener{
	JMenuBar mBar;
	JMenu mFichier,mApropos;
	JMenuItem mFord,mDantzig,mDemoucron,mQuitter,mCpr;
	static Ford frd;
	static Dantzig dnz;
	static Demoucron dem;

	public Principale(){
		setTitle("TP RO : Ford-Dantzig-Demoucron CUFP-SIGTD2 2016 Copyright: Savigny & Raymond");
		//setIconImage(new ImageIcon("images/icone.jpg").getImage());
		setContentPane( new FondImage() ) ;
		mBar = new JMenuBar();
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		setJMenuBar(mBar);

		//JMenu
		mFichier = new JMenu("Fichier");
		mBar.add(mFichier);

		mApropos= new JMenu("A Propos");
		mBar.add(mApropos);

					//JMenuItem
        mFord = new JMenuItem("Ford-Fulkerson");
			mFord.setBackground(Color.darkGray);
			mFord.setForeground(Color.white);
			mFichier.add(mFord);

           

            mFichier.addSeparator();


			mQuitter = new JMenuItem("Quitter");
			mQuitter.setBackground(Color.darkGray);
			mQuitter.setForeground(Color.white);
			mQuitter.setIcon(new ImageIcon("src/images/bquitter.jpg"));
			mFichier.add(mQuitter);


			//Ecouteur d'evennement
			mFord.addActionListener(this);
			mQuitter.addActionListener(this);

			setVisible(true);
	}
    public void actionPerformed(ActionEvent evt){
		Object obj = evt.getSource();

		if (obj.equals(mQuitter)){
			int reponse = JOptionPane.showConfirmDialog(this,"Voulez-vous quitter l'application?","TP Chemin Optimale",JOptionPane.YES_NO_OPTION);
			if (reponse == 0){
				System.exit(0);
			}
		}
		else if (obj.equals(mFord)){
			frd = new Ford();
		}
/*
		else if (obj.equals(mDantzig)){
			dnz = new Dantzig(this);
		}

		else if (obj.equals(mDemoucron)){
			dmc = new Demoucron(this);
		}*/
    }
	
	//Fond
	public class FondImage extends JPanel {
		private Image img ;
		public FondImage() {
			setLayout( new BorderLayout() ) ;
			img = new ImageIcon( "src/images/fond.jpg" ).getImage() ;
		}
		public void drawBackground( Graphics g ) {
			int w = getWidth() ;
			int h = getHeight() ;
			int iw = img.getWidth( this ) ;
			int ih = img.getHeight( this ) ;
			for( int i = 0 ; i < w ; i+=iw ) {
				for( int j = 0 ; j < h ; j+= ih ) {
					g.drawImage( img , i , j , this ) ;
				}
			}
		}
        @Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawBackground( g ) ;
		}
	}


    public static void main(String[] args) {
        // TODO code application logic here
        new Principale();
    }

}