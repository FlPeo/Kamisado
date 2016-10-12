import java.util.Arrays;
import java.util.Random;

/**
 * On définira dans cette classe et dans un soucis d'économie de place :
 *      - les pions blancs et noirs seront définis de la facon suivante :
 *
 *          ----------------------
 *          00 = pion blanc marron
 *          01 = pion blanc vert
 *          02 = pion blanc rouge
 *          03 = pion blanc jaune
 *          04 = pion blanc rose
 *          05 = pion blanc violet
 *          06 = pion blanc bleu
 *          07 = pion blanc orange
 *          ----------------------
 *          08 = pion noir marron
 *          09 = pion noir vert
 *          10 = pion noir rouge
 *          11 = pion noir jaune
 *          12 = pion noir rose
 *          13 = pion noir violet
 *          14 = pion noir bleu
 *          15 = pion noir orange
 *          ----------------------
 *
 *      - les couleurs :
 *          0 = marron
 *          1 = vert
 *          2 = rouge
 *          3 = jaune
 *          4 = rose
 *          5 = violet
 *          6 = bleu
 *          7 = orange
 */
public class Model_Partie_IA {
    static final byte MARRON = 0;
    static final byte GREEN = 1;
    static final byte RED = 2;
    static final byte YELLOW = 3;
    static final byte PINK = 4;
    static final byte VIOLET = 5;
    static final byte BLUE = 6;
    static final byte ORANGE = 7;

    static final byte LIGNE = 8;
    static final byte NBCASESATTEIGNABLESPOSSIBLESPREMIERTOUR = 14;
    static final byte NBCASESATTEIGNABLESPOSSIBLESJOUEURCOURANT = 15;
    static final byte PIONBLANCMARRON = 0;
    static final byte PIONBLANCVERT   = 1;
    static final byte PIONBLANCROUGE  = 2;
    static final byte PIONBLANCJAUNE  = 3;
    static final byte PIONBLANCROSE   = 4;
    static final byte PIONBLANCVIOLET = 5;
    static final byte PIONBLANCBLEU   = 6;
    static final byte PIONBLANCORANGE = 7;
    static final byte PIONNOIRMARRON  = 8;
    static final byte PIONNOIRVERT    = 9;
    static final byte PIONNOIRROUGE   = 10;
    static final byte PIONNOIRJAUNE   = 11;
    static final byte PIONNOIRROSE    = 12;
    static final byte PIONNOIRVIOLET  = 13;
    static final byte PIONNOIRBLEU    = 14;
    static final byte PIONNOIRORANGE  = 15;

    private boolean isTourUn = true;
    private boolean tourDuJoueurBlanc = true;
    private boolean joueurBlancGagnant = false;
    private boolean estGagnee = false;

    private byte[] plateau;
    private byte[] plateauCase;

    //pion selectionné (choix au tour 1 / pion qui doit être joué pour les autres tours)
    private byte pionMemoire = -1;
    private byte casePionMemoire = -1;
    private byte couleurPionAJouer = -1;

    //pion qui a été joué au dernier tour
    private byte dernierPionJoue = -1;

    // 14 = nombre de cases atteignables max
    // on notera -1 pour les cases non utilisées
    private byte[] casesAtteignablesJoueurCourant = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    // 8 = nb pions
    private byte[][] casesAtteignablesTourUn = new byte[8][14];

    Model_Partie_IA()
    {
        plateau = new byte[LIGNE * LIGNE];
        Arrays.fill(plateau , (byte) -1);
        placePionsBlancs();
        placePionsNoirs();
        initPlateauCase();

    }

    private void initPlateauCase()
    {
        plateauCase = new byte[64];
        plateauCase[0] = MARRON;
        plateauCase[1] = GREEN;
        plateauCase[2] = RED;
        plateauCase[3] = YELLOW;
        plateauCase[4] = PINK;
        plateauCase[5] = VIOLET;
        plateauCase[6] = BLUE;
        plateauCase[7] = ORANGE;

        plateauCase[8] = VIOLET;
        plateauCase[9] = MARRON;
        plateauCase[10] = YELLOW;
        plateauCase[11] = BLUE;
        plateauCase[12] = GREEN;
        plateauCase[13] = PINK;
        plateauCase[14] = ORANGE;
        plateauCase[15] = RED;

        plateauCase[16] = BLUE;
        plateauCase[17] = YELLOW;
        plateauCase[18] = MARRON;
        plateauCase[19] = VIOLET;
        plateauCase[20] = RED;
        plateauCase[21] = ORANGE;
        plateauCase[22] = PINK;
        plateauCase[23] = GREEN;

        plateauCase[24] = YELLOW;
        plateauCase[25] = RED;
        plateauCase[26] = GREEN;
        plateauCase[27] = MARRON;
        plateauCase[28] = ORANGE;
        plateauCase[29] = BLUE;
        plateauCase[30] = VIOLET;
        plateauCase[31] = PINK;

        plateauCase[32] = PINK;
        plateauCase[33] = VIOLET;
        plateauCase[34] = BLUE;
        plateauCase[35] = ORANGE;
        plateauCase[36] = MARRON;
        plateauCase[37] = GREEN;
        plateauCase[38] = RED;
        plateauCase[39] = YELLOW;

        plateauCase[40] = GREEN;
        plateauCase[41] = PINK;
        plateauCase[42] = ORANGE;
        plateauCase[43] = RED;
        plateauCase[44] = VIOLET;
        plateauCase[45] = MARRON;
        plateauCase[46] = YELLOW;
        plateauCase[47] = BLUE;

        plateauCase[48] = RED;
        plateauCase[49] = ORANGE;
        plateauCase[50] = PINK;
        plateauCase[51] = GREEN;
        plateauCase[52] = BLUE;
        plateauCase[53] = YELLOW;
        plateauCase[54] = MARRON;
        plateauCase[55] = VIOLET;

        plateauCase[56] = ORANGE;
        plateauCase[57] = BLUE;
        plateauCase[58] = VIOLET;
        plateauCase[59] = PINK;
        plateauCase[60] = YELLOW;
        plateauCase[61] = RED;
        plateauCase[62] = GREEN;
        plateauCase[63] = MARRON;
    }

    private void placePionsBlancs()
    {
        plateau[7] = PIONBLANCORANGE;
        plateau[6] = PIONBLANCBLEU;
        plateau[5] = PIONBLANCVIOLET;
        plateau[4] = PIONBLANCROSE;
        plateau[3] = PIONBLANCJAUNE;
        plateau[2] = PIONBLANCROUGE;
        plateau[1] = PIONBLANCVERT;
        plateau[0] = PIONBLANCMARRON;
    }

    private void placePionsNoirs()
    {
        plateau[63] = PIONNOIRMARRON;
        plateau[62] = PIONNOIRVERT;
        plateau[61] = PIONNOIRROUGE;
        plateau[60] = PIONNOIRJAUNE;
        plateau[59] = PIONNOIRROSE;
        plateau[58] = PIONNOIRVIOLET;
        plateau[57] = PIONNOIRBLEU;
        plateau[56] = PIONNOIRORANGE;
    }
    public void setCasesAtteignablesPremierTour() {
        for (int i = 0; i < casesAtteignablesTourUn.length; i++){
            for(int j=0 ; j<casesAtteignablesTourUn[i].length; j++)
                casesAtteignablesTourUn[i][j] = -1;
        }


        for (int i = 0; i < casesAtteignablesTourUn.length; i++)
        {
            int row = i / 8, column = i % 8;
            int decal = 1, curseur = 0;
            boolean deplacableNS = true, deplacableO = true, deplacableE = true;

            while (deplacableNS || deplacableO || deplacableE)
            {
                if (deplacableNS)
                {
                    if (row + decal <= 7
                            && plateau[column + (row + decal) * LIGNE] < 0) {
                        casesAtteignablesTourUn[i][curseur] = (byte)(column + (row + decal) * LIGNE);
                        curseur++;
                    } else
                        deplacableNS = false;
                }
                if (deplacableO) {
                    if (row + decal <= 7 && column - decal >= 0
                            && plateau[(column - decal) + (row + decal) * LIGNE] < 0) {
                        casesAtteignablesTourUn[i][curseur] = (byte)((column - decal) + (row + decal) * LIGNE);
                        curseur++;
                    } else
                        deplacableO = false;
                }
                if (deplacableE) {
                    if (row + decal <= 7 && column + decal <= 7
                            && plateau[(column + decal) + (row + decal) * LIGNE] < 0) {
                        casesAtteignablesTourUn[i][curseur] = (byte)((column + decal) + (row + decal) * LIGNE);
                        curseur++;

                    } else
                        deplacableE = false;
                }
                decal++;
            }
        }
    }

    public void setCasesAtteignablesJoueurCourant(boolean isTourBlanc, byte casePion)
    {
        //On réinitialise le tableau de case atteignable
        for(int i=0; i<casesAtteignablesJoueurCourant.length; i++)
            casesAtteignablesJoueurCourant[i]=-1;
        int row = casePion / 8, column = casePion % 8;
        int decal, curseur = 0;
        if(isTourBlanc)
            decal=1;
        else
            decal=-1;
        boolean deplacableNS = true, deplacableO = true, deplacableE = true;

        while (deplacableNS || deplacableO || deplacableE)
        {
            if (deplacableNS)
            {
                if (row + decal <= 7
                        && row + decal >= 0
                        && plateau[column + (row + decal) * LIGNE] < 0)
                {
                    casesAtteignablesJoueurCourant[curseur] = (byte)(column + (row + decal) * LIGNE);
                    curseur++;
                }
                else
                    deplacableNS = false;
            }
            if (deplacableO)
            {
                if (row + decal <= 7 && column - decal >= 0
                        && row + decal >= 0 && column - decal <= 7
                        && plateau[(column - decal) + (row + decal) * LIGNE] < 0)
                {
                    casesAtteignablesJoueurCourant[curseur] = (byte)((column - decal) + (row + decal) * LIGNE);
                    curseur++;
                }
                else
                    deplacableO = false;
            }
            if (deplacableE)
            {
                if (row + decal <= 7 && column + decal <= 7
                        && row + decal >=0 && column + decal >=0
                        && plateau[(column + decal) + (row + decal) * LIGNE] < 0)
                {
                    casesAtteignablesJoueurCourant[curseur] = (byte)((column + decal) + (row + decal) * LIGNE);
                    curseur++;

                }
                else
                    deplacableE = false;
            }
            if(isTourBlanc)
                decal++;
            else
                decal--;
        }
    }

    byte evaluate()
    {
        // On choisit aléatoirement la case ou va se déplacer le pion (pseudo IA)
        Random rand = new Random();
        int nbCasesPossibles = 0;
        for (int i = 0; i < NBCASESATTEIGNABLESPOSSIBLESJOUEURCOURANT; i++)
            if (casesAtteignablesJoueurCourant[i] == -1)
                break;
            else
                nbCasesPossibles++;
        int caseAlea = rand.nextInt(nbCasesPossibles);
        // On récupère la couleur de la case où va etre déplacer le pion pour le tour d'après
        couleurPionAJouer = plateauCase[casesAtteignablesJoueurCourant[caseAlea]];
        // On indique que le pion est maintenant sur la case cliqué
        plateau[casesAtteignablesJoueurCourant[caseAlea]] = pionMemoire;
        // On supprime le pion de son ancien emplacement
        plateau[casePionMemoire] = -1;
        // On enregistre le pion qui vient d'être bougé
        dernierPionJoue = pionMemoire;
        tourDuJoueurBlanc = true;
        return (byte)caseAlea;
    }

    void deplacerPiece(byte caseArrivee)
    {
        // On indique que le pion est maintenant sur la case cliqué
        plateau[caseArrivee] = pionMemoire;
        // On supprime le pion de son ancien emplacement
        plateau[casePionMemoire] = -1;
        // On enregistre le pion qui vient d'être bougé
        dernierPionJoue = pionMemoire;
    }

    boolean controlBlocage()
    {
        return casesAtteignablesJoueurCourant[0] == -1;
    }

    byte[] getPlateau() { return plateau; }
    byte[] getPlateauCase() {
        return plateauCase;
    }
    boolean isTourUn() { return isTourUn; }
    void setTourUn(boolean tourUn) {
        isTourUn = tourUn;
    }
    boolean isTourDuJoueurBlanc() {
        return tourDuJoueurBlanc;
    }
    void setTourDuJoueurBlanc(boolean tourDuJoueurBlanc) {
        this.tourDuJoueurBlanc = tourDuJoueurBlanc;
    }
    byte getPionMemoire() {
        return pionMemoire;
    }
    void setPionMemoire(byte pionMemoire) {
        this.pionMemoire = pionMemoire;
    }
    byte getDernierPionJoue() {
        return dernierPionJoue;
    }
    byte[] getCasesAtteignablesJoueurCourant() {
        return casesAtteignablesJoueurCourant;
    }
    byte[][] getCasesAtteignablesTourUn() {
        return casesAtteignablesTourUn;
    }
    void setCasePionMemoire(byte casePionMemoire) {
        this.casePionMemoire = casePionMemoire;
    }
    byte getCasePionMemoire() {
        return casePionMemoire;
    }
    byte getCouleurPionAJouer() {
        return couleurPionAJouer;
    }
    void setCouleurPionAJouer(byte couleurPionAJouer) {
        this.couleurPionAJouer = couleurPionAJouer;
    }

    public void setCasesAtteignablesJoueurCourant(byte[] casesAtteignablesJoueurCourant) {
        this.casesAtteignablesJoueurCourant = casesAtteignablesJoueurCourant;
    }
}
