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

    private void makeDeck() {
        int idx1 = 0;
        int idx2 = 0;
        for(int i = 0; i < deck.length; i++) {
            if(idx1 == 4) {
                idx1 = 0;
                idx2++;
            }
            this.deck[i] = new card(suits[idx1], nums[idx2]);
            idx1++;
        }
    }

    private void shuffleDeck(card[] deck) {
        Random rand = new Random();
        for(int i = deck.length - 1; i > 0; i--) {
            int idx = rand.nextInt(i + 1);
            card x = deck[i];
            deck[i] = deck[idx];
            deck[idx] = x;
        }
    }

    public void play() {
        int deckIdx = 0;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < players.length; j++) {
                players[j].hand[i] = this.deck[deckIdx];
                deckIdx++;
            }
        }
        for(int i = 0; i < 2; i++) {
            System.out.println(players[0].hand[i]);
        }
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        card[] shared = new card[5];
        for(int i = 0; i < 3; i++) {
            shared[i] = deck[deckIdx];
            deckIdx++;
            System.out.println(shared[i]);
        }
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        shared[3] = deck[deckIdx];
        System.out.println(shared[3]);
        deckIdx++;
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        shared[4] = deck[deckIdx];
        System.out.println(shared[4]);
        if(this.keepPlaying() == false) {
            System.out.println("you folded");
            return;
        }
        return;
    }

    private boolean keepPlaying() {
        Scanner input = new Scanner(System.in);
        System.out.println("Keep playing? y/n");
        String ans = input.nextLine();
        while(true) {
            if(ans.equals("y") || ans.equals("yes")) {
                input.close();
                return true;
            }
            if(ans.equals("n") || ans.equals("no")) {
                input.close();
                return false;
            }
            else {
                System.out.println("must be yes or no");
                ans = input.nextLine();
            }
        }
    }
    
}