import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Control_Menu_Accueil implements ActionListener
{
    private Model_Accueil accueil;
    private Vue vue;
    private Control_Partie Control_Partie;

    /**
     * Constructeur du controleur de l'accueil
     * @param accueil (model de l'accueil)
     * @param vue (vue générale)
     * @param Control_Partie (controleur d'une partie)
     */
    Control_Menu_Accueil(Model_Accueil accueil, Vue vue, Control_Partie Control_Partie)
    {
        this.accueil = accueil;
        this.vue = vue;
        this.Control_Partie = Control_Partie;
        vue.setButtonControl(this);
    }

    /**
     * Définition des actions à entreprendre si un écouteur détecte une action
     * @param e (evenement détecté)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(vue.getLancerPartieLocale()))
        {
            accueil.demarrerPartie();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();
        }
    }
}
