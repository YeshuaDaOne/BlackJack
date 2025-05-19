package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private ArrayList<Card> hand;

    private String name;
    private boolean isCPU; // Distinguish CPU players
    private int exp;



    private HashMap<String, Boolean> upgrades; // Track purchased upgrades

    public Player(String name, boolean isCPU) {
        this.name = name;
        this.isCPU = isCPU;
        this.hand = new ArrayList<>();
        this.exp = 0;
        this.upgrades = new HashMap<>(); // Initialize upgrade storage

        // Default: No upgrades are active
        upgrades.put("Skip Turn", false);
        upgrades.put("Force Draw", false);
        upgrades.put("Elimination Block", false);
        upgrades.put("Double EXP", false);
    }

    public void addCard(Card card) {
        hand.add(card);
    }
    private boolean aceDecisionPending; // Tracks whether player needs to choose Ace value

    public void setAceDecisionPending(boolean pending) {
        this.aceDecisionPending = pending;
    }

    public boolean isAceDecisionPending() {
        return aceDecisionPending;
    }
    public int getHandValue() {
        int value = 0, aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }


        while (aceCount > 0 && value > 21) {
            value -= 10; // Change Ace from 11 to 1
            aceCount--;
        }

        return value;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }


    public void earnExp(int amount) {
        exp += amount;
        System.out.println(name + " earned " + amount + " EXP.");
    }

    // Spend EXP at shop
    public void spendExp(int amount) {
        if (exp >= amount) {
            exp -= amount;
            System.out.println(name + " spent " + amount + " EXP.");
        } else {
            System.out.println(name + " does not have enough EXP!");
        }
    }

    public int getExp() {
        return exp;
    }
    public void setUpgrade(String upgradeName, boolean value) {
        if (upgrades.containsKey(upgradeName)) {
            upgrades.put(upgradeName, value);
        }
    }

    public boolean hasUpgrade(String upgradeName) {
        return upgrades.getOrDefault(upgradeName, false);
    }

    public String getName() {
        return name;
    }

    public boolean isCPU() {
        return isCPU;
    }

    public void eliminatePlayer(Player target) {
        System.out.println(target.getName() + " has been eliminated!");

    }
}