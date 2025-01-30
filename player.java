public class player {
    int playerId;
    card[] hand = new card[2];
    // could add money or points

    public player(int playerId, card[] hand) {
        this.playerId = playerId;
        this.hand = hand;
    }
    public int getPlayerId() {
        return this.playerId;
    }
    public card [] getHand() {
        return this.hand;
    }
}