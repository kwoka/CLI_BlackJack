import java.util.ArrayList;
import java.util.Collections;

public class Shoe {
    private final static int NUMBER_OF_DECKS_USED = 1;
    private ArrayList<Card> cards;

    public Shoe() {
        this(NUMBER_OF_DECKS_USED);
    }

    public Shoe(int size) {
        cards = new ArrayList<>();
        for (int i = 0; i < size; i++){
            Deck deck = new Deck();
            cards.addAll(deck.getDeck());
        }
    }

    public Shoe(ArrayList<Card> card) {
        cards = card;
    }

    public void shuffle() {
        int numOfShuffles = (int)((Math.random() * 10) + 1);
        for(int i = 0; i < numOfShuffles; i++) {
            Collections.shuffle(cards);
        }
    }

    public void display() {
        for(Card card : cards) {
            System.out.println(card);
        }
    }

    /**
     *
     * @return Top card from the Deck.
     */
    public Card dealCard(){
        if(this.getRemainingCards() > 0) {
            return cards.remove(0);
        }
        return null;
    }

    public int getRemainingCards() {
        return cards.size();
    }

    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    private static class Deck {
        private static final String[] suits = new String[] {"Clubs","Spades", "Hearts", "Diamonds"};
        private ArrayList<Card> deck;

        // Create deck
        public Deck() {
            deck = new ArrayList<>();
            // Ace = 1 ... King = 13
            for (int i = 1; i <= 13; i++) {
                for (String suit : suits) {
                    String cardValue = parseCardValue(i);
                    deck.add(new Card(suit, cardValue));
                }
            }
        }
        public ArrayList<Card> getDeck() {
            return new ArrayList<>(deck);
        }

        private static String parseCardValue(int cardValue) {
            String value = "";
            switch (cardValue){
                case 1:
                    value = "Ace";
                    break;
                case 2:
                    value = "Two";
                    break;
                case 3:
                    value = "Three";
                    break;
                case 4:
                    value = "Four";
                    break;
                case 5:
                    value = "Five";
                    break;
                case 6:
                    value = "Six";
                    break;
                case 7:
                    value = "Seven";
                    break;
                case 8:
                    value = "Eight";
                    break;
                case 9:
                    value = "Nine";
                    break;
                case 10:
                    value = "Ten";
                    break;
                case 11:
                    value = "Jack";
                    break;
                case 12:
                    value = "Queen";
                    break;
                case 13:
                    value = "King";
                    break;
                default:
                    value = "Joker";
            }
            return value;
        }

    }
}
