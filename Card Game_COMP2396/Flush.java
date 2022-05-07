/**
 * a subclass of the Hand class and is used to model a hand of flush
 *
 * @author He Zixuan
 */
public class Flush extends Hand{
    /**
     * a constructor for building a Flush hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public Flush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid flush hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if (this.size() == 5){
            for (int i = 1; i < this.size();i++){
                if (this.getCard(i).getSuit() != this.getCard(0).getSuit()){
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
        return "Flush";
    }
}
