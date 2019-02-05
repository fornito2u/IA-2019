package main;

import modele.Humain;
import modele.IA;
import modele.Jeu;
import modele.Joueur;
import vue.VueGrille;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main
{
    public static void main(String [] args)
    {
        Jeu jeu = new Jeu();
        Scanner sc = new Scanner(System.in);
        int nbJoueurHumain;
        System.out.println("Combien de joueur humain voulez-vous ? Choisir un nombre entre 0 et 2 inclus");
        nbJoueurHumain = sc.nextInt();
        while(nbJoueurHumain < 0 || nbJoueurHumain > 2)
        {
            System.out.println("Le nombre entré est invalide. Combien de joueur humain voulez-vous ?");
            nbJoueurHumain = sc.nextInt();
        }
        for(int i = 1; i <= nbJoueurHumain; i++)
        {
            Joueur j1 = new Humain();
            jeu.ajoutJoueur(j1);
        }
        for(int j = 0; j < 2-nbJoueurHumain; j++)
        {
            Joueur j2 = new IA();
            jeu.ajoutJoueur(j2);
        }
        VueGrille grilleDeJeu = new VueGrille(jeu);
        jeu.addObserver(grilleDeJeu);


        Joueur[] listeJoueurs=jeu.getListeJoueurs();
        for(int i = 0; i < 2; i++)
        {
            System.out.println(listeJoueurs[i]);
        }
        testGraphique();
    }

    private static void testGraphique() {
        JFrame frame = new JFrame("Puissance 4");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int longueur=4;
        int largeur=4;
        GridLayout gridLayout=new GridLayout(longueur,largeur);
        JPanel panelJeu=new JPanel(gridLayout);
        for (int i=0;i<longueur*largeur;i++) {
            panelJeu.add(new JButton(""+i));
        }
        frame.add(panelJeu);
        frame.pack();
        frame.setVisible(true);
    }
}
