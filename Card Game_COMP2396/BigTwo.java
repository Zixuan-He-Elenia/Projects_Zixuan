import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card game.
 * It has private instance variables for storing the number of players,
 * a deck of cards, a list of players, a list of hands played on the table,
 * an index of the current player, and a user interface.
 *
 * @author He Zixuan
 */
public class BigTwo implements CardGame {
    private int numOfPlayers;//an int specifying the number of players.
    private Deck deck;// a deck of cards.
    private ArrayList<CardGamePlayer> playerList;// a list of players.
    private ArrayList<Hand> handsOnTable;// a list of hands played on the table.
    private int currentPlayIdx;// an integer specifying the index of the current player.
    private BigTwoGUI ui;// a BigTwoGUI object for providing the user interface.
    private BigTwoClient client;//a BigTwoClient object used in this game.

    /**
     * a constructor for creating a Big Two card game.
     * (i) create 4 players and add them to the player list;
     * and (ii) create a BigTwoUI object for providing the user interface.
     */
    public BigTwo(){
        numOfPlayers = 4;
        this.playerList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            CardGamePlayer player = new CardGamePlayer();
            playerList.add(player);
        }

        handsOnTable = new ArrayList<>();
        ui = new BigTwoGUI(this);

    }

    /**
     * a method for getting the number of players.
     *
     * @return number of players
     */
    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * a method for retrieving the deck of cards being used.
     *
     * @return the deck in this game
     */
    @Override
    public Deck getDeck() {
        return deck;
    }

    /**
     * a method for retrieving the list of players.
     *
     * @return the list of players
     */
    @Override
    public ArrayList<CardGamePlayer> getPlayerList() {
        return playerList;
    }

    /**
     * a method for retrieving the list of hands played on the table.
     *
     * @return hands on Table
     */
    @Override
    public ArrayList<Hand> getHandsOnTable() {
        return handsOnTable;
    }

    /**
     * a method for retrieving the index of the current player.
     * @return the index of the current player
     */
    @Override
    public int getCurrentPlayerIdx() {
        return currentPlayIdx;
    }

    /**
     * a method for starting/restarting the game with a given shuffled deck of cards.
     * (i) remove all the cards from the players as well as from the table;
     * (ii) distribute the cards to the players;
     * (iii) identify the player who holds the Three of Diamonds;
     * (iv) set both the currentPlayerIdx of the BigTwo object
     * and the activePlayer of the BigTwoUI object to the index of the player who holds the Three of Diamonds;
     * (v) call the repaint() method of the BigTwoUI object to show the cards on the table;
     * and (vi) call the promptActivePlayer() method of the BigTwoUI object to
     * prompt user to select cards and make his/her move.
     *
     * @param deck the deck of (shuffled) cards to be used in this game
     */
    public void start(Deck deck) {
        this.deck = deck;
        handsOnTable.clear();
        for (int i = 0; i < 4; i++){
            playerList.get(i).removeAllCards();
        }
        for (int i = 0; i < 4;i++){
            for (int j = 0; j < 13; j++) {
                playerList.get(i).addCard(this.deck.getCard(i*13+j));
                //identify the player who holds the Three of Diamonds
                //set both the currentPlayerIdx of the BigTwo object
                //set the activePlayer of the BigTwoUI object
                if (deck.getCard(i*13+j).getSuit() == 0 && deck.getCard(i*13+j).getRank() == 2){
                    currentPlayIdx = i;
                    ui.setActivePlayer(i);
                }
            }
            playerList.get(i).getCardsInHand().sort();
        }
        ui.promptActivePlayer();
        ui.repaint();

    }

    /**
     * a method for making a move by a player with the specified index
     * using the cards specified by the list of indices.
     * This method should be called from the BigTwoUI
     * after the active player has selected cards to make his/her move.
     *
     * @param playerIdx the index of the player who makes the move
     * @param cardIdx   the list of the indices of the cards selected by the player
     */
    @Override
    public void makeMove(int playerIdx, int[] cardIdx) {
        client.sendMessage(new CardGameMessage(6, playerIdx, cardIdx));
    }

    /**
     *a method for checking a move made by a player.
     *
     * @param playerIdx the index of the player who makes the move
     * @param cardIdx   the list of the indices of the cards selected by the player
     */
    @Override
    public void checkMove(int playerIdx, int[] cardIdx) {
        Card D3 = new BigTwoCard(0,2);
        //the first player can't pass
        if (endOfGame()){
            ui.disablePlayAndPass();
            ui.repaint();
            ui.printMsg("Game ends"+"\n");
            //things that will be printed by message dialog
            String toBePrinted = "";
            for (int i = 0; i < 4;i++) {
                //print format: ZIXUAN (Player 1) wins the game.
                if (playerList.get(i).getNumOfCards() == 0){
                    toBePrinted += (playerList.get(i).getName()+" (Player "+i+")"+" wins the game."+"\n");
                }
                //print format: ZIXUAN (Player 1) has 10 cards in hand.
                else {
                    toBePrinted += (playerList.get(i).getName()+" (Player "+i+")"+" has " + playerList.get(i).getNumOfCards() + " cards in hand."+"\n");
                }
            }
            //print message 
            JOptionPane.showMessageDialog(null,toBePrinted,"GAME END",JOptionPane.QUESTION_MESSAGE);
            //send READY to server
            client.sendMessage(new CardGameMessage(4, client.getPlayerID(), client.getPlayerName()));
            return;
        }
        if (handsOnTable.isEmpty() && cardIdx == null){
            ui.printMsg("Not a legal move!!!"+"\n");
            ui.promptActivePlayer();
            return;
        }
        CardList chosen = playerList.get(playerIdx).play(cardIdx);
        if (handsOnTable.isEmpty()) {
            //don't have diamond 3
            if (!chosen.contains(D3)) {
                ui.printMsg("Not a legal move!!!"+"\n");
                ui.promptActivePlayer();
                return;
            }
            //have 3 and valid
            Hand ThisPlayer = composeHand(playerList.get(playerIdx), chosen);
            if (ThisPlayer != null) {
                ui.printMsg("{"+ThisPlayer.getType()+"} ");
                ui.printMsg(chosen.toString()+"\n");
                handsOnTable.add(ThisPlayer);
                playerList.get(playerIdx).removeCards(chosen);
                if (currentPlayIdx == 3) {
                    currentPlayIdx = -1;
                }
                currentPlayIdx++;
                ui.setActivePlayer(currentPlayIdx);
                ui.promptActivePlayer();
                return;
            }
            //non-valid
            ui.printMsg("Not a legal move!!!"+"\n");
            ui.promptActivePlayer();
            return;
        }
        //the first player for each turn
        if(handsOnTable.get(handsOnTable.size()-1).getPlayer() == playerList.get(playerIdx)){
            // first player for each turn can't pass
            if(cardIdx == null) {
                ui.printMsg("Not a legal move!!!"+"\n");
                ui.promptActivePlayer();
                return;
            }
            //valid
            Hand ThisPlayer = composeHand(playerList.get(playerIdx), chosen);
            if (ThisPlayer != null) {
                ui.printMsg("{"+ThisPlayer.getType()+"} ");
                ui.printMsg(chosen.toString()+"\n");
                handsOnTable.add(ThisPlayer);
                playerList.get(playerIdx).removeCards(chosen);
                if (currentPlayIdx == 3) {
                    currentPlayIdx = -1;
                }
                currentPlayIdx++;
                ui.setActivePlayer(currentPlayIdx);
                ui.promptActivePlayer();
                return;
            }
            //non-valid
            ui.printMsg("Not a legal move!!!"+"\n");
            ui.promptActivePlayer();
            return;
        }
        //other case of player(need consider beats)
        //other player pass
        if(cardIdx == null) {
            ui.printMsg("{Pass}"+"\n");
            if (currentPlayIdx == 3) {
                currentPlayIdx = -1;
            }
            currentPlayIdx++;
            ui.setActivePlayer(currentPlayIdx);
            ui.promptActivePlayer();
            return;
        }
        //valid
        Hand ThisPlayer = composeHand(playerList.get(playerIdx), chosen);
        if (ThisPlayer != null) {
            //can beat the last hand
            if(ThisPlayer.beats(handsOnTable.get(handsOnTable.size() - 1))){
                ui.printMsg("{"+ThisPlayer.getType()+"} ");
                ui.printMsg(chosen.toString()+"\n");
                handsOnTable.add(ThisPlayer);
                playerList.get(playerIdx).removeCards(chosen);
                if (currentPlayIdx == 3) {
                    currentPlayIdx = -1;
                }
                currentPlayIdx++;
                ui.setActivePlayer(currentPlayIdx);
                ui.promptActivePlayer();
                return;
            }
        }
        //non-valid
        ui.printMsg("Not a legal move!!!"+"\n");
        ui.promptActivePlayer();
        return;

    }

    /**
     * a method for checking if the game ends.
     *
     * @return whether the game is end
     */
    @Override
    public boolean endOfGame() {
        for (int i = 0; i < 4; i++){
            if (playerList.get(i).getNumOfCards() == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * a method for returning a valid hand from the specified list of cards of the player.
     * Returns null if no valid hand can be composed from the specified list of cards.
     *
     * @param player a specific player
     * @param cards specific list of cards from that player
     * @return valid hand can be formed.
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards){
        //Single
        Hand ThisPlayer = new Single(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //Pair
        ThisPlayer = new Pair(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //Triple
        ThisPlayer = new Triple(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //StraightFlush
        ThisPlayer = new StraightFlush(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //Quad
        ThisPlayer = new Quad(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //FullHouse
        ThisPlayer = new FullHouse(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //Flush
        ThisPlayer = new Flush(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        //Straight
        ThisPlayer = new Straight(player,cards);
        if (ThisPlayer.isValid()){
            return ThisPlayer;
        }
        return null;
    }

    /**
     * Return the GUI used in this game
     *
     * @return ui
     */
    public BigTwoGUI getGUI(){
        return ui;
    }

    /**
     * Set the client for this game
     *
     * @param client client for this game
     */
    public void setClient(BigTwoClient client){
        this.client = client;
    }

    /**
     * Return the client for this game
     *
     * @return client
     */
    public BigTwoClient getClient(){
        return this.client;
    }

}
