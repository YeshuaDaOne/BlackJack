package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import core.Images;

import java.util.ArrayList;

public class Game extends BasicGameState
{
	private int id;

	public Game(int id)
	{
		this.id = id;
	}

	public int getID()
	{
		return id;
	}

    private Player player;
    private Player dealer;
    private Deck deck;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Player currentPlayer;
    private boolean win = false;
    private boolean lose = false;
    private boolean tie = false;
    private int playerHitCount = 0;
    private Shop shop;
    private boolean roundAlreadyUpdated = false;
    private Image winImage;
    private Image loseImage;
    private Image tieImage;
private Image background;
private Sound loseSound;
private Sound winSound;
private Sound backSound;
    private boolean isMuted = false;
    private TrueTypeFont boldFont;


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(true);
        deck = new Deck(); // Initialize the deck
        player = new Player("Human", false); // Initialize the player
        dealer = new Player("Dealer", true); // Initialize the dealer
        shop = new Shop();
        players = new ArrayList<>();
        players.add(player);
        players.add(new Player("CPU 1", true));
        players.add(new Player("CPU 2", true));
        winImage = new Image("assets/winText.png");
        loseImage = new Image("assets/loseText.png");
        tieImage = new Image("assets/tieText.png");
    background = new Image("assets/blackjackTable.png");
    loseSound = new Sound("assets/loseSound.wav");
    winSound = new Sound("assets/winSound.wav");
backSound = new Sound("assets/backSound.wav");
        java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 24); // Bold, size 24
        boldFont = new TrueTypeFont(awtFont, true);


        for (Player p : players) {
            p.addCard(deck.dealCard());
            p.addCard(deck.dealCard());
        }

        dealer.addCard(deck.dealCard());


        currentPlayerIndex = 0;
    }

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
	}

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       background.draw(0,0,Main.getScreenWidth(),Main.getScreenHeight());
        g.setColor(Color.white);

        boldFont.drawString(Main.getScreenWidth()/2-50,50,"Round: " + roundCount);
        boldFont.drawString(Main.getScreenWidth() / 2 - 50,80,"EXP: " + player.getExp());
        float playerX = 100, playerY = Main.getScreenHeight() - 200;
        float dealerX = 100, dealerY = 100;
        float cpu1X = Main.getScreenWidth() - 500, cpu1Y = 200;
        float cpu2X = cpu1X, cpu2Y = cpu1Y + 250;

        for (Card card : player.getHand()) {
            Images cardImage = new Images(card);
            cardImage.render(playerX, playerY);
            playerX += cardImage.getWidth() * 0.25f + 10;
        }
        g.setColor(Color.white);
        boldFont.drawString(150,playerY-75,"Player's Hand Value: " + player.getHandValue());

        boldFont.drawString(150,dealerY-50,"Dealer's Hand Value: " + dealer.getHandValue(),Color.yellow);
        for (Card card : dealer.getHand()) {
            Images cardImage = new Images(card);
            cardImage.render(dealerX, dealerY);
            dealerX += cardImage.getWidth() * 0.25f + 10;
        }

        g.setColor(Color.white);
        g.drawString("CPU Hands", cpu1X, cpu1Y - 30);
        g.drawString("CPU Hands", cpu1X, cpu1Y - 30);

        for (Player cpu : players) {
            if (cpu.isCPU()) {
                float currentCpuX = (cpu.getName().equals("CPU 1")) ? cpu1X : cpu2X;
                float currentCpuY = (cpu.getName().equals("CPU 1")) ? cpu1Y : cpu2Y;

                // Display only revealed hand values
                if (playerHitCount >= cpu.getHand().size()) {
                    g.setColor(Color.yellow);
                    g.drawString(cpu.getName() + " Hand Value: " + cpu.getHandValue(), currentCpuX, currentCpuY - 50);
                } else {
                    g.drawString(cpu.getName() + " Hand Value: ???", currentCpuX, currentCpuY - 50);
                }

                for (int i = 0; i < cpu.getHand().size(); i++) {
                    if (i < playerHitCount) {
                        Images cardImage = new Images(cpu.getHand().get(i));
                        cardImage.render(currentCpuX, currentCpuY); // Flip up progressively
                    } else {
                        g.fillRect(currentCpuX, currentCpuY, 50, 70); // Keep face-down
                    }
                    currentCpuX += 60;
                }
            }

        }

        g.setColor(Color.white);
        if (win && winImage != null) {
            winImage.draw(Main.getScreenWidth() / 2f - winImage.getWidth() / 2f, Main.getScreenHeight() / 2f - winImage.getHeight() / 2f);
        } else if (lose && loseImage != null) {
            loseImage.draw(Main.getScreenWidth() / 2f - loseImage.getWidth() / 2f, Main.getScreenHeight() / 2f - loseImage.getHeight() / 2f);
        } else if (tie && tieImage != null) {
            tieImage.draw(Main.getScreenWidth() / 2f - tieImage.getWidth() / 2f, Main.getScreenHeight() / 2f - tieImage.getHeight() / 2f);
        }

        if (shop.isShopOpen()) {
            shop.render(g); // Render shop UI when open
        }
        g.setColor(Color.white);
        boldFont.drawString(20, 30, "Music: " + (isMuted ? "Muted (M to unmute)" : "Playing (M to mute)"), Color.orange);


    }



    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{

        if (backSound != null && !backSound.playing()) {
backSound.loop();
 }

    }

	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		// This code happens when you leave a gameState.
	}


    public void keyPressed(int key, char c) {
       if(key==Input.KEY_O) {
           shop.openShop();

       }

        if (shop.isShopOpen()) {
            if (key == Input.KEY_D) {
                shop.purchaseItem(player, "D. Double EXP Next Round (Legendary)");
            }
            if (key == Input.KEY_T) {
                shop.purchaseItem(player, "T. Skip Turn (Common)");
            }
            if (key == Input.KEY_E) {
                shop.purchaseItem(player, "E. Force Draw (Uncommon)");
            }
            if (key == Input.KEY_B) {
                shop.purchaseItem(player, "B. Elimination Block (Rare)");
            }
            if (key == Input.KEY_C) { // Close shop
                shop.closeShop();
            }
            return;
        }

        if ((key == Input.KEY_R) && (win || lose || tie)) {
            try {
                resetGame();
            } catch (SlickException e) {
                e.printStackTrace();
            }
            return;
        }
if((key == Input.KEY_ESCAPE) &&(win||lose||tie)){
    System.exit(0);
}
        if (win || lose || tie) {
            return;
        }

        if (key == Input.KEY_F && player.hasUpgrade("Force Draw")) { // Example key for forced draw
            Player target = players.get((currentPlayerIndex + 1) % players.size());
            target.addCard(deck.dealCard());

            System.out.println(player.getName() + " forces " + target.getName() + " to draw a card!");
            player.setUpgrade("Force Draw", false); // Use upgrade once
        }
        if (key == Input.KEY_H) {
            player.addCard(deck.dealCard());
            playerHitCount++;

            if (player.getHandValue() > 21) {
                playerHitCount = 999;
                compareFinalScores();
                lose = true;
            }
        }

        if (key == Input.KEY_S) {
            endPlayerTurn();
            handleDealerTurn(deck, dealer);
        }
        if (key == Input.KEY_M) {
            isMuted = !isMuted;

            if (isMuted) {
                if (backSound.playing()) {
                    backSound.stop();
                }
            } else {
                if (!backSound.playing()) {
                    backSound.loop();
                }
            }
        }

    }




    public void mousePressed(int button, int x, int y)
	{

	}

    private void handleTurn(Player player, Deck deck) {
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());



        if (player.getHandValue() > 21) {
            System.out.println("Player bust! Dealer wins.");
        } else if (dealer.getHandValue() > 21) {
            System.out.println("Dealer bust! Player wins.");
        } else if (player.getHandValue() > dealer.getHandValue()) {
            System.out.println("Player wins!");
        } else {
            System.out.println("Dealer wins!");
        }
    }

    private void checkWinCondition(Player player, Player dealer) {
        int playerScore = player.getHandValue();
        int dealerScore = dealer.getHandValue();
        int cpu1Score = players.get(1).getHandValue();
        int cpu2Score = players.get(2).getHandValue();

        // Set scores over 21 to 0 (busted)
        if (playerScore > 21) playerScore = 0;
        if (dealerScore > 21) dealerScore = 0;
        if (cpu1Score > 21) cpu1Score = 0;
        if (cpu2Score > 21) cpu2Score = 0;

        // Find the best score
        int bestScore = Math.max(playerScore, Math.max(dealerScore, Math.max(cpu1Score, cpu2Score)));

        // Determine outcome
        if (playerScore == bestScore && bestScore != 0) {
            System.out.println("Player wins with " + playerScore + "!");
            win = true;
            if (winSound != null && !winSound.playing()) winSound.play();
        } else if (playerScore == bestScore && bestScore == 0) {
            System.out.println("Everyone busted. It's a tie!");
            tie = true;
        } else if (playerScore == bestScore) {
            System.out.println("It's a tie!");
            tie = true;
        } else {
            System.out.println("Player loses. Best score: " + bestScore);
            lose = true;
            if (loseSound != null && !loseSound.playing()) loseSound.play();
        }

        rewardExp();
    }





    private void handleDealerTurn(Deck deck, Player dealer) {
        System.out.println("Dealer reveals second card...");


        while (dealer.getHandValue() < 17) {
            dealer.addCard(deck.dealCard());
            System.out.println("Dealer hits! New hand value: " + dealer.getHandValue());
        }

        System.out.println("Dealer stands with " + dealer.getHandValue());

    }


    private void endPlayerTurn() {
        System.out.println("\nPlayer stands. Revealing all CPU cards...");

        // Ensure all CPU cards flip up
        playerHitCount = 999; // Set a high number to ensure all cards flip

        // Handle dealer's turn
        handleDealerTurn(deck, dealer);

        // Display game outcome
        checkWinCondition(player, dealer);
    }







    public void handleCPUTurn(Player cpuPlayer, String difficulty) {
        int handValue = cpuPlayer.getHandValue();

        switch (difficulty) {
            case "Easy":
                if (handValue < 16) {
                    cpuPlayer.addCard(deck.dealCard());
                    System.out.println(cpuPlayer.getName() + " hits!");
                } else {
                    System.out.println(cpuPlayer.getName() + " stands.");
                }
                break;

            case "Medium":
                if (handValue < 17) {
                    cpuPlayer.addCard(deck.dealCard());
                    System.out.println(cpuPlayer.getName() + " plays aggressively!");
                } else {
                    System.out.println(cpuPlayer.getName() + " stands.");
                }
                break;

            case "Hard":
                if (handValue < 18 && player.getHandValue() > 18) {
                    cpuPlayer.addCard(deck.dealCard());
                    System.out.println(cpuPlayer.getName() + " plays strategically!");
                } else {
                    System.out.println(cpuPlayer.getName() + " stands.");
                }
                break;
        }

    }


    private int roundCount = 1; //

    private void nextTurn() {
        currentPlayerIndex++;

        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;


            System.out.println("\nRound " + roundCount + " completed.");


            if (currentPlayer.hasUpgrade("Skip Turn")) {
                System.out.println(currentPlayer.getName() + " skips their turn!");
                currentPlayer.setUpgrade("Skip Turn", false); // Use upgrade once

            }

        }

        Player currentPlayer = players.get(currentPlayerIndex);

        if (currentPlayer.isCPU()) {
            handleCPUTurn(currentPlayer, "Hard");
        } else {
            System.out.println("It's your turn!");
        }
    }




    private void resetGame() throws SlickException {
        System.out.println("\n--- RESETTING GAME ---");
        roundAlreadyUpdated = false;

        deck = new Deck();
        player.getHand().clear();
        dealer.getHand().clear();

        for (Player cpu : players) {
            if (cpu.isCPU()) {
                cpu.getHand().clear();
            }
        }


        win = false;
        lose = false;
        tie = false;
        playerHitCount = 0;


        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        for (Player cpu : players) {
            if (cpu.isCPU()) {
                cpu.addCard(deck.dealCard());
                cpu.addCard(deck.dealCard());
            }
        }

        currentPlayerIndex = 0;
        roundCount++;
        rewardExp();
        if (loseSound.playing()) {
            loseSound.stop();
        }
        if(winSound.playing()){
            winSound.stop();
        }

    }

    private void openShop() {
        Shop shop = new Shop();
        System.out.println("\n--- ROUND " + roundCount + " COMPLETE ---");
        System.out.println("--- SHOP IS NOW OPEN! ---");
        shop.showItems();

        for (Player p : players) {
            if (!p.isCPU()) {
                System.out.println(p.getName() + ", would you like to purchase an item? (Y/N)");
                // Handle input logic for shop interactions
            }
        }
    }



    private void compareFinalScores() {
        System.out.println("\n--- FINAL SCORE COMPARISON ---");

        for (Player cpu : players) {
            if (cpu.isCPU()) {
                System.out.println(cpu.getName() + "'s final hand: " + cpu.getHand() + " (Total: " + cpu.getHandValue() + ")");
            }
        }

        int dealerScore = dealer.getHandValue();
        int cpu1Score = players.get(1).getHandValue();
        int cpu2Score = players.get(2).getHandValue();
        int playerScore = player.getHandValue();

        int bestScore = Math.max(
                (playerScore > 21 ? 0 : playerScore),
                Math.max(cpu1Score > 21 ? 0 : cpu1Score,
                        Math.max(cpu2Score > 21 ? 0 : cpu2Score, dealerScore > 21 ? 0 : dealerScore))
        );

        if (bestScore == playerScore) {
            System.out.println("Player wins with " + playerScore + "!");
            win = true;
        } else if (bestScore == cpu1Score) {
            System.out.println("CPU 1 wins with " + cpu1Score + "!");
            lose = true;
        } else if (bestScore == cpu2Score) {
            System.out.println("CPU 2 wins with " + cpu2Score + "!");
            lose = true;
        } else {
            System.out.println("Dealer wins with " + dealerScore + "!");
            lose = true;
        }
    }
    private void rewardExp() {
        int baseExp = 25; // Base EXP for a win
        int tieExp = 10;   // Base EXP for a tie

        if (win) {
            player.earnExp(baseExp); // Triple once
        } else if (tie) {
            player.earnExp(tieExp);  // Triple once
        }
    }





}
