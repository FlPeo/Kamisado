/**
 Created by cladlink on 06/11/16.
 */

class BDD_Tools
{
    static void saveHistory(int j1, int j2, String history)
    {
        String requeteHistorique = "INSERT INTO HISTORIQUEPARTIE VALUES (null, "
                + j1 + ", "
                + j2 + ", '"
                + history + "', now());";
        BDDManager bdd = new BDDManager();
        bdd.start();
        bdd.edit(requeteHistorique);
        bdd.stop();
    }
}
