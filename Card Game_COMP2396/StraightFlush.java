import java.util.ArrayList;

/**
 * a subclass of the Hand class and is used to model a hand of straight flush
 *
 * @author He Zixuan
 */
public class StraightFlush extends Hand{

    /**
     * a constructor for building a StraightFlush hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public StraightFlush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid straight flush hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if(this.size() == 5){
            //same suit?
            for (int i = 1; i < this.size(); i++){
                if (this.getCard(i).getSuit() != this.getCard(0).getSuit()){
                    return false;
                }
            }

            //continuous ranks?
            this.sort();
            int current;

            ArrayList<Integer> ranks = new ArrayList<>();//list for storing ranks of given hand, with A is 13, 2 is 14

            for (int i = 0; i < this.size();i++){
                current = this.getCard(i).getRank();
                if (current == 0 || current == 1){
                    current += 13;//turning A into 13, 2 into 14
                }
                ranks.add(current);
            }

            ranks.sort(null);

            int start = ranks.get(0);
            for (int i = 1; i < ranks.size();i++){
                if (start + 1 == ranks.get(i)){
                    start++;
                }
                else{
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * a method for returning a string specifying the type of this hand.
     *
     * @return type
     */
    @Override
    public String getType() {
        return "StraightFlush";
    }
}
