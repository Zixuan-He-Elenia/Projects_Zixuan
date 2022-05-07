/**
 * a subclass of the Hand class and is used to model a hand of single
 *
 * @author He Zixuan
 */
public class Single extends Hand{

    /**
     * a constructor for building a Single hand with the specified player and list of cards.
     * @param player player who played these cards
     * @param cards cards played
     */
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid single hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if (this.size() == 1){
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
        return "Single";
    }
}
