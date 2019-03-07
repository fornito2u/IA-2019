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
    private double uct=0;

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
            double maxUCT=this.enfants.get(0).calculeUCT();
            int maxIndice=0;

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

    public Noeud noeudEnfantMeilleurMouvement() {
        if (this.estNoeudFeuille() == true) {
            System.err.println("Erreur le noeud n'a pas d'enfant");
            return null;
        } else {
            double probaMax=1.0*this.enfants.get(0).getNbVictoires()/this.enfants.get(0).getNbParties();
            int maxIndice=0;

            for (int i=0;i<this.enfants.size();i++) {
                double probaCourante=1.0*this.enfants.get(i).getNbVictoires()/this.enfants.get(i).getNbParties();
                if (probaCourante > probaMax) {
                    probaMax=probaCourante;
                    maxIndice=i;
                }
            }
            return this.enfants.get(maxIndice);
        }
    }

    // Return parmi les enfants du noeud courant, le noeud avec le plus grand nombre de simulations
    public Noeud noeudEnfantRobuste() {
        if (this.estNoeudFeuille() == true) {
            System.err.println("Erreur le noeud n'a pas d'enfant");
            return null;
        } else {
            double maxSimulations=this.enfants.get(0).getNbParties();
            int maxIndice=0;

            for (int i=0;i<this.enfants.size();i++) {
                double simulationsCourant=this.enfants.get(i).getNbParties();
                if (simulationsCourant > maxSimulations) {
                    maxSimulations=simulationsCourant;
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
            this.uct=1;
            return 1;
        } else {
            double UCT = 0.0;

            double pourcentageVictoire;
            if (nbParties == 0) {
                System.out.println("YOLO");
                this.uct=1;
                return 1;
            } else {
                pourcentageVictoire=1.0*nbVictoires/nbParties;
            }

            if (estNoeudHumain()) {
                UCT=pourcentageVictoire+c* Math.sqrt(Math.log(this.parent.getNbParties()) / this.nbParties);
            } else {
                UCT=-pourcentageVictoire+c* Math.sqrt(Math.log(this.parent.getNbParties()) / this.nbParties);
            }
            this.uct=UCT;
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
    public Noeud developpement() {
        ArrayList<Coup> coupsPossibles=this.etat.coupsPossibles();

        if (coupsPossibles.size() > 0 && this.etat.testFin() == Etat.PARTIE_EN_COURS) {
            Random rand=new Random();
            int indiceCoup=rand.nextInt(coupsPossibles.size());
            Noeud noeufils=new Noeud(this, coupsPossibles.get(indiceCoup));
            return noeufils;
        } else {
            //Aucun coup possible car le noeud représente un état final"
            return this;
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

        // Mettre à jour pour tous les noeuds entre la racine et le noeud courant les attributs nbParties et nbVictoires

        Noeud noeudCourant=this;
        //Tant que le noeud n'est pas un noeud racine
        while (true) {
            noeudCourant.setNbParties(noeudCourant.getNbParties()+1);
            if(resultatSimulation == Etat.J2_GAGNE) {
                noeudCourant.setNbVictoires(noeudCourant.getNbVictoires()+1);
            }
            if (noeudCourant.estNoeudRacine()) {
                break;
            } else {
                noeudCourant=noeudCourant.getParent();
            }
        }
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
