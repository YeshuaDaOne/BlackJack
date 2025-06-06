package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class RulesScreen extends BasicGameState {
    private int id;
    private Image ruleScreen;
    private int backX;
    private int backY;
    private int x;
    private int y;
    private TrueTypeFont boldFont;

    private StateBasedGame sbg;
    public RulesScreen(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.sbg = sbg;
        ruleScreen= new Image("assets/ruleScreen.png");
        java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 24); // Bold, size 24
        boldFont = new TrueTypeFont(awtFont, true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        backX = Main.getScreenWidth();
        backY = Main.getScreenHeight();
        x = Main.getScreenWidth() / 3;
        y = Main.getScreenHeight() / 4;

        ruleScreen.draw(0, 0, backX, backY);
        g.setColor(Color.white);


        boldFont.drawString(x, y, "How to Play Blackjack", Color.red);
 boldFont.drawString(x, y + 40, "- Try to get as close to 21 as possible", Color.red);
 boldFont.drawString(x, y + 80, "- Face cards are worth 10, Aces are 1 or 11", Color.red);
 boldFont.drawString(x, y + 120, "- Press H to Hit, S to Stand", Color.red);
 boldFont.drawString(x, y + 160, "- Use EXP to buy upgrades (press O)", Color.red);
 boldFont.drawString(x, y + 200, "- Press M to mute music", Color.red);
 boldFont.drawString(x, y + 240, "- Press B to go back to Title Screen", Color.red);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {}

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_B) {
            sbg.enterState(Main.TITLE_ID);
        }
    }
}
