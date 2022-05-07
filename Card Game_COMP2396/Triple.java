/**
 * a subclass of the Hand class and is used to model a hand of triple
 *
 * @author He Zixuan
 */
public class Triple extends Hand{
    /**
     * a constructor for building a Triple hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public Triple(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid triple hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if (this.size() == 3){
            if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(0).getRank() == this.getCard(2).getRank()){
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
        return "Triple";
    }
}
