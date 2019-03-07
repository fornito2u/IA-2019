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
        long dureeReflexion=3000;

        long dateDebut=System.currentTimeMillis();

        Etat etat = jeu.getGrille().getEtat();
        this.noeudRacineArbre = new Noeud(etat);
        int compteurBoucle = 1;

        // Coup gagnant
        Etat etatTemp = new Etat(jeu.getGrille().getEtat());
        ArrayList<Coup> coupsPossibles=etatTemp.coupsPossibles();
        int idCoupGagnant = -1;
        for(int i = 0; i < coupsPossibles.size();i++)
        {
            etatTemp.jouerCoup(coupsPossibles.get(i));
            if(etatTemp.testFin() == Etat.J2_GAGNE)
            {
                idCoupGagnant = i;
            }
            etatTemp = new Etat(jeu.getGrille().getEtat());
        }

        if(idCoupGagnant >= 0)
        {
            etat.jouerCoup(coupsPossibles.get(idCoupGagnant));
            idCoupGagnant = -1;
        } else {
            while (System.currentTimeMillis() - dateDebut < dureeReflexion) {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // IA Intelligent
                // Etape 1 : Selection
                Noeud noeudFeuille = this.noeudRacineArbre.selection();
                //System.out.println("Etape 1 - Selection : Done");

                // Etape 2 : Développement

                Noeud noeudFils=noeudFeuille.developpement();
                /*Noeud noeudFils;
                if (noeudFeuille.getEnfants().size() > 0) {
                    noeudFils = noeudFeuille.getPremierEnfant();
                } else {
                    noeudFils = noeudFeuille;
                }*/

               // System.out.println("Etape 2 - Développement : Done");

                // Etape 3 : Simulation
                // Etat.J1_GAGNE => 1
                // Etat.J2_GAGNE => 2
                // Etat.MATCH_NUL => 3
                int resultatSimulation = noeudFils.simulation();
                // System.out.println("Etape 3 - Simulation : Done");
                //Etape 4 : Backpropagation
                noeudFils.backPropagation(resultatSimulation);
                //System.out.println("Etape 4 - Backpropagation : Done\n");

                compteurBoucle += 1;
            }


            // Etape 5 : Choix du coup à effectuer

            //Max
            Noeud noeudChoisi=this.noeudRacineArbre.noeudEnfantMeilleurMouvement();

            //Robuste
            //Noeud noeudChoisi=this.noeudRacineArbre.noeudEnfantRobuste();


            Coup coupAJouer = noeudChoisi.getCoupAssocie();
            etat.jouerCoup(coupAJouer);
            System.out.println("Nombre de simulation : " + compteurBoucle + "\n");
            System.out.println("Pourcentage de victoire de l'IA : "+(1.0*noeudChoisi.getNbVictoires()/noeudChoisi.getNbParties())*100+"%");
        }



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
