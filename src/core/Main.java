package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame 
{
	public final static int FRAMES_PER_SECOND = 60;
	private static AppGameContainer appgc;
	
    public static final int GAME_ID  = 0;
	public static final int TITLE_ID = 1;
	public static final  int RULES_ID = 2;
    
    private BasicGameState game;  
    //This comment is for if Dylan attempts to copy/take my work as I am commiting and pushing on github to not lose my work and this will then be proof of such
	public Main(String name) 
	{
		super(name);
		
		game = new Game(GAME_ID);
	}

	public static int getScreenWidth()
	{
		return appgc.getScreenWidth();
	}
	
	public static int getScreenHeight()
	{
		return appgc.getScreenHeight();
	}
	

	public void initStatesList(GameContainer gc) throws SlickException 
	{

		addState(new TitleScreen(TITLE_ID));
		addState(new RulesScreen(RULES_ID));

		addState(game);
	}

	public static void main(String[] args) 
	{
		try 
		{
			appgc = new AppGameContainer(new Main("Blackjack(Hugo)"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
			appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.setVSync(true);
			appgc.start();

		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
}