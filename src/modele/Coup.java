package modele;

public class Coup {

    /**
     * La colonne sur laquelle va être joué le coup
     */
    private int colonne;

    public Coup(int c) {
        this.colonne=c;
    }

    public int getColonne() {
        return colonne;
    }
}
