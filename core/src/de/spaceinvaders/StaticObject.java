package de.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class StaticObject {
    protected final int x;
    protected final int y;
    protected final Sprite sprite;

    private final SpriteCreator spriteCreator = new SpriteCreator();
    private final String textureID;

    protected StaticObject(String imagePath, int x, int y, int width, int height){
        this.x = x;
        this.y = y;

        this.textureID = (imagePath+width+height);
        this.sprite = spriteCreator.create(imagePath,width,height);
    }

    public void dispose() {
        spriteCreator.dispose(textureID);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
