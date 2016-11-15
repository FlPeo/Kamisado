import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;


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
    private Vue_Bouton retourMenu;
    private Vue_Bouton lancerPartieLocale2;
    private Vue_Bouton nouveauPseudo;
    private Vue_Bouton undoMenu, retourMenuPrincipalMenu;
    private Vue_Bouton precedent;
    private Vue_Bouton retour;
    private Vue_Bouton suivant;
    private JLabel titre;
    private JLabel background;
    private JLabel joueur1;
    private JLabel joueur2;
    private JComboBox listePseudo1;
    private JFrame vueHisto;

    private JComboBox listePseudo2;
    private ResourceBundle texteInternational;
    private ResourceBundle texteInternational2;

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
        texteInternational = ResourceBundle.getBundle("Traductions.boutons");
        texteInternational2 = ResourceBundle.getBundle("Traductions.labels");

        titre = new JLabel(texteInternational.getString("titre"));
        lancerPartieLocale = new Vue_Bouton(texteInternational.getString("partieLocale"));
        lancerPartieContreIA = new Vue_Bouton(texteInternational.getString("defierIA"));

        lancerPartieEnReseau = new Vue_Bouton(texteInternational.getString("partieReseau"));
        chargerPartie = new Vue_Bouton(texteInternational.getString("chargerPartie"));
        historique = new Vue_Bouton(texteInternational.getString("historique"));
        statistiquesDuJoueur = new Vue_Bouton(texteInternational.getString("statistiques"));
        options=new Vue_Bouton(texteInternational.getString("options"));
        credits=new Vue_Bouton(texteInternational.getString("credits"));
        quitter=new Vue_Bouton(texteInternational.getString("quitter"));
        retourMenu=new Vue_Bouton(texteInternational.getString("retourMenu"));
        lancerPartieLocale2=new Vue_Bouton(texteInternational.getString("partieLocale"));
        nouveauPseudo=new Vue_Bouton(texteInternational.getString("ajouterPseudo"));
        precedent=new Vue_Bouton(texteInternational.getString("precedent"));
        suivant=new Vue_Bouton(texteInternational.getString("suivant"));
        retour=new Vue_Bouton(texteInternational.getString("retour"));

        majListeJoueur();

        joueur1 = new JLabel(texteInternational2.getString("pseudoJ1"));
        joueur2 = new JLabel(texteInternational2.getString("pseudoJ2"));

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
            Vue_FactorPopup.creerPopupErreurChargement();
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
        Font policeTitre = new Font("Ace Records", Font.BOLD, 150);
        Font policeLabel = new Font("Cardinal", Font.BOLD, 25);
        titre.setFont(policeTitre);
        joueur1.setFont(policeLabel);
        joueur2.setFont(policeLabel);
    }

    /**
     * initMenuPartie
     * Instancie les attributs de la bar de menu
     *
     */
    private void initMenuPartie()
    {
        JMenuBar barMenu = new JMenuBar();

        JMenu optionPartie = new JMenu("Fichier");
        JMenu parametres = new JMenu("Options");

        retourMenuPrincipalMenu = new Vue_Bouton("Menu Principal");

        undoMenu = new Vue_Bouton("Undo");

        optionPartie.add(retourMenuPrincipalMenu);
        optionPartie.addSeparator();
        optionPartie.add(quitter);

        parametres.add(undoMenu);

        barMenu.add(optionPartie);
        barMenu.add(parametres);

        setJMenuBar(barMenu);
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

    void creerWidgetChoixPseudos()
    {
        JPanel pseudos = new JPanel(new GridLayout(3, 3));
        pseudos.setOpaque(false);
        pseudos.add(joueur1);
        pseudos.add(Box.createVerticalGlue());
        pseudos.add(joueur2);
        pseudos.add(listePseudo1);
        pseudos.add(Box.createVerticalGlue());
        pseudos.add(listePseudo2);
        pseudos.add(nouveauPseudo);
        pseudos.add(lancerPartieLocale2);
        pseudos.add(retourMenu);

        JPanel organisation = new JPanel(new BorderLayout());
        organisation.setOpaque(false);
        organisation.add(titre, BorderLayout.NORTH);
        organisation.add(pseudos, BorderLayout.SOUTH);

        // Mise en place du fond d'écran
        background = new JLabel(new ImageIcon("Images/Fonds/fond1.jpg"));
        background.setSize(xSize, ySize);
        background.setLayout(new FlowLayout());
        background.add(organisation, BorderLayout.CENTER);

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
     * afficherMenu
     * Permet de relancer la musique du menu et d'afficher les boutons du menu
     *
     */
    void afficherMenu()
    {
        //MusiqueChess.playMedievalTheme();
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
        retourMenu.addActionListener(listener);
        lancerPartieLocale2.addActionListener(listener);
        nouveauPseudo.addActionListener(listener);
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
        quitter.addActionListener(e);
    }

    void choixHistoriqueAConsulter()
    {
        int i,j;
        BDDManager bdd = new BDDManager();
        bdd.start();

        // On récupère les id des joueurs ayants sauvegardé une partie
        ArrayList<ArrayList<String>> idJoueursHistorique = bdd.ask("SELECT joueurBlanc_id, joueurNoir_id" +
                " FROM HISTORIQUEPARTIE;");

        if(idJoueursHistorique.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Il n'y a pas d'historique disponible.", "Voir l'historique d'une partie"
                    , JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ArrayList<ArrayList<String>> datesParties = bdd.ask("SELECT dateHistorique FROM HISTORIQUEPARTIE;");

        String[][] pseudosJoueurs = new String[idJoueursHistorique.size()][2];

        String[] joueursBlancs = new String[idJoueursHistorique.size()];
        String[] joueursNoirs = new String[idJoueursHistorique.size()];
        String[] dates = new String[datesParties.size()];

        // On récupère les pseudos corespondants à chaque id récupérés précédamment
        for (i = 0; i < idJoueursHistorique.size(); i++)
        {
            for(j=0; j<idJoueursHistorique.get(i).size(); j++)
            {
                pseudosJoueurs[i][j] = bdd.ask("SELECT JOUEUR.pseudoJoueur FROM JOUEUR WHERE JOUEUR.id = "
                        + idJoueursHistorique.get(i).get(j) + ";").get(0).get(0) + "";
            }
            joueursBlancs[i] = pseudosJoueurs[i][0];
            joueursNoirs[i] = pseudosJoueurs[i][1];
        }
        for (i = 0; i < datesParties.size(); i++)
            for(j=0; j<datesParties.get(i).size(); j++)
                dates[i] = datesParties.get(i).get(j);

        // On crée une liste qui va être affichée dans une fenetre popup pour que l'utilisateur choisisse
        // quelle sauvegarde il veut reprendre
        String[] possibilitesParties = new String[idJoueursHistorique.size()];
        for(i=0; i<possibilitesParties.length; i++)
            possibilitesParties[i] = joueursBlancs[i] + " VS " + joueursNoirs[i] + " le " + dates[i];

        // On crée la boite de dialogue
        accueil.setPartieAVisualiser((String) JOptionPane.showInputDialog(null, "De quelle partie voulez-vous charger l'historique ?",
                "Voir l'historique d'une partie", JOptionPane.QUESTION_MESSAGE, null, possibilitesParties,
                possibilitesParties[0]));

        bdd.stop();
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

    /**
     * majListeJoueur
     * Met à jour la liste des joueurs pour le formulaire
     */
    void majListeJoueur()
    {
        listePseudo1 = new JComboBox(accueil.listePseudos());
        listePseudo2 = new JComboBox(accueil.listePseudos());
    }

    void creerWidgetAfficherHistorique()
    {
        vue_plateau = new Vue_Plateau(this, accueil);
        vueHisto = new JFrame();

        JPanel panelBoard = new JPanel();
        panelBoard.setLayout(new GridLayout(1,1));
        panelBoard.add(vue_plateau);

        JPanel panelButton = new JPanel();
        panelButton.add(precedent);
        panelButton.add(suivant);
        panelButton.add(retour);

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
        panelGeneral.add(panelBoard);
        panelGeneral.add(panelButton);

        setContentPane(panelGeneral);
    }

    ArrayList<String> recupererHistoCoupsPartie()
    {
        BDDManager bdd = new BDDManager();
        bdd.start();

        // partieAVisualiser au format : "pseudo1 VS pseudo2"
        String[] partieAVisualiserSplitee = accueil.getPartieAVisualiser().split(" ");
        String joueurBlanc = partieAVisualiserSplitee[0];
        String joueurNoir = partieAVisualiserSplitee[2];
        String dates = partieAVisualiserSplitee[4] + " " + partieAVisualiserSplitee[5];

        String idJB = bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur LIKE '" + joueurBlanc + "';").get(0).get(0);
        String idJN = bdd.ask("SELECT id FROM JOUEUR WHERE pseudoJoueur LIKE '" + joueurNoir + "';").get(0).get(0);
        String requete = "SELECT coupsJouee FROM HISTORIQUEPARTIE "
                + "WHERE joueurBlanc_id = " + idJB
                + " AND joueurNoir_id = " + idJN
                + " AND dateHistorique = '" + dates
                + "';";
        String histo = bdd.ask(requete).get(0).get(0);
        ArrayList<String> coups = new ArrayList<>();
        for(int i=0; i<histo.split(":").length; i++)
            coups.add(histo.split(":")[i]);
        bdd.stop();
        return coups;
    }

    // GETTERS & SETTERS
    Vue_Bouton getLancerPartieContreIA() { return lancerPartieContreIA; }
    Vue_Plateau getVue_plateau() { return vue_plateau; }
    void setVue_plateau(Vue_Plateau vue_plateau) { this.vue_plateau = vue_plateau; }
    void display(){ setVisible(true); }
    Vue_Bouton getLancerPartieLocale() { return lancerPartieLocale; }
    Vue_Bouton getLancerPartieEnReseau() { return lancerPartieEnReseau; }
    Vue_Bouton getChargerPartie() { return chargerPartie; }
    Vue_Bouton getHistorique() { return historique; }
    Vue_Bouton getStatistiquesDuJoueur() { return statistiquesDuJoueur; }
    Vue_Bouton getOptions() { return options;}
    Vue_Bouton getCredits() { return credits; }
    Vue_Bouton getQuitter() { return quitter; }
    Vue_Bouton getRetourMenuPrincipalMenu() {
        return retourMenuPrincipalMenu;
    }
    Vue_Bouton getUndoMenu() {
        return undoMenu;
    }
    Vue_Bouton getRetourMenu() {
        return retourMenu;
    }
    Vue_Bouton getLancerPartieLocale2() {
        return lancerPartieLocale2;
    }
    Vue_Bouton getNouveauPseudo() {
        return nouveauPseudo;
    }
    JComboBox getListePseudo1() {
        return listePseudo1;
    }
    JComboBox getListePseudo2() {
        return listePseudo2;
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
}