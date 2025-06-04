package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TitleScreen extends BasicGameState {
    private int id;
    private Image startBackground;
private StateBasedGame sbg;
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
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        int x = Main.getScreenWidth();
        int y = Main.getScreenHeight();
        startBackground.draw(0,0,x,y);
        g.setColor(Color.white);
        g.drawString("Welcome to Blackjack (Hugo Edition)", Main.getScreenWidth()/2-100, 150);
        g.drawString("Press ENTER to Start", Main.getScreenWidth()/2-100, 250);
        g.drawString("Press R to View Rules", Main.getScreenWidth()/2-100, 300);
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
