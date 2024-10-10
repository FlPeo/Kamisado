package kamisado;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;


class Vue extends JFrame
{
    private Model_Accueil accueil;
    private Vue_Plateau vue_plateau;
    private int xSize, ySize;
    private Vue_Bouton lancerPartieContreIA;

    private JButton options;
    private JButton credits;
    private Vue_Bouton quitter;
    private Vue_Bouton retourMenu;
    private Vue_Bouton lancerPartieLocale2;
    private JMenuItem undoMenu, retourMenuPrincipalMenu, quitterMenu;
    private Vue_Bouton precedent;
    private Vue_Bouton retour;
    private Vue_Bouton suivant;
    private JLabel titre;
    private JPanel background;
    private JPanel boutonsMenu;

    // Options
    private JLabel titreOptions;
    private JButton francaisFlag;
    private JButton anglaisFlag;
    private JLabel france;
    private JLabel angleterre;
    private JLabel choixLangue;

    // Menu
    JMenu parametres;
    JMenuBar barMenu;

    private ResourceBundle texteInternationalBoutons;
    private ResourceBundle texteInternationalLabels;

    /**
     * Constructeur de la vu
     * @param accueil (model utilisé pour l'accueil)
     */
    Vue(Model_Accueil accueil)
    {
        this.accueil = accueil;
        Toolkit tk = Toolkit.getDefaultToolkit();
        xSize = (int) tk.getScreenSize().getWidth();
        ySize = (int) tk.getScreenSize().getHeight();

        initAttribut();
        creerWidgetAccueil();

        setUndecorated(true);
        setSize(xSize, ySize);
        setTitle("Kamisado");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initialisation de tous les attribus de la classe
     */
    public void initAttribut()
    {
        texteInternationalBoutons = ResourceBundle.getBundle("Traductions.boutons");
        texteInternationalLabels = ResourceBundle.getBundle("Traductions.labels");

        titre = new JLabel(texteInternationalBoutons.getString("titre"));
        titre.setHorizontalAlignment(JLabel.CENTER);
        lancerPartieContreIA = new Vue_Bouton(texteInternationalBoutons.getString("defierIA"));

        options=new BackgroundButton(50, 50, "/Images/iconesBouton/option.jpg");
        credits=new BackgroundButton(50, 50, "/Images/iconesBouton/credit.jpg");
        quitter=new Vue_Bouton(texteInternationalBoutons.getString("quitter"));
        retourMenu=new Vue_Bouton(texteInternationalBoutons.getString("retourMenu"));
        lancerPartieLocale2=new Vue_Bouton(texteInternationalBoutons.getString("partieLocale"));
        precedent=new Vue_Bouton(texteInternationalBoutons.getString("precedent"));
        suivant=new Vue_Bouton(texteInternationalBoutons.getString("suivant"));
        retour=new Vue_Bouton(texteInternationalBoutons.getString("retour"));

        // Options
        try {
            france = new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/drapeaux/franceFlag.jpg")))));
            angleterre = new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/drapeaux/anglaisFlag.jpg")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        francaisFlag = new JButton(france.getIcon());
        anglaisFlag = new JButton(angleterre.getIcon());
        francaisFlag.setBackground(Color.black);
        anglaisFlag.setBackground(Color.black);
        francaisFlag.setBorder(null);
        anglaisFlag.setBorder(null);
        titreOptions = new JLabel(texteInternationalLabels.getString("titreOption"));

        choixLangue = new JLabel(texteInternationalLabels.getString("langue"));

        GraphicsEnvironment fontLabel = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsEnvironment fontTitre = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsEnvironment fontChoix = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try
        {
            fontLabel.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/Cardinal.ttf"))));
            fontTitre.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/Ace Records.ttf"))));
            fontChoix.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/CalligraphyFLF.ttf"))));
        }
        catch (FontFormatException fe)
        {
            Vue_FactorPopup.creerPopupErreurChargement();
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
        Font policeTitre = new Font("Ace Records", Font.BOLD, 150);
        Font policeTitreSecondaire = new Font("Ace Records", Font.BOLD, 70);
        Font policeLabel = new Font("Cardinal", Font.BOLD, 25);
        titre.setFont(policeTitre);
        titreOptions.setFont(policeTitreSecondaire);
        choixLangue.setFont(policeLabel);
    }

    /**
     * initMenuPartie
     * Instancie les attributs de la bar de menu
     *
     */
    void initMenuPartie()
    {
        barMenu = new JMenuBar();

        JMenu optionPartie = new JMenu("Fichier");
        parametres = new JMenu("Options");

        retourMenuPrincipalMenu = new JMenuItem(texteInternationalBoutons.getString("retourMenu"));
        quitterMenu = new JMenuItem(texteInternationalBoutons.getString("quitter"));
        undoMenu = new JMenuItem(texteInternationalBoutons.getString("undo"));

        optionPartie.add(retourMenuPrincipalMenu);
        optionPartie.addSeparator();
        optionPartie.add(quitterMenu);



        barMenu.add(optionPartie);

        setJMenuBar(barMenu);
    }

    void addUndo()
    {
        parametres.add(undoMenu);
        barMenu.add(parametres);
    }

    /**
     * boolJOptionPane
     * Fenetre de dialogue qui demande confiramtion a l'utilisateur (choix YES ou NO)
     * @param message (texte affiché)
     * @return choix de l'utilisateur
     *
     */
    boolean boolJOptionPane(String message)
    {
        int answer = JOptionPane.showConfirmDialog(null, message, null, JOptionPane.YES_NO_OPTION);
        return (answer == JOptionPane.YES_OPTION);
    }

    /**
     * Organise la vue de l'accueil
     */
    private void creerWidgetAccueil()
    {
        boutonsMenu = new JPanel(new GridLayout(9, 1, 0, 15));
        boutonsMenu.setOpaque(false);
        boutonsMenu.add(lancerPartieContreIA);
        //boutonsMenu.add(options);
        //boutonsMenu.add(credits);
        boutonsMenu.add(quitter);

        JPanel iconesOptionEtCredit = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconesOptionEtCredit.setOpaque(false);
        iconesOptionEtCredit.add(options);
        iconesOptionEtCredit.add(credits);




        JPanel center = new JPanel(new GridLayout(1, 3, 0, 0));
        center.getMinimumSize().width = xSize;
        center.setOpaque(false);
        JPanel gauche = new JPanel();
        gauche.setOpaque(false);
        //gauche.getPreferredSize().width = (int)(xSize*0.33333);
        center.add(gauche);


        //boutonsMenu.getPreferredSize().width = (int)(xSize*0.33333);
        center.add(boutonsMenu);
        //iconesOptionEtCredit.getPreferredSize().width =  (int)(xSize*0.33333);
        center.add(iconesOptionEtCredit);


        JPanel organisation = new JPanel(new BorderLayout());
        organisation.setOpaque(false);
        organisation.add(titre, BorderLayout.NORTH);
        organisation.add(center, BorderLayout.SOUTH);

        // Mis ene place du fond d'écran
        background = new BackgroundPanel(xSize, ySize, "/Images/Fonds/fond1.jpg");//new ImageIcon("Images/Fonds/fond1.jpg"));
        background.setSize(xSize, ySize);
        background.setLayout(new FlowLayout());
        background.add(organisation, BorderLayout.SOUTH);

        setContentPane(background);
    }

    /**
     * Lance la vue du plateau
     */
    void creerWidgetPartie()
    {
        initMenuPartie();
        setControlMenu(new Control_Partie_Menu(this, accueil));
        setContentPane(vue_plateau);
    }

    /**
     * Lance la vue des options
     */
    void creerWidgetOptions()
    {
        JPanel titres = new JPanel(new GridLayout(2,1));
        titres.setOpaque(false);
        titres.add(titre);
        titres.add(titreOptions);

        JPanel options = new JPanel(new GridLayout(3,3, 0, 20));
        options.setOpaque(false);
        francaisFlag.setOpaque(false);
        anglaisFlag.setOpaque(false);

        options.add(choixLangue);
        options.add(francaisFlag);
        options.add(anglaisFlag);

        options.add(Box.createVerticalGlue());
        options.add(Box.createVerticalGlue());
        options.add(retourMenu);

        JPanel organisation = new JPanel(new BorderLayout());
        organisation.setOpaque(false);
        organisation.add(titres, BorderLayout.NORTH);
        organisation.add(options, BorderLayout.SOUTH);

        // Mise en place du fond d'écran
        background = new BackgroundPanel(xSize, ySize, "/Images/Fonds/fond1.jpg");//new ImageIcon("Images/Fonds/fond1.jpg"));
        background.setLayout(new FlowLayout());
        background.add(organisation, BorderLayout.CENTER);

        setContentPane(background);
    }

    /**
     * afficherMenu
     * Permet d'afficher les boutons du menu
     *
     */
    void afficherMenu()
    {
        creerWidgetAccueil();
        setVisible(true);
    }

    /**
     * Ecoute les evenements sur les cases du plateau
     * @param e (ecouteur de type MouseListener)
     */
    void setPartieControl(MouseListener e)
    {
        if (vue_plateau != null)
            vue_plateau.addMouseListener(e);
    }

    void removePartieControl(MouseListener e)
    {
        if (vue_plateau != null)
            vue_plateau.removeMouseListener(e);
    }

    /**
     * Ecoute les évènements sur les boutons du menu
     * @param listener (ecouteur de type ActionListener)
     */
    void setButtonControl(ActionListener listener)
    {
        lancerPartieContreIA.addActionListener(listener);
        options.addActionListener(listener);
        credits.addActionListener(listener);
        quitter.addActionListener(listener);
        retourMenu.addActionListener(listener);
        lancerPartieLocale2.addActionListener(listener);
        francaisFlag.addActionListener(listener);
        anglaisFlag.addActionListener(listener);
    }

    void setButtonHistoriqueControl(ActionListener listener)
    {
        retour.addActionListener(listener);
        suivant.addActionListener(listener);
        precedent.addActionListener(listener);
    }


    /**
     * setControlMenu
     * Ecoute les evenements du menu
     * @param e (ecouteur de type ActionListener)
     */
    void setControlMenu(ActionListener e)
    {
        retourMenuPrincipalMenu.addActionListener(e);
        undoMenu.addActionListener(e);
        quitterMenu.addActionListener(e);
    }

    /**
     * messagePop
     * Permet d'afficher une fenetre pop-up avec un champs de saisi pour récupérer une information
     * @param message (texte à afficher)
     * @return fenetre qui s'affiche
     */
    String messagePop(String message)
    {
        return JOptionPane.showInputDialog(this, message, "ChessMaster", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * jOptionMessage
     * Fenetre de dialogue permettant de donner une information a l'utilisateur
     * @param message (texte affiché)
     *
     */
    void jOptionMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "A propos", JOptionPane.INFORMATION_MESSAGE);
    }




    // GETTERS & SETTERS
    Vue_Bouton getLancerPartieContreIA() { return lancerPartieContreIA; }
    Vue_Plateau getVue_plateau() { return vue_plateau; }
    void setVue_plateau(Vue_Plateau vue_plateau) { this.vue_plateau = vue_plateau; }
    void display(){
        setVisible(true);
        updateSizePanelBouton();
        setVisible(true);
    }

    JButton getOptions() { return options;}
    JButton getCredits() { return credits; }
    Vue_Bouton getQuitter() { return quitter; }
    JMenuItem getRetourMenuPrincipalMenu() {
        return retourMenuPrincipalMenu;
    }
    JMenuItem getUndoMenu() {
        return undoMenu;
    }
    JMenuItem getQuitterMenu() {
        return quitterMenu;
    }
    Vue_Bouton getRetourMenu() {
        return retourMenu;
    }
    Vue_Bouton getLancerPartieLocale2() {
        return lancerPartieLocale2;
    }

    Vue_Bouton getPrecedent() {
        return precedent;
    }
    Vue_Bouton getRetour() {
        return retour;
    }
    Vue_Bouton getSuivant() {
        return suivant;
    }
    JButton getFrancaisFlag() {
        return francaisFlag;
    }
    void setFrancaisFlag(JButton francaisFlag) {
        this.francaisFlag = francaisFlag;
    }
    JButton getAnglaisFlag() {
        return anglaisFlag;
    }
    void setAnglaisFlag(JButton anglaisFlag) {
        this.anglaisFlag = anglaisFlag;
    }
    JLabel getFrance() {
        return france;
    }
    void setFrance(JLabel france) {
        this.france = france;
    }
    JLabel getAngleterre() {
        return angleterre;
    }
    void setAngleterre(JLabel angleterre) {
        this.angleterre = angleterre;
    }


    void updateSizePanelBouton(){
        boutonsMenu.setPreferredSize(new Dimension(boutonsMenu.getWidth(), ySize-titre.getHeight()-150));
    }
}