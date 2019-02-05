package Main;

import Modele.Humain;
import Modele.IA;
import Modele.Jeu;
import Modele.Joueur;
import Vue.VueGrille;

public class Main
{
    public static void main(String [] args)
    {
        Joueur j1 = new Humain();
        Joueur j2 = new IA();
        Jeu jeu = new Jeu(j1, j2);
        VueGrille grilledeJeu = new VueGrille(jeu);
        jeu.addObserver(grilledeJeu);
    }
}
