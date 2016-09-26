class Model_Plateau
{
    private Model_Partie partie;
    private Model_Pion[] pionsBlancs;
    private Model_Pion[] pionsNoirs;
    private Model_Case[] board;
    final static int LIGNE = 8;

    /**
     * Constructeur initialisant l'état du plateau au début d'une partie.
     */
    Model_Plateau()
    {
        pionsBlancs = new Model_Pion[8];
        pionsNoirs = new Model_Pion[8];
        board = new Model_Case[LIGNE * LIGNE];
        initCouleursPlateau();
        placementPions();
    }

    /**
     * Initialisation des cases du plateau
     */
    private void initCouleursPlateau()
    {
        board[0]= new Model_Case(this,0, 0, Couleur.MARRON);
        board[1]= new Model_Case(this,0, 1, Couleur.VERT);
        board[2]= new Model_Case(this,0, 2, Couleur.ROUGE);
        board[3]= new Model_Case(this,0, 3, Couleur.JAUNE);
        board[4]= new Model_Case(this,0, 4, Couleur.ROSE);
        board[5]= new Model_Case(this,0, 5, Couleur.VIOLET);
        board[6]= new Model_Case(this,0, 6, Couleur.BLEU);
        board[7]= new Model_Case(this,0, 7, Couleur.ORANGE);

        board[8] = new Model_Case(this,1, 0, Couleur.VIOLET);
        board[9] = new Model_Case(this,1, 1, Couleur.MARRON);
        board[10]= new Model_Case(this,1, 2, Couleur.JAUNE);
        board[11]= new Model_Case(this,1, 3, Couleur.BLEU);
        board[12]= new Model_Case(this,1, 4, Couleur.VERT);
        board[13]= new Model_Case(this,1, 5, Couleur.ROSE);
        board[14]= new Model_Case(this,1, 6, Couleur.ORANGE);
        board[15]= new Model_Case(this,1, 7, Couleur.ROUGE);

        board[16]= new Model_Case(this,2, 0, Couleur.BLEU);
        board[17]= new Model_Case(this,2, 1, Couleur.JAUNE);
        board[18]= new Model_Case(this,2, 2, Couleur.MARRON);
        board[19]= new Model_Case(this,2, 3, Couleur.VIOLET);
        board[20]= new Model_Case(this,2, 4, Couleur.ROUGE);
        board[21]= new Model_Case(this,2, 5, Couleur.ORANGE);
        board[22]= new Model_Case(this,2, 6, Couleur.ROSE);
        board[23]= new Model_Case(this,2, 7, Couleur.VERT);

        board[24]= new Model_Case(this,3, 0, Couleur.JAUNE);
        board[25]= new Model_Case(this,3, 1, Couleur.ROUGE);
        board[26]= new Model_Case(this,3, 2, Couleur.VERT);
        board[27]= new Model_Case(this,3, 3, Couleur.MARRON);
        board[28]= new Model_Case(this,3, 4, Couleur.ORANGE);
        board[29]= new Model_Case(this,3, 5, Couleur.BLEU);
        board[30]= new Model_Case(this,3, 6, Couleur.VIOLET);
        board[31]= new Model_Case(this,3, 7, Couleur.ROSE);

        board[32]= new Model_Case(this,4, 0, Couleur.ROSE);
        board[33]= new Model_Case(this,4, 1, Couleur.VIOLET);
        board[34]= new Model_Case(this,4, 2, Couleur.BLEU);
        board[35]= new Model_Case(this,4, 3, Couleur.ORANGE);
        board[36]= new Model_Case(this,4, 4, Couleur.MARRON);
        board[37]= new Model_Case(this,4, 5, Couleur.VERT);
        board[38]= new Model_Case(this,4, 6, Couleur.ROUGE);
        board[39]= new Model_Case(this,4, 7, Couleur.JAUNE);

        board[40]= new Model_Case(this,5, 0, Couleur.VERT);
        board[41]= new Model_Case(this,5, 1, Couleur.ROSE);
        board[42]= new Model_Case(this,5, 2, Couleur.ORANGE);
        board[43]= new Model_Case(this,5, 3, Couleur.ROUGE);
        board[44]= new Model_Case(this,5, 4, Couleur.VIOLET);
        board[45]= new Model_Case(this,5, 5, Couleur.MARRON);
        board[46]= new Model_Case(this,5, 6, Couleur.JAUNE);
        board[47]= new Model_Case(this,5, 7, Couleur.BLEU);

        board[48]= new Model_Case(this,6, 0, Couleur.ROUGE);
        board[49]= new Model_Case(this,6, 1, Couleur.ORANGE);
        board[50]= new Model_Case(this,6, 2, Couleur.ROSE);
        board[51]= new Model_Case(this,6, 3, Couleur.VERT);
        board[52]= new Model_Case(this,6, 4, Couleur.BLEU);
        board[53]= new Model_Case(this,6, 5, Couleur.JAUNE);
        board[54]= new Model_Case(this,6, 6, Couleur.MARRON);
        board[55]= new Model_Case(this,6, 7, Couleur.VIOLET);

        board[56]= new Model_Case(this,7, 0, Couleur.ORANGE);
        board[57]= new Model_Case(this,7, 1, Couleur.BLEU);
        board[58]= new Model_Case(this,7, 2, Couleur.VIOLET);
        board[59]= new Model_Case(this,7, 3, Couleur.ROSE);
        board[60]= new Model_Case(this,7, 4, Couleur.JAUNE);
        board[61]= new Model_Case(this,7, 5, Couleur.ROUGE);
        board[62]= new Model_Case(this,7, 6, Couleur.VERT);
        board[63]= new Model_Case(this,7, 7, Couleur.MARRON);
    }

    /**
     * Placement des pions au début du jeu
     */
    private void placementPions()
    {
        pionsBlancs = new Model_Pion[8];
        pionsBlancs[0] = new Model_Pion(board[0],true,Couleur.MARRON);
        pionsBlancs[1] = new Model_Pion(board[1],true,Couleur.VERT);
        pionsBlancs[2] = new Model_Pion(board[2],true,Couleur.ROUGE);
        pionsBlancs[3] = new Model_Pion(board[3],true,Couleur.JAUNE);
        pionsBlancs[4] = new Model_Pion(board[4],true,Couleur.ROSE);
        pionsBlancs[5] = new Model_Pion(board[5],true,Couleur.VIOLET);
        pionsBlancs[6] = new Model_Pion(board[6],true,Couleur.BLEU);
        pionsBlancs[7] = new Model_Pion(board[7],true,Couleur.ORANGE);
        for(int i=0; i<LIGNE; i++) board[i].addPion(pionsBlancs[i]);

        pionsNoirs = new Model_Pion[8];
        pionsNoirs[0] = new Model_Pion(board[56],false,Couleur.ORANGE);
        pionsNoirs[1] = new Model_Pion(board[57],false,Couleur.BLEU);
        pionsNoirs[2] = new Model_Pion(board[58],false,Couleur.VIOLET);
        pionsNoirs[3] = new Model_Pion(board[59],false,Couleur.ROSE);
        pionsNoirs[4] = new Model_Pion(board[60],false,Couleur.JAUNE);
        pionsNoirs[5] = new Model_Pion(board[61],false,Couleur.ROUGE);
        pionsNoirs[6] = new Model_Pion(board[62],false,Couleur.VERT);
        pionsNoirs[7] = new Model_Pion(board[63],false,Couleur.MARRON);

        for(int i=0; i<pionsNoirs.length; i++) board[56+i].addPion(pionsNoirs[i]);
    }

    /**
     * Déplace un pion du point de vue du plateau
     * @param caseSource (case où était initialement le pion)
     * @param caseDestination (case où sur laquelle on déplace le pion)
     * @param pion (pion qui est concerné par ce déplacement)
     */
    void deplacer(Model_Case caseSource, Model_Case caseDestination, Model_Pion pion)
    {
        caseSource.removePion();
        caseDestination.addPion(pion);
        partie.setDernierPionJoue(pion);
    }

    // GETTERS & SETTERS
    Model_Case[] getBoard() {
        return board;
    }
    Model_Pion[] getPionsBlancs() { return pionsBlancs; }
    Model_Pion[] getPionsNoirs() { return pionsNoirs; }
    Model_Partie getPartie() { return partie; }

    public void setPartie(Model_Partie partie) {
        this.partie = partie;
    }
}
