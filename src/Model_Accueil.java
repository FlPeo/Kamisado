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
        this.partie = new Model_Partie(this);
    }

    // GETTERS & SETTERS
    Model_Partie getPartie() {
        return partie;
    }
}
