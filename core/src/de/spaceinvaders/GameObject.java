package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class GameObject extends StaticObject {
   private final int MOVE_STEPS=8;
   boolean active = true;
    public GameObject(String imagePath, int x, int y, int width, int height){
        super(imagePath, x, y, width, height);
        reset();
    }


    public void reset() {
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
    }

    public void moveDown(Viewport viewport) {
        if ((sprite.getY() + sprite.getHeight() + MOVE_STEPS) < viewport.getScreenHeight())
            translateY(MOVE_STEPS);
    }

    public boolean moveUp(Viewport viewport) {
        boolean couldMove=true;

        if((sprite.getY()-MOVE_STEPS)>0)
            translateY(-MOVE_STEPS);
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
    public boolean moveRight(Viewport viewport) {
        boolean couldMove=true;

        if((sprite.getX()+sprite.getWidth()+MOVE_STEPS)<viewport.getWorldWidth())
            translateX(MOVE_STEPS);
        else couldMove = false;

        return couldMove;
    }

    public void translateY(int i) {
        sprite.translateY(i);
    }

    public void translateX(int i) {
        sprite.translateX(i);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    protected void collision() {
        setActive(false);
    }

    public void checkCollision(GameObject otherObject){
        if(otherObject.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle())){
            otherObject.collision();
            this.collision();
        }
    }
}
