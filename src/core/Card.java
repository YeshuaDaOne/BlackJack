package core;

public class Card extends Deck{
    public static String getRank;
    private String suit;
    private String rank;
    private int value;
    Card(String suit, String rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }
    public String getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }
}
