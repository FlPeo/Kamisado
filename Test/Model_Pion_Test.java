import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

/**
 Created by cladlink on 24/09/16.
 */
public class Model_Pion_Test
{
    @Ignore
    @Test
    public void testSetCaseActuelle()
    {
        Model_Case caseAuPif = Mockito.mock(Model_Case.class);
        Model_Case caseAuPifDeux = Mockito.mock(Model_Case.class);
        Model_Pion pion = new Model_Pion(caseAuPif, true, Couleur.BLEU, new ArrayList<>());
        pion.setCaseActuelle(caseAuPifDeux);
        Assert.assertEquals(pion.getCaseActuelle(), caseAuPifDeux);
    }
}
