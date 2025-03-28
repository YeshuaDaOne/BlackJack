package core;

import java.util.ArrayList;

public class Deck {
    public ArrayList<Card> deck;
    Deck(){
       deck = new ArrayList<>(52);
       String[] suits ={"Hearts", "Diamonds", "Clubs", "Spades"};
       String[] ranks ={"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

       for(String suit: suits){
           for(String rank: ranks){
//           int value = determineCardValue(new Card("Hearts","Ace",""));
//           deck.add(new Card(suit,rank,value));
       }
       }
        }
        public void shuffle(){

        }
        private int determineCardValue(Card card){
        String rank = Card.getRank;
        if(rank.equals("Ace")){
            return 1;
        }
        else if (rank.equals("King") || rank.equals("Queen")|| rank.equals("Jack")){
            return 10;
        }
        else{
            return Integer.parseInt(rank);
        }
        }

    }


