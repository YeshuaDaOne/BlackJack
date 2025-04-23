package core;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;


                if (rank.equals("Ace")) {
                    value = 11;
                } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) { //
                    value = 10;
                } else {

                    value = Integer.parseInt(rank);
                }


                cards.add(new Card(suit, rank, value));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }
}