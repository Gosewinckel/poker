import java.util.Map;
import java.util.HashMap;

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
        //what m8
    }

    private void shuffleDeck() {
        //solve after makedeck
    }
    
}