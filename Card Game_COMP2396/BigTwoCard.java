/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a Big Two card game.
 *
 * @author He Zixuan
 */
public class BigTwoCard extends Card{
    /**
     * a constructor for building a card with the specified suit and rank.
     * suit is an integer between 0 and 3, and rank is an integer between 0 and 12.
     *
     * @param suit suit of this card
     * @param rank rank of this card
     */
    public BigTwoCard(int suit, int rank){
        super(suit,rank);
    }

    /**
     * a method for comparing the order of this card with the specified card.
     *
     * @param card the card to be compared
     * @return a negative integer, zero, or a positive integer
     * when this card is less than, equal to, or greater than the specified card.
     */
    @Override
    public int compareTo(Card card){
        int cardRank = card.getRank();
        int thisRank = this.getRank();
        if (thisRank == 0 || thisRank == 1){
            thisRank += 13;
        }
        if(cardRank == 0 || cardRank == 1){
            cardRank += 13;
        }
        if (thisRank > cardRank) {
            return 1;
        } else if (thisRank < cardRank) {
            return -1;
        } else if (this.suit > card.suit) {
            return 1;
        } else if (this.suit < card.suit) {
            return -1;
        } else {
            return 0;
        }
    }
}
