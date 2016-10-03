import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

class Vue extends JFrame
{
    private Model_Accueil accueil;
    private Vue_Plateau vue_plateau;
    private int xSize, ySize;
    private Vue_Bouton lancerPartieLocale;
    private Vue_Bouton lancerPartieContreIA;
    private JLabel titre;
    private JLabel background;

    /**
     * Constructeur de la vu
     * @param accueil (model utilisé pour l'accueil)
     */
    Vue(Model_Accueil accueil)
    {
        this.accueil = accueil;

        initAttribut();
        creerWidgetAccueil();

        setUndecorated(true);
        Toolkit tk = Toolkit.getDefaultToolkit();
        xSize = (int) tk.getScreenSize().getWidth();
        ySize = (int) tk.getScreenSize().getHeight();
        setSize(xSize, ySize);
        setTitle("Kamisado");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initialisation de tous les attribus de la classe
     */
    private void initAttribut()
    {
        titre = new JLabel("Kamisado");
        lancerPartieLocale = new Vue_Bouton("Lancer une partie");
        lancerPartieContreIA = new Vue_Bouton("Défi l'IA");

        GraphicsEnvironment fontLabel = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsEnvironment fontTitre = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsEnvironment fontChoix = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try
        {
            fontLabel.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Cardinal.ttf")));
            fontTitre.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Ace Records.ttf")));
            fontChoix.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/CalligraphyFLF.ttf")));
        }
        catch (FontFormatException fe)
        {
            jOptionMessage("pas le bon format !", "Erreur");
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
        Font policeTitre = new Font("Ace Records", Font.BOLD, 150);
        titre.setFont(policeTitre);
    }

    /**
     * Affiche une fenêtre popup
     * @param message (chaine de caractères qui sera affichée dans le corps de la fenêtre
     * @param titreFenetre (titre de la fenêtre)
     */
    void jOptionMessage(String message, String titreFenetre)
    {
        JOptionPane.showMessageDialog(this, message, titreFenetre, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Organise la vue de l'accueil
     */
    private void creerWidgetAccueil()
    {
        JPanel boutonsMenu = new JPanel(new GridLayout(2, 1, 0, 10));
        boutonsMenu.setOpaque(false);
        boutonsMenu.add(lancerPartieLocale);
        boutonsMenu.add(lancerPartieContreIA);

        JPanel organisation = new JPanel(new BorderLayout());
        organisation.setOpaque(false);
        organisation.add(titre, BorderLayout.NORTH);
        organisation.add(boutonsMenu, BorderLayout.SOUTH);

        // Mis ene place du fond d'écran
        background = new JLabel(new ImageIcon("Images/Fonds/fond1.jpg"));
        background.setSize(xSize, ySize);
        background.setLayout(new FlowLayout());
        background.add(organisation, BorderLayout.CENTER);

        setContentPane(background);
    }

    /**
     * Lance la vue du plateau
     */
    void creerWidgetPartie() { setContentPane(vue_plateau); }

    /**
     * Ecoute les evenements sur les cases du plateau
     * @param e (ecouteur de type MouseListener)
     */
    void setPartieControl(MouseListener e)
    {
        if (vue_plateau != null)
            vue_plateau.addMouseListener(e);
    }

    /**
     * Ecoute les évènements sur les boutons du menu
     * @param listener (ecouteur de type ActionListener)
     */
    void setButtonControl(ActionListener listener)
    {
        lancerPartieLocale.addActionListener(listener);
        lancerPartieContreIA.addActionListener(listener);
    }

    // GETTERS & SETTERS

    Vue_Bouton getLancerPartieContreIA() { return lancerPartieContreIA; }
    Vue_Plateau getVue_plateau() { return vue_plateau; }
    void setVue_plateau(Vue_Plateau vue_plateau) { this.vue_plateau = vue_plateau; }
    void display(){ setVisible(true); }
    Vue_Bouton getLancerPartieLocale() { return lancerPartieLocale; }
}