import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class Control_Menu_Accueil implements ActionListener
{
    private Model_Accueil accueil;
    private Vue vue;
    private Control_Partie Control_Partie;
    private Control_Partie_IA control_Partie_IA;


    /**
     * Constructeur du controleur de l'accueil
     * @param accueil (model de l'accueil)
     * @param vue (vue générale)
     * @param Control_Partie (controleur d'une partie)
     */
    Control_Menu_Accueil(
            Model_Accueil accueil, Vue vue, Control_Partie Control_Partie, Control_Partie_IA control_Partie_IA)
    {
        this.accueil = accueil;
        this.vue = vue;
        this.Control_Partie = Control_Partie;
        this.control_Partie_IA = control_Partie_IA;
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
            vue.creerWidgetChoixPseudos();
        }
        else if(e.getSource().equals(vue.getLancerPartieLocale2()))
        {
            accueil.setPseudoJoueurBlanc((String)vue.getListePseudo1().getSelectedItem());
            accueil.setPseudoJoueurNoir((String)vue.getListePseudo2().getSelectedItem());
            if(accueil.getPseudoJoueurBlanc().equals(accueil.getPseudoJoueurNoir()))
            {
                vue.jOptionMessage("Vous ne pouvez pas jouer contre vous-même !");
                return;
            }
            accueil.demarrerPartie();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();
        }
        else if (e.getSource().equals(vue.getLancerPartieContreIA()))
        {
            accueil.demarrerPartieContreLIA();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(control_Partie_IA);
            accueil.getPartieIa().setCasesAtteignablesPremierTour();
            vue.display();
        }
        else if(e.getSource().equals(vue.getHistorique()))
        {
            vue.choixHistoriqueAConsulter();
            if(accueil.getPartieAVisualiser() == null || accueil.getPartieAVisualiser().equals(""))
            {
                System.err.println("escape");
                return;
            }
        }
        else if(e.getSource().equals(vue.getCredits()))
        {
            Vue_FactorPopup.creerPopupCredits();
        }
        else if (e.getSource().equals(vue.getQuitter()))
        {
            System.exit(0);
        }
        else if(e.getSource().equals(vue.getRetourMenu()))
        {
            vue.afficherMenu();
        }
        else if(e.getSource().equals(vue.getNouveauPseudo()))
        {
            String pseudo = vue.messagePop("Entrez un nouveau pseudo :");
            if (pseudo == null)
                return;
            String[] listeJoueurs = accueil.listePseudos();
            for(int i = 0; i < listeJoueurs.length; i++)
            {
                if (listeJoueurs[i].equals(pseudo)) {
                    vue.jOptionMessage("Ce pseudo n'est pas disponible");
                    return;
                }
            }
            accueil.ajouterNouveauJoueur(pseudo);
            vue.majListeJoueur();
            vue.creerWidgetChoixPseudos();
        }
    }
}
