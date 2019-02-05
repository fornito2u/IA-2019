package modele;

public class Joueur
{
    protected int id;
    private static int idGlobale = 1;

    public Joueur () {
        this.id=idGlobale;
        idGlobale++;
    }


    public void jouer() {

    }
}
