
class Model_Accueil
{
    private Model_Partie partie;
    private Model_Partie_IA partieIa;
    private boolean ia = false;

    /**
     * Constructeur du model relatif à l'accueil
     */
    Model_Accueil()
    {
        partie = null;
        partieIa = null;
    }

    /**
     * Instanciation d'une partie
     */
    void demarrerPartie()
    {
        Model_Case[] board = Model_Case.initCasesPlateau();
        Model_Pion[] pionsBlancs = Model_Pion.creationPionsBlancs(board);
        Model_Pion[] pionsNoirs = Model_Pion.creationPionsNoirs(board);

        for(int i=0; i<Model_Plateau.LIGNE; i++) board[i].addPion(pionsBlancs[i]);
        for(int i=0; i<pionsNoirs.length; i++) board[56+i].addPion(pionsNoirs[i]);

        Model_Joueur joueurBlanc = new Model_Joueur("blanc", true);
        Model_Joueur joueurNoir = new Model_Joueur("noir", false);
        this.partie = Model_Partie.factPartie(this, joueurBlanc, joueurNoir, board, pionsBlancs, pionsNoirs, null, true);

        joueurBlanc.setPartie(partie);
        joueurNoir.setPartie(partie);
    }

    /**
     * initie la partie contre l'IA
     */
    void demarrerPartieContreLIA()
    {
        ia = true;
        partieIa = new Model_Partie_IA();
    }

    // GETTERS & SETTERS
    Model_Partie getPartie() { return partie; }

    public boolean getia() {
        return ia;
    }

    public Model_Partie_IA getPartieIa() {
        return partieIa;
    }
}