package modele;

import java.util.ArrayList;
import java.util.Random;

public class Noeud {

    private Noeud parent;
    private ArrayList<Noeud> enfant;
    private Etat etat;
    private int nbVictoires;
    private int nbParties;
    private int UCT;

    ///////////////////////////////////////////////////////////////////
    // Rajouté (Marvin)
    // Correspond à la couleur du dernier pion posé sur le plateau. Vrai si de la couleur pikachu.
    // Utile pour l'étape 4 de l'algorithme de Monte Carlo UCT (Backpropagation)
    private boolean couleurPikachu;
    ///////////////////////////////////////////////////////////////////

    public Noeud(Etat etat) {
        this.etat = new Etat(etat);
        this.nbVictoires = 0;
        this.nbParties = 0;
        enfant = new ArrayList<>();

        ///////////////////////////////////////////////////////////////////
        // Rajouté (Marvin)
        // Si le joueur qui doit jouer est le joueur 1 (Pikachu),
        // alors le dernier pion a était posé par le joeur 2 (Rondoudou)
        if(this.etat.getJoueur()==1)
        {
            this.couleurPikachu = false;
        }
        else
        {
            this.couleurPikachu = true;
        }
        ///////////////////////////////////////////////////////////////////
    }

    public Noeud(Noeud noeudParent, Coup coup){
        this.parent=noeudParent;
        Etat etat=new Etat(this.parent.getEtat());
        etat.jouerCoup(coup);
        this.etat=etat;
        this.nbVictoires = 0;
        this.nbParties = 0;
        enfant = new ArrayList<>();
    }

    // Etape 1 : Selection
    // Depuis la racine, on selectionne successivement des enfants jusqu'à atteindre une feuille.
    // Le choix des enfants est déterminé par la valeur UCT des noeuds (On choisi le noeud avec l'UCT le plus grand)
    public Noeud selection() {
        Noeud noeudSelectionne = null;

        // TODO

        return noeudSelectionne;
    }

    // Etape 2 : Développement
    // A utiliser uniquement si l'état du noeud actuel n'est pas un état finale.
    // Retourne un noeud enfant ayant +1 jeton sur la grille de son Etat.
    public Noeud developpement() {
        Noeud noeudEnfant = null;

        // TODO

        return noeudEnfant;
    }

    // TODO (Fonction simulation() a tester)
    // Etape 3 : Simulation
    // Simulation la fin d'une partie à partir du noeud courant
    // Retourne le résultat de la partie sous la forme d'un entier respectant le code suivant :
    // Etat.J1_GAGNE => 1
    // Etat.J2_GAGNE => 2
    // Etat.MATCH_NUL => 3
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

    // Etape 4 : Backpropagation
    // Utilise le résultat de la simulation effectué au préalable pour mettre à jour les attributs nbVictoire et nbParties
    // du noeud courant et des noeuds entre la racine et le noeud courant.
    // Met ensuite  à jour les informations la valeur UCT du noeud courant et des noeuds entre la racine et le noeud courant.
    // Le Noeud Courant correspond au noeud feuille sur lequel on a effectué la simulation.
    public void backPropagation(int resultatSimulation){

        // Partie 1 : Mettre à jour pour le noeud courant les attributs nbParties et nbVictoires
        this.nbParties += 1;
        if(resultatSimulation == this.etat.J1_GAGNE)
        {
            this.nbVictoires += 1;
        }

        // Partie 2 : Mettre à jour pour tous les noeuds entre la racine et le noeud courant
        // les attributs nbParties et nbVictoires
        // TODO

        // Partie 3 : Mettre à jour la valeur UCT du noeud courant
        // TODO

        // Partie 4 : Mettre à jour la valeur UCT pour tous les noeuds entre la racine et le noeud courant
        // TODO
    }

    // Sous méthode utilsé dans l'étape 4 de UCT par la méthode backPropagation ci-dessus
    // Permet de calculer la valeur UCT du noeud courant (Le noeud "this")
    public int calculeUCT()
    {
        int UCT = 0;

        // Calcule de la valeur UCT du noeud courant en fonction des attributs nbParties et nbVictoires
        // du noeud courant et du noeud pere au noeud courant
        //TODO

        return UCT;
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
