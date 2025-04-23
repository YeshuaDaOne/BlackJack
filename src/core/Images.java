package core;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
    private Image sprite;
    private Card card;

    public Images(Card card) throws SlickException {
        this.card = card;


        String path = "assets/cards/" + card.getRank().toLowerCase() + "_of_" + card.getSuit().toLowerCase() + ".png";

        this.sprite = new Image(path);
    }

    public void render(float x, float y) {
        float scale = 0.25f;
        sprite.draw(x, y, sprite.getWidth() * scale, sprite.getHeight() * scale);
    }
    public float getWidth() {
        return sprite.getWidth();
    }
}