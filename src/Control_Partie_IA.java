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
                if (plateau[index] != -1 && index < 8) // parenthese autour de l'index inutile
                    accueil.getPartieIa().setPionMemoire(plateau[index]);
                // Si il n'y a pas de pion sur la case cliquée et qu'il y a un pionMemoire
                else if (plateau[index] == -1 && accueil.getPartieIa().getPionMemoire() != -1)
                {
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
                        // todo ca c'est une méthode de modele elle s'occupe de deplacerLaPiece
                        // On indique que le pion est maintenant sur la case cliqué
                        plateau[index] = accueil.getPartieIa().getPionMemoire();
                        // On supprime le pion de son ancien emplacement
                        // todo pas efficace il faudrait juste mettre en mémoire son emplacement dans un attribut du
                        // todo model pour ne pas avoir à faire cette boucle qui peut rajouter jusqu'à 63 instructions
                        // todo inutiles
                        for (i=0; i<8; i++)
                            if (plateau[i] == accueil.getPartieIa().getPionMemoire())
                            {
                                plateau[i] = -1;
                                break;
                            }

                        // On enregistre le pion qui vient d'être bougé
                        accueil.getPartieIa().setDernierPionJoue(accueil.getPartieIa().getPionMemoire());
                        accueil.getPartieIa().setTourUn(false);
                        accueil.getPartieIa().setTourDuJoueurBlanc(false);

                        vue.getVue_plateau().repaint();

                        // On prépare le tour d'après
                        // On regarde la couleur de la case où se trouve le dernier pion joué
                        for(i=0; i<64; i++) // todo pour tous les "64" et autres for de la page je pense que ca serait plus clair de
                            // todo faire des plateau.lenght juste pour qu'on sache ce que tu parcours sans trop réfléchir
                            if(plateau[i]==accueil.getPartieIa().getDernierPionJoue())
                                accueil.getPartieIa().setCouleurPionAJouer(accueil.getPartieIa().getPlateauCase()[i]);

                        // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                        for (i=0; i< 64; i++)
                            if (plateau[i]!=-1 && plateau[i]%8 == accueil.getPartieIa().getCouleurPionAJouer()%8
                                    && plateau[i]>7)
                            {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                accueil.getPartieIa().setCasePionMemoire(i);
                            }

                        // On calcul les cases atteignables du pion mémoire
                        accueil.getPartieIa().setCasesAtteignablesJoueurCourant(false, accueil.getPartieIa().getCasePionMemoire());
                        // On fait jouer l'IA
                        accueil.getPartieIa().evaluate(accueil.getPartieIa().getCasePionMemoire());

                        // On prépare le tour suivant
                        // On regarde la couleur de la case où se trouve le dernier pion joué
                        for(i=0; i<64; i++) // todo meme remarque qu'au dessus
                            if(accueil.getPartieIa().getPlateau()[i]==accueil.getPartieIa().getDernierPionJoue())
                                accueil.getPartieIa().setCouleurPionAJouer(accueil.getPartieIa().getPlateauCase()[i]);
                        // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                        for (i=0; i<64; i++) // todo meme remarque qu'au dessus ter
                            if (accueil.getPartieIa().getPlateau()[i]!=-1
                                    && accueil.getPartieIa().getPlateau()[i] == accueil.getPartieIa().getCouleurPionAJouer())
                            {
                                accueil.getPartieIa().setPionMemoire(accueil.getPartieIa().getPlateau()[i]);
                                accueil.getPartieIa().setCasePionMemoire(i);
                            }
                        //On calcul les cases atteignables du pion pour le tour suivant
                        accueil.getPartieIa().setCasesAtteignablesJoueurCourant(true, accueil.getPartieIa().getCasePionMemoire());
                    }
                }
            }
            // Si ce n'est pas le premier tour
            else
            {
                if(plateau[index] == -1)
                {
                    // On vérifie que la case cliquée est dans les cases atteignables du pion en mémoire
                    boolean isCaseAtteignable = false;
                    for (i=0; i<14; i++)
                        if (accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[i] == index)
                        {
                            isCaseAtteignable = true;
                            break;
                        }

                    if (isCaseAtteignable)
                    {
                        // On indique que le pion est maintenant sur la case cliqué
                        plateau[index] = accueil.getPartieIa().getPionMemoire();
                        // On supprime le pion de son ancien emplacement
                        plateau[accueil.getPartieIa().getCasePionMemoire()] = -1;
                        // On enregistre le pion qui vient d'être bougé
                        accueil.getPartieIa().setDernierPionJoue(accueil.getPartieIa().getPionMemoire());
                        accueil.getPartieIa().setTourDuJoueurBlanc(false);

                        vue.repaint();
                        // On vérifie si il y a victoire ou pas
                        if (index > 55 && index <= 63)
                        {
                            vue.jOptionMessage(texteInternational.getString("joueurBlancGagnant") + " "
                                            + texteInternational.getString("message"),
                                    texteInternational.getString("titreFenetre"));
                            return;
                        }

                        // On regarde la couleur de la case où se trouve le dernier pion joué
                        for (i=0; i<64; i++) // todo idem blabla au dessus
                            if (plateau[i] == accueil.getPartieIa().getDernierPionJoue())
                                accueil.getPartieIa().setCouleurPionAJouer(accueil.getPartieIa().getPlateauCase()[i]);

                        // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                        for (i=0; i<64; i++) // todo idem blabla au dessus
                            if (plateau[i] != -1 && plateau[i] % 8 == accueil.getPartieIa().getCouleurPionAJouer() % 8
                                    && plateau[i] > 7)
                            {
                                accueil.getPartieIa().setPionMemoire(plateau[i]);
                                accueil.getPartieIa().setCasePionMemoire(i);
                            }

                        // On calcul les cases atteignables du pion mémoire
                        accueil.getPartieIa().setCasesAtteignablesJoueurCourant(false, accueil.getPartieIa().getCasePionMemoire());
                    }
                }

                // Tour du joueur noir (IA)
                if(!accueil.getPartieIa().isTourDuJoueurBlanc())
                {
                    // On fait le déplacement aléatoire de l'IA
                    byte caseAlea = accueil.getPartieIa().evaluate(accueil.getPartieIa().getCasePionMemoire());
                    vue.repaint();

                    // On vérifie si il y a victoire ou pas
                    if (accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[caseAlea] < 8
                            && accueil.getPartieIa().getCasesAtteignablesJoueurCourant()[caseAlea] >= 0)
                        vue.jOptionMessage( texteInternational.getString("IAgagnant") +
                                        " "+ texteInternational.getString("message"),
                                texteInternational.getString("titreFenetre"));

                    // On prépare le tour suivant
                    // On regarde la couleur de la case où se trouve le dernier pion joué
                    for(i=0; i<64; i++) // todo idem blabla
                        if(accueil.getPartieIa().getPlateau()[i]==accueil.getPartieIa().getDernierPionJoue())
                            accueil.getPartieIa().setCouleurPionAJouer(accueil.getPartieIa().getPlateauCase()[i]);
                    // On retrouve le pion qui doit jouer et on le met dans le pion mémoire
                    for (i=0; i<64; i++) // todo idem blabla
                        if (accueil.getPartieIa().getPlateau()[i]!=-1
                                && accueil.getPartieIa().getPlateau()[i] == accueil.getPartieIa().getCouleurPionAJouer())
                        {
                            accueil.getPartieIa().setPionMemoire(accueil.getPartieIa().getPlateau()[i]);
                            accueil.getPartieIa().setCasePionMemoire(i);
                        }
                    //On calcul les cases atteignables du pion pour le tour suivant
                    accueil.getPartieIa().setCasesAtteignablesJoueurCourant(true, accueil.getPartieIa().getCasePionMemoire());
                }
            }
        }
    }
    // ML fin
}