import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final int NUM_DECKS = 4;
    private static final int MAX_ALLOWED_PLAYERS = 7;
    private static final int MAX_ALLOWED_HANDS_PER_PLAYER = 4;
    private Player[] players;
    private Dealer dealer;
    private Shoe deck;

    public Game(int numPlayers) {
        this.players = new Player[numPlayers];
        this.dealer = new Dealer("Dealer");
        this.deck = new Shoe(NUM_DECKS);
        this.deck.shuffle();


//        ArrayList<Card> testDeck = new ArrayList<>();

//        Test Case 1 : Dealer and Player have Black Jack
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "King"));
//        testDeck.add(new Card("Diamonds", "King"));


//        Test Case 2: Dealer has black Jack, player does not
//        testDeck.add(new Card("Diamonds", "Ten"));
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "King"));
//        testDeck.add(new Card("Diamonds", "King"))
//
//        Test Case 3: Dealer hand draws into card that puts their max hand over 21 but not their min hand.
//        testDeck.add(new Card("Diamonds", "Seven"));    // Player Card
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "Ace"));      // Player Card
//        testDeck.add(new Card("Diamonds", "Two"));
//        testDeck.add(new Card("Diamonds", "Three"));
//        testDeck.add(new Card("Diamonds", "Ten"));
//        testDeck.add(new Card("Diamonds", "Ace"));
//
//        Test Case 4: Dealer has a soft 17 and should draw a card.
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "Ace"));
//        testDeck.add(new Card("Diamonds", "Six"));
//        testDeck.add(new Card("Diamonds", "Ace"));

//        Test Case 5: Player Splits hands up to 4 hands
//        testDeck.add(new Card("Diamonds", "King"));
//        testDeck.add(new Card("Diamonds", "King"));
//        testDeck.add(new Card("Test", "Ten"));
//        testDeck.add(new Card("Diamonds", "King"));
//        testDeck.add(new Card("Spades", "Ten"));
//        testDeck.add(new Card("Clubs", "Jack"));
//        testDeck.add(new Card("Hearts", "Three"));
//        testDeck.add(new Card("Diamonds", "Two"));
//        testDeck.add(new Card("Diamonds", "Five"));
//        testDeck.add(new Card("Diamonds", "Seven"));
//        this.deck = new Shoe(testDeck);

    }

    public static void main(String [] args) {
        // 1. Figure how many players.
        // 2. dealHands to players and Dealer.
        // 3. Check if Dealer has black jack/Is Dealer showing and Ace or Ten card?
        //      a. if they have blackjack round is over.
        // 4. Check if Users have black Jack.
        // 5. Ask users if they want to hit or split.
        // 6. Dealer reveals hole card.
        // 7. Dealer hits until 17 or Bust.
        // 8. Confirm is Players have won.

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many Players will be playing?");
        int numOfPlayers = scanner.nextInt();
        scanner.nextLine();

        while (numOfPlayers > MAX_ALLOWED_PLAYERS) {
            System.out.println("There are too many players.  The most players allowed is " + MAX_ALLOWED_PLAYERS + ".");
            System.out.println("How many Players will be playing?");
            numOfPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        Game game = new Game(numOfPlayers);
        game.play();
    }

    /**
     * Method to run game.  Method calls all helper functions.
     */
    public void play() {
        boolean playGame = true;
        Scanner scanner = new Scanner(System.in);
        this.createPlayers();

        while(playGame) {
            this.dealHands();
            boolean dealerHasBlackJack = this.checkForInitialBlackJack();
            if (!dealerHasBlackJack) {
                for(Player player : players){
                    this.beginTurn(player);
                }
                if(this.checkIfDealerNeedsToPlay()) {
                    this.dealersTurn();
                }
            }
            if (deck.getRemainingCards() < (NUM_DECKS * 5 * (players.length + 1))) {
                deck = new Shoe(NUM_DECKS);
                deck.shuffle();
                System.out.println("============================================================================");
                System.out.println("============================ STARTING NEW SHOE ============================");
                System.out.println("============================================================================");
            }

            System.out.println("Do you wish to continue? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("n")) {
                playGame = false;
            }
        }
    }

    /**
     *  Creates array of players.
     */
    private void createPlayers(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.players.length; i++) {
            System.out.println("Enter Player #" + (i+1) +"'s Name");
            players[i] = new Player(scanner.nextLine());
        }
    }

    /**
     *  Deal cards to players & dealer in round-robin fashion
     */
    private void dealHands() {
        // Clear out old hands.
        dealer.getNewHand();
        for(int i = 0; i < players.length; i++) {
            players[i].getNewHand();
        }

        for (int j = 0; j < 2; j++) {
            for(int i = 0; i < players.length; i++) {
                Player player = players[i];
                player.addCardToHand(deck.dealCard(), player.getHand(0));
            }
            dealer.addCardToHand(deck.dealCard());
        }
        this.showHands();
    }

    /**
     * Displays hands that have been dealt to all players.
     */
    private void showHands(){
        System.out.println("============================================================================");
        System.out.println("============================== HANDS DEALT ================================");
        System.out.println("============================================================================");
        for(int i = 0; i < players.length; i++) {
            Player player = players[i];
            System.out.println(player.getName() + " : ");
            player.showHands();
            Hand initialPlayerHand = player.getHand(0);
            showHandValue(initialPlayerHand);
        }
        System.out.println(dealer.getName() + " : ");
        dealer.showStartingHand();
    }

    /**
     * Checks if either Dealer or Player has black jack from initial deal.
     * @return if the deal has black jack.
     */
    private boolean checkForInitialBlackJack(){
        // Dealer Check
        boolean dealerHasBlackJack = doesPlayerHaveBlackJack(dealer.getHand());
        if(dealer.getShowingCardValue() == 10 || dealer.getShowingCardValue() == 1) {
            if (dealerHasBlackJack) {
                System.out.println("Dealer has Black Jack");
                dealer.showFullHand();
            } else {
                System.out.println("Dealer has confirmed they do not have Black Jack");
            }
        }
        // Player Check
        for (Player player : players) {
            boolean playerHandBlackJack = doesPlayerHaveBlackJack(player.getHand(0));
            if(playerHandBlackJack && dealerHasBlackJack) {
                System.out.println("PUSH : Dealer and " + player.getName() + " have Black Jack");
            } else if(playerHandBlackJack) {
                System.out.println("Congratulations " + player.getName() + " has Black Jack");
            }

        }
        return dealerHasBlackJack;
    }

    /**
     *  Helper Method to hand individual players turn
     * @param player whose it to interact with the dealer/deck.
     */
    private void beginTurn(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.println("============================================================================");
        System.out.println("================================ " + player.getName() + " TURN ================================");
        System.out.println("============================================================================");
        updatePlayersHandsForSplit(player, MAX_ALLOWED_HANDS_PER_PLAYER);
        for(int i = 0; i < player.getNumHands(); i++) {
            Hand currentHand = player.getHand(i);
            if (doesPlayerHaveBlackJack(currentHand)){
                continue;
            }
            if(player.getNumHands() > 1) {
                int handNum = i +1;
                System.out.println("Hand #" + handNum);
            }
            currentHand.showHand();
            showHandValue(currentHand);
            boolean isOnCurrentHand = true;

            while(isOnCurrentHand) {
                System.out.println(player.getName() + " do you wish to Hit? (Y/N)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    player.addCardToHand(deck.dealCard(), currentHand);
                    currentHand.showHand();
                    showHandValue(currentHand);
                    if (currentHand.isBusted()) {
                        // Hand is busted.
                        System.out.println(player.getName() + " Hand #" + (i+1) + " has busted");
                        isOnCurrentHand = false;
                    }
                } else {
                    isOnCurrentHand = false;
                }
                // Hand is valid and player wishes to 'Stay'
            }
        }
    }

    /**
     * Dealer reveals hand and show results of hands.
     */
    private void dealersTurn(){
        System.out.println("============================================================================");
        System.out.println("=============================== " + dealer.getName() + " TURN ===============================");
        System.out.println("============================================================================");
        dealer.showFullHand();
        Hand dealerHand = dealer.getHand();
        showHandValue(dealerHand);

        while (dealerHand.getMinHandValue() == 7 ||(dealerHand.getMaxHandValue() > 21 && dealerHand.getMinHandValue() < 17) || dealerHand.getMaxHandValue() < 17 ) {
            dealer.addCardToHand(deck.dealCard());
            System.out.println("Dealer hits.");
            dealer.showFullHand();
            showHandValue(dealerHand);
        }

        System.out.println("============================================================================");
        System.out.println("=============================== HAND RESULTS ===============================");
        System.out.println("============================================================================");
        for(Player player : players) {
            System.out.println(player.getName()+" -----------------------------------------------------------------------------------------");
            for (int i = 0; i < player.getNumHands(); i++) {
                Hand playersHand = player.getHand(i);
                boolean playedBusted = playersHand.isBusted();
                if (player.getNumHands() > 1) {
                    System.out.println("Hand #" +  (1+i));
                }
                if (playedBusted) {System.out.println(player.getName()  + " busted.");}
                if (doesPlayerHaveBlackJack(playersHand)) {
                    System.out.println("WIN : " + player.getName() + " had Black Jack");
                }
                if(!doesPlayerHaveBlackJack(playersHand) && !playedBusted ) {
                    int playerHandValue = playersHand.getMaxHandValue() > 21 ? playersHand.getMinHandValue() : playersHand.getMaxHandValue();
                    int dealerHandValue = dealerHand.getMaxHandValue() > 21 ? dealerHand.getMinHandValue() : dealerHand.getMaxHandValue();

                    if(dealerHand.isBusted()) {
                        // Player wins by default
                        System.out.println("Dealer has busted ("+ dealerHandValue + "). " + player.getName() + " won with a hand of " + playerHandValue  );
                    } else {
                        if (playerHandValue > dealerHandValue) {
                            System.out.println("WIN : " + player.getName() + " has beat the " + dealer.getName() + ".\n" +
                                    "   - " + player.getName() + " (" + playerHandValue + ")\n" +
                                    "   - " + dealer.getName() + " (" + dealerHandValue + ")\n");
                        } else if (playerHandValue < dealerHandValue) {
                            System.out.println("LOSS : " + dealer.getName() + " has beat "+ player.getName() + ".\n" +
                                    "   - " + dealer.getName() + " (" + dealerHandValue + ")\n" +
                                    "   - " + player.getName() + " (" + playerHandValue + ")\n");
                        } else {
                            System.out.println("PUSH :" + player.getName() + " tie with " + dealer.getName()+ " with a hand of " + dealerHandValue);
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper Method to show the numeric value of a hand
     * @param hand whose numeric value to show.
     */
    private void showHandValue(Hand hand){
        if (hand.getMinHandValue() != hand.getMaxHandValue() && (hand.getMaxHandValue() <= 21)) {
            System.out.println("Two possible hand values  \n" +
                    " - " + hand.getMinHandValue() + " \n" +
                    " - " + hand.getMaxHandValue());
        } else {
            System.out.println("Hand Value = " + hand.getMinHandValue());
        }
    }

    /**
     *  Checks if there are any valid hands remaining.  Hands that are busted and have already received black jack are invalid.
     * @return If dealer show the rest of the cards.
     */
    private boolean checkIfDealerNeedsToPlay(){
        boolean playerHasValidHand = false;
        for(Player player : players) {
            for(int i = 0; i < player.getNumHands(); i++) {
                Hand hand = player.getHand(i);
                if (!doesPlayerHaveBlackJack(hand) && !hand.isBusted()) {
                    playerHasValidHand = true;
                }
            }
        }
        return playerHasValidHand;
    }

    /**
     *  Adds hands to Player when splits are possible and requested.
     * @param player who is currently interacting with the dealer/deck
     * @param maxHandsToSplit Number of hands a player can have from splitting.
     */
    private void updatePlayersHandsForSplit(Player player, int maxHandsToSplit) {
        for (int i = 0; i < player.getNumHands(); i++) {
            Hand currentHand = player.getHand(i);
            if(currentHand.isSplittable() && player.getNumHands() < maxHandsToSplit){
                System.out.println("Hand #" + (1+i));
                currentHand.showHand();
                System.out.println("Do you wish to split Hand #" + (1+i) +"? (Y/N)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();
                if(response.equalsIgnoreCase("Y")) {
                    Hand splitHand = currentHand.split();
                    player.addCardToHand(deck.dealCard(), currentHand);
                    player.addCardToHand(deck.dealCard(), splitHand);
                    player.addHand(splitHand, i+1);
                    player.showHands();
                    updatePlayersHandsForSplit(player, maxHandsToSplit);
                }
            }
        }
    }

    /**
     * Helper Method to verify hand is black jack
     * @param hand Hand that needs to be checked for black jack.
     * @return if hand is black jack.
     */
    private static boolean doesPlayerHaveBlackJack(Hand hand) {
        return (hand.getHandSize() == 2 && hand.getMaxHandValue() == 21);
    }

}
