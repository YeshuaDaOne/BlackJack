package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;

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
    private boolean win = false;
    private boolean lose = false;
    private boolean tie = false;


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(true);
        deck = new Deck(); // Initialize the deck
        player = new Player("Human", false); // Initialize the player
        dealer = new Player("Dealer", true); // Initialize the dealer

        players = new ArrayList<>();
        players.add(player);
        players.add(new Player("CPU 1", true));
        players.add(new Player("CPU 2", true));
        players.add(new Player("CPU 3", true));



        for (Player p : players) {
            p.addCard(deck.dealCard());
            p.addCard(deck.dealCard());
        }

        dealer.addCard(deck.dealCard());


        currentPlayerIndex = 0;
    }

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This updates your game's logic every frame.  NO DRAWING.
	}






    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        float playerX = 100, playerY = Main.getScreenHeight() - 200;
        float dealerX = 100, dealerY = 100;

        // Render player's cards
        for (Card card : player.getHand()) {
            Images cardImage = new Images(card);
            cardImage.render(playerX, playerY);
            playerX += cardImage.getWidth() * 0.25f + 10;
        }

        g.setColor(Color.white);
        g.drawString("Player's Hand Value: " + player.getHandValue(), 100, playerY - 50);

        // Render dealer's cards
        g.setColor(Color.yellow);
        g.drawString("Dealer's Hand Value: " + (currentPlayerIndex < players.indexOf(player) ? "???" : dealer.getHandValue()), 100, dealerY - 50);

        for (int i = 0; i < dealer.getHand().size(); i++) {
            Images cardImage = new Images(dealer.getHand().get(i));
            if (currentPlayerIndex < players.indexOf(player) && i == 0) {

                g.setColor(Color.white);
                g.fillRect(dealerX, dealerY, 50, 70);
            } else {
                cardImage.render(dealerX, dealerY);
            }
            dealerX += cardImage.getWidth() * 0.25f + 10;
        }
        if (win) {
            g.setColor(Color.green);
            g.drawString("You Win!, \n click space to play again!", 400, Main.getScreenHeight()/2);
        } else if (lose) {
            g.setColor(Color.red);
            g.drawString("You Lose!,\n click space to try again!", 400, Main.getScreenHeight()/2);
        }
        else if(tie){
            g.setColor(Color.gray);
            g.drawString("You Tied!,\n click space to play again!", 400, Main.getScreenHeight()/2);
        }
    }
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}


    public void keyPressed(int key, char c) {
        if ((key == Input.KEY_SPACE) && (win || lose || tie)) {
            try {
                resetGame();
            } catch (SlickException e) {
                e.printStackTrace();
            }
            return;
        }

        if (win || lose || tie) {
            return;
        }


        if(key == Input.KEY_H) { // Hit
               player.addCard(deck.dealCard());
                 handleDealerTurn(deck,dealer);
                 if (player.getHandValue() > 21) {
                     System.out.println("Player bust! Dealer wins.");
                     lose = true;
                 }

             }


           if(key == Input.KEY_S) { // Stand
               endPlayerTurn();
               handleDealerTurn(deck,dealer);
           }

    }


	public void mousePressed(int button, int x, int y)
	{
		// This code happens every time the user presses the mouse
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

        if (playerScore > 21) {
            System.out.println("Player bust! Dealer wins.");
            lose = true;
        } else if (dealerScore > 21) {
            System.out.println("Dealer bust! Player wins.");
            win = true;
        } else if (playerScore > dealerScore) {
            System.out.println("Player wins!");
            win = true;
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins!");
            lose = true;
        } else {
            System.out.println("It's a tie!");
        }
    }
    private void handleDealerTurn(Deck deck, Player dealer) {
        System.out.println("Dealer gets their second card...");


        if (dealer.getHand().size() == 1) {
            dealer.addCard(deck.dealCard());
        }


        System.out.println("Dealer stands with " + dealer.getHandValue());

        checkWinCondition(player, dealer);
    }

    private void endPlayerTurn() {

        if (player.getHandValue() > 21) {
            System.out.println("Player bust! Dealer wins.");
        } else {
            handleDealerTurn(deck,dealer);
        }
    }


    private void handleCPUTurn(Player cpuPlayer) {
        if (cpuPlayer.getHandValue() < 16) {
            cpuPlayer.addCard(deck.dealCard());
            System.out.println(cpuPlayer.getName() + " hits!");
        } else {
            System.out.println(cpuPlayer.getName() + " stands.");
        }

        nextTurn(); // CPU ends their turn
    }

    private void nextTurn() {
        currentPlayerIndex++; // Move to the next player

        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0; // Reset to the first player
        }

        Player currentPlayer = players.get(currentPlayerIndex);

        if (currentPlayer.isCPU()) {
            handleCPUTurn(currentPlayer); // CPU automatically plays
        } else {
            System.out.println("It's your turn!");
        }
    }

    private void resetGame() throws SlickException {
        System.out.println("Resetting game...");

        // Reset deck and hands
        deck = new Deck();
        player.getHand().clear();
        dealer.getHand().clear();

        // Reset win/lose/tie flags
        win = false;
        lose = false;
        tie = false;

        // Redeal starting cards
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        currentPlayerIndex = 0; // Reset to human player’s turn
    }
}
