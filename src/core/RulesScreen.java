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
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        backX = Main.getScreenWidth();
        backY = Main.getScreenHeight();
        x = Main.getScreenWidth()/3;
        y = Main.getScreenHeight()/3;

        ruleScreen.draw(0,0,backX,backY);
        g.setColor(Color.white);
        g.drawString("How to Play Blackjack:", x, y);
        g.drawString("- Try to get as close to 21 as possible without going over.", x, y+50);
        g.drawString("- Face cards are worth 10, Aces are 1 or 11.", x, y+100);
        g.drawString("- Press H to Hit, S to Stand.", x, y+150);
        g.drawString("- Use EXP to buy upgrades in the shop (press O to open it).", x, y+200);
        g.drawString("Press B to go back to the Title Screen.", x, y+250);
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
