import java.util.ArrayList;

class Model_Accueil
{
    private Model_Partie partie;
    private Model_Partie_IA partieIa;
    private String partieAVisualiser;
    private String pseudoJoueurBlanc;
    private String pseudoJoueurNoir;
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
        int idBlanc, idNoir;
        BDDManager bdd = new BDDManager();
        bdd.start();
        idBlanc = Integer.parseInt(bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur LIKE '" + pseudoJoueurBlanc + "';").get(0).get(0));
        idNoir = Integer.parseInt(bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur LIKE '" + pseudoJoueurNoir + "';").get(0).get(0));
        Model_Joueur joueurBlanc = new Model_Joueur(pseudoJoueurBlanc, true, idBlanc);
        Model_Joueur joueurNoir = new Model_Joueur(pseudoJoueurNoir, false, idNoir);
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

    void ajouterNouveauJoueur(String pseudo)
    {
        BDDManager bdd = new BDDManager();
        bdd.start();
        bdd.edit("INSERT INTO JOUEUR VALUES (null, \"" + pseudo + "\", 0, 0);");
        bdd.stop();
    }

    String[] listePseudos()
    {
        BDDManager bdd = new BDDManager();
        bdd.start();
        ArrayList<ArrayList<String>> joueurs = bdd.ask("SELECT JOUEUR.pseudoJoueur FROM JOUEUR;");
        ArrayList<String> listeJoueurs = new ArrayList<>();
        for (ArrayList<String> joueur : joueurs)
            for (String aJoueur : joueur)
                listeJoueurs.add(aJoueur);
        String[] liste = new String[listeJoueurs.size()];
        for(int i=0; i<listeJoueurs.size(); i++)
            liste[i] = listeJoueurs.get(i);

        bdd.stop();
        return liste;
    }

    // GETTERS & SETTERS
    Model_Partie getPartie() { return partie; }
    boolean getia() { return ia; }
    Model_Partie_IA getPartieIa() { return partieIa; }
    void setPartieAVisualiser(String partieAVisualiser) {
        this.partieAVisualiser = partieAVisualiser;
    }
    String getPartieAVisualiser() {
        return partieAVisualiser;
    }
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
}