import java.util.*;

public class poker {
    player[] players = null;
    card[] deck = new card[52];
    static final String[] nums = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    static final String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
    static Map<Integer, String> numVal = new HashMap<>();
    static Map<Integer, String> suitVal = new HashMap<>();

    public poker(player[] players) {
        this.players = players;
        this.initNumVal();
        this.initSuitVal();
        this.makeDeck();
        this.shuffleDeck(this.deck);
    } 

    private void initNumVal() {
        for(int i = 0; i < nums.length; i++) {
            numVal.put(i, nums[i]);
        }
    }

    private void initSuitVal() {
        for(int i = 0; i < suits.length; i++) {
            suitVal.put(i, suits[i]);
        }
    }
    //makes deck
    private void makeDeck() {
        int idx1 = 0;
        int idx2 = 0;
        for(int i = 0; i < deck.length; i++) {
            if(idx1 == 4) {
                idx1 = 0;
                idx2++;
            }
            this.deck[i] = new card(nums[idx2], suits[idx1]);
            idx1++;
        }
    }
    //shuffles deck
    private void shuffleDeck(card[] deck) {
        Random rand = new Random();
        for(int i = deck.length - 1; i > 0; i--) {
            int idx = rand.nextInt(i + 1);
            card x = deck[i];
            deck[i] = deck[idx];
            deck[idx] = x;
        }
    }
    //plays a round of poker
    public void play() {
        //gives each player 2 cards, 1 at a time
        int deckIdx = 0;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < players.length; j++) {
                players[j].hand[i] = this.deck[deckIdx];
                deckIdx++;
            }
        }
        for(int i = 0; i < 2; i++) {
            System.out.println(players[0].hand[i].getNum() + players[0].hand[i].getSuit());
        }
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        //deals cards
        card[] shared = new card[5];
        for(int i = 0; i < 3; i++) {
            shared[i] = deck[deckIdx];
            deckIdx++;
            System.out.println(shared[i].getNum() + shared[i].getSuit());
        }
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        shared[3] = deck[deckIdx];
        System.out.println(shared[3].getNum() + shared[3].getSuit());
        deckIdx++;
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        shared[4] = deck[deckIdx];
        System.out.println(shared[4].getNum() + shared[4].getSuit());
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        //finds each players most valuable hand
        for(int i = 0; i < players.length; i++) {
            for(int j = 0; j < shared.length; j++) {
                this.findHighestHand(shared, players[i]);
            }
        }
    }
    //method to check if player wants to fold or continue
    private boolean keepPlaying() {
        Scanner input = new Scanner(System.in);
        System.out.println("Keep playing? y/n");
        String ans = input.nextLine();
        while(true) {
            if(ans.toLowerCase().equals("y") || ans.toLowerCase().equals("yes")) {
                return true;
            }
            if(ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no")) {
                return false;
            }
            else {
                System.out.println("must be yes or no");
                ans = input.nextLine();
            }
        }
    }
    //finds most valuable hand
    private card[] findHighestHand(card[] all, player priv) {
        card[] allCards = new card[7];
        int idx = 0;
        for(int i = 0; i < priv.hand.length; i++) {
            allCards[idx] = priv.hand[i];
            idx++;
        }
        for(int i = 0; i < all.length; i++) {
            allCards[idx] = all[i];
            idx++;
        }
        for(int i = 0; i < allCards.length; i++) {
            priv.handValue.replace(allCards[i].getNum(), priv.handValue.get(allCards[i].getNum()) + 1);
            priv.handValue.replace(allCards[i].getSuit(), priv.handValue.get(allCards[i].getSuit()) + 1);
        }
        
    }
    
}