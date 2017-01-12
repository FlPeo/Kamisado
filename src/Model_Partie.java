import java.util.ArrayList;

class Model_Partie
{
    private Model_Accueil accueil;

    private boolean isTourUn;
    private boolean tourDuJoueurBlanc;
    private boolean joueurBlancGagnant;

    private Model_Plateau plateau;
    private Model_Pion pionMemoire;
    private Model_Pion dernierPionJoue;

    private Model_Pion[] pionsBlancs;
    private Model_Pion[] pionsNoirs;

    private Model_Joueur joueurBlanc;
    private Model_Joueur joueurNoir;

    private boolean estGagnee;
    private String history;

    /**
     * Instancie les objets qui doivent l'être avant de
     * @param accueil (accueil du jeu)
     * @return (une nouvelle instance de Partie)
     */
    static Model_Partie factPartie(Model_Accueil accueil, Model_Joueur j1, Model_Joueur j2, Model_Case[] board, Model_Pion[] pionsBlancs, Model_Pion[] pionsNoirs,
                                   Model_Pion pionMemoire, boolean isTourUn)
    {

        Model_Plateau plateau = new Model_Plateau(board);        //passer en parametre si besoin de le mocker en testant la partie
        for(int i=0; i<board.length; i++) board[i].setPlateau(plateau);


        return new Model_Partie(accueil, plateau, j1, j2, pionsBlancs, pionsNoirs, pionMemoire, isTourUn);
    }

    /**
     * Constructeur d'une partie sans spécificité
     */
    private Model_Partie(Model_Accueil accueil, Model_Plateau plateau, Model_Joueur j1, Model_Joueur j2,
                         Model_Pion[] pionsBlancs, Model_Pion[] pionsNoirs, Model_Pion pionMemoire, boolean isTourUn)
    {
        this.accueil = accueil;
        this.plateau = plateau;
        this.estGagnee = false;
        this.tourDuJoueurBlanc = true;
        this.isTourUn = isTourUn;
        this.pionMemoire = pionMemoire;

        this.pionsBlancs = pionsBlancs;
        this.pionsNoirs = pionsNoirs;

        joueurBlanc = j1;
        joueurNoir = j2;
        history = "";
    }

    /**
     * Définit les cases atteignables du pion qui va bouger en fonction du pion qui a bougé
     */
    void casesAtteignablesProchainTour()
    {
        Model_Pion[] pionsDuTour = tourDuJoueurBlanc?pionsBlancs:pionsNoirs;

        // Le premier tour est géré séparément car c'est un cas particulier où le joueur à la possibilité de choisir
        // le pion qu'il va bouger.
        if(isTourUn)
        {
            pionMemoire = null; // en cas de undo il faut remettre le pion memoire à null
            for(Model_Pion pionsTour : pionsDuTour) pionsTour.casesAtteignables();
        }
        else
        {
            int couleurDuPionQuiDoitBouger = dernierPionJoue.getCaseActuelle().getCOULEUR();
            for(int i=0; i<pionsDuTour.length; i++)
            {
                if(pionsDuTour[i].getCOULEUR() == couleurDuPionQuiDoitBouger)
                {
                    pionMemoire = pionsDuTour[i];
                    break;
                }
            }
            pionMemoire.casesAtteignables();
        }
    }

    /**
     * undo()
     *
     * @return (if undo is ok)
     */
    boolean undo()
    {
        if(history.length() == 0)
            return false;
        String[] listesCoups = history.split(":");
        String dernierCoup = listesCoups[(listesCoups.length-1)];
        String[] tabCoupDecoupe = dernierCoup.split("");

        if (dernierCoup.equals("!"))
        {
            history = "";
            for (int i = 0; i < listesCoups.length-1; i++)
                history += listesCoups[i] + ":";

            tourDuJoueurBlanc = !tourDuJoueurBlanc;
            undo();
        }
        else
        {
            int rowDepart = Integer.parseInt(tabCoupDecoupe[0]);
            int columnDepart = Integer.parseInt(tabCoupDecoupe[1]);
            int rowArrivee = Integer.parseInt(tabCoupDecoupe[2]);
            int columnArrivee = Integer.parseInt(tabCoupDecoupe[3]);
            Model_Case caseDepart = plateau.getBoard()[rowDepart*Model_Plateau.LIGNE+columnDepart];
            Model_Case caseArrivee = plateau.getBoard()[rowArrivee*Model_Plateau.LIGNE+columnArrivee];
            Model_Pion pieceBougee = caseArrivee.getPion();
            plateau.deplacer(caseArrivee, caseDepart, pieceBougee);
            history = "";
            for (int i = 0; i < listesCoups.length-1; i++)
                history += listesCoups[i] + ":";
            if (history.length() == 0)
            {
                isTourUn = true;
                tourDuJoueurBlanc = !tourDuJoueurBlanc;
                casesAtteignablesProchainTour();
                return true;
            }

            tourDuJoueurBlanc = !tourDuJoueurBlanc;

            int rowDernierpionJoue = Character.getNumericValue(listesCoups[listesCoups.length-2].charAt(2));
            int colDernierpionJoue = Character.getNumericValue(listesCoups[listesCoups.length-2].charAt(3));
            dernierPionJoue = plateau.getBoard()[rowDernierpionJoue*Model_Plateau.LIGNE + colDernierpionJoue].getPion();
            casesAtteignablesProchainTour();
        }
        return true;
    }

    /**
     * Vérifie si nous sommes ou non dans une situation gagnante, donc de fin de partie
     * @param row (ligne sur laquelle a été déplacé le dernier pion joué)
     */
    void verifieVictoire(int row)
    {
        if((tourDuJoueurBlanc && row == 7)
                || (!tourDuJoueurBlanc && row == 0))
        {
            estGagnee = true;
            joueurBlancGagnant = tourDuJoueurBlanc;
        }
    }

    /**
     * Controle si un pion ne peut plus se déplacer et dans ce cas indique une situation gagnante
     */
    void controleBlocage()
    {
        if(pionMemoire.getCasesAtteignables().isEmpty())
        {
            history+="!:";
            boolean impossibleDeJouer = false;
            boolean joueurBlancGagnantSiJeuBloque = tourDuJoueurBlanc;
            ArrayList<Model_Pion> arrayPions = new ArrayList<>();
            arrayPions.add(pionMemoire);
            dernierPionJoue = pionMemoire;
            tourDuJoueurBlanc = !tourDuJoueurBlanc;
            casesAtteignablesProchainTour();
            while(pionMemoire.getCasesAtteignables().isEmpty() && !impossibleDeJouer)
            {
                if(arrayPions.contains(pionMemoire))
                    impossibleDeJouer = true;
                else
                {
                    arrayPions.add(pionMemoire);
                    dernierPionJoue = pionMemoire;
                    tourDuJoueurBlanc = !tourDuJoueurBlanc;
                    casesAtteignablesProchainTour();
                }
            }
            if(impossibleDeJouer)
            {
                estGagnee = true;
                joueurBlancGagnant = joueurBlancGagnantSiJeuBloque;
            }
        }
    }

    /**
     * Permet de déplacer un pion sur la case voulue
     * @param caseDest (case où le pion doit arriver)
     */
    void deplacerPion(Model_Case caseDest)
    {
        history += pionMemoire.getCaseActuelle().getRow() + "" +
                + pionMemoire.getCaseActuelle().getColumn() + "" +
                + caseDest.getRow() + "" +
                + caseDest.getColumn()+':';
        plateau.deplacer(pionMemoire.getCaseActuelle(), caseDest, pionMemoire);
        System.out.println(history);
        dernierPionJoue = pionMemoire;
        tourDuJoueurBlanc = !tourDuJoueurBlanc;
        casesAtteignablesProchainTour();
    }

    /**
     *
     * @return
     */
    boolean save()
    {
        BDDManager bdd = new BDDManager();
        bdd.start();
        int jb = Integer.parseInt(bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur = '" + joueurBlanc.getNom() + "';")
                .get(0).get(0));
        int jn = Integer.parseInt(bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur = '" + joueurNoir.getNom() + "';")
                .get(0).get(0));
        System.out.println(jb);
        System.out.println(jn);
        ArrayList<ArrayList<String>> joueurs = bdd.ask("" +
                "SELECT joueurBlancSave, joueurNoirSave " +
                "FROM SAUVEGARDEPARTIE " +
                "WHERE joueurBlancSave = '" + jb +"' AND joueurNoirSave = '" + jn + "';");
        if (!joueurs.isEmpty())
            return false;

        boolean tour = tourDuJoueurBlanc; // true si joueur blanc
        String etatPlateau = "";
        for (int i = 0; i < plateau.getBoard().length; i++)
        {
            if(plateau.getBoard()[i].getPion() == null) {
                etatPlateau += " ,";
            }
            else
            {
                if(plateau.getBoard()[i].getPion().isEstBlanc())
                    etatPlateau += plateau.getBoard()[i].getPion().getCOULEUR() + "b,";
                else
                    etatPlateau += plateau.getBoard()[i].getPion().getCOULEUR() + "n,";

            }

        }
        int id = BDD_Tools.saveHistory(jb, jn, history);

        bdd.edit("INSERT INTO SAUVEGARDEPARTIE VALUES(null, '"+
                jb + "', '" +
                jn + "', " +
                tour + ", '" +
                etatPlateau + "', " +
                id + ");");
        bdd.stop();
        return true;
    }

    // GETTERS & SETTERS
    boolean isJoueurBlancGagnant() { return joueurBlancGagnant; }
    Model_Plateau getPlateau() { return plateau; }
    boolean isTourUn() { return isTourUn; }
    boolean estGagnee() { return estGagnee; }
    boolean isTourDuJoueurBlanc() { return tourDuJoueurBlanc; }
    Model_Pion getPionMemoire() { return pionMemoire; }
    void setPionMemoire(Model_Pion pionMemoire) { this.pionMemoire = pionMemoire; }
    void setTourUn(boolean tourUn) { isTourUn = tourUn; }
    Model_Joueur getJoueurBlanc() { return joueurBlanc; }
    Model_Joueur getJoueurNoir() { return joueurNoir; }
    Model_Pion getDernierPionJoue() { return dernierPionJoue; }
    String getHistory() { return history; }

}