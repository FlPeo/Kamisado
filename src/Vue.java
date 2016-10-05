import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

class Vue extends JFrame
{
    private Model_Accueil accueil;
    private Vue_Plateau vue_plateau;
    private int xSize, ySize;
    private Vue_Bouton lancerPartieLocale;
    private Vue_Bouton lancerPartieContreIA;
    private Vue_Bouton lancerPartieEnReseau;
    private Vue_Bouton chargerPartie;
    private Vue_Bouton historique;
    private Vue_Bouton statistiquesDuJoueur;
    private Vue_Bouton options;
    private Vue_Bouton credits;
    private Vue_Bouton quitter;
    private JLabel titre;
    private JLabel background;

    private ResourceBundle texteInternational;
    private Locale locale = new Locale("");  // mettre "" pour anglais, code de deux lettres du pays pour les autres



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
        Locale.setDefault(locale);
        texteInternational = ResourceBundle.getBundle("Traductions.boutons");

        titre = new JLabel(texteInternational.getString("titre"));
        lancerPartieLocale = new Vue_Bouton(texteInternational.getString("partieLocale"));
        lancerPartieContreIA = new Vue_Bouton(texteInternational.getString("defierIA"));

        lancerPartieEnReseau = new Vue_Bouton(texteInternational.getString("partieReseau"));
        chargerPartie = new Vue_Bouton(texteInternational.getString("chargerPartie"));
        historique = new Vue_Bouton(texteInternational.getString("historique"));
        statistiquesDuJoueur = new Vue_Bouton(texteInternational.getString("statistiques"));
        options=new Vue_Bouton(texteInternational.getString("options"));
        credits=new Vue_Bouton(texteInternational.getString("credits"));
        quitter=new Vue_Bouton(texteInternational.getString("quiter"));
        /**
         *
         private Vue_Bouton lancerPartieEnReseau;
         private Vue_Bouton chargerPartie;
         private Vue_Bouton historique;
         private Vue_Bouton statistiquesDuJoueur;
         private Vue_Bouton options;
         private Vue_Bouton credits;
         private Vue_Bouton quitter;
         */

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
        JPanel boutonsMenu = new JPanel(new GridLayout(9, 1, 0, 10));
        boutonsMenu.setOpaque(false);
        boutonsMenu.add(lancerPartieLocale);
        boutonsMenu.add(lancerPartieContreIA);
        boutonsMenu.add(lancerPartieEnReseau);
        boutonsMenu.add(chargerPartie);
        boutonsMenu.add(historique);
        boutonsMenu.add(statistiquesDuJoueur);
        boutonsMenu.add(options);
        boutonsMenu.add(credits);
        boutonsMenu.add(quitter);

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
        lancerPartieEnReseau.addActionListener(listener);
        chargerPartie.addActionListener(listener);
        historique.addActionListener(listener);
        statistiquesDuJoueur.addActionListener(listener);
        options.addActionListener(listener);
        credits.addActionListener(listener);
        quitter.addActionListener(listener);
    }

    // GETTERS & SETTERS

    Vue_Bouton getLancerPartieContreIA() { return lancerPartieContreIA; }
    Vue_Plateau getVue_plateau() { return vue_plateau; }
    void setVue_plateau(Vue_Plateau vue_plateau) { this.vue_plateau = vue_plateau; }
    void display(){ setVisible(true); }
    Vue_Bouton getLancerPartieLocale() { return lancerPartieLocale; }

    public Vue_Bouton getLancerPartieEnReseau() {
        return lancerPartieEnReseau;
    }

    public Vue_Bouton getChargerPartie() {
        return chargerPartie;
    }

    public Vue_Bouton getHistorique() {
        return historique;
    }

    public Vue_Bouton getStatistiquesDuJoueur() {
        return statistiquesDuJoueur;
    }

    public Vue_Bouton getOptions() {
        return options;
    }

    public Vue_Bouton getCredits() {
        return credits;
    }

    public Vue_Bouton getQuitter() {
        return quitter;
    }
}