package controleur;

import modele.Coup;
import modele.Etat;
import modele.Jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonControleur implements ActionListener {

    private Jeu jeu;
    private Etat etat;
    private int colonne;

    public BoutonControleur(Etat etat, int colonne, Jeu jeu) {
        this.etat = etat;
        this.colonne = colonne;
        this.jeu=jeu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        etat.jouerCoup(new Coup(this.colonne));
    }
}
