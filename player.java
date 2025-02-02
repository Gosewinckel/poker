import java.util.Map;
import java.util.HashMap;

public class player {
    int playerId;
    card[] hand = new card[2];
    private static String[] cardTypes = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Spades", "Clubs", "Diamonds", "Hearts"};
    Map<String, Integer> handValue = new HashMap<>(); 
    // could add money or points

    public player(int playerId) {
        this.playerId = playerId;
        for(int i = 0; i < cardTypes.length; i++) {
            handValue.put(cardTypes[i], 0);
        }
    }
    public int getPlayerId() {
        return this.playerId;
    }
    public card [] getHand() {
        return this.hand;
    }
}