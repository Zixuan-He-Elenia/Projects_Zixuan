/**
 * a subclass of the Hand class and is used to model a hand of quad
 *
 * @author He Zixuan
 */
public class Quad extends Hand{

    /**
     * a constructor for building a Quad hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public Quad(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid quad hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if (this.size() == 5){
            int count1 = 0;
            int count2 = 0;
            for (int i = 0; i < this.size();i++){
                if(this.getCard(0).getRank() == this.getCard(i).getRank()){
                    count1++;
                }
                if(this.getCard(1).getRank() == this.getCard(i).getRank()){
                    count2++;
                }
            }
            if (count1 == 4 || count2 == 4){
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
        return "Quad";
    }
}
