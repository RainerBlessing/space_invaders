package de.spaceinvaders;

import com.badlogic.gdx.utils.viewport.Viewport;

public class InvaderCharacter  extends NonPlayerCharacter{
    public static final int SPEED = 1;
    private int moveX = SPEED;

    public InvaderCharacter(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    public boolean move(Viewport viewport) {
        boolean couldMove;
        if(moveX>0){
           if(sprite.getX()+sprite.getWidth()<viewport.getWorldWidth()) {
                sprite.setX(sprite.getX() + moveX);
                couldMove = true;
            }else{
                couldMove = false;
           }
        }else{
            if(sprite.getX()+moveX>0) {
                sprite.setX(sprite.getX() + moveX);
                couldMove = true;
            }else{
                couldMove = false;
            }
        }

        return couldMove;
    }

    public void moveDown() {
        if(sprite.getY()>0){
            sprite.setY(sprite.getY()-sprite.getHeight());
        }
    }

    public void setDirection(int invaderDirection) {
        if(invaderDirection<0){
            this.moveX = -SPEED;
        }else{
            this.moveX = SPEED;
        }
    }
}
