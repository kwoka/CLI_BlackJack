import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private int minHandValue;
    private int maxHandValue;


    public Hand() {
        this.cards = new ArrayList<>();
        this.minHandValue = -1;
        this.maxHandValue = -1;
    }

    public int getMinHandValue() {
        return minHandValue;
    }

    public int getMaxHandValue() {
        return maxHandValue;
    }

    public boolean isBusted() {
        return minHandValue > 21 && maxHandValue > 21;
    }

    public void showHand() {
        for (Card c : cards){
            System.out.println("    " + c.getValue() + " of " + c.getSuit());
        }
    }

    public int getHandSize(){
        return this.cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
        calculateHandValues();
    }

    public Card getCard(int i) {
        if(i < cards.size()) {
            return cards.get(i);
        } else {
            return null;
        }
    }

    public Hand split(){
        if(this.isSplittable()) {
            Hand splitHand = new Hand();
            splitHand.addCard(this.cards.remove(1));
            return splitHand;
        } else {
            return null;
        }
    }

    public boolean isSplittable(){
        return (this.getHandSize() == 2) && this.cards.get(0).hasSameValue(this.cards.get(1));
    }

    /**
     * Calculates and updates minHandValue and maxHandValue.
     */
    private void calculateHandValues(){
        // Invalid hand size
        if(cards.size() < 2) {
            minHandValue = -1;
            maxHandValue = -1;
        } else {
            int minSum = 0;
            int maxSum = 0;
            for (Card card : cards) {
                minSum = minSum + parseCardIntValue(card);
                // Aces are worth either 1 or 11
                if(card.getValue().equals("Ace")){
                    maxSum = maxSum + parseCardIntValue(card);  // ISSUE WITH DOUBLE STARTING HAND Ace and Drawing into and another Ace and wanting it to be 1.
                    if ((maxSum + 10) <= 21) {
                        maxSum = maxSum + 10;
                    }
                    // Check if we can count it as 11.
                } else {
                    maxSum = maxSum + parseCardIntValue(card);
                }
            }
            minHandValue = minSum;
            maxHandValue = maxSum;
        }
    }

    /**
     * Helper Method to find numerical value of a card from its String value.
     * @param card to extract value
     * @return Int value of card
     */
    private int parseCardIntValue(Card card){
        String value = card.getValue();
        int intValue = 0;
        switch (value){
            case "Ace" :
                intValue = 1;
                break;
            case "Two":
                intValue = 2;
                break;
            case "Three":
                intValue = 3;
                break;
            case "Four":
                intValue = 4;
                break;
            case "Five":
                intValue = 5;
                break;
            case "Six":
                intValue = 6;
                break;
            case "Seven":
                intValue = 7;
                break;
            case "Eight":
                intValue = 8;
                break;
            case "Nine":
                intValue = 9;
                break;
            case "Ten":
            case "Jack":
            case "Queen":
            case "King":
                intValue = 10;
                break;
        }
        return intValue;
    }
}
