import javax.swing.*;
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
        if (e.getSource().equals(vue.getLancerPartieRapide())){
            accueil.demarrerPartieRapide();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();
        }
        else if(e.getSource().equals(vue.getLancerPartieLocale()))
        {
            vue.creerWidgetChoixPseudos();
        }
        else if(e.getSource().equals(vue.getLancerPartieLocale2()))
        {
            if (vue.getListePseudo1().getSelectedItem() == null ||
                    vue.getListePseudo2().getSelectedItem() == null)
            {
                vue.jOptionMessage("Veuillez ajouter ou sélectionner des pseudos.");
                return;
            }
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
            accueil.setPartieAVisualiser(null);
            vue.choixHistoriqueAConsulter();
            if(accueil.getPartieAVisualiser() == null || accueil.getPartieAVisualiser().equals(""))
            {
                System.err.println("escape");
                return;
            }
            accueil.demarrerPartieFictive();
            vue.creerWidgetAfficherHistorique();
            vue.display();
        }
        else if (e.getSource().equals(vue.getStatistiquesDuJoueur()))
        {
            if (vue.statistiquesJoueur() == 0)
                return;
            if (accueil.getPseudoChoisi() != null)
                vue.fenetreStatsJoueur(accueil.getPseudoChoisi());
            else
                System.err.println("escape");
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
        else if(e.getSource().equals(vue.getChargerPartie()))
        {
            // Affichage de la pop-up pour le choix des parties à charger
            vue.afficherPartiesACharger();

            // Si aucune partie n'est séléctionnée
            if(accueil.getPartieACharger() == null || accueil.getPartieACharger().isEmpty())
                return;

            // Initialisation des joueurs
            String jBlanc = accueil.getPartieACharger().split(" ")[0];
            String jNoir = accueil.getPartieACharger().split(" ")[2];
            accueil.setPseudoJoueurBlanc(jBlanc);
            accueil.setPseudoJoueurNoir(jNoir);
            accueil.demarrerPartie();

            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();

            // On supprime pour ne pas interférer avec la prochaine fois qu'un joueur voudra charger une partie. Ex : si
            // on ne supprime pas et qu'un joueur clique sur Charger une partie et que finalement il fait Annuler, la
            // partie chargée sera quand même lancée.
            accueil.setPartieACharger("");

            /*
            accueil.demarrerPartie();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();
            */
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
            vue.dispose();
            vue = new Vue(accueil);
            vue.setButtonControl(this);
            vue.setVisible(true);
        }
        else if(e.getSource().equals(vue.getFrancaisFlag()))
        {
            accueil.setLangue("fr");
            Locale locale = new Locale(accueil.getLangue());
            Locale.setDefault(locale);
            JOptionPane.setDefaultLocale(Locale.getDefault());
            vue.dispose();
            vue = new Vue(accueil);
            vue.setButtonControl(this);
            vue.setVisible(true);
        }
        else if(e.getSource().equals(vue.getMusiqueOff()))
        {

        }
        else if(e.getSource().equals(vue.getMusiqueOn()))
        {

        }
    }
}
