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
        Jeu jeu = new Jeu();
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
        Etat etatInitial=new Etat(6,6,(byte)1);
        VueGrille grilleDeJeu = new VueGrille(etatInitial,jeu);

        etatInitial.addObserver(grilleDeJeu);
        testGraphique(grilleDeJeu);
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
                if (nbJoueurHumain >= 1 || nbJoueurHumain <= 2) {
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
}
