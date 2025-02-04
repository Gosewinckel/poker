import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class player {
    int playerId;
    card[] hand = new card[2];
    private static String[] cardTypes = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
    Map<String, Integer> handValue = new HashMap<>(); 
    Map<String, Integer> suitValue = new HashMap<>();
    LinkedList<String> pairs = new LinkedList<>();
    LinkedList<String> triple = new LinkedList<>();
    String highestTriple;
    String highestPair;
    boolean hasTriple = false;
    boolean hasFlush = false;
    String bestHand;
    LinkedList<card> finalHand = new LinkedList<>();
    // could add money or points

    public player(int playerId) {
        this.playerId = playerId;
        for(int i = 0; i < cardTypes.length; i++) {
            handValue.put(cardTypes[i], 0);
        }
        for(int i = 0; i < suits.length; i++) {
            suitValue.put(suits[i], 0);
        }
    }
    public int getPlayerId() {
        return this.playerId;
    }
    public card [] getHand() {
        return this.hand;
    }
}