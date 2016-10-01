class Model_Accueil
{
    private Model_Partie partie;

    /**
     * Constructeur du model relatif à l'accueil
     */
    Model_Accueil()
    {
        partie = null;
    }

    /**
     * Instanciation d'une partie
     */
    void demarrerPartie()
    {
        Model_Case[] board = new Model_Case[Model_Plateau.LIGNE * Model_Plateau.LIGNE];
        Model_Case.initCasesPlateau(board);

        Model_Pion[] pionsBlancs = new Model_Pion[Model_Plateau.LIGNE];
        Model_Pion.creationPionsBlancs(board, pionsBlancs);

        Model_Pion[] pionsNoirs = new Model_Pion[Model_Plateau.LIGNE];
        Model_Pion.creationPionsNoirs(board, pionsNoirs);

        for(int i=0; i<Model_Plateau.LIGNE; i++) board[i].addPion(pionsBlancs[i]);
        for(int i=0; i<pionsNoirs.length; i++) board[56+i].addPion(pionsNoirs[i]);

        this.partie = Model_Partie.factPartie(this, board, pionsBlancs, pionsNoirs, null, true);
    }

    // GETTERS & SETTERS
    Model_Partie getPartie() {
        return partie;
    }
}
