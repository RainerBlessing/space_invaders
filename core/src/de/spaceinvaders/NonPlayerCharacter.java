package de.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class NonPlayerCharacter extends PlayerCharacter{
    protected int move=8;
    public NonPlayerCharacter(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    protected void collision() {
        setActive(false);
        this.reset();
    }

    public abstract boolean move(Viewport viewport);
}
