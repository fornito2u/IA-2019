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
        try {
            Thread.sleep(100);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Rajouté (Marvin)
            // IA Intelligent
            // Etat etat = jeu.getGrille().getEtat();
            // this.noeudRacineArbre = new Noeud(etat);
            // Il faudra faire la sélection, le développement, la simulation et la backpropagation sur noeudRacineArbre

            // Etape 1 : Selection

            // Etape 2 : Développement

            // Etape 3 : Simulation

            // Etape 4 : Backpropagation

            // Etape 5 : Choix du coup à effectuer

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // IA Aléatoire

            Etat etat=jeu.getGrille().getEtat();
            ArrayList<Coup> coupsPossibles=etat.coupsPossibles();

            if (coupsPossibles.size() > 0) {
                Random rand=new Random();
                int indiceCoup=rand.nextInt(coupsPossibles.size());

                etat.jouerCoup(coupsPossibles.get(indiceCoup));
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        } catch (InterruptedException e) {
            System.out.println("Erreur InterruptedException");
        }
    }

    @Override
    public String toString() {
        return "IA{" +
                "id=" + id +
                '}';
    }
}
