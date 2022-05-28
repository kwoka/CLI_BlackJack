import java.util.ArrayList;

public final class Dealer {
    private int showingCardValue = -1;
    private String name;
    private Hand hand;



    public Dealer(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }
    public int getShowingCardValue() {
        return showingCardValue;
    }


    public void addCardToHand(Card card) {
        hand.addCard(card);
        if(hand.getHandSize()== 1) {
            showingCardValue = Card.parseCardIntValue(card);
        }
    }

    public void getNewHand(){
        this.hand = new Hand();
    }

    public void showStartingHand(){
        if(hand.getHandSize() < 1) {
            System.out.println("Not Enough cards to display a hand");
        } else if (hand.getHandSize() <= 2) {
            System.out.println("    " + hand.getCard(0).getValue() + " of " + hand.getCard(0).getSuit());
            System.out.println("    ?????? of ??????") ;
        }
    }
    public void showFullHand() {
        if(hand.getHandSize() < 2) {
            System.out.println("Not Enough cards to display a hand");
        } else {
            System.out.println(getName() + " : ");
            hand.showHand();
        }

    }

}
