class main {
    public static void main(String[] args) {
        player self = new player(0);
        int numPlayers = 6;
        player[] players = new player[numPlayers];
        for(int i = 1; i < numPlayers; i++) {
            players[i] = new player(i);
        }
        players[0] = self;
        poker game = new poker(players);
        game.play();
    }
}