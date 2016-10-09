import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        else if(e.getSource().equals(vue.getCredits()))
        {
            vue.jOptionMessage("Jeu réalisé par : " +
                    "\n Marie-Lucile Caniard " +
                    "\n Michael Boutboul " +
                    "\n Adonis N'Dolo " +
                    "\n Florian Party", "Crédits");
        }
        else if (e.getSource().equals(vue.getQuitter()))
        {
            System.exit(0);
        }
    }
}
