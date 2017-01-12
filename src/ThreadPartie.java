import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
  Created by cladlink on 12/04/16.
 */
class ThreadPartie extends Thread
{
    private String monPseudo, pseudoAdversaire;
    private int monSkin, skinAdversaire;
    private int modePartie;
    private boolean jeSuisBlanc;
    private Model_Partie partie;
    private boolean isServer;
    private Socket comm;
    private Control_Partie controller;
    private Control_Menu_Accueil cbm;
    private int port;

    // ajout SD
    private String ipServer;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private int id; // =1 si joueur blanc et 2 si joueur noir

    /**
     * ThreadPartie
     * initie la partie pour le client
     *
     * @param partie (model)
     * @param controller (controller de la partie)
     * @param port (port de communication)
     * @param isServer (suis-je serveur)
     * @param ipServer (ip de communication)
     * @param pseudo (pseudonyme du joueur)
     * @param cbm (controlleur du menu d'accueil)
     */
    ThreadPartie(Model_Partie partie, Control_Partie controller, int port, boolean isServer,
                 String ipServer, String pseudo, Control_Menu_Accueil cbm)
    {
        this.partie = partie;
        this.controller = controller;
        this.port = port;
        this.isServer = isServer;
        this.ipServer = ipServer;
        this.monPseudo = pseudo;
        this.cbm = cbm;
    }

    /**
     * ThreadPartie
     * initie la partie pour le serveur
     *
     * @param partie (model)
     * @param controller (controller de la partie)
     * @param port (port de communication)
     * @param isServer (suis-je serveur)
     * @param ipServer (ip de communication)
     * @param pseudo (pseudonyme du joueur)
     * @param modePartie (mode de la partie)
     * @param cbm (controlleur du menu d'accueil)
     */
    ThreadPartie(Model_Partie partie, Control_Partie controller, int port, boolean isServer,
                 String ipServer, String pseudo, int modePartie, Control_Menu_Accueil cbm)
    {
        this.partie = partie;
        this.controller = controller;
        this.port = port;
        this.isServer = isServer;
        this.ipServer = ipServer;
        this.monPseudo = pseudo;
        this.modePartie = modePartie;
        this.jeSuisBlanc = partie.jeSuisBlanc();
        this.cbm = cbm;
    }

    /**
     * run
     *
     */
    @Override
    public void run()
    {
        boolean stop = false;

        if(isServer)
            try
            {
                initServer();
            }
            catch(ClassNotFoundException | IOException e)
            {
                e.printStackTrace();
            }
        else
            try
            {
                initClient();
            }
            catch (ClassNotFoundException | IOException e)
            {
                e.printStackTrace();
            }

        try
        {
            while (!stop)
            {
                if ( id == partie.getIdCurrentPlayer() )
                {
                    System.out.println("C'est à moi de jouer");
                    controller.debutTour();
                    partie.waitFinTour();
                    int rowSrc = partie.getCaseSrc().getRow();
                    int colSrc = partie.getCaseSrc().getColumn();
                    int rowDest = partie.getCaseDest().getRow();
                    int colDest = partie.getCaseDest().getColumn();
                    oos.writeInt(rowSrc);
                    oos.writeInt(colSrc);
                    oos.writeInt(rowDest);
                    oos.writeInt(colDest);
                    oos.writeDouble(0.0);
                    oos.writeDouble(0.0);
                    oos.writeBoolean(partie.isPartieFinie());
                    oos.flush();
                }
                else
                {
                    System.out.println("C'est à l'adversaire de jouer");
                    controller.enableView(false);
                    int srcX = ois.readInt();
                    int srcY = ois.readInt();
                    int destX = ois.readInt();
                    int destY = ois.readInt();

                    int promo = ois.readInt();
                    long chronoBlanc = ois.readLong();
                    long chronoNoir = ois.readLong();
                    boolean partieFinie = ois.readBoolean();
                    if (partieFinie)
                        stop = true;
                    else
                        controller.updatePartie(srcX, srcY, destX, destY);
                    partie.setTourDuJoueurBlanc(!partie.isTourDuJoueurBlanc());
                    partie.setCaseDest(partie.getPlateau().getBoard()[destX*Model_Plateau.LIGNE+destY]); // A vérifier
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * initServer
     * initie les sockets pour le serveur
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void initServer() throws IOException,ClassNotFoundException
    {
        ServerSocket conn = new ServerSocket(port);
        comm = conn.accept();
        oos = new ObjectOutputStream(comm.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(comm.getInputStream());
        oos.writeObject(monPseudo);
        oos.writeInt(monSkin);
        oos.writeInt(modePartie);
        oos.writeBoolean(!jeSuisBlanc);
        oos.flush();
        pseudoAdversaire = (String)ois.readObject();
        skinAdversaire = ois.readInt();
        if (jeSuisBlanc)
            partie.initPartie(pseudoAdversaire, monPseudo, true);
        else
            partie.initPartie(monPseudo, pseudoAdversaire, true);
        setId();
        cbm.initPartie();
    }

    /**
     * initClient
     * initialise les sockets pour le client
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void initClient() throws IOException,ClassNotFoundException
    {
        comm = new Socket(ipServer, port);
        ois = new ObjectInputStream(comm.getInputStream());
        oos = new ObjectOutputStream(comm.getOutputStream());
        oos.flush();
        pseudoAdversaire = (String)ois.readObject();
        skinAdversaire = ois.readInt();
        modePartie = ois.readInt();
        jeSuisBlanc = ois.readBoolean();
        oos.writeObject(monPseudo);
        oos.writeInt(monSkin);
        oos.flush();
        setId();
        if (jeSuisBlanc)
            partie.initPartie(pseudoAdversaire, monPseudo, true);
        else
            partie.initPartie(monPseudo, pseudoAdversaire, true);
        cbm.initPartie();
    }

    /**
     * setID
     * définit l'ID en fonction de si le joueur est blanc ou noir
     *
     */
    private void setId()
    {
        if (jeSuisBlanc)
            id = 1;
        else
            id = 2;
    }
}