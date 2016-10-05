import java.util.Arrays;

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
 *      - le plateau sera défini comme suit :
 *          String[] de trois caracteres : le premier pour la couleur le deuxieme et le troisieme pour le pion associé
 *          ex: "m11"
 *          ex: "r10"
 *          ex: "d-1" (pas de pions)
 *
 *      - les couleurs :
 *          'm' = marron
 *          'g' = vert
 *          'r' = rouge
 *          'y' = jaune
 *          'p' = rose
 *          'v' = violet
 *          'b' = bleu
 *          'o' = orange
 */
public class Model_Partie_IA {
    static final char MARRON = 'm';
    static final char GREEN = 'g';
    static final char RED = 'r';
    static final char YELLOW = 'y';
    static final char PINK = 'p';
    static final char VIOLET = 'v';
    static final char BLUE = 'b';
    static final char ORANGE = 'o';

    static final byte LIGNE = 8;
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
    private char[] plateauCase;

    //pion selectionné (choix au tour 1 / pion qui doit être joué pour les autres tours)
    private byte pionMemoire = -1;

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
        plateauCase = new char[64];
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

    private void placePionsNoirs()
    {
        plateau[56] = PIONBLANCORANGE;
        plateau[57] = PIONBLANCBLEU;
        plateau[58] = PIONBLANCVIOLET;
        plateau[59] = PIONBLANCROSE;
        plateau[60] = PIONBLANCJAUNE;
        plateau[61] = PIONBLANCROUGE;
        plateau[62] = PIONBLANCVERT;
        plateau[63] = PIONBLANCMARRON;
    }

    private void placePionsBlancs()
    {
        plateau[0] = PIONNOIRMARRON;
        plateau[1] = PIONNOIRVERT;
        plateau[2] = PIONNOIRROUGE;
        plateau[3] = PIONNOIRJAUNE;
        plateau[4] = PIONNOIRROSE;
        plateau[5] = PIONNOIRVIOLET;
        plateau[6] = PIONNOIRBLEU;
        plateau[7] = PIONNOIRORANGE;
    }

    public byte[] getPlateau() {
        return plateau;
    }

    public char[] getPlateauCase() {
        return plateauCase;
    }
}
