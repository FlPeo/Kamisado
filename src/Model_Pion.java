import java.util.ArrayList;

class Model_Pion
{
    private Model_Case caseActuelle;
    private boolean estBlanc;
    private final int COULEUR;
    private ArrayList<Model_Case> casesAtteignables;

    /**
     * Constructeur d'un pion
     * @param caseInitiale (case où se trouve le pion au début de la partie)
     * @param estBlanc (indique à quel joueur appartient le pion)
     * @param COULEUR (indique de quelle couleur est ce pion)
     */
    Model_Pion(Model_Case caseInitiale, boolean estBlanc, int COULEUR)
    {
        this.estBlanc = estBlanc;
        this.caseActuelle = caseInitiale;
        this.COULEUR = COULEUR;
        this.casesAtteignables = new ArrayList<>();
    }

    /**
     * Modifie la case où se trouve actuellement le pion
     * @param nouvelleCaseActuelle (case où va se retrouver le pion)
     */
    void setCaseActuelle(Model_Case nouvelleCaseActuelle)
    {
        this.caseActuelle = nouvelleCaseActuelle;
    }

    /**
     * Liste les cases où la pièce peut se déplacer
     */
    void casesAtteignables()
    {
        casesAtteignables.clear();

        //valable que pour les blancs
        Model_Case[] plateau = caseActuelle.getPlateau().getBoard();
        int row = caseActuelle.getRow(), column = caseActuelle.getColumn();
        int decal, ligne = Model_Plateau.LIGNE;
        boolean deplacableN = true, deplacableO = true, deplacableE = true;

        decal = estBlanc?1:-1;
        while (deplacableN || deplacableO || deplacableE)
        {
            if (deplacableN)
            {
                if( row + decal <=7
                    && plateau[column + (row + decal) * ligne].getPion() == null)
                    casesAtteignables.add(plateau[column + (row + decal) * ligne]);
                else
                    deplacableN = false;
            }
            if (deplacableO)
            {
                if( row + decal <=7 && column - decal >= 0
                        && plateau[(column - decal) + (row + decal) * ligne].getPion() == null)
                    casesAtteignables.add(plateau[(column - decal) + (row + decal) * ligne]);
                else
                    deplacableO = false;
            }
            if (deplacableE)
            {
                if( row + decal <=7 && column + decal <= 7
                        && plateau[(column + decal) + (row + decal) * ligne].getPion() == null)
                    casesAtteignables.add(plateau[(column + decal) + (row + decal) * ligne]);
                else
                    deplacableE= false;
            }
            decal = estBlanc?decal+1:decal-1;
        }
    }

    /**
     * Met en chaine de caractère les caractéristiques du pion
     * @return (chaine de caractère qui décrit le pion)
     */
    public String toString()
    {
        String joueur = estBlanc? "blanc": "noir";
        return "pion " + COULEUR + " du joueur " + joueur;
    }

    // GETTERS & SETTERS
    int getCOULEUR() { return COULEUR; }
    Model_Case getCaseActuelle() { return caseActuelle; }
    ArrayList<Model_Case> getCasesAtteignables() { return casesAtteignables; }
    boolean isEstBlanc() { return estBlanc; }
}