package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class NonPlayerCharacter extends PlayerCharacter{

    protected NonPlayerCharacter(String imagePath, Viewport viewport, int x, int y, int width, int height) {
        super(imagePath, viewport, x, y, width, height);
    }

    @Override
    protected void collision() {
        setActive(false);
        this.reset();
    }

    public abstract boolean move();
}
