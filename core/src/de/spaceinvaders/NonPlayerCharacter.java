package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class NonPlayerCharacter extends PlayerCharacter{

    public NonPlayerCharacter(String imagePath, Viewport viewport, int x, int y, int width, int height) {
        super(imagePath, viewport, x, y, width, height);
    }

    protected void collision() {
        setActive(false);
        this.reset();
    }

    public abstract boolean move();
}
