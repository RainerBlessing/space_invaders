package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerCharacter extends GameObject {
    private int lives = 3;
    protected final Viewport viewport;
    public PlayerCharacter(String imagePath, Viewport viewport, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
        this.viewport = viewport;
    }
    @Override
    public void reset() {
        super.reset();
        this.lives = 3;
    }
    public void moveRight() {
        if((sprite.getX()+sprite.getWidth()+MOVE_STEPS)<viewport.getWorldWidth())
            translateX(MOVE_STEPS);
    }

    public void moveLeft() {
        if((sprite.getX()-MOVE_STEPS)>0)
            translateX(-MOVE_STEPS);
    }

    @Override
    protected void collision() {
        lives = lives -1;
    }

    public int getLives() {
        return lives;
    }
}
