package main;

import modele.Humain;
import modele.IA;
import modele.Jeu;
import modele.Joueur;
import vue.VueGrille;
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
            Joueur j1 = new Humain(i);
            jeu.ajoutJoueur(j1);
        }
        for(int j = 0; j < 2-nbJoueurHumain; j++)
        {
            Joueur j2 = new IA(j+1);
            jeu.ajoutJoueur(j2);
        }
        VueGrille grilleDeJeu = new VueGrille(jeu);
        jeu.addObserver(grilleDeJeu);
    }
}
