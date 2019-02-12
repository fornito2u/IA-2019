package controleur;

import modele.Coup;
import modele.Etat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonControleur implements ActionListener {

    private Etat etat;
    private int colonne;

    public BoutonControleur(Etat etat, int colonne) {
        this.etat = etat;
        this.colonne = colonne;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        etat.jouerCoup(new Coup(this.colonne));
    }
}
