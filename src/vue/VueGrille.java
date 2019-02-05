package vue;

import modele.Jeu;

import java.util.Observable;
import java.util.Observer;

public class VueGrille implements Observer
{
    Jeu jeu;

    public VueGrille(Jeu jeu)
    {

        this.jeu = jeu;
    }

    @Override
    public void update(Observable o, Object arg)
    {

    }
}
