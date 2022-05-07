import java.util.ArrayList;

/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards.
 * It has a private instance variable for storing the player who plays this hand.
 * It also has methods for getting the player of this hand,
 * checking if it is a valid hand, getting the type of this hand,
 * getting the top card of this hand, and checking if it beats a specified hand.
 *
 * @author He Zixuan
 */
public abstract class Hand extends CardList {
    private CardGamePlayer player;//the player who plays this hand.

    /**
     * a constructor for building a hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public Hand(CardGamePlayer player, CardList cards){
        this.player = player;
        for (int i = 0; i < cards.size();i++){
            this.addCard(cards.getCard(i));
        }
    }

    /**
     * method for retrieving the player of this hand.
     *
     * @return the player
     */
    public CardGamePlayer getPlayer(){
        return player;
    }

    /**
     * a method for retrieving the top card of this hand.
     *
     * @return the top card
     */
    public Card getTopCard(){
        if (this.getType().equals("Single")){
            return this.getCard(0);
        }

        else if(this.getType().equals("Pair")){
            if (this.getCard(0).getSuit() > this.getCard(1).getSuit()){
                return this.getCard(0);
            }
            return this.getCard(1);
        }

        else if(this.getType().equals("Triple")){
            if (this.getCard(0).getSuit()>= this.getCard(1).getSuit()){
                if(this.getCard(0).getSuit()>= this.getCard(2).getSuit()){
                    return this.getCard(0);
                }
            }
            else{
                if(this.getCard(1).getSuit()>= this.getCard(2).getSuit()){
                    return this.getCard(1);
                }

            }
            return this.getCard(2);
        }

        else if(this.getType().equals("Straight") || this.getType().equals("StraightFlush") || this.getType().equals("Flush")){
            this.sort();
            return this.getCard(this.size()-1);
        }

        else if(this.getType().equals("FullHouse")) {
            ArrayList<Card> lst1 = new ArrayList<>();//first list storing cards having same rank with the first card(2 or 3)
            ArrayList<Card> lst2 = new ArrayList<>();//second list storing cards with same rank(2 or 3)
            int index = 1;//index of card that has different rank with the first card
            //append cards into lst1
            for (int i = 0; i < this.size(); i++) {
                if (this.getCard(i).getRank() == this.getCard(0).getRank()) {
                    lst1.add(this.getCard(i));
                } else {
                    index = i;
                }
            }
            //append cards into lst2
            for (int i = 0; i < this.size(); i++) {
                if (this.getCard(i).getRank() == this.getCard(index).getRank()) {
                    lst2.add(this.getCard(i));
                }

            }
            //find out which one is triplet and the top card
            //lst1 is triplet
            if (lst1.size() == 3) {
                lst1.sort(null);
                return lst1.get(lst1.size() - 1);
            }
            //lst2 is triplet
            else {
                lst2.sort(null);
                return lst2.get(lst2.size() - 1);
            }
        }

        else if (this.getType().equals("Quad")){
            ArrayList<Card> lst1 = new ArrayList<>();//first list storing cards having same rank with the first card
            ArrayList<Card> lst2 = new ArrayList<>();//second list storing cards having same rank with second card
            //appending cards to suitable lists
            for (int i = 0; i < this.size();i++){
                if(this.getCard(0).getRank() == this.getCard(i).getRank()){
                    lst1.add(this.getCard(i));
                }
                if(this.getCard(1).getRank() == this.getCard(i).getRank()){
                    lst2.add(this.getCard(i));
                }
            }

            //find out which list is the quadruplet
            //lst1 is quadruplet
            if (lst1.size() == 4){
                lst1.sort(null);
                return lst1.get(lst1.size() - 1);
            }
            //lst2 is quadruplet
            else{
                lst2.sort(null);
                return lst2.get(lst2.size() - 1);
            }
        }
        return null;
    }

    /**
     * a method for checking if this hand beats a specified hand.
     * @param hand a given hand
     * @return whether this beats the given hand
     */
    public boolean beats(Hand hand){
        //this's rank and hand's rank, with A turned into 13, 2 turned into 14
        int thisRank = this.getTopCard().getRank();
        int handRank = hand.getTopCard().getRank();
        //set special thisRank
        if (thisRank == 0 || thisRank == 1){
            thisRank = this.getTopCard().getRank()+13;
        }
        //set special handRank
        if (handRank == 0 || handRank == 1){
            handRank = hand.getTopCard().getRank()+13;
        }


        if (this.getType().equals("StraightFlush")) {
            if (hand.getType().equals("Straight") || hand.getType().equals("Flush") || hand.getType().equals("FullHouse") || hand.getType().equals("Quad")) {
                return true;
            }
            else if (hand.getType().equals("Single") || hand.getType().equals("Pair") || hand.getType().equals("Triple")) {
                return false;
            }
            else{
                //higher rank beats
                if(thisRank>handRank){
                    return true;
                }

                //same rank, higher suit beats
                else return thisRank == handRank && this.getTopCard().getSuit() > hand.getTopCard().getSuit();
            }
        }

        else if(this.getType().equals("Quad")){
            if (hand.getType().equals("Straight") || hand.getType().equals("Flush") || hand.getType().equals("FullHouse")) {
                return true;
            }
            else if (hand.getType().equals("Single") || hand.getType().equals("Pair") || hand.getType().equals("Triple") || hand.getType().equals("StraightFlush")) {
                return false;
            }
            else{
                //higher rank beats
                return thisRank > handRank;
            }
        }

        else if(this.getType().equals("FullHouse")){
            if (hand.getType().equals("Straight") || hand.getType().equals("Flush")) {
                return true;
            }
            else if (hand.getType().equals("Single") || hand.getType().equals("Pair") || hand.getType().equals("Triple") || hand.getType().equals("StraightFlush") || hand.getType().equals("Quad")) {
                return false;
            }
            else{
                //higher  rank beats
                return thisRank > handRank;
            }
        }

        else if(this.getType().equals("Flush")){
            if (hand.getType().equals("Straight")) {
                return true;
            }
            else if (hand.getType().equals("Single") || hand.getType().equals("Pair") || hand.getType().equals("Triple") || hand.getType().equals("StraightFlush") || hand.getType().equals("Quad") || hand.getType().equals("FullHouse")) {
                return false;
            }
            else{
                //higher suit beats
                if(this.getTopCard().getSuit() > hand.getTopCard().getSuit()){
                    return true;
                }

                //same suit, higher rank beats
                else if(this.getTopCard().getSuit() == hand.getTopCard().getSuit()){
                    //higher rank beats
                    return thisRank > handRank;
                }
            }
        }

        else if (this.getType().equals("Straight")){
            if (hand.getType().equals("Straight")){
                //higher rank beats
                if(thisRank > handRank){
                    return true;
                }

                //same rank, higher suit beats
                else return thisRank == handRank && this.getTopCard().getSuit() > hand.getTopCard().getSuit();
            }
            return false;
        }

        else if (this.getType().equals("Triple")){
            if (hand.getType().equals("Triple")){
                //higher rank beats
                if(thisRank > handRank){
                    return true;
                }
            }
            return false;
        }

        else if (this.getType().equals("Pair")){
            if (hand.getType().equals("Pair")){
                //higher rank beats
                if(thisRank > handRank){
                    return true;
                }

                //same rank, higher suit beats
                else return thisRank == handRank && this.getTopCard().getSuit() > hand.getTopCard().getSuit();
            }
            return false;
        }

        else if (this.getType().equals("Single")){
            if (hand.getType().equals("Single")){
                //higher rank beats
                if(thisRank > handRank){
                    return true;
                }

                //same rank, higher suit beats
                else return thisRank == handRank && this.getTopCard().getSuit() > hand.getTopCard().getSuit();
            }
            return false;
        }

        return false;
    }

    /**
     * a method for checking if this is a valid hand.
     *
     * @return whether it is valid
     */
    public abstract boolean isValid();

    /**
     * a method for returning a string specifying the type of this hand.
     *
     * @return type of this hand
     */
    public abstract String getType();

}
