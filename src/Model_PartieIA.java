import java.util.ArrayList;
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
public class Model_PartieIA
{
    private boolean isTourUn;
    private boolean tourDuJoueurBlanc;
    private boolean joueurBlancGagnant;
    private boolean estGagnee;

    private String[] plateau;
    private byte pionMemoire;
    private byte dernierPionJoue;

    private byte[] pions;
    private ArrayList<Byte> casesAtteignablesPionsBlancs;
    private ArrayList<Byte> casesAtteignablesPionsNoirs;
    private ArrayList<ArrayList<Byte>> casesAtteignablesTourUn;

}
