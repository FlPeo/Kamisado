import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by belfort on 01/10/2016.
 */
public class Model_Partie_Test {

    @Test
    public void testControleBlocageSiPresenceCasesAtteignablesTour1(){    //ne passe pas le premier if
        Model_Case[] cases = new Model_Case[1];
        cases[0] = new Model_Case(0,0, Couleur.VIOLET);

        Model_Pion[] pionsBlancs = new Model_Pion[1];
        Model_Pion[] pionsNoirs = new Model_Pion[1];

        pionsBlancs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsBlancs[0]).casesAtteignables();

        ArrayList<Model_Case> array = new ArrayList<Model_Case>();
        array.add(cases[0]);
        Mockito.when(pionsBlancs[0].getCasesAtteignables()).thenReturn(array);


        Model_Accueil accueil = new Model_Accueil();
        Model_Partie partie = Model_Partie.factPartie(accueil, cases, pionsBlancs, pionsNoirs, pionsBlancs[0], true);

        partie.setTourDuJoueurBlanc(true);
        assertTrue(partie.isTourDuJoueurBlanc());
        partie.controleBlocage();
        assertTrue(partie.isTourDuJoueurBlanc());
        assertFalse(partie.estGagnee());

    }

    @Test
    public void testControleBlocageSiPresenceCasesAtteignablesPasTour1(){    //ne passe pas le premier if
        Model_Case[] cases = new Model_Case[1];
        cases[0] = new Model_Case(0,0, Couleur.VIOLET);

        Model_Pion[] pionsBlancs = new Model_Pion[1];
        Model_Pion[] pionsNoirs = new Model_Pion[1];

        pionsBlancs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsBlancs[0]).casesAtteignables();

        ArrayList<Model_Case> array = new ArrayList<Model_Case>();
        array.add(cases[0]);
        Mockito.when(pionsBlancs[0].getCasesAtteignables()).thenReturn(array);


        Model_Accueil accueil = new Model_Accueil();
        Model_Partie partie = Model_Partie.factPartie(accueil, cases, pionsBlancs, pionsNoirs, pionsBlancs[0], false);

        partie.setTourDuJoueurBlanc(true);
        assertTrue(partie.isTourDuJoueurBlanc());
        partie.controleBlocage();
        assertTrue(partie.isTourDuJoueurBlanc());
        assertFalse(partie.estGagnee());

    }

    @Test
    public void testControleBlocageSiBlocageMettantFinAPartie(){
        Model_Case[] cases = new Model_Case[2];
        cases[0] = new Model_Case(0,0, Couleur.VIOLET);
        cases[1] = new Model_Case(0,1, Couleur.BLEU);

        Model_Pion[] pionsBlancs = new Model_Pion[1];
        Model_Pion[] pionsNoirs = new Model_Pion[1];

        pionsBlancs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsBlancs[0]).casesAtteignables();
        pionsNoirs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsNoirs[0]).casesAtteignables();

        ArrayList<Model_Case> array = new ArrayList<Model_Case>();
        Mockito.when(pionsBlancs[0].getCasesAtteignables()).thenReturn(array);
        Mockito.when(pionsBlancs[0].getCaseActuelle()).thenReturn(cases[1]);
        Mockito.when(pionsBlancs[0].getCOULEUR()).thenReturn(Couleur.VIOLET);


        Mockito.when(pionsNoirs[0].getCasesAtteignables()).thenReturn(array);
        Mockito.when(pionsNoirs[0].getCaseActuelle()).thenReturn(cases[0]);
        Mockito.when(pionsNoirs[0].getCOULEUR()).thenReturn(Couleur.BLEU);


        Model_Accueil accueil = new Model_Accueil();
        Model_Partie partie = Model_Partie.factPartie(accueil, cases, pionsBlancs, pionsNoirs, pionsBlancs[0], false);


        assertTrue(partie.isTourDuJoueurBlanc());
        assertFalse(partie.estGagnee());

        partie.controleBlocage();
        assertTrue(partie.isTourDuJoueurBlanc());

        assertTrue(partie.estGagnee());
        assertTrue(partie.isJoueurBlancGagnant());
    }

    @Test
    public void testControleBlocageAvecSeulementUnBlocage(){
        Model_Case[] cases = new Model_Case[2];
        cases[0] = new Model_Case(0, 0, Couleur.VIOLET);
        cases[1] = new Model_Case(0, 1, Couleur.BLEU);

        Model_Pion[] pionsBlancs = new Model_Pion[1];
        Model_Pion[] pionsNoirs = new Model_Pion[1];


        pionsBlancs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsBlancs[0]).casesAtteignables();
        pionsNoirs[0] = Mockito.mock(Model_Pion.class);
        Mockito.doNothing().when(pionsNoirs[0]).casesAtteignables();

        ArrayList<Model_Case> array = new ArrayList<Model_Case>();
        Mockito.when(pionsBlancs[0].getCasesAtteignables()).thenReturn(array);
        Mockito.when(pionsBlancs[0].getCaseActuelle()).thenReturn(cases[1]);
        Mockito.when(pionsBlancs[0].getCOULEUR()).thenReturn(Couleur.VIOLET);

        ArrayList<Model_Case> array2 = new ArrayList<Model_Case>();
        array2.add(cases[1]);
        Mockito.when(pionsNoirs[0].getCasesAtteignables()).thenReturn(array2);
        Mockito.when(pionsNoirs[0].getCOULEUR()).thenReturn(Couleur.BLEU);
        Mockito.when(pionsNoirs[0].getCaseActuelle()).thenReturn(cases[0]);


        Model_Accueil accueil = new Model_Accueil();
        Model_Partie partie = Model_Partie.factPartie(accueil, cases, pionsBlancs, pionsNoirs, pionsBlancs[0], false);


        assertTrue(partie.isTourDuJoueurBlanc());
        assertFalse(partie.estGagnee());

        partie.controleBlocage();
        assertTrue(!partie.isTourDuJoueurBlanc());
        assertFalse(partie.estGagnee());
    }
}
