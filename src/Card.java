public class Card {
    private String suit;
    private String value;
    private static int count = 0;
    private final int cardID;

    public static void main(String[] args) {
        System.out.println("Test " + (16 < 17));
    }

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        count++;
        cardID = count;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", value='" + value + '\'' +
                ", cardID=" + cardID +
                '}';
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public boolean hasSameValue(Card otherCard) {
        return (parseCardIntValue(this) == parseCardIntValue(otherCard));
    }

    /**
     * Helper Method to find numerical value of a card from its String value.
     * @param card to extract value
     * @return Int value of card
     */
    public static int parseCardIntValue(Card card){
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
