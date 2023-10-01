package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class GameObject extends StaticObject {
   protected static final int MOVE_STEPS=8;
   boolean active = true;
    public GameObject(String imagePath, int x, int y, int width, int height){
        super(imagePath, x, y, width, height);
        reset();
    }


    public void reset() {
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
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
