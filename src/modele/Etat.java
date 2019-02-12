package modele;

import java.util.ArrayList;
import java.util.Observable;

public class Etat extends Observable {

    /**
     * 0 Case inutilisé
     * 1 Case utilisé par le J1 (pikachu)
     * 2 Case utilisé par le J2 (rondoudou)
     */
    private byte[][] tableau;
    /**
     * Le prochain joueur qui doit jouer
     */
    private byte joueur;

    public Etat (int nbLigne, int nbColonne, byte jo) {
        joueur=jo;
        tableau=new byte[nbLigne][nbColonne];
        for (int i=0;i<nbLigne;i++) {
            for (int j=0;j<nbColonne;j++) {
                tableau[i][j]=0;
            }
        }
    }

    public Etat (Etat etat) {
        int nbLigne=etat.getTableau().length;
        int nbColonne=etat.getTableau()[0].length;
        tableau=new byte[nbLigne][nbColonne];
        for (int i=0;i<nbLigne;i++) {
            for (int j=0;j<nbColonne;j++) {
                tableau[i][j]=etat.getTableau()[i][j];
            }
        }
    }

    public byte[][] getTableau() {
        return tableau;
    }

    /**
     * Joue un coup si il est possible et retourne true sinon retourne false
     * @param c Le coup (ligne et colonne)
     * @return Un booléen représentant si le coup est possible
     */
    public boolean jouerCoup(Coup c) {
        //On parcourt chaque ligne du bas vers le haut et on joue sur la première case libre
        for (int i=tableau.length-1;i>=0;i--) {
            byte unecase=tableau[i][c.getColonne()];
            if (unecase == 0) {
                tableau[i][c.getColonne()] = joueur;
                if (joueur == 1) {
                    joueur = 2;
                } else {
                    joueur = 1;
                }
                this.setChanged();
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction retournant une ArrayList avec tous les coups possbile de l'état actuel
     * @return Une ArrayList avec tous les coups possibles de l'état actuel
     */
    public ArrayList<Etat> coupsPossibles() {
        ArrayList<Etat> tableauEtat=new ArrayList<Etat>();
        //On essaye de jouer un coup sur chaque colonne et on retourne les états possibles
        for (int i=0;i<this.tableau[0].length;i++) {
            Etat etatCopie=new Etat(this);
            boolean coupPossible=etatCopie.jouerCoup(new Coup(i));
            if (coupPossible) {
                tableauEtat.add(etatCopie);
            }
        }
        return tableauEtat;
    }

    /**
     * Fonction qui retourne si l'etat entraine la fin de la partie
     * @return
     */
    public boolean testFin() {
        return false;
    }

    /**
     * Fonction d'affichage pour un etat
     * @return Une représentation 2D du tableau
     */
    public String toString() {
        StringBuilder tabString= new StringBuilder();
        for (int i=0;i<tableau.length;i++) {
            tabString.append("[");
            for (int j=0;j<tableau.length;j++) {
                tabString.append("[");
                tabString.append(tableau[i][j]);
                tabString.append("]");
            }
            tabString.append("]\n");
        }
        return tabString.toString();
    }
}
