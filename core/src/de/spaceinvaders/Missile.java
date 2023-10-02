package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class Missile extends NonPlayerCharacter {
    protected static final int MOVE =4;

    public Missile(String imagePath, Viewport viewport, int width, int height) {
        super(imagePath, viewport, 0, 0, width, height);
    }

    public boolean move() {
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
