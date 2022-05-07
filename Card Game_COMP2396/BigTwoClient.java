import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The BigTwoClient class implements the NetworkGame interface. 
 * It is used to model a Big Two game client 
 * that is responsible for establishing a connection and communicating with the Big Two game server. 
 * 
 * @author He Zixuan
 */
public class BigTwoClient implements NetworkGame{
    private BigTwo game;//a BigTwo object for the Big Two card game.
    private BigTwoGUI gui;//a BigTwoGUI object for the Big Two card game.
    private Socket sock;//a socket connection to the game server.
    private ObjectOutputStream oos;//an ObjectOutputStream for sending messages to the server.
    private int playerID;//an integer specifying the playerID (i.e., index) of the local player.
    private String playerName;//a string specifying the name of the local player.
    private String serverIP;//a string specifying the IP address of the game server.
    private int serverPort;//an integer specifying the TCP port of the game server.
    private boolean isConnected;//whether this client is connected to the game server.

    /**
     * a constructor for creating a Big Two client.
     *
     * @param game  a reference to a BigTwo object associated with this client
     * @param gui  a reference to a BigTwoGUI object associated the BigTwo object.
     */
    public BigTwoClient(BigTwo game, BigTwoGUI gui){
        this.game = game;
        this.gui = gui;
        serverPort = 2396;
        serverIP = "127.0.0.1";
        JOptionPane pane = new JOptionPane();
        playerName = pane.showInputDialog("Please enter your name:");
        //if user directly closed the window or enter nothing, they will be Anonymous Players
        if (playerName == null || playerName.equals("")){
            playerName = "Anonymous Player";
        }
        connect();



    }

    /**
     * a method for getting the playerID (i.e., index) of the local player.
     *
     * @return palyerID
     */
    @Override
    public int getPlayerID() {
        return playerID;
    }

    /**
     * a method for setting the playerID (i.e., index) of the local player.
     * This method should be called from the parseMessage() method
     * when a message of the type PLAYER_LIST is received from the game server.
     *
     * @param playerID the player ID
     */
    @Override
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * a method for getting the name of the local player.
     *
     * @return playerName
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * a method for setting the name of the local player.
     *
     * @param playerName the name of player
     */
    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * a method for getting the IP address of the game server.
     *
     * @return IP address
     */
    @Override
    public String getServerIP() {
        return serverIP;
    }

    /**
     * a method for setting the IP address of the game server.
     *
     * @param serverIP IP address
     */
    @Override
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * a method for getting the TCP port of the game server.
     *
     * @return TCP port of server
     */
    @Override
    public int getServerPort() {
        return serverPort;
    }

    /**
     * a method for setting the TCP port of the game server.
     *
     * @param serverPort TCP port of server
     */
    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * a method for making a socket connection with the game server.
     * Upon successful connection, this method should
     * (i) create an ObjectOutputStream for sending messages to the game server;
     * (ii) create a new thread for receiving messages from the game server.
     */
    @Override
    public void connect() {
        try {
            sock = new Socket("127.0.0.1", 2396);
            isConnected = true;
            oos = new ObjectOutputStream(sock.getOutputStream());
            Runnable threadJob = new ServerHandler(sock);
            Thread thread = new Thread(threadJob);
            thread.start();

        } catch (Exception ex) {
            System.out.println("Cannot connect to server");
            ex.printStackTrace();
        }
        

    }

    /**
     * a method for sending the specified message to the game server.
     * This method should be called whenever the client wants to communicate with the game server or other clients.
     *
     * @param message the message to be sent to server
     */
    @Override
    public void sendMessage(GameMessage message) {
        try{
            oos.writeObject(message);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * a method for parsing the messages received from the game server.
     * This method should be called from the thread responsible for receiving messages from the game server.
     * Based on the message type, different actions will be carried out
     *
     * @param message the message received from server
     */
    @Override
    public void parseMessage(GameMessage message) {
        //PLAYER_LIST
        if (message.getType() == 0){
            String[] nameArray = (String[]) message.getData();
            setPlayerID(message.getPlayerID());
            for (int i = 0; i < game.getNumOfPlayers();i++){
                if (nameArray[i] != null) {
                    game.getPlayerList().get(i).setName(nameArray[i]);  
                }
            }
            //Send JOIN
            game.getPlayerList().get(playerID).setName(playerName);
            gui.repaint();
            GameMessage messageBack = new CardGameMessage(1,-1,playerName);
            sendMessage(messageBack);
            return;
        }
        //JOIN
        if (message.getType() == 1){
            String name = (String) message.getData();
            game.getPlayerList().get(message.getPlayerID()).setName(name);
            if (message.getPlayerID() == getPlayerID()){
                //Send READY
                GameMessage messageBack = new CardGameMessage(4,-1,null);
                sendMessage(messageBack);
            }
            return;
        }

        //FULL
        if(message.getType() == 2){
            gui.printMsg("The server is full, cannot join the game!\n");
            return;
        }

        //QUIT
        if (message.getType() == 3){
            gui.printMsg("Player"+message.getPlayerID()+" "+game.getPlayerList().get(message.getPlayerID()).getName()+" leaves the game!\n");
            game.getPlayerList().get(message.getPlayerID()).setName("");
            if (!game.endOfGame()){
                game.getPlayerList().get(message.getPlayerID()).removeAllCards();
                CardGameMessage messageBack = new CardGameMessage(4, -1, null);
                sendMessage(messageBack);
                gui.repaint();
                gui.disablePlayAndPass();
            }
        }

        //READY
        if(message.getType() == 4){
            gui.printMsg("Player"+message.getPlayerID()+" "+game.getPlayerList().get(message.getPlayerID()).getName()+" is ready!\n");
            return;
        }

        //START
        if(message.getType() == 5){
            BigTwoDeck cards = (BigTwoDeck) message.getData();
            game.start(cards);
            gui.enablePlayAndPass();
            gui.repaint();
            return;
        }

        //MOVE
        if(message.getType() == 6){
            game.checkMove(message.getPlayerID(),(int[]) message.getData());
            gui.repaint();
            return;
        }

        //MSG
        if(message.getType() == 7){
            gui.Chatting((String) message.getData());
            return;
        }
    }

    /**
     * an inner class that implements the Runnable interface,
     * implement the run() method from the Runnable interface
     * and create a thread with an instance of this class
     * as its job in the connect() method from the NetworkGame interface
     * for receiving messages from the game server.
     * Upon receiving a message,
     * the parseMessage() method from the NetworkGame interface should be called
     * to parse the messages accordingly.
     */
    public class ServerHandler implements Runnable {
        private Socket sock;//the client socket
        private ObjectInputStream reader;//used for reading object from server

        /**
         * initializing the serverHandler
         *
         * @param serverSocket the socket that will be used
         */
        public ServerHandler(Socket serverSocket) {
            this.sock = serverSocket;
            try {
                reader = new ObjectInputStream(serverSocket.getInputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        /**
         * used for reading messages from server and handle them
         */
        public void run() {
            CardGameMessage message;
            try {
                while ((message = (CardGameMessage) (reader.readObject())) != null) {
                    //used for print out which type of message is received
                    System.out.println("message read! type: "+message.getType());
                    parseMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Terminate the client
     */
    public void exit(){
        System.exit(0);
    }

    /**
     * Whether this client is connected to server or not
     *
     * @return isConnected
     */
    public boolean getIsConnected(){
        return isConnected;
    }

    /**
     * The main method used for start the client and the game
     *
     * @param args
     */
    public static void main(String[] args){
        BigTwo Game = new BigTwo();
        Game.getGUI().disablePlayAndPass();
        BigTwoClient client = new BigTwoClient(Game, Game.getGUI());
        Game.setClient(client);
    }

}
