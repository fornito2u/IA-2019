package modele;

import java.util.Arrays;
import java.util.Observable;

public class Jeu extends Observable {
    /**
     * Etat en cours du jeu (Grille du jeu, 0 la case est vide, 1 la case appartient au joueur1, 2 la case appartient au joueur2)
     */
    private Etat etat;
    private Joueur[] listeJoueurs;
    private int nbTour;

    public Jeu() {
        this.etat = new Etat(4,5,(byte)1);
        this.listeJoueurs = new Joueur[2];
        this.nbTour = 0;
    }

    public void ajoutJoueur(Joueur joueur) {
        for(int i = 0; i < 2 ; i++) {
            if(this.listeJoueurs[i] == null) {
                this.listeJoueurs[i] = joueur;
                return;
            }
        }
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
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
        return "Jeu\n{\n" +
                "grilleDeJeu=\n" + etat.toString() +
                "listeJoueurs=" + Arrays.toString(listeJoueurs) +
                "\nnbTour=" + nbTour +
                "\n}";
    }
}
