import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

class Control_Partie_IA extends MouseAdapter
{

    private final Model_Accueil accueil;
    private final Vue vue;
    private ResourceBundle texteInternational;
    private Locale locale = new Locale("");

    Control_Partie_IA(Model_Accueil accueil, Vue vue)
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
        byte i;
        int column = (e.getX() - 360) / 80;
        int row = Math.abs(((e.getY() - 20) / 80) - 7);
        byte[] plateau = accueil.getPartieIa().getPlateau();
        int index = 8 * row + column;

        if (e.getSource().equals(vue.getVue_plateau())
                && column >= 0
                && column <= 7
                && row >= 0
                && row <= 7)
        {
            // ML début
            if (accueil.getPartieIa().isTourUn())
            {
                // Joueur blanc = humain et c'est lui qui commence
                // On regarde si il y a un pion sur la case cliquée et si le pion appartient au joueur
                if (plateau[index] != -1 && (index) < 8)
                {
                    accueil.getPartieIa().setPionMemoire(plateau[index]);
                }
                // Si il n'y a pas de pion sur la case cliquée et qu'il y a un pionMemoire
                else if (plateau[index] == -1
                        && accueil.getPartieIa().getPionMemoire() != -1) {
                    // On vérifie que la case cliquée est dans les cases atteignables du pion en mémoire
                    boolean isCaseAtteignable = false;
                    for (i = 0; i < 14; i++)
                    {
                        if (accueil.getPartieIa().getCasesAtteignablesTourUn()[accueil.getPartieIa().getPionMemoire()][i]
                                == index)
                        {
                            isCaseAtteignable = true;
                            break;
                        }
                    }

                    if (isCaseAtteignable)
                    {
                        // On indique que le pion est maintenant sur la case cliqué
                        plateau[8 * row + column] = accueil.getPartieIa().getPionMemoire();
                        // On supprime le pion de son ancien emplacement
                        for (i = 0; i < 8; i++)
                        {
                            if (plateau[i] == accueil.getPartieIa().getPionMemoire())
                            {
                                plateau[i] = -1;
                                break;
                            }
                        }
                        // On enregistre le pion qui vient d'être bougé
                        accueil.getPartieIa().setDernierPionJoue(accueil.getPartieIa().getPionMemoire());
                        accueil.getPartieIa().setTourUn(false);
                        accueil.getPartieIa().setTourDuJoueurBlanc(false);
                    }
                    vue.getVue_plateau().repaint();
                }
            }
            // Si ce n'est pas le premier tour
            else
            {
                // On regarde la couleur de la case où se trouve le dernier pion joué
                char couleurPionAJouer = 'e';
                for(i=0; i<64; i++)
                    if(plateau[i]==accueil.getPartieIa().getDernierPionJoue())
                        couleurPionAJouer = accueil.getPartieIa().getPlateauCase()[i];

                // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                byte casePionMemoire = -1;
                if(couleurPionAJouer=='m')
                {
                    for (i = 0; i < 64; i++)
                        if (plateau[i] == Model_Partie_IA.getPIONBLANCMARRON())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='g')
                {
                    for (i = 0; i < 64; i++)
                        if (plateau[i] == Model_Partie_IA.getPIONBLANCVERT())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='r')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i]== Model_Partie_IA.getPIONBLANCROUGE())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='y')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i]== Model_Partie_IA.getPIONBLANCJAUNE())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='p')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i]== Model_Partie_IA.getPIONBLANCROSE())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='v')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i] == Model_Partie_IA.getPIONBLANCVIOLET())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='b')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i]== Model_Partie_IA.getPIONBLANCBLEU())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }
                else if(couleurPionAJouer=='o')
                {
                    for(i=0; i<64; i++)
                        if(plateau[i]== Model_Partie_IA.getPIONBLANCORANGE())
                        {
                            accueil.getPartieIa().setPionMemoire(plateau[i]);
                            casePionMemoire = i;
                        }
                }

                // On calcul les cases atteignables du pion mémoire
                accueil.getPartieIa().setCasesAtteignablesJoueurCourant(true, casePionMemoire);

                if(plateau[index] == -1)
                {
                    // On vérifie que la case cliquée est dans les cases atteignables du pion en mémoire
                    boolean isCaseAtteignable = false;
                    for (i = 0; i < 14; i++)
                    {
                        if (accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[i] == index)
                        {
                            isCaseAtteignable = true;
                            break;
                        }
                    }

                    if (isCaseAtteignable)
                    {

                        // On indique que le pion est maintenant sur la case cliqué
                        plateau[8 * row + column] = accueil.getPartieIa().getPionMemoire();
                        // On supprime le pion de son ancien emplacement
                        plateau[casePionMemoire] = -1;
                        // On enregistre le pion qui vient d'être bougé
                        accueil.getPartieIa().setDernierPionJoue(accueil.getPartieIa().getPionMemoire());
                        accueil.getPartieIa().setTourDuJoueurBlanc(false);
                    }
                    vue.getVue_plateau().repaint();
                    // On vérifie si il y a victoire ou pas
                    if(index>55 && index<=63) {
                        vue.jOptionMessage(texteInternational.getString("joueurBlancGagnant") + " "
                                + texteInternational.getString("message"),
                                texteInternational.getString("titreFenetre"));
                        return;
                    }
                }


                // Tour du joueur noir (IA)
                if(!accueil.getPartieIa().isTourDuJoueurBlanc())
                {
                    // On regarde la couleur de la case où se trouve le dernier pion joué
                    couleurPionAJouer = 'e';
                    for(i=0; i<64; i++)
                        if(plateau[i]==accueil.getPartieIa().getDernierPionJoue())
                            couleurPionAJouer = accueil.getPartieIa().getPlateauCase()[i];
                    // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                    casePionMemoire = -1;
                    if (couleurPionAJouer == 'm') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRMARRON()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'g') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRVERT()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'r') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRROUGE()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'y') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRJAUNE()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'p') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRROSE()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'v') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRVIOLET()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'b') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRBLEU()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    } else if (couleurPionAJouer == 'o') {
                        for (i = 0; i < 64; i++)
                            if (plateau[i] == Model_Partie_IA.getPIONNOIRORANGE()) {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                casePionMemoire = i;
                            }
                    }

                    // On calcul les cases atteignables du pion mémoire
                    accueil.getPartieIa().setCasesAtteignablesJoueurCourant(false, casePionMemoire);

                    // On fait le déplacement aléatoire de l'IA
                    evaluate(casePionMemoire);
                }
            }
            // ML fin
        }
    }

    void evaluate(byte casePionMemoire)
    {
        // On choisit aléatoirement la case ou va se déplacer le pion (pseudo IA)
        Random rand = new Random();
        int nbCasesPossibles = 0;
        for (int i = 0; i < 14; i++) {
            if (accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[i] == -1)
                break;
            else
                nbCasesPossibles++;
        }
        int caseAlea = rand.nextInt(nbCasesPossibles);
        // On indique que le pion est maintenant sur la case cliqué
        accueil.getPartieIa().getPlateau()[accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[caseAlea]] = accueil.getPartieIa().getPionMemoire();
        // On supprime le pion de son ancien emplacement
        accueil.getPartieIa().getPlateau()[casePionMemoire] = -1;
        // On enregistre le pion qui vient d'être bougé
        accueil.getPartieIa().setDernierPionJoue(accueil.getPartieIa().getPionMemoire());
        accueil.getPartieIa().setTourDuJoueurBlanc(true);

        vue.getVue_plateau().repaint();

        // On vérifie si il y a victoire ou pas
        if (accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[caseAlea] < 8
                && accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[caseAlea] >= 0)
            vue.jOptionMessage(texteInternational.getString("IAgagnant") + " "
                    + texteInternational.getString("message"),
                    texteInternational.getString("titreFenetre"));
    }
}
