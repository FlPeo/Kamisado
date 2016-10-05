import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 Created by cladlink on 01/10/16.
 */
class Control_Partie_IA extends MouseAdapter
{

    private final Model_Accueil accueil;
    private final Vue vue;

    Control_Partie_IA(Model_Accueil accueil, Vue vue)
    {
        this.accueil = accueil;
        this.vue = vue;
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
            if(accueil.getPartieIa().isTourUn())
            {
                // TODO A faire
            }
        }

    }
}