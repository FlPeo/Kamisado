import java.util.ArrayList;

class Model_Partie
{
    private Model_Accueil accueil;
    private Model_Joueur joueur1;
    private Model_Joueur joueur2;

    private boolean isTourUn;
    private boolean tourDuJoueurBlanc;
    private boolean joueurBlancGagnant;

    private Model_Plateau plateau;
    private Model_Pion pionMemoire;
    private Model_Pion dernierPionJoue;

    private boolean estGagnee;

    /**
     * Constructeur d'une partie sans spécificité
     */
    Model_Partie(Model_Accueil accueil)
    {
        this.accueil = accueil;
        this.joueur1 = new Model_Joueur();
        this.joueur2 = new Model_Joueur();
        this.plateau = new Model_Plateau(this);
        this.estGagnee = false;
        this.tourDuJoueurBlanc = true;
        this.isTourUn = true;
        this.pionMemoire = null;
    }

    /**
     * Définit les cases atteignables du pion qui va bouger en fonction du pion qui a bougé
     */
    void casesAtteignablesProchainTour()
    {
        Model_Pion[] pionsDuTour = tourDuJoueurBlanc?plateau.getPionsBlancs():plateau.getPionsNoirs();

        // Le premier tour est géré séparément car c'est un cas particulier où le joueur à la possibilité de choisir
        // le pion qu'il va bouger.
        if(isTourUn)
        {
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
     * Gère le tour tout en vérifiant que nous ne sommes pas dans un cas de victoire
     * @param row (ligne sur laquelle se trouve la case où le joueur à cliquée)
     * @param column (colonne sur laquelle se trouve la case où le joueur à cliquée)
     */
    void gestionTourJoueur(int row, int column) {
        Model_Case casesPlateau[] = this.plateau.getBoard();
        int ligne = Model_Plateau.LIGNE;

        if (isTourUn)
        {
            if(casesPlateau[column+row*ligne].getPion() != null
                    && tourDuJoueurBlanc == casesPlateau[column+row*ligne].getPion().isEstBlanc() )
            {
                pionMemoire = casesPlateau[column+row*ligne].getPion();
            }
            else if(casesPlateau[column+row*ligne].getPion() == null
                    && pionMemoire != null
                    && pionMemoire.getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
            {
                isTourUn = false;
                deplacerPion(casesPlateau[column+row*ligne]);
            }
        }
        else if(casesPlateau[column+row*ligne].getPion() == null
                && pionMemoire.getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
        {
                    /*System.out.println(dernierPionJoue);
                    System.out.println(pionMemoire);*/
            verifieVictoire(row);
            deplacerPion(casesPlateau[column+row*ligne]);
        }
    }

    /**
     * Vérifie si nous sommes ou non dans une situation gagnante, donc de fin de partie
     * @param row (ligne sur laquelle a été déplacé le dernier pion joué)
     */
    private void verifieVictoire(int row)
    {
        if((tourDuJoueurBlanc && row == 7)
                || (!tourDuJoueurBlanc && row == 0))
        {
            estGagnee = true;
            joueurBlancGagnant = tourDuJoueurBlanc;
        }
    }

    /**
     * Control si un pion ne peut plus se déplacer et dans ce cas indique une situation gagnante
     */
    void controleBlocage()
    {
        if(pionMemoire.getCasesAtteignables().isEmpty())
        {
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
    private void deplacerPion(Model_Case caseDest)
    {
        plateau.deplacer(pionMemoire.getCaseActuelle(), caseDest, pionMemoire);
        tourDuJoueurBlanc = !tourDuJoueurBlanc;
        casesAtteignablesProchainTour();
    }

    // GETTERS & SETTERS
    boolean isJoueurBlancGagnant() { return joueurBlancGagnant; }
    Model_Plateau getPlateau() { return plateau; }
    void setDernierPionJoue(Model_Pion dernierPionJoue) { this.dernierPionJoue = dernierPionJoue; }
    boolean isTourUn() { return isTourUn; }
    boolean estGagnee() { return estGagnee; }
}