package modele;

import java.util.ArrayList;

public class Noeud {

    private Noeud parent;
    private ArrayList<Noeud> enfant;
    private Etat etat;
    private int nbVictoires;
    private int nbSimulation;

    public Noeud(Etat etat) {
        this.etat = new Etat(etat);
        this.nbVictoires = 0;
        this.nbSimulation = 0;
        enfant = new ArrayList<>();
    }

    public Noeud(Noeud noeudParent, Coup coup){
        this.parent=noeudParent;
        Etat etat=new Etat(this.parent.getEtat());
        etat.jouerCoup(coup);
        this.etat=etat;
        this.nbVictoires = 0;
        this.nbSimulation = 0;
        enfant = new ArrayList<>();

    }



    //Est-ce un noeud joueur ou un noeud IA ?
    //true joueur
    //false IA
    public boolean estNoeudHumain() {
        return (this.etat.getJoueur() == 1);
    }

    public Etat getEtat() {
        return etat;
    }
}
