class Model_Joueur
{
    private String nom;
    private Model_Partie model_partie;
    private boolean estJoueurBlanc;

    public Model_Joueur(String nom, boolean joueurBlanc){
        this.nom = nom;
        estJoueurBlanc = joueurBlanc;
    }

    void gestionTourJoueur(int row, int column) {
        Model_Case casesPlateau[] = model_partie.getPlateau().getBoard();
        int ligne = Model_Plateau.LIGNE;                      //utiliser autre chose (sqrt(casesPlateau.length) par ex) si besoin d'un mock plus reduit

        if (model_partie.isTourUn())
        {
            if(casesPlateau[column+row*ligne].getPion() != null
                    && estJoueurBlanc == casesPlateau[column+row*ligne].getPion().isEstBlanc() )
            {
                model_partie.setPionMemoire(casesPlateau[column+row*ligne].getPion());
            }
            else if(casesPlateau[column+row*ligne].getPion() == null
                    && model_partie.getPionMemoire() != null
                    && model_partie.getPionMemoire().getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
            {
                model_partie.setTourUn(false);
                model_partie.deplacerPion(casesPlateau[column+row*ligne]);
            }
        }
        else if(casesPlateau[column+row*ligne].getPion() == null
                && model_partie.getPionMemoire().getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
        {
                    /*System.out.println(dernierPionJoue);
                    System.out.println(pionMemoire);*/
            model_partie.verifieVictoire(row);
            model_partie.deplacerPion(casesPlateau[column+row*ligne]);
        }
    }

    public String getNom() {
        return nom;
    }

    public boolean isJoueurBlanc() {
        return estJoueurBlanc;
    }

    public void setPartie(Model_Partie model_partie) {
        this.model_partie = model_partie;
    }
}
