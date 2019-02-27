package modele;

import java.util.ArrayList;
import java.util.Random;

public class IA extends Joueur {

    public IA() {
        super();
    }

    @Override
    public void jouer(Jeu jeu) {
        try {
            Thread.sleep(100);

            //L'IA fait son truc
            //TODO

            //Aléatoire
            ////////////////////////////////////////////////////////////////////////////////////////////////
            Etat etat=jeu.getGrille().getEtat();
            ArrayList<Coup> coupsPossibles=etat.coupsPossibles();


            if (coupsPossibles.size() > 0) {
                Random rand=new Random();
                int indiceCoup=rand.nextInt(coupsPossibles.size());

                etat.jouerCoup(coupsPossibles.get(indiceCoup));
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////

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
