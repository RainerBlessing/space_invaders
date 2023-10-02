package de.spaceinvaders;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Bomb extends NonPlayerCharacter{
    protected static final int MOVE =-1;

    public Bomb(String imagePath, Viewport viewport, int width, int height) {
        super(imagePath, viewport, 0, 0, width, height);
    }

    @Override
    public boolean move() {
        boolean couldMove;

        if (sprite.getY() + sprite.getHeight()/2 > 0) {
            sprite.setY(sprite.getY() + MOVE);
            couldMove = true;
        } else {
            active = false;
            this.reset();
            couldMove = false;
        }

        return couldMove;
    }
    public void setPosition(float x, float y) {
        sprite.setX(x);
        sprite.setY(y-4);//underneath invader
    }

    @Override
    protected void collision() {
        setActive(false);
    }

    public Rectangle getBoundingRectangle() {
        Rectangle spriteRectangle=sprite.getBoundingRectangle();
        Rectangle rectangle = new Rectangle();
        rectangle.x=spriteRectangle.x+7;
        rectangle.y=spriteRectangle.y+16;
        rectangle.width=2;
        rectangle.height=2;

        return rectangle;
    }
}
