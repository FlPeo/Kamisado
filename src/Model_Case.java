class Model_Case
{
    private int row;
    private int column;
    private Model_Plateau plateau;
    private Model_Pion pion;
    private final int COULEUR;

    /**
     * COnstructeur du model d'une case
     * @param plateau (model relatif au plateau de jeu)
     * @param row (ligne à laquelle se trouve la case courante)
     * @param column (colonne à laquelle se trouve la case courante
     * @param COULEUR (couleur de la case courante)
     */
    Model_Case(Model_Plateau plateau, int row, int column, int COULEUR)
    {
        this.plateau = plateau;
        this.COULEUR = COULEUR;
        this.row = row;
        this.column = column;
    }

    /**
     * Ajoute un pion sur la case courante
     * @param pion (pion a ajouter)
     */
    void addPion(Model_Pion pion)
    {
        this.pion = pion;
        this.pion.setCaseActuelle(this);
    }

    /**
     * Supprime le pion présent sur la case courante
     */
    void removePion() { pion = null; }

    /**
     * Indice où se trouve la case courante dans le tableau de case du plateau courant
     * @return (indice de la case courante en chaîne de caractère)
     */
    @Override
    public String toString() { return column+row*Model_Plateau.LIGNE + " "; }

    // GETTERS & SETTERS
    Model_Pion getPion() { return pion; }
    int getCOULEUR() { return COULEUR; }
    Model_Plateau getPlateau() { return plateau; }
    int getRow() { return row; }
    int getColumn() { return column; }
}
