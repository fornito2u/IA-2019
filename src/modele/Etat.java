package modele;

import java.util.ArrayList;
import java.util.Observable;

public class Etat extends Observable {


    /**
     * Constantes utilisés par testFin
     */
    public static final int J1_GAGNE=1;
    public static final int J2_GAGNE=2;
    public static final int MATCH_NUL=3;
    public static final int PARTIE_EN_COURS=4;

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

    /**
     * Le dernier coup joué
     */
    private Coup dernierCoup;

    public Etat (int nbLigne, int nbColonne, byte jo) {
        joueur=jo;
        tableau=new byte[nbLigne][nbColonne];
        for (int i=0;i<nbLigne;i++) {
            for (int j=0;j<nbColonne;j++) {
                tableau[i][j]=0;
            }
        }
        dernierCoup=new Coup (-1);
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
        dernierCoup=new Coup(etat.getDernierCoup());
        joueur=etat.getJoueur();
    }

    public void setTableau(byte[][] tableau) {
        this.tableau = tableau;
    }

    public byte[][] getTableau() {
        return tableau;
    }

    public byte getJoueur() {
        return joueur;
    }

    public Coup getDernierCoup() {
        return dernierCoup;
    }

    /**
     * Joue un coup si il est possible et retourne true sinon retourne false
     * @param c Le coup (ligne et colonne)
     * @return Un booléen représentant si le coup est possible
     */
    public boolean jouerCoup(Coup c) {
        dernierCoup=c;

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
     * Liste tous les coups possibles de l'état actuel
     * @return Une ArrayList avec tous les coups possibles de l'état actuel
     */
    public ArrayList<Coup> coupsPossibles() {
        ArrayList<Coup> tableauCoups=new ArrayList<Coup>();
        //On essaye de jouer un coup sur chaque colonne et on retourne les états possibles
        for (int i=0;i<this.tableau[0].length;i++) {
            Etat etatCopie=new Etat(this);
            Coup coupI=new Coup(i);
            boolean coupPossible=etatCopie.jouerCoup(coupI);
            if (coupPossible) {
                tableauCoups.add(coupI);
            }
        }
        return tableauCoups;
    }

    /**
     * Liste tous les etat possibles de l'état actuel
     * @return Une ArrayList avec tous les etat possibles de l'état actuel
     */
    public ArrayList<Etat> etatPossibles() {
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
     * Fonction qui retourne si l'etat entraine la fin de la partie et le gagnant
     * @return Un entier indiquant le joueur gagnant ou si match nul ou encore si la partie est toujours en cours (voir constantes)
     */
    public int testFin() {

        int dernièreCaseJouéeY=-1;
        int dernièreCaseJouéeX=dernierCoup.getColonne();
        byte dernierJoueur=-1;

        //Si aucun coup n'a été joué, la partie est en cours
        if (dernièreCaseJouéeX == -1) {
            return PARTIE_EN_COURS;
        }

        //On parcourt chaque ligne du haut vers le bas et on sélectionne la dernière case jouée
        for (int i=0;i<tableau.length;i++) {
            dernierJoueur=tableau[i][dernierCoup.getColonne()];
            if (dernierJoueur != 0) {
                dernièreCaseJouéeY=i;
                break;
            }
        }

        //Ce cas ne devrait jamais arriver (la colonne du coup joué est vide)
        if (dernièreCaseJouéeY == -1) {
            return PARTIE_EN_COURS;
        } else {

            //Vérification en colonne
            int casesAlignés=1;
            int y=dernièreCaseJouéeY;
            int x=dernièreCaseJouéeX;

            for (int i=dernièreCaseJouéeY+1;i<tableau.length;i++) {
                y=i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (casesAlignés>=4) {
                return retourneGagnant(dernierJoueur);
            }


            //Vérification en ligne
            casesAlignés=1;
            y=dernièreCaseJouéeY;
            x=dernièreCaseJouéeX;

            for (int i=dernièreCaseJouéeX-1;i>-1;i--) {
                x=i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            for (int i=dernièreCaseJouéeX+1;i<tableau[0].length;i++) {
                x=i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (casesAlignés>=4) {
                return retourneGagnant(dernierJoueur);
            }

            //Vérification en diagonale haut-gauche bas-droite
            casesAlignés=1;
            y=dernièreCaseJouéeY;
            x=dernièreCaseJouéeX;

            for (int i=1;i<4;i++) {
                y=dernièreCaseJouéeY-i;
                x=dernièreCaseJouéeX-i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            for (int i=1;i<4;i++) {
                y=dernièreCaseJouéeY+i;
                x=dernièreCaseJouéeX+i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (casesAlignés>=4) {
                return retourneGagnant(dernierJoueur);
            }

            //Vérification en diagonale haut-droite bas-gauche
            casesAlignés=1;
            y=dernièreCaseJouéeY;
            x=dernièreCaseJouéeX;

            for (int i=1;i<4;i++) {
                y=dernièreCaseJouéeY+i;
                x=dernièreCaseJouéeX-i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            for (int i=1;i<4;i++) {
                y=dernièreCaseJouéeY-i;
                x=dernièreCaseJouéeX+i;
                if (caseAccessible(y,x)) {
                    if (tableau[y][x] == dernierJoueur) {
                        casesAlignés++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            if (casesAlignés>=4) {
                return retourneGagnant(dernierJoueur);
            }

            //On regarde si il y a un match nul
            for (int i=0;i<tableau.length;i++) {
                for (int j=0;j<tableau[0].length;j++) {
                    if (tableau[i][j] == 0) {
                        return PARTIE_EN_COURS;
                    }
                }
            }
            return MATCH_NUL;
        }



    }

    private int retourneGagnant(byte dernierJoueur) {
        if (dernierJoueur == 1) {
            return J1_GAGNE;
        } else {
            return J2_GAGNE;
        }
    }

    /**
     * Indique si une case est accessible
     * @param y La position y de la case (1ère coordonnée du tableau)
     * @param x La position x de la case (2ème coordonnée du tableau)
     * @return Un booléen indiquant si la case est accessible
     */
    private boolean caseAccessible(int y, int x) {
        return (y>-1 && y<tableau.length && x>-1 && x<tableau[y].length);
    }

    /**
     * Affichage pour un etat
     * @return Une représentation 2D du tableau
     */
    public String toString() {
        StringBuilder tabString= new StringBuilder();
        for (int i=0;i<tableau.length;i++) {
            tabString.append("[");
            for (int j=0;j<tableau[0].length;j++) {
                tabString.append("[");
                tabString.append(tableau[i][j]);
                tabString.append("]");
            }
            tabString.append("]\n");
        }
        return tabString.toString();
    }
}
