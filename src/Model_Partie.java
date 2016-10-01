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
     * Vérifie si nous sommes ou non dans une situation gagnante, donc de fin de partie
     * @param row (ligne sur laquelle a été déplacé le dernier pion joué)
     */
    public void verifieVictoire(int row)
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
    public void deplacerPion(Model_Case caseDest)
    {
        plateau.deplacer(pionMemoire.getCaseActuelle(), caseDest, pionMemoire);
        setDernierPionJoue(pionMemoire);
        tourDuJoueurBlanc = !tourDuJoueurBlanc;
        casesAtteignablesProchainTour();
    }

    // GETTERS & SETTERS
    boolean isJoueurBlancGagnant() { return joueurBlancGagnant; }
    Model_Plateau getPlateau() { return plateau; }
    void setDernierPionJoue(Model_Pion dernierPionJoue) { this.dernierPionJoue = dernierPionJoue; }
    boolean isTourUn() { return isTourUn; }
    boolean estGagnee() { return estGagnee; }

    boolean isTourDuJoueurBlanc() {
        return tourDuJoueurBlanc;
    }

    void setTourDuJoueurBlanc(boolean tourDuJoueurBlanc) {
        this.tourDuJoueurBlanc = tourDuJoueurBlanc;
    }

    public Model_Pion getPionMemoire() {
        return pionMemoire;
    }

    public void setPionMemoire(Model_Pion pionMemoire) {
        this.pionMemoire = pionMemoire;
    }

    public void setTourUn(boolean tourUn) {
        isTourUn = tourUn;
    }

    public Model_Joueur getJoueurBlanc() {
        return joueurBlanc;
    }

    public Model_Joueur getJoueurNoir() {
        return joueurNoir;
    }
}