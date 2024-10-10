package kamisado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
        if (e.getSource().equals(vue.getLancerPartieContreIA()))
        {
            accueil.demarrerPartieContreLIA();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(control_Partie_IA);
            accueil.getPartieIa().setCasesAtteignablesPremierTour();
            vue.display();
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
        else if(e.getSource().equals(vue.getOptions()))
        {
            vue.creerWidgetOptions();
        }
        else if(e.getSource().equals(vue.getAnglaisFlag()))
        {
            accueil.setLangue("en");
            Locale locale = new Locale(accueil.getLangue());
            Locale.setDefault(locale);
            JOptionPane.setDefaultLocale(Locale.getDefault());
            vue.initAttribut();
            vue.afficherMenu();
            vue.setButtonControl(this);
        }
        else if(e.getSource().equals(vue.getFrancaisFlag()))
        {
            accueil.setLangue("fr");
            Locale locale = new Locale(accueil.getLangue());
            Locale.setDefault(locale);
            JOptionPane.setDefaultLocale(Locale.getDefault());
            vue.initAttribut();
            vue.afficherMenu();
            vue.setButtonControl(this);
        }
    }

    /**
     * initPartie
     * ensemble d'éléments nécessaire à la création d'une partie
     *
     */
    void initPartie()
    {
        Control_Partie.setJoueurBlanc(accueil.getPartie().getJoueurBlanc().getNom());
        Control_Partie.setJoueurNoir(accueil.getPartie().getJoueurNoir().getNom());
        vue.setVue_plateau(new Vue_Plateau(vue, accueil));
        vue.creerWidgetPartie();
        accueil.getPartie().casesAtteignablesProchainTour(); // a vérifier
        vue.setPartieControl(Control_Partie);
        vue.initMenuPartie();
        vue.setControlMenu(new Control_Partie_Menu(vue, accueil));
        vue.setVisible(true);
    }
}