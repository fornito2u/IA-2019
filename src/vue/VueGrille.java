package vue;

import controleur.BoutonControleur;
import modele.Etat;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class VueGrille extends JPanel implements Observer {
    private Etat etat;
    private JButton[][] grilleBouton;
    private GridLayout gridLayout;
    private JPanel panelJeu;

    public static final Color specialBlue=new Color(100, 135, 255);

    public VueGrille(Etat etatInitial) {
        super();
        this.etat = etatInitial;
        byte[][] tableau=etat.getTableau();
        this.grilleBouton = new JButton[tableau.length][tableau[0].length];
        this.gridLayout=new GridLayout(grilleBouton.length,grilleBouton[0].length);
        this.panelJeu=new JPanel(gridLayout);
        for (int i=0;i<grilleBouton.length;i++) {
            for (int j=0;j<grilleBouton[0].length;j++) {
                this.grilleBouton[i][j]=new JButton(Textures.iconeBlanche);
                this.grilleBouton[i][j].setPreferredSize(new Dimension(100, 100));
                this.grilleBouton[i][j].setBorder(new LineBorder(Color.BLACK,2));
                this.grilleBouton[i][j].setBackground(specialBlue);
                this.grilleBouton[i][j].addActionListener(new BoutonControleur(etat, j));
                panelJeu.add(this.grilleBouton[i][j]);
            }
        }
        this.add(panelJeu);
    }

    @Override
    public void update(Observable o, Object arg) {
        byte[][] tableau=etat.getTableau();
        for (int i=0;i<grilleBouton.length;i++) {
            for (int j=0;j<grilleBouton[0].length;j++) {
                if (tableau[i][j] == 0) {
                    this.grilleBouton[i][j].setIcon(Textures.iconeBlanche);
                } else if (tableau[i][j] == 1) {
                    this.grilleBouton[i][j].setIcon(Textures.iconePikachu);
                } else {
                    this.grilleBouton[i][j].setIcon(Textures.iconeRondoudou);
                }
            }
        }
    }
}
