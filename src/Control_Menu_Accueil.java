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
    private Control_Partie_IA Control_Partie_IA;

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
        this.Control_Partie_IA = Control_Partie_IA;
        vue.setButtonControl(this);
    }

    /**
     * Définition des actions à entreprendre si un écouteur détecte une action
     * @param e (evenement détecté)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        BufferedImage[] imagesPionsJoueurBlanc = new BufferedImage[Model_Plateau.LIGNE];
        BufferedImage[] imagesPionsJoueurNoir = new BufferedImage[Model_Plateau.LIGNE];
        String[] listeCouleurs = Couleur.getListeStringCouleurs();
        int i;
        try
        {
            for(i=0 ; i<listeCouleurs.length; i++){
                imagesPionsJoueurBlanc[i] = ImageIO.read(new File("Images/Jetons/Spirale noire/spirale" + listeCouleurs[i] + ".png"));
                imagesPionsJoueurNoir[i] = ImageIO.read(new File("Images/Jetons/Vague noire/vague" + listeCouleurs[i] + ".png"));
            }
        }
        catch(IOException e2)
        {
            //Cas d'erreur à gérer !!
        }

        if(e.getSource().equals(vue.getLancerPartieLocale()))
        {
            accueil.demarrerPartie();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil, imagesPionsJoueurBlanc, imagesPionsJoueurNoir));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie);
            accueil.getPartie().casesAtteignablesProchainTour();
            vue.display();
        }
        else if (e.getSource().equals(vue.getLancerPartieContreIA()))
        {
            accueil.demarrerPartieContreLIA();
            vue.setVue_plateau(new Vue_Plateau(vue, accueil, imagesPionsJoueurBlanc, imagesPionsJoueurNoir));
            vue.creerWidgetPartie();
            vue.setPartieControl(Control_Partie_IA);
            // cases atteignables de début de partie
            vue.display();
        }
    }
}
