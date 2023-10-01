package de.spaceinvaders;

public class PlayerCharacter extends GameObject {
    public PlayerCharacter(String imagePath, int x, int y, int width, int height) {
        super(imagePath, x, y, width, height);
    }

    final private int SPEED = 10;

    public void move(int direction) {
        if(direction>0){
            y -= direction * SPEED;
        }else if(direction<0){
            y += direction * SPEED;
        }

    }
}
