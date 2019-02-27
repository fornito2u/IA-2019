package modele;

import vue.VueGrille;

import java.util.Arrays;
import java.util.Observable;

public class Jeu extends Observable {

    private VueGrille grille;
    private Joueur[] listeJoueurs;
    private int nbTour;

    private int nbJoueurHumains;

    public Jeu() {
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

    public int getNbJoueurHumains() {
        return nbJoueurHumains;
    }

    public void setNbJoueurHumains(int nbJoueurHumains) {
        this.nbJoueurHumains = nbJoueurHumains;
    }

    public void setGrille(VueGrille grille) {
        this.grille = grille;
    }

    public VueGrille getGrille() {
        return grille;
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
                "listeJoueurs=" + Arrays.toString(listeJoueurs) +
                "\nnbTour=" + nbTour +
                "\n}";
    }
}
