package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TitleScreen extends BasicGameState {
    private int id;
    private Image startBackground;
private StateBasedGame sbg;
    private TrueTypeFont boldFont;

    public TitleScreen(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.sbg = sbg;
        startBackground = new Image("assets/startBackground.png");
        java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 24); // Bold, size 24
        boldFont = new TrueTypeFont(awtFont, true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        int x = Main.getScreenWidth();
        int y = Main.getScreenHeight();
        startBackground.draw(0,0,x,y);
        g.setColor(Color.white);

        boldFont.drawString(Main.getScreenWidth()/2-100,250,"Welcome to Blackjack (Hugo Edition)",Color.yellow);
        boldFont.drawString(Main.getScreenWidth()/2-50,350,"Press ENTER to Start",Color.green);
        boldFont.drawString(Main.getScreenWidth()/2-50,450,"Press R to View Rules",Color.red);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {}

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER) {
            sbg.enterState(Main.GAME_ID);
        } else if (key == Input.KEY_R) {
            sbg.enterState(Main.RULES_ID);
        }
    }
}
