package modele;

import java.util.ArrayList;
import java.util.Random;

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

    /*public Noeud selection() {
        int filsPossible = 0;
        if(this.state.getEnd()!= Puissance4.End.NO) {
            return this;
        }else{
            for(int i = 0; i < 7 ; i++){
                if(state.mouvementValide(i)){
                    filsPossible++;
                }
            }
            if(this.filsListe.isEmpty()||this.filsListe.size() < filsPossible){
                return this;
            }else{
                double max =this.getFils(0).getBeta();
                int selection = 0;
                for(int i = 0; i < filsListe.size();i++){
                    if(this.getFils(i).getBeta()>max){
                        max = this.getFils(i).getBeta();
                        selection = i;
                    }
                }
                return getFils(selection).selection();
            }
        }
    }*/

    // return un int correspondant
    // Etat.J1_GAGNE
    // Etat.J2_GAGNE
    // Etat.MATCH_NUL
    public int simulation() {
        Etat etatEncours=new Etat(this.etat);
        int finPartie=etatEncours.testFin();
        //Si la partie est finie
        while (finPartie == Etat.PARTIE_EN_COURS) {
            ArrayList<Coup> coupsPossibles=etatEncours.coupsPossibles();
            Random random= new Random();
            int indiceCoup=random.nextInt(coupsPossibles.size());
            etatEncours.jouerCoup(coupsPossibles.get(indiceCoup));
            finPartie=etatEncours.testFin();
        }
        return finPartie;
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
