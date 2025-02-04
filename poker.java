import java.util.*;

public class poker {
    player[] players = null;
    card[] deck = new card[52];
    static final String[] nums = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    static final String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
    static final String[] hands = {"High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
    static Map<String, Integer> numVal = new HashMap<>();
    static Map<String, Integer> suitVal = new HashMap<>();
    static Map<String, Integer> handVal = new HashMap<>();
    //make hashtable for hand values

    public poker(player[] players) {
        this.players = players;
        this.initNumVal();
        this.initSuitVal();
        this.initHandVal();
        this.makeDeck();
        this.shuffleDeck(this.deck);
    } 

    private void initNumVal() {
        for(int i = 0; i < nums.length; i++) {
            numVal.put(nums[i], i);
        }
    }

    private void initSuitVal() {
        for(int i = 0; i < suits.length; i++) {
            suitVal.put(suits[i], i);
        }
    }
    private void initHandVal() {
        for(int i = 0; i < hands.length; i++) {
            handVal.put(hands[i], i);
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
        int winningPlayer = -1;
        int winningHand = -1;
        outer: for(int i = 0; i < players.length; i++) {
            this.findHighestHand(shared, players[i]);
            if(handVal.get(players[i].bestHand) > winningHand) {
                winningPlayer = i;
                winningHand = handVal.get(players[i].bestHand);
            }
            if(handVal.get(players[i].bestHand) == winningHand) {
                if((!players[i].bestHand.equals("Full House") && !players[i].bestHand.equals("Three of a Kind")) && (!players[i].bestHand.equals("Two Pair") && !players[i].bestHand.equals("Pair"))) {
                    for(int j = 0; j < players[i].finalHand.size(); j++) {
                        if(numVal.get(players[i].finalHand.get(j).getNum()) > numVal.get(players[winningPlayer].finalHand.get(j).getNum())) {
                            winningPlayer = i;
                        }
                        else if(numVal.get(players[i].finalHand.get(j).getNum()) < numVal.get(players[winningPlayer].finalHand.get(j).getNum())) {
                            break;
                        }
                    }
                }
                if(players[i].bestHand.equals("Full House")) {
                    if(numVal.get(players[i].highestTriple) > numVal.get(players[winningPlayer].highestTriple)) {
                        winningPlayer = i;
                    }
                    else if(numVal.get(players[i].highestTriple) < numVal.get(players[winningPlayer].highestTriple)) {
                        continue outer;
                    }
                    else if(numVal.get(players[i].highestTriple) == numVal.get(players[winningPlayer].highestTriple)) {
                        if((players[i].highestPair != null) && (numVal.get(players[i].highestPair) > numVal.get(players[winningPlayer].highestPair))) {
                            winningPlayer = i;
                        }
                    }
                }
                if(players[i].bestHand.equals("Three of a Kind")) {
                    if(numVal.get(players[i].highestTriple) > numVal.get(players[winningPlayer].highestTriple)) {
                        winningPlayer = i;
                    }
                    else if(numVal.get(players[i].highestTriple) < numVal.get(players[winningPlayer].highestTriple)) {
                        continue outer;
                    }
                    else if(numVal.get(players[i].highestTriple) == numVal.get(players[winningPlayer].highestTriple)) {
                        for(int j = 0; j < 5; j++) {
                            if(numVal.get(players[i].finalHand.get(j).getNum()) > numVal.get(players[winningPlayer].finalHand.get(j).getNum())) {
                                winningPlayer = i;
                            }
                            if(numVal.get(players[i].finalHand.get(j).getNum()) < numVal.get(players[winningPlayer].finalHand.get(j).getNum())) {
                                continue outer;
                            }
                        }
                    }
                }
                if(players[i].bestHand.equals("Two Pair")) {
                    if(numVal.get(players[i].highestPair) > numVal.get(players[winningPlayer].highestPair)) {
                        winningPlayer = i;
                    }
                    else if(numVal.get(players[i].highestPair) == numVal.get(players[winningPlayer].highestPair)) {
                        for(int j = 0; j < players[i].pairs.size(); j++) {
                            if(players[i].pairs.get(j).equals(players[i].highestPair)) {
                                continue;
                            }
                            for(int k = 0; k < players[winningPlayer].pairs.size(); k++) {
                                if(numVal.get(players[i].pairs.get(j)) > numVal.get(players[winningPlayer].pairs.get(k))) {
                                    winningPlayer = i;
                                }
                                if(numVal.get(players[i].pairs.get(j)) < numVal.get(players[winningPlayer].pairs.get(k))) {
                                    continue outer;
                                }
                            }
                        }
                        for(int x = 0; x < 5; x++) {
                            if(numVal.get(players[i].finalHand.get(x).getNum()) > numVal.get(players[winningPlayer].finalHand.get(x).getNum())) {
                                winningPlayer = i;
                            }
                            else if(numVal.get(players[i].finalHand.get(x).getNum()) < numVal.get(players[winningPlayer].finalHand.get(x).getNum())) {
                                continue outer;
                            }
                        }
                    }
                }
                if(players[i].bestHand.equals("Pair")) {
                    if(numVal.get(players[i].highestPair) > numVal.get(players[winningPlayer].highestPair)) {
                        winningPlayer = i;
                    }
                    else if(numVal.get(players[i].highestPair) < numVal.get(players[winningPlayer].highestPair)) {
                        continue outer;
                    }
                    else if(numVal.get(players[i].highestPair) == numVal.get(players[winningPlayer].highestPair)) {
                        for(int x = 0; x < 5; x++) {
                            if(numVal.get(players[i].finalHand.get(x).getNum()) > numVal.get(players[winningPlayer].finalHand.get(x).getNum())) {
                                winningPlayer = i;
                            }
                            else if(numVal.get(players[i].finalHand.get(x).getNum()) < numVal.get(players[winningPlayer].finalHand.get(x).getNum())) {
                                continue outer;
                            }
                        }
                    }
                }
            }
        }
        if(winningPlayer == 0) {
            System.out.printf("You won with a " + players[0].bestHand);
            return;
        }
        else {
            System.out.printf("Player " + winningPlayer + " won with a " + players[winningPlayer].bestHand + "\n");
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
    private void findHighestHand(card[] all, player priv) {
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
        for(int i = 1; i < allCards.length; i++) {
            int j = i;
            while(j > 0 &&numVal.get(allCards[j - 1].getNum()) > numVal.get(allCards[j].getNum())) {
                card temp = allCards[j];
                allCards[j] = allCards[j - 1];
                allCards[j - 1] = temp;
                j--;
            }
        }
        int highestConsec = 0;
        int consec = 1;
        int prev = 0;
        int sameSuit = 1;
        int topSuit = 0;
        for(int i = 0; i < allCards.length; i++) {
            if(i > 0) {
                if(numVal.get(allCards[i].getNum()) == numVal.get(allCards[prev].getNum()) + 1) {
                    consec++;
                    if(consec >= 5) {
                        highestConsec = i;
                    } 
                    prev = i;
                    if(suitVal.get(allCards[i].getSuit()) == suitVal.get(allCards[prev].getSuit())) {
                        sameSuit++;
                        if(sameSuit >= 5) {
                            topSuit = i;
                        }
                    }
                }
                if(numVal.get(allCards[i].getNum()) == numVal.get(allCards[i - 1].getNum()) && suitVal.get(allCards[i].getSuit()) == suitVal.get(allCards[prev].getSuit())) {
                    sameSuit++;
                    prev = i;
                    if(sameSuit >= 5) {
                        topSuit = i;
                    }
                }
                else {
                    consec = 1;
                    sameSuit = 1;
                    prev = i;
                }

            }
            priv.handValue.replace(allCards[i].getNum(), priv.handValue.get(allCards[i].getNum()) + 1);
            priv.suitValue.replace(allCards[i].getSuit(), priv.suitValue.get(allCards[i].getSuit()) + 1);
        }
        //start identifying and returning hands
        //Straight flush
        if(highestConsec != 0 && topSuit != 0) {
            priv.bestHand = "Straight Flush";
            for(int i = topSuit; i >= 0; i--) {
                if(allCards[i].getSuit() == allCards[topSuit].getSuit()) {
                    priv.finalHand.add(allCards[i]);
                    if(priv.finalHand.size() == 5) {
                        break;
                    }
                }
            }
            return;
        }
        //search for matched cards
        for(String i: priv.handValue.keySet()) {
            //four of a kind
            if(priv.handValue.get(i) == 4) {
                card highest = new card("2", "Spades");
                for(int j = 0; j < allCards.length; j++) {
                    if(allCards[j].getNum().equals("4")) {
                        priv.finalHand.add(allCards[j]);
                    }
                    else if(numVal.get(allCards[j].getNum()) >= numVal.get(highest.getNum())) {
                        highest = allCards[j];
                    }
                }
                priv.finalHand.add(highest);
                priv.bestHand = "Four of a Kind";
                return;
            }
            if(priv.handValue.get(i) == 3) {
                priv.triple.add(i);
                priv.pairs.add(i);
                if(priv.highestTriple == null || numVal.get(priv.highestTriple) < numVal.get(i)) {
                    if(priv.highestPair == null || numVal.get(priv.highestTriple) > numVal.get(priv.highestPair)) {
                        priv.highestPair = priv.highestTriple;
                    }
                    priv.highestTriple = i;
                }
                else if(priv.highestPair == null || numVal.get(priv.highestPair) < numVal.get(i)) {
                    priv.highestPair = i;
                }
                

            }
            if(priv.handValue.get(i) == 2) {
                priv.pairs.add(i);
                if(priv.highestPair == null || numVal.get(priv.highestPair) < numVal.get(i)) {
                    priv.highestPair = i;
                }
            }
        }
        //full house
        if((priv.triple.size() > 0 && priv.pairs.size() > 0) || priv.triple.size() == 2) {
            priv.bestHand = "Full House";
            return;
        }
        //Flush
        for(String i: priv.suitValue.keySet()) {
            if(priv.suitValue.get(i) >= 5) {
                priv.hasFlush = true;
                for(int j = allCards.length - 1; j >= 0; j--) {
                    if(allCards[j].getSuit().equals(i)) {
                        priv.finalHand.add(allCards[j]);
                        if(priv.finalHand.size() == 5) {
                            break;
                        }
                    }
                }
            }
        }
        if(priv.hasFlush == true) {
            priv.bestHand = "Flush";
            return;
        }
        //straight
        if(highestConsec != 0) {
            int count = 1;
            String last = allCards[highestConsec].getNum();
            priv.finalHand.add(allCards[highestConsec]);
            for(int i = highestConsec - 1; i >= 0; i--) {
                if(!allCards[i].getNum().equals(last)) {
                    priv.finalHand.add(allCards[i]);
                    count++;
                    if(count == 5) {
                        break;
                    }
                }
            }
            priv.bestHand = "Straight";
            return;
        }
        //three of a kind
        if(priv.triple.size() > 0) {
            priv.bestHand = "Three of a Kind";
            int count = 0;
            for(int i = allCards.length - 1; i >= 0; i--) {
                if(count < 2 && allCards[i].getNum() != priv.triple.element()) {
                    priv.finalHand.add(allCards[i]);
                    count++;
                }
                if(allCards[i].getNum() == priv.triple.element()) {
                    priv.finalHand.add(allCards[i]);
                }
            }
            return;
        }
        //two pair
        if(priv.pairs.size() > 1) {
            String pair1 = priv.pairs.get(0);
            String pair2 = priv.pairs.get(1);
            if(priv.pairs.size() == 3) {
                if(numVal.get(priv.pairs.get(2)) > numVal.get(pair1) || numVal.get(priv.pairs.get(2)) > numVal.get(pair2)) {
                    if(numVal.get(pair1) > numVal.get(pair2)) {
                        pair2 = priv.pairs.get(2);
                    }
                    else {
                        pair1 = priv.pairs.get(2);
                    }
                }
            }
            int count= 0;
            int index = 0;
            for(int i = allCards.length - 1; i >=0; i--) {
                if(count == 0 && (allCards[i].getNum() != pair1 && allCards[i].getNum() != pair2)) {
                    priv.finalHand.add(allCards[i]);
                    count++;
                    index++;
                    if(index == 5) {
                        break;
                    }
                }
                else if(allCards[i].getNum() == pair1 || allCards[i].getNum() == pair2) {
                    priv.finalHand.add(allCards[i]);
                    index++;
                    if(index == 5) {
                        break;
                    }
                }
            }
            priv.bestHand = "Two Pair";
            return;
        }
        //pair
        if(priv.pairs.size() == 1) {
            int count = 0;
            int index = 0;
            for(int i = allCards.length - 1; i >= 0; i--) {
                if(count < 3 && allCards[i].getNum() != priv.pairs.get(0)) {
                    priv.finalHand.add(allCards[i]);
                    index++;
                    count++;
                    if(index == 5) {
                        break;
                    }
                }
                else if(allCards[i].getNum() == priv.pairs.get(0)) {
                    priv.finalHand.add(allCards[i]);
                    index++;
                    if(index == 5) {
                        break;
                    }
                }
            }
            priv.bestHand = "Pair";
            return;
        }
        //high card
        else {
            for(int i = allCards.length - 1; i > 1; i--) {
                priv.finalHand.add(allCards[i]);
            }
            priv.bestHand = "High Card";
            return;
        }
    }
}