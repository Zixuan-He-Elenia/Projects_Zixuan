/**
 * a subclass of the Hand class and is used to model a hand of full house
 *
 * @author He Zixuan
 */
public class FullHouse extends Hand{

    /**
     * a constructor for building a FullHouse hand with the specified player and list of cards.
     *
     * @param player player who played those cards
     * @param cards cards played
     */
    public FullHouse(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * a method for checking if this is a valid full house hand.
     *
     * @return whether it is valid
     */
    @Override
    public boolean isValid() {
        if(this.size() == 5){
            int firstRank = this.getCard(0).getRank();
            int count1 = 1;
            int secondIndex = 1;
            for (int i = 1; i < size(); i++){
                if (firstRank == this.getCard(i).getRank()){
                    count1++;
                }
                else{
                    secondIndex = i;
                }
            }

            int count2 = 0;
            for (int i = 0; i < size(); i++){
                if (this.getCard(secondIndex).getRank() == this.getCard(i).getRank()){
                    count2++;
                }
            }

            if(count1 == 2 && count2 == 3){
                return true;
            }
            else return count1 == 3 && count2 == 2;
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
        return "FullHouse";
    }
}
