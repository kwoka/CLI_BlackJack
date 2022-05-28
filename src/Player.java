import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Hand> hands;  // Player can split a Hand.

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void showHands() {
        for (int i = 0; i < hands.size(); i++){
            if (hands.size() != 1) {
                System.out.println("  Hand #" + (1+i));
            }
            Hand hand = hands.get(i);
            hand.showHand();
        }
    }


    public void addCardToHand(Card card, Hand hand) {
        hand.addCard(card);
    }

    public void getNewHand() {
        this.hands = new ArrayList<>();
        this.addHand(new Hand());
    }

    public int getNumHands() {
        return hands.size();
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }

    public void addHand(Hand hand, int index) {
        hands.add(index, hand);
    }

    public Hand getHand(int i){
        if (i < hands.size()) {
            return hands.get(i);
        } else {
            return null;
        }
    }
}