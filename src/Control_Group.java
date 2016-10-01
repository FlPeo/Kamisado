class Control_Group
{
    private Model_Accueil accueil;
    private Vue vue;
    private Control_Menu_Accueil Control_Menu_Accueil;
    private Control_Partie Control_Partie;
    private Control_Partie_IA control_partie_IA;

    /**
     * Crée l'ensmeble des éléments du model MVC
     */
    Control_Group()
    {
        this.accueil = new Model_Accueil();
        vue = new Vue(accueil);

        Control_Partie = new Control_Partie(accueil, vue);
        control_partie_IA = new Control_Partie_IA(accueil, vue);
        Control_Menu_Accueil = new Control_Menu_Accueil(accueil, vue, Control_Partie, control_partie_IA);
        vue.display();
    }
}