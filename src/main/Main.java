package main;

import modele.*;
import vue.Textures;
import vue.VueGrille;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) {
        /*Jeu jeu = new Jeu();
        int nbJoueurHumain=selectionJoueur();
        for(int i = 1; i <= nbJoueurHumain; i++) {
            Joueur j1 = new Humain();
            jeu.ajoutJoueur(j1);
        }
        for(int j = 0; j < 2-nbJoueurHumain; j++) {
            Joueur j2 = new IA();
            jeu.ajoutJoueur(j2);
        }

        jeu.setNbJoueurHumains(nbJoueurHumain);
        Etat etatInitial=new Etat(6,7,(byte)1);
        VueGrille grilleDeJeu = new VueGrille(etatInitial,jeu);

        etatInitial.addObserver(grilleDeJeu);
        testGraphique(grilleDeJeu);*/
        testSimulation();
    }

    public static int selectionJoueur() {
        String messageSelection="Combien de joueur humain voulez-vous (1-2) ?";
        String entreeInvalide="Entrée invalide";
        String messageInvalide="Choisir un nombre entre 1 et 2 inclus";
        while(true) {
            int nbJoueurHumain=1;
            String nbJoueurHumainString=JOptionPane.showInputDialog(messageSelection,nbJoueurHumain);
            try {
                nbJoueurHumain=Integer.parseInt(nbJoueurHumainString);
                if (nbJoueurHumain >= 1 && nbJoueurHumain <= 2) {
                    return nbJoueurHumain;
                }
            } catch (NumberFormatException e) {
                System.out.println("Sélection invalide");
            }
            JOptionPane.showMessageDialog(null,messageInvalide,entreeInvalide,JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void testGraphique(JPanel grilleDeJeu) {
        //On utilise le rendu par défaut
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Puissance 4");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(grilleDeJeu);
        frame.pack();
        frame.setVisible(true);
    }

    /*private static void testSelection() {
        Etat etatInitial=new Etat(3,3,(byte)1);

        //etatInitial.jouerCoup(new Coup(0));

        Noeud noeudRacineArbre = new Noeud(etatInitial);
        noeudRacineArbre.setUctTemp(1);
        Noeud fils1=new Noeud (noeudRacineArbre,new Coup(0));
        fils1.setUctTemp(1.1);
        Noeud fils2=new Noeud (noeudRacineArbre,new Coup(1));
        fils2.setUctTemp(1.5);
        Noeud fils3=new Noeud (noeudRacineArbre,new Coup(2));
        fils3.setUctTemp(1.3);
        Noeud fils2_2=new Noeud (fils2,new Coup(1));
        fils2_2.setUctTemp(1.7);

        System.out.println(noeudRacineArbre.selection().getEtat());

    }

    private static void testDeveloppement() {
        Etat etatInitial=new Etat(3,3,(byte)1);

        //etatInitial.jouerCoup(new Coup(0));

        Noeud noeudRacineArbre = new Noeud(etatInitial);
        noeudRacineArbre.setUctTemp(1);
        Noeud fils1=new Noeud (noeudRacineArbre,new Coup(0));
        fils1.setUctTemp(1.1);
        Noeud fils2=new Noeud (noeudRacineArbre,new Coup(1));
        fils2.setUctTemp(1.5);
        Noeud fils3=new Noeud (noeudRacineArbre,new Coup(2));
        fils3.setUctTemp(1.3);
        Noeud fils2_2=new Noeud (fils2,new Coup(1));
        fils2_2.setUctTemp(1.7);

        Noeud noeudSelection=noeudRacineArbre.selection();
        noeudSelection.developpement();
        Noeud noeudFils=noeudSelection.getPremierEnfant();
        System.out.println(noeudFils.getEtat());
    }*/


    private static void testSimulation() {
        Etat etatInitial=new Etat(4,4,(byte)1);

        byte[][] tableau={{0,0,0,0},{2,2,2,0},{2,2,2,0},{2,2,2,0}};
        etatInitial.setTableau(tableau);
        //etatInitial.jouerCoup(new Coup(0));

        Noeud noeudRacineArbre = new Noeud(etatInitial);

        /*noeudRacineArbre.setUctTemp(1);
        Noeud fils1=new Noeud (noeudRacineArbre,new Coup(0));
        fils1.setUctTemp(1.1);
        Noeud fils2=new Noeud (noeudRacineArbre,new Coup(1));
        fils2.setUctTemp(1.5);
        Noeud fils3=new Noeud (noeudRacineArbre,new Coup(2));
        fils3.setUctTemp(1.3);
        Noeud fils2_2=new Noeud (fils2,new Coup(1));
        fils2_2.setUctTemp(1.7);*/

        /*Noeud noeudSelection=noeudRacineArbre.selection();
        noeudSelection.developpement();
        Noeud noeudFils=noeudSelection.getPremierEnfant();*/
        int resultat=noeudRacineArbre.simulation();
        System.out.println(resultat);
    }
}
