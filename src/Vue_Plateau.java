import javax.swing.*;
import java.awt.*;

class Vue_Plateau extends JPanel
{
    private Model_Accueil accueil;
    private Vue vue;
    private final int SIZECASE = 80;

    /**
     * COnstructeur d'une vue de plateau
     * @param vue (vue générale)
     * @param accueil (model relatif à l'accueil du jeu)
     */
    Vue_Plateau(Vue vue, Model_Accueil accueil)
    {
        this.vue = vue;
        this.accueil = accueil;
    }

    /**
     * Met en place les dimensions du plateau
     * @return (dimensions du plateau)
     */
    @Override
    public Dimension getPreferredSize() { return new Dimension(8*SIZECASE,8*SIZECASE); }

    /**
     * Paint l'objet graphique. Regroupe tous les objets graphiques
     * @param g (boite à outil servant à peindre des éléments)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int i, x, y, tailleDuPlateau = accueil.getPartie().getPlateau().getBoard().length;
        int couleurCase, couleurPion;
        Model_Pion pionSurLaCase;

        for(i=0; i<8*8; i++)
        {
            couleurCase = accueil.getPartie().getPlateau().getBoard()[i].getCOULEUR();
            switch (couleurCase)
            {
                case Couleur.MARRON:
                    g.setColor(Color.getHSBColor(31, 0.36f, 0.26f)); break;
                case Couleur.ROSE:
                    g.setColor(Color.PINK); break;
                case Couleur.ORANGE:
                    g.setColor(Color.ORANGE); break;
                case Couleur.ROUGE:
                    g.setColor(Color.RED); break;
                case Couleur.VERT:
                    g.setColor(Color.GREEN); break;
                case Couleur.JAUNE:
                    g.setColor(Color.YELLOW); break;
                case Couleur.BLEU:
                    g.setColor(Color.BLUE); break;
                case Couleur.VIOLET:
                    g.setColor(Color.getHSBColor(300, 0.76f, 0.72f)); break;
            }
            y = i%8;
            x = i/8;
            g.fillRect(y * SIZECASE + 360, -x * SIZECASE + 580, SIZECASE, SIZECASE);
        }
        g.setColor(Color.WHITE);      //Juste pour les tests avec les pions affiches en texte

        //dessine les pieces
        for(i=0; i<tailleDuPlateau; i++)
        {
            pionSurLaCase = accueil.getPartie().getPlateau().getBoard()[i].getPion();
            if(pionSurLaCase != null)
            {
                couleurPion = accueil.getPartie().getPlateau().getBoard()[i].getPion().getCOULEUR();
                x = i/8;
                y = i%8;
                g.drawString(""+couleurPion, y * SIZECASE +380, -x * SIZECASE +620);
            }
        }
    }
}
