/**
 * a subclass of the Hand class and is used to model a hand of pair
 *
 * @author He Zixuan
 */
public class Pair extends Hand{

    /**
     * a constructor for building a Pair hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public Pair(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid pair hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if (this.size() == 2){
            if (this.getCard(0).getRank() == this.getCard(1).getRank()){
                return true;
            }
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
        return "Pair";
    }
}
