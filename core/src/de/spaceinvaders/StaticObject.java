package de.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StaticObject {
    protected final Texture img;
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    protected final Sprite sprite;

    public StaticObject(String imagePath, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.img = resizeTexture(imagePath,width,height);
        this.sprite = new Sprite(getImg());
    }

    public Texture getImg() {
        return img;
    }

    private Texture resizeTexture(String imagePath, int width, int height){
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal(imagePath));
        Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());

        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );

        Texture texture = new Texture(pixmap100);

        pixmap200.dispose();
        pixmap100.dispose();

        return texture;
    }

    public void dispose() {
        img.dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }
}
