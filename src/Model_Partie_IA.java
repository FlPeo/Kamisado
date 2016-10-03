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
public class Model_Partie_IA
{
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

    private String[] plateau;

    //pion selectionné (choix au tour 1 / pion qui doit être joué pour les autres tours)
    private byte pionMemoire = -1;

    //pion qui a été joué au dernier tour
    private byte dernierPionJoue = -1;

    // 14 = nombre de cases atteignables max
    // on notera -1 pour les cases non utilisées
    private byte[] casesAtteignablesPionsBlancs = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    // 8 = nb pions
    private byte[][] casesAtteignablesTourUn = new byte[8][14];
    
}
