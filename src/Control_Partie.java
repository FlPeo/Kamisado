import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

class Control_Partie extends MouseAdapter
{
    private Vue vue;
    private Model_Accueil accueil;

    private ResourceBundle texteInternational;
    private Locale locale = new Locale("");

    /**
     * Constructeur du controleur d'une partie
     * @param accueil (model de l'accueil)
     * @param vue (vue générale)
     */
    Control_Partie(Model_Accueil accueil, Vue vue)
    {
        this.accueil = accueil;
        this.vue = vue;
        Locale.setDefault(locale);
        texteInternational = ResourceBundle.getBundle("Traductions.victoire");
        vue.setPartieControl(this);
    }

    /**
     * Définition des actions à entreprendre si un écouteur détecte une action
     * @param e (evenement détecté)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int column = (e.getX()-360)/80;
        int row = Math.abs(((e.getY()-20)/80)-7);

        if (e.getSource().equals(vue.getVue_plateau())
                && column >= 0
                && column <=7
                && row >=0
                && row <=7)
        {
            if(accueil.getPartie().isTourDuJoueurBlanc()){
                accueil.getPartie().getJoueurBlanc().gestionTourJoueur(row, column);
            }
            else{
                accueil.getPartie().getJoueurNoir().gestionTourJoueur(row, column);
            }

            if(!accueil.getPartie().estGagnee() && !accueil.getPartie().isTourUn())
                accueil.getPartie().controleBlocage();

            vue.repaint();

            if(accueil.getPartie().estGagnee()) finPartie();
        }

    }

    /**
     * Actions à entreprendre si une situation gagnante est détectée
     */
    private void finPartie()
    {
        String nomJoueur;
        vue.setPartieControl(null);

        if(accueil.getPartie().isJoueurBlancGagnant())
            nomJoueur = accueil.getPartie().getJoueurBlanc().getNom();
        else
            nomJoueur = accueil.getPartie().getJoueurNoir().getNom();
        vue.jOptionMessage(nomJoueur
                + " "
                + texteInternational.getString("message"),
                texteInternational.getString("titreFenetre"));
    }
}