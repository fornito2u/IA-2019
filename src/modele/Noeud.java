package modele;

import java.util.ArrayList;

public class Noeud {

    private Noeud parent;
    private ArrayList<Noeud> enfant;
    private Etat etat;
    private int nbVictoires;
    private int nbDefaites;

    public Noeud(Etat etat) {
        this.etat = etat;
        this.nbVictoires=0;

    }

    //Est-ce un noeud joueur ou un noeud IA ?
    //true joueur
    //false IA
    public boolean estNoeudHumain() {
        return (this.etat.getJoueur() == 1);
    }
}
