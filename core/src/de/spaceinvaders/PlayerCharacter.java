package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerCharacter extends GameObject {
    public PlayerCharacter(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    public boolean moveRight(Viewport viewport) {
        boolean couldMove=true;

        if((sprite.getX()+sprite.getWidth()+MOVE_STEPS)<viewport.getWorldWidth())
            translateX(MOVE_STEPS);
        else couldMove = false;

        return couldMove;
    }

    public boolean moveLeft(Viewport viewport) {
        boolean couldMove=true;

        if((sprite.getX()-MOVE_STEPS)>0)
            translateX(-MOVE_STEPS);
        else couldMove = false;

        return couldMove;
    }
}
