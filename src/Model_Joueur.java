class Model_Joueur
{
    private int id;
    private String nom;
    private Model_Partie model_partie2V2;
    private boolean estJoueurBlanc;

    Model_Joueur(String nom, boolean joueurBlanc, int id)
    {
        this.id = id;
        this.nom = nom;
        estJoueurBlanc = joueurBlanc;
    }

    void gestionTourJoueur(int row, int column)
    {
        Model_Case casesPlateau[] = model_partie2V2.getPlateau().getBoard();
        int ligne = Model_Plateau.LIGNE;
        //utiliser autre chose (sqrt(casesPlateau.length) par ex) si besoin d'un mock plus reduit

        if (model_partie2V2.isTourUn())
        {
            if(casesPlateau[column+row*ligne].getPion() != null
                    && estJoueurBlanc == casesPlateau[column+row*ligne].getPion().isEstBlanc() )
            {
                model_partie2V2.setPionMemoire(casesPlateau[column+row*ligne].getPion());
            }
            else if(casesPlateau[column+row*ligne].getPion() == null
                    && model_partie2V2.getPionMemoire() != null
                    && model_partie2V2.getPionMemoire().getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
            {
                model_partie2V2.setTourUn(false);
                model_partie2V2.deplacerPion(casesPlateau[column+row*ligne]);
            }
        }
        else if(casesPlateau[column+row*ligne].getPion() == null
                && model_partie2V2.getPionMemoire().getCasesAtteignables().contains(casesPlateau[column+row*ligne]))
        {
                    /*System.out.println(dernierPionJoue);
                    System.out.println(pionMemoire);*/
            model_partie2V2.verifieVictoire(row);
            model_partie2V2.deplacerPion(casesPlateau[column+row*ligne]);
        }
    }

    public String getNom() {
        return nom;
    }

    public boolean isJoueurBlanc() {
        return estJoueurBlanc;
    }

    public void setPartie(Model_Partie model_partie2V2) {
        this.model_partie2V2 = model_partie2V2;
    }

    public int getId() {
        return id;
    }
}
