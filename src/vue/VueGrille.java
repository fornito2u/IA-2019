package vue;

import controleur.BoutonControleur;
import modele.Etat;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueGrille extends JPanel implements Observer {

    private Jeu jeu;
    private Etat etat;
    private JButton[][] grilleBouton;
    private JPanel conteneurPanel;
        private JPanel infoPanel;
            private JLabel infoLabel;
        private JPanel grillePanel;

    //public static final Dimension espaceInfos=new Dimension(0,10);
    public static final Color specialBlue=new Color(100, 135, 255);

    public VueGrille(Etat etatInitial, Jeu jeu) {
        super();
        this.jeu=jeu;
        jeu.setGrille(this);
        this.etat = etatInitial;
        byte[][] tableau=etat.getTableau();
        this.grilleBouton = new JButton[tableau.length][tableau[0].length];


        this.conteneurPanel = new JPanel();
        this.conteneurPanel.setLayout(new BoxLayout(this.conteneurPanel,BoxLayout.Y_AXIS));
            this.infoPanel = new JPanel();
            this.infoPanel.setBackground(Color.black);
                this.infoLabel=new JLabel("");
                this.infoLabel.setForeground(Color.white);
                this.miseAJourJLabel(etatInitial.getJoueur());
            this.infoPanel.add(this.infoLabel);

            this.grillePanel = new JPanel(new GridLayout(grilleBouton.length,grilleBouton[0].length));

        for (int i=0;i<grilleBouton.length;i++) {
            for (int j=0;j<grilleBouton[0].length;j++) {
                this.grilleBouton[i][j]=new JButton(Textures.iconeBlanche);
                this.grilleBouton[i][j].setPreferredSize(new Dimension(100, 100));
                this.grilleBouton[i][j].setBorder(new LineBorder(Color.BLACK,2));
                this.grilleBouton[i][j].setBackground(specialBlue);
                this.grilleBouton[i][j].addActionListener(new BoutonControleur(etat, j,this.jeu));
                grillePanel.add(this.grilleBouton[i][j]);
            }
        }


        conteneurPanel.add(infoPanel);
        //conteneurPanel.add(Box.createRigidArea(espaceInfos));
        conteneurPanel.add(grillePanel);
        this.add(conteneurPanel);
    }

    public Etat getEtat() {
        return etat;
    }

    public void finPartie() {
        for (int i=0;i<grilleBouton.length;i++) {
            for (int j=0;j<grilleBouton[0].length;j++) {
                this.grilleBouton[i][j].removeActionListener(this.grilleBouton[i][j].getActionListeners()[0]);
            }
        }
    }

    public void miseAJourJLabel(byte joueur) {
        if (joueur == 1) {
            this.infoLabel.setText("Pikachu doit jouer");
        } else {
            this.infoLabel.setText("Rondoudou doit jouer");
        }
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
        this.miseAJourJLabel(etat.getJoueur());
        int testFin=etat.testFin();
        if (testFin != Etat.PARTIE_EN_COURS) {
            if (testFin == Etat.J1_GAGNE) {
                this.infoLabel.setText("Pikachu a gagné");
            } else if (testFin == Etat.J2_GAGNE) {
                this.infoLabel.setText("Rondoudou a gagné");
            } else {
                this.infoLabel.setText("Match nul");
            }
            finPartie();
        } else {
            //Si c'est 1 IA 1 joueur
            if (jeu.getNbJoueurHumains() == 1) {
                //Si c'est à L'IA de jouer
                if (this.etat.getJoueur() == 2) {
                    (jeu.getListeJoueurs()[1]).jouer(jeu);
                }
            }
        }
    }
}
