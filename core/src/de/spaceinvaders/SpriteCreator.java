package de.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class SpriteCreator {
    private static final Map<String, Texture> textureCache = new HashMap<>();
    public Sprite create(String imagePath, int width, int height) {
        Texture texture = getTexture(imagePath, width, height);

        return new Sprite(texture);
    }

    private Texture getTexture(String imagePath, int width, int height) {
        String textureCacheID = imagePath + width + height;
        Texture texture = textureCache.get(textureCacheID);
        if(texture==null){
            texture = resizeTexture(imagePath, width, height);
            textureCache.put(textureCacheID, texture);
        }

        return texture;
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

    public void dispose(String textureID)
    {
        Texture texture = textureCache.get(textureID);
        if(texture!=null){
            texture.dispose();
        }
    }
}
