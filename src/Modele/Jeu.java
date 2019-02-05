package Modele;

import java.util.Arrays;
import java.util.Observable;

public class Jeu extends Observable
{
    // Grille du jeu, 0 la lace est vide, 1 la case appartient au joueur1, 2 la case appartient au joueur2
    private int[][] grilleDeJeu;
    private Joueur[] listeJoueurs;
    private int nbTour;

    public Jeu(Joueur joueur1, Joueur joueur2)
    {
        this.grilleDeJeu = new int[7][6];
        for(int i = 0; i < 7; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                this.grilleDeJeu[i][j] = 0;
            }
        }
        this.listeJoueurs = new Joueur[2];
        this.listeJoueurs[0] = joueur1;
        this.listeJoueurs[1] = joueur2;
        this.nbTour = 0;
    }

    public void update()
    {
        notifyObservers();
    }

    public void setGrilleDeJeu(int[][] grilleDeJeu)
    {
        this.grilleDeJeu = grilleDeJeu;
    }

    public int[][] getGrilleDeJeu()
    {
        return grilleDeJeu;
    }

    public void setListeJoueurs(Joueur[] listeJoueurs)
    {
        this.listeJoueurs = listeJoueurs;
    }

    public Joueur[] getListeJoueurs()
    {
        return listeJoueurs;
    }

    public void setNbTour(int nbTour)
    {
        this.nbTour = nbTour;
    }

    public int getNbTour()
    {
        return nbTour;
    }

    @Override
    public String toString()
    {
        return "Jeu{" +
                "grilleDeJeu=" + Arrays.toString(grilleDeJeu) +
                ", listeJoueurs=" + Arrays.toString(listeJoueurs) +
                ", nbTour=" + nbTour +
                '}';
    }
}
