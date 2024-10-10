package kamisado;

class Model_Accueil
{
    private Model_Partie partie;
    private Model_Partie_IA partieIa;
    private String partieAVisualiser;
    private String pseudoJoueurBlanc;
    private String pseudoJoueurNoir;
    private String pseudoChoisi;
    private String partieACharger;
    private String langue;
    private boolean ia = false;


    /**
     * Constructeur du model relatif à l'accueil
     */
    Model_Accueil()
    {
        partie = null;
        partieIa = null;
        partieAVisualiser = "";
        pseudoJoueurBlanc = "";
        pseudoJoueurNoir = "";
        pseudoChoisi = "";
        partieACharger = "";
        langue = "fr";
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
    boolean getia() { return ia; }
    Model_Partie_IA getPartieIa() { return partieIa; }
    void setPseudoJoueurBlanc(String pseudoJoueurBlanc) {
        this.pseudoJoueurBlanc = pseudoJoueurBlanc;
    }
    void setPseudoJoueurNoir(String pseudoJoueurNoir) {
        this.pseudoJoueurNoir = pseudoJoueurNoir;
    }
    String getPseudoJoueurBlanc() {
        return pseudoJoueurBlanc;
    }
    String getPseudoJoueurNoir() {
        return pseudoJoueurNoir;
    }
    String getLangue() {
        return langue;
    }
    void setLangue(String langue) {
        this.langue = langue;
    }
}