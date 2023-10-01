package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class Missile extends NonPlayerCharacter {
    protected static final int MOVE =4;
    public Missile(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    public boolean move(Viewport viewport) {
        boolean couldMove;

        if (sprite.getY() + sprite.getHeight() < viewport.getWorldHeight()) {
            sprite.setY(sprite.getY() + MOVE);
            couldMove = true;
        } else {
            active = false;
            this.reset();
            couldMove = false;
        }

        return couldMove;
    }
}
