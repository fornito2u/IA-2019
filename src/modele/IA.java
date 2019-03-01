package modele;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur {

    private Noeud noeudRacineArbre;

    public IA() {
        super();
    }

    @Override
    public void jouer(Jeu jeu) {
        long dureeReflexion=1000;

        long dateDebut=System.currentTimeMillis();

        Etat etat = jeu.getGrille().getEtat();
        this.noeudRacineArbre = new Noeud(etat);
        int compteurBoucle = 1;
        while (System.currentTimeMillis()-dateDebut < dureeReflexion) {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // IA Intelligent
            System.out.println("Tour de boucle n°"+compteurBoucle+" :\n");
            // Etape 1 : Selection
            Noeud noeudFeuille=this.noeudRacineArbre.selection();
            System.out.println("Etape 1 - Selection : Done");

            // Etape 2 : Développement
            noeudFeuille.developpement();
            Noeud noeudFils=noeudFeuille.getPremierEnfant();
            System.out.println("Etape 2 - Développement : Done");

            // Etape 3 : Simulation
            // Etat.J1_GAGNE => 1
            // Etat.J2_GAGNE => 2
            // Etat.MATCH_NUL => 3
            int resultatSimulation=noeudFils.simulation();
            System.out.println("Etape 3 - Simulation : Done");
            // Etape 4 : Backpropagation
            noeudFils.backPropagation(resultatSimulation);
            System.out.println("Etape 4 - Backpropagation : Done\n");

            compteurBoucle+=1;
        }


        // Etape 5 : Choix du coup à effectuer
        Coup coupAJouer=this.noeudRacineArbre.noeudEnfantUCTMax().getCoupAssocie();
        etat.jouerCoup(coupAJouer);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
        /*// IA Aléatoire
        Etat etat=jeu.getGrille().getEtat();
        ArrayList<Coup> coupsPossibles=etat.coupsPossibles();

        if (coupsPossibles.size() > 0) {
            Random rand=new Random();
            int indiceCoup=rand.nextInt(coupsPossibles.size());

            etat.jouerCoup(coupsPossibles.get(indiceCoup));
        }*/

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public String toString() {
        return "IA{" +
                "id=" + id +
                '}';
    }
}
