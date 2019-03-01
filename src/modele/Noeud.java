package modele;

import java.util.ArrayList;
import java.util.Random;

public class Noeud {

    private Noeud parent;
    private ArrayList<Noeud> enfants;
    private Etat etat;
    private int nbVictoires;
    private int nbParties;
    //Le coup que l'on a utilisé pour arriver à ce noued
    private Coup coupAssocie;

   // private double uctTemp;

    //La constante c utilisé dans le calcul de la valeur UCT du noeud
    public static double c=Math.sqrt(2);

    public Noeud(Etat etat) {
        this.etat = new Etat(etat);
        this.nbVictoires = 0;
        this.nbParties = 0;
        this.enfants = new ArrayList<>();
        this.coupAssocie=null;
    }

    public Noeud(Noeud noeudParent, Coup coup){
        this.parent=noeudParent;
        this.coupAssocie=coup;
        this.parent.getEnfants().add(this);
        Etat etat=new Etat(this.parent.getEtat());
        etat.jouerCoup(this.coupAssocie);
        this.etat=etat;
        this.nbVictoires = 0;
        this.nbParties = 0;
        this.enfants = new ArrayList<>();

    }

    // Etape 1 : Selection
    // Depuis la racine, on selectionne successivement des enfants jusqu'à atteindre une feuille.
    // Le choix des enfants est déterminé par la valeur UCT des noeuds (On choisi le noeud avec l'UCT le plus grand)
    // et on ne descend pas dans un noeud dont l'UCT est plus petit
    // L'UCT est calculé dynamiquement à chaque fois que l'on descend dans l'arbre.
    public Noeud selection() {
        Noeud noeudSelectionne = this;

        if (noeudSelectionne.estNoeudFeuille()) {
            return noeudSelectionne;
        } else {
            Noeud noeudActuel=noeudSelectionne;
            double uctActuel=noeudActuel.calculeUCT();

            noeudSelectionne=this.noeudEnfantUCTMax();

            if (noeudSelectionne.calculeUCT() > uctActuel) {
                noeudSelectionne=noeudSelectionne.selection();
            } else {
                return noeudActuel;
            }
        }

        return noeudSelectionne;
    }

    // Sous méthode utilsé dans l'étape 1 de UCT par la méthode sélection ci-dessus
    // Return parmi les enfants du noeud courant, le noeud avec le plus grand UCT
    public Noeud noeudEnfantUCTMax() {
        if (this.estNoeudFeuille() == true) {
            System.err.println("Erreur le noeud n'a pas d'enfant");
            return null;
        } else {
            double maxUCT=0.0;
            int maxIndice=-1;

            for (int i=0;i<this.enfants.size();i++) {
                double uctCourant=this.enfants.get(i).calculeUCT();
                if (uctCourant > maxUCT) {
                    maxUCT=uctCourant;
                    maxIndice=i;
                }
            }
            return this.enfants.get(maxIndice);
        }
    }

    // Sous méthode utilsé dans l'étape 1 de UCT par la méthode sélection ci-dessus
    // Permet de calculer la valeur UCT du noeud courant (Le noeud "this")
    // Calcule de la valeur UCT du noeud courant en fonction des attributs nbParties et nbVictoires
    // du noeud courant et du noeud pere au noeud courant
    public double calculeUCT()
    {
        //return this.uctTemp;
        if (estNoeudRacine()) {
            return 1;
        } else {
            double UCT = 0.0;
            double pourcentageVictoire=1.0*nbVictoires/nbParties;

            if (!estNoeudHumain()) {
                pourcentageVictoire=-pourcentageVictoire;
            }
            UCT=pourcentageVictoire+c* Math.sqrt(Math.log(this.parent.getNbParties() / Math.log(this.nbParties)));
            return UCT;
        }
    }

    /*public double getUctTemp() {
        return uctTemp;
    }

    public void setUctTemp(double uctTemp) {
        this.uctTemp = uctTemp;
    }*/

    // Etape 2 : Développement
    // A utiliser uniquement si l'état du noeud actuel n'est pas un état final.
    // Crée un noeud enfant au noeud actuel
    public void developpement() {
        ArrayList<Coup> coupsPossibles=this.etat.coupsPossibles();

        if (coupsPossibles.size() > 0 && this.etat.testFin() == Etat.PARTIE_EN_COURS) {
            Random rand=new Random();
            int indiceCoup=rand.nextInt(coupsPossibles.size());
            new Noeud(this, coupsPossibles.get(indiceCoup));
        }
        else {
            System.err.println("Erreur : Aucun coup possible car le noeud représente un état final");

        }

    }


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
    // Le noeud courant correspond au noeud feuille sur lequel on a effectué la simulation.
    // Pour nbParties : Le noeud courant et les noeuds de la racine au noeud courant augment leur attribut nbParties de +1
    // Pour nbVictoires il y a deux cas:
    //    - Si le noeud courant gagne, les noeuds du même type au noeud courant (même jeton sur le plateau) ont leur attributs nbVictoires augmenté
    //    - Si le noeud courant perd, les noeuds du type opposé au noeud courant (jeton opposé sur le plateau) ont leur attributs nbVictoires augmenté
    public void backPropagation(int resultatSimulation){

        // Partie 1 : Mettre à jour pour le noeud courant les attributs nbParties et nbVictoires

        //true noeud du bas gagnant
        //false noeud du bas perdant
        boolean noeudDuBasGagnant=false;

        this.nbParties += 1;
        if(resultatSimulation == Etat.J1_GAGNE && this.estNoeudHumain())
        {
            this.nbVictoires += 1;
            noeudDuBasGagnant=true;
        } else if(resultatSimulation == Etat.J2_GAGNE && !this.estNoeudHumain()) {
            this.nbVictoires += 1;
            noeudDuBasGagnant=true;
        }
        System.out.println("Etape 4 - Backpropagation - Partie 1 : Done");

        // Partie 2 : Mettre à jour pour tous les noeuds entre la racine et le noeud courant les attributs nbParties et nbVictoires

        Noeud noeudCourant=this;

        //true noeud humain
        //false noeud IA
        boolean typeNoeud=this.estNoeudHumain();

        while (!noeudCourant.estNoeudRacine()) {
            noeudCourant=noeudCourant.getParent();
            noeudCourant.setNbParties(noeudCourant.getNbParties()+1);

            //Si le noeud du bas est gagant et le noeud courant est un noeud du même type
            if(noeudDuBasGagnant && (typeNoeud == noeudCourant.estNoeudHumain())) {
                noeudCourant.setNbVictoires(noeudCourant.getNbVictoires()+1);
            //Si le noeud du bas est perdant et que le noeud courant est un noeud du type opposé et que ce n'est pas une égalité
            } else if (!noeudDuBasGagnant && (typeNoeud == !noeudCourant.estNoeudHumain()) && (resultatSimulation!= Etat.MATCH_NUL)) {
                noeudCourant.setNbVictoires(noeudCourant.getNbVictoires()+1);
            }
        }
        System.out.println("Etape 4 - Backpropagation - Partie 2 : Done");
    }

    public Noeud getPremierEnfant () {
        if (this.enfants.size() > 0) {
            return this.enfants.get(0);
        } else {
            System.err.println("Erreur pas d'enfant pour ce noeud !");
            return null;
        }
    }

    public Noeud getParent() {
        return parent;
    }

    public Coup getCoupAssocie() {
        return coupAssocie;
    }

    public ArrayList<Noeud> getEnfants() {
        return enfants;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public int getNbParties() {
        return nbParties;
    }

    // Utile pour l'étape 4 de l'algorithme de Monte Carlo UCT (Backpropagation)
    //Est-ce un noeud joueur ou un noeud IA ?
    //true joueur
    //false IA
    public boolean estNoeudHumain() {
        return (this.etat.getJoueur() == 1);
    }

    public boolean estNoeudFeuille() {
        return (this.enfants.size() == 0);
    }

    public boolean estNoeudRacine() {
        return (this.parent == null);
    }

    public Etat getEtat() {
        return etat;
    }
}
