import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This class implements CardGameUI,
 * builds a GUI for the game,
 * and handles all user actions.
 *
 * @author He Zixuan
 */

public class BigTwoGUI implements CardGameUI{
    private BigTwo game;//a Big Two card game associates with this GUI.
    private boolean[] selected;//a boolean array indicating which cards are being selected.
    private int activePlayer;//an integer specifying the index of the active player.
    private JFrame frame;//the main window of the application.
    private JPanel bigTwoPanel;//a panel for showing the cards of each player and the cards played on the table.
    private JButton playButton;//a “Play” button for the active player to play the selected cards.
    private JButton passButton;//a “Pass” button for the active player to pass his/her turn to the next player.
    private JTextArea msgArea;//a text area for showing the current game status as well as end of game messages.
    private JTextArea chatArea;//a text area for showing chat messages sent by the players.
    private JTextField chatInput;//a text field for players to input chat messages.
    private ArrayList<Image> cardPictures;//an ArrayList for storing the images of 52 cards and their back slide

    /**
     * a Big Two card game associates with this GUI.
     *
     * @param game the game that this GUI handles with
     */
    public BigTwoGUI(BigTwo game){
        this.game = game;
        activePlayer = -1;
        //initializing selected
        selected = new boolean[52];
        for (int i = 0; i < 52; i++){
            selected[i] = false;
        }
        //initializing cardPictures
        cardPictures  = new ArrayList<>();
        cardPictures.add(new ImageIcon("cards/ad.gif").getImage());
        cardPictures.add(new ImageIcon("cards/ac.gif").getImage());
        cardPictures.add(new ImageIcon("cards/ah.gif").getImage());
        cardPictures.add(new ImageIcon("cards/as.gif").getImage());
        cardPictures.add(new ImageIcon("cards/2d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/2c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/2h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/2s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/3d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/3c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/3h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/3s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/4d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/4c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/4h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/4s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/5d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/5c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/5h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/5s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/6d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/6c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/6h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/6s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/7d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/7c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/7h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/7s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/8d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/8c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/8h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/8s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/9d.gif").getImage());
        cardPictures.add(new ImageIcon("cards/9c.gif").getImage());
        cardPictures.add(new ImageIcon("cards/9h.gif").getImage());
        cardPictures.add(new ImageIcon("cards/9s.gif").getImage());
        cardPictures.add(new ImageIcon("cards/td.gif").getImage());
        cardPictures.add(new ImageIcon("cards/tc.gif").getImage());
        cardPictures.add(new ImageIcon("cards/th.gif").getImage());
        cardPictures.add(new ImageIcon("cards/ts.gif").getImage());
        cardPictures.add(new ImageIcon("cards/jd.gif").getImage());
        cardPictures.add(new ImageIcon("cards/jc.gif").getImage());
        cardPictures.add(new ImageIcon("cards/jh.gif").getImage());
        cardPictures.add(new ImageIcon("cards/js.gif").getImage());
        cardPictures.add(new ImageIcon("cards/qd.gif").getImage());
        cardPictures.add(new ImageIcon("cards/qc.gif").getImage());
        cardPictures.add(new ImageIcon("cards/qh.gif").getImage());
        cardPictures.add(new ImageIcon("cards/qs.gif").getImage());
        cardPictures.add(new ImageIcon("cards/kd.gif").getImage());
        cardPictures.add(new ImageIcon("cards/kc.gif").getImage());
        cardPictures.add(new ImageIcon("cards/kh.gif").getImage());
        cardPictures.add(new ImageIcon("cards/ks.gif").getImage());
        cardPictures.add(new ImageIcon("cards/b.gif").getImage());
        //build frame
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1080,780));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating menu
        JMenuBar menuBar = new JMenuBar();
        //Game menu
        JMenu gameMenu = new JMenu("Game");

        //adding connect and quit item
        JMenuItem connect = new JMenuItem("Connect");
        connect.addActionListener(new ConnectMenuItemListener());
        gameMenu.add(connect);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitMenuItemListener());
        gameMenu.add(quit);
        menuBar.add(gameMenu);

        //Massage menu
        JMenu MessageMenu = new JMenu("Message");
        menuBar.add(MessageMenu);
        frame.setJMenuBar(menuBar);

        //Panel
        bigTwoPanel = new BigTwoPanel();
        bigTwoPanel.setSize(1080,780);
        //set layout
        bigTwoPanel.setLayout(null);

        //add play button
        playButton = new JButton("Play");
        playButton.setLocation(250, 700);
        playButton.setSize(50,20);
        playButton.addActionListener(new PlayButtonListener());
        bigTwoPanel.add(playButton);

        //add pass button
        passButton = new JButton("Pass");
        passButton.setLocation(315, 700);
        passButton.setSize(50,20);
        passButton.addActionListener(new PassButtonListener());
        bigTwoPanel.add(passButton);

        //add chat input area
        chatInput = new JTextField();
        chatInput.setLocation(680,700);
        chatInput.setSize(400,20);
        chatInput.addActionListener(new chatListener());
        bigTwoPanel.add(chatInput);

        //add chatArea
        chatArea = new JTextArea();
        chatArea.setLocation(620, 350);
        chatArea.setSelectionColor(Color.BLUE);
        chatArea.setSelectedTextColor(Color.MAGENTA);
        chatArea.setEditable(false);
        chatArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chatArea.setForeground(Color.magenta);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        DefaultCaret caretChat = (DefaultCaret)chatArea.getCaret();
        caretChat.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPaneChat = new JScrollPane();
        scrollPaneChat.setLocation(620, 350);
        scrollPaneChat.setSize(new Dimension(458,340));
        scrollPaneChat.add(chatArea);
        scrollPaneChat.setViewportView(chatArea);
        bigTwoPanel.add(scrollPaneChat);

        //add massage Area
        msgArea = new JTextArea(15, 50);
        msgArea.setLocation(620, 3);
        msgArea.setEditable(false);
        msgArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        msgArea.setCaretPosition(msgArea.getDocument().getLength());
        DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(620, 3);
        scrollPane.setSize(new Dimension(458,345));
        scrollPane.add(msgArea);
        scrollPane.setViewportView(msgArea);
        bigTwoPanel.add(scrollPane);
        frame.add(bigTwoPanel);
        frame.pack();
        frame.setVisible(true);
    }

    //an inner class that extends the JPanel class.
    //implements the MouseListener interface.
    //Overrides the paintComponent()
    private class BigTwoPanel extends JPanel implements MouseListener {
        //Constructor
        //used for adding MouseListener to panel
        public BigTwoPanel(){
            addMouseListener(this);
        }

        //the method used for the drawing part in panel(ex: putting the card images on right position...)
        //Including when game is processing and when game is ended two types of GUI drawing
        //Also for handling the change in position when card is selected
        public void paintComponent(Graphics g) {
            if (game.endOfGame()){
                g.setColor(Color.PINK);
                g.fillRect(0,0,618,690);
                g.setColor(Color.BLACK);
                g.drawString("Waiting for everyone to be ready",210,350);
                return;
            }
            Color c = new Color(150,200,150);
            g.setColor(c);
            g.fillRect(0, 0, 618,690);
            g.setColor(Color.BLACK);
            g.drawLine(0, 138, 618, 138);
            g.drawLine(0, 276, 618, 276);
            g.drawLine(0, 414, 618, 414);
            g.drawLine(0, 552, 618, 552);
            g.drawString("Message: ", 618, 715);
            g.setFont(new Font("Courier", Font.BOLD,15));
            for (int i = 0; i < game.getNumOfPlayers();i++){
                if (i == activePlayer){
                    g.setColor(Color.MAGENTA);
                    g.drawString(game.getPlayerList().get(i).getName(), 25, 20+138*activePlayer);
                    g.setColor(Color.BLACK);
                }
                else{
                    g.drawString(game.getPlayerList().get(i).getName(), 20, 20+138*i);
                }
            }
            Image head1 = new ImageIcon("008.png").getImage();
            Image head2 = new ImageIcon("016.png").getImage();
            Image head3 = new ImageIcon("033.png").getImage();
            Image head4 = new ImageIcon("036.png").getImage();
            int cardY = 30;
            for (int i = 0; i < game.getNumOfPlayers();i++){
                int cardX = 150;
                //only display local player's cards
                if (i == game.getClient().getPlayerID()){
                    for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++){
                        Card card = game.getPlayerList().get(i).getCardsInHand().getCard(j);
                        if (!selected[card.getRank() * 4 + card.getSuit()]) {
                            //card image size: 70*100
                            g.drawImage(cardPictures.get(card.getRank() * 4 + card.getSuit()), cardX, cardY, 70, 100, this);
                        }
                        else{
                            g.drawImage(cardPictures.get(card.getRank() * 4 + card.getSuit()), cardX, cardY-10, 70, 100, this);
                        }
                        cardX += 15;
                    }
                }
                else{
                    for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards();j++){
                        g.drawImage(cardPictures.get(52),cardX,cardY,70,100,this);
                        cardX += 15;
                    }
                }
                cardY += 138;
            }
            g.drawImage(head1,10,35,90,90,this);
            g.drawImage(head2,10, 173,90,90,this);
            g.drawImage(head3,10,311,90,90,this);
            g.drawImage(head4,10,449,90,90,this);
            if (!game.getHandsOnTable().isEmpty()){
                Hand lastHand = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
                g.drawString("Played by "+ lastHand.getPlayer().getName(),20,572);
                cardY = 582;
                int cardX = 20;
                for (int i = 0; i < lastHand.size(); i++) {
                    Card ca = lastHand.getCard(i);
                    g.drawImage(cardPictures.get(ca.getRank()*4+ca.getSuit()),cardX,cardY,70,100,this);
                    cardX += 15;
                }
            }
        }

        //Not used
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        //Not used
        @Override
        public void mousePressed(MouseEvent e) {
        }

        //When mouse released, recognize the position matches which card in active player's cards on hand
        //and set the corresponding value in selected to true
        @Override
        public void mouseReleased(MouseEvent e) {
            if (game.endOfGame())
                return;
            int x = e.getX();
            int y = e.getY();
            int cardNum = game.getPlayerList().get(activePlayer).getNumOfCards();
            for (int i = cardNum-1; i >= 0; i--){
                //the specific card
                Card ca = game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i);
                int index = ca.getSuit() + ca.getRank()*4;
                int isSelected = 0;
                if (selected[index]){
                    isSelected = 1;
                }
                int up = 30 + 138 * activePlayer - isSelected * 10;
                int down = 130 + 138 * activePlayer - isSelected * 10;
                int left = 150 + 15 * i;
                int right = 220 + 15 * i;

                if (up <= y && down >= y && left <= x && right >= x){
                    selected[index] = !selected[index];
                    break;
                }
            }
            frame.repaint();
        }

        //Not used
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        //Not used
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    //an inner class that implements the ActionListener interface.
    // Implements the actionPerformed() method from the ActionListener interface
    // to handle menu-item-click events for the “Restart” menu item.
    private class ConnectMenuItemListener implements ActionListener{

        //When the “Connect” menu item is selected,
        //establishing a connection to the game server.
        public void actionPerformed(ActionEvent e) {
            if (!game.getClient().getIsConnected()){
                game.getClient().connect();
            }
            else{
                printMsg("You have already connected to the server!\n");
            }
        }
    }

    //an inner class that implements the ActionListener interface.
    //Implements the actionPerformed() method from the ActionListener interface
    //to handle menu-item-click events for the “Quit” menu item.
    private class QuitMenuItemListener implements  ActionListener{

        //When the “Quit” menu item is selected, terminate the game and client.
        @Override
        public void actionPerformed(ActionEvent e) {
            game.getClient().exit();
            System.exit(0);
        }
    }

    //an inner class that implements the ActionListener interface.
    //Implements the actionPerformed() method from the ActionListener interface
    //to handle button-click events for the “Play” button
    private class PlayButtonListener implements ActionListener {

        //When the “Play” button is clicked,
        //if selected is not null
        //call the makeMove() method from BigTwo object to make a move.
        //else, display "Please select cards to play" on msgArea
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getSelected() != null){
                game.makeMove(activePlayer,getSelected());
                repaint();
                resetSelected();
            }
            else{
                printMsg("Please select cards to play!\n");
            }
        }
    }

    //an inner class that implements the ActionListener interface.
    //Implements the actionPerformed() method from the ActionListener interface
    //to handle button-click events for the “Pass” button
    private class PassButtonListener implements ActionListener {

        //When the “Pass” button is clicked,
        //call the makeMove() method from BigTwo object to make a move,
        //with an empty cardIdx array
        @Override
        public void actionPerformed(ActionEvent e) {
            game.makeMove(activePlayer,null);
            repaint();
        }
    }

    //an inner class that implements the ActionListener interface.
    //Implements the actionPerformed() method from the ActionListener interface
    //to handle text inputting
    private class chatListener implements ActionListener{

        //when user press "return" on their keyboard, the words entered will be displayed on chatArea
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = e.getActionCommand();
            chatInput.setText("");
            //send msg to server
            game.getClient().sendMessage(new CardGameMessage(7,game.getClient().getPlayerID(),message));
        }
    }

    /**
     * a method for setting the index of the active player
     * (i.e., the player having control of the GUI).
     *
     * @param activePlayer an int value representing the index of the active player
     */
    @Override
    public void setActivePlayer(int activePlayer) {
        if (activePlayer < 0 || activePlayer >= game.getPlayerList().size()) {
            this.activePlayer = -1;
        } else {
            this.activePlayer = activePlayer;
        }
    }

    /**
     * a method for repainting the GUI.
     */
    @Override
    public void repaint() {
        //if is loca player's turn: enable
        //else: disable
        if (activePlayer == game.getClient().getPlayerID()){
            enablePlayAndPass();
        }
        else{
            disablePlayAndPass();
        }
        frame.repaint();
    }

    /**
     * a method for printing the specified string to the message
     * area of the GUI.
     *
     * @param msg the string to be printed to the message area of the card game user
     */
    @Override
    public void printMsg(String msg) {
        msgArea.append(msg);
        msgArea.setCaretPosition(msgArea.getDocument().getLength());
    }

    /**
     * a method for clearing the message area of the GUI.
     */
    @Override
    public void clearMsgArea() {
        msgArea.setText("");
    }

    /**
     * a method for resetting the GUI.
     * (i) reset the list of selected cards;
     * (ii) clear the message area; and
     * (iii) enable user interactions.
     */
    @Override
    public void reset() {
        resetSelected();
        clearMsgArea();
        enable();
    }

    /**
     * a method for enabling user interactions with the GUI.
     * (i) enable the “Play” button and “Pass” button (i.e., making them clickable);
     * (ii) enable the chat input;
     * (iii) enable the BigTwoPanel for selection of cards through mouse clicks.
     */
    @Override
    public void enable() {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        chatInput.setEnabled(true);
        bigTwoPanel.setEnabled(true);
    }

    /**
     *  a method for disabling user interactions with the GUI.
     *  (i) disable the “Play” button and “Pass” button (i.e., making them not clickable);
     *  (ii) disable the chat input;
     *  (ii) disable the BigTwoPanel for selection of cards through mouse clicks.
     */
    @Override
    public void disable() {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        chatInput.setEnabled(false);
        bigTwoPanel.setEnabled(false);
    }

    /**
     * a method for prompting the active player to select cards and make his/her move.
     * A message should be displayed in the message area showing it is the active player’s turn.
     */
    @Override
    public void promptActivePlayer() {
        if (game.endOfGame()){
            game.checkMove(activePlayer,getSelected());
        }
        else {
            printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: " + "\n");
            resetSelected();
        }
    }

    /**
     * A method for getting the selected cards' indices in active player's cards in hand
     *
     * @return an int array with card indices
     */
    public int[] getSelected() {
        int count = 0;
        for (int j = 0; j < selected.length; j++) {
            if (selected[j]) {
                count++;
            }
        }
        if (count == 0){
            return null;
        }
        //initialize cardIdx
        int[] cardIdx = new int[count];

        //append index
        if (count != 0) {
            count = 0;
            for (int j = 0; j < selected.length; j++) {
                if (selected[j]) {

                    for (int ii = 0; ii < game.getPlayerList().get(activePlayer).getNumOfCards(); ii++) {
                        if (game.getPlayerList().get(activePlayer).getCardsInHand().getCard(ii).getRank() == j/4 && game.getPlayerList().get(activePlayer).getCardsInHand().getCard(ii).getSuit() == j%4) {
                            cardIdx[count] = ii;
                            count++;
                        }
                    }
                }
            }
        }
        return cardIdx;
    }

    /**
     * A method for resetting all values in selected to false
     */
    public void resetSelected() {
        for (int j = 0; j < selected.length; j++) {
            selected[j] = false;
        }
    }

    /**
     * A method for appending string to chatArea
     *
     * @param str message that needs to be appended to chatArea
     */
    public void Chatting(String str){
        chatArea.append(str+"\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());

    }

    /**
     * A method for disable pass and play button
     */
    public void disablePlayAndPass(){
        playButton.setEnabled(false);
        passButton.setEnabled(false);
    }

    /**
     * A method for enable pass and play button
     */
    public void enablePlayAndPass(){
        playButton.setEnabled(true);
        passButton.setEnabled(true);
    }

}
