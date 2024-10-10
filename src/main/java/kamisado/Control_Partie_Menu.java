package kamisado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 Created by cladlink on 06/11/16.
 */
class Control_Partie_Menu implements ActionListener
{
    private Vue vue;
    private Model_Accueil accueil;
    Control_Partie_Menu(Vue vue, Model_Accueil accueil)
    {
        this.vue = vue;
        this.accueil = accueil;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(vue.getQuitterMenu()))
        {
            System.exit(0);
        }

        else if (e.getSource().equals(vue.getRetourMenuPrincipalMenu()))
        {
            vue.setJMenuBar(null);
            vue.afficherMenu();
        }

        else if (e.getSource().equals(vue.getUndoMenu()))
        {
            boolean undo = vue.boolJOptionPane("Voulez-vous annuler le dernier coup ?");
            if (undo)
            {
                if(!accueil.getPartie().undo())
                    Vue_FactorPopup.creerPopupText();
                vue.repaint();
            }
        }
    }
}