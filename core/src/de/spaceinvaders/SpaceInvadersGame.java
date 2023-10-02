package de.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

public class SpaceInvadersGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private boolean gameLost;
    private boolean gameWon;

    private Viewport viewport;
    private OrthographicCamera camera;

    private PlayerCharacter playerCharacter;
    private Missile playerMissile;
    private Bomb bomb;

    static final private String ASSET_DIRECTORY = "assets";
    private final List<InvaderCharacter> npcList = new LinkedList<>();

    private int invaderDirection = 1;
    private boolean moveDown = false;

    private final static int invaderColumns = 8;
    private final static int invaderRows = 5;

    @Override
    public void create() {
        setupDisplay();
        playerCharacter = new PlayerCharacter(ASSET_DIRECTORY + "/player.png", viewport, 160, 8, 16, 16);

        bomb = new Bomb(ASSET_DIRECTORY + "/bomb.png", viewport, 16, 16);

        for (int i = 0; i < invaderRows; i++) {
            for (int j = 0; j < invaderColumns; j++) {
                npcList.add(new InvaderCharacter(ASSET_DIRECTORY + "/invader.png", viewport, j * 24 + 8, 160 - (i * 16), 16, 16));
            }
        }

        playerMissile = new Missile(ASSET_DIRECTORY + "/missile.png", viewport, 16, 16);
        playerMissile.setActive(false);

    }

    private void setupDisplay() {
        camera = new OrthographicCamera(320, 200);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new StretchViewport(camera.viewportWidth, camera.viewportHeight, camera);
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void dropBomb() {

        List<InvaderCharacter> invaderCharactersAbleToDropBomb = new ArrayList<>();
        for (int row = invaderRows-1; row >= 0; row--) {
            for (int column = invaderColumns-1; column >= 0; column--) {
                int currentIndex = (row * invaderColumns) + column;
                InvaderCharacter invaderCharacter = npcList.get(currentIndex);
                if(invaderCharacter.isActive() ){
                    if (currentIndex + invaderColumns >= npcList.size()) {
                        invaderCharactersAbleToDropBomb.add(invaderCharacter);
                    }else{
                        int lineOfSightIndex=currentIndex+invaderColumns;
                        while(lineOfSightIndex<npcList.size()){
                            InvaderCharacter currentInvader = npcList.get(lineOfSightIndex);
                            if(currentInvader.isActive()){
                               break;
                            }
                            lineOfSightIndex = lineOfSightIndex+invaderColumns;
                        }
                        if(lineOfSightIndex>npcList.size()){
                            invaderCharactersAbleToDropBomb.add(invaderCharacter);
                        }
                    }
                }
            }
        }

        if (!invaderCharactersAbleToDropBomb.isEmpty()) {
            InvaderCharacter invaderCharacter;
            Random randomNum = new Random();
            if (invaderCharactersAbleToDropBomb.size() - 1 > 0) {
                int index = randomNum.nextInt(invaderCharactersAbleToDropBomb.size() );
                invaderCharacter = invaderCharactersAbleToDropBomb.get(index);
            } else {
                invaderCharacter = invaderCharactersAbleToDropBomb.get(0);
            }

            bomb.setPosition(invaderCharacter.getSprite().getX(), invaderCharacter.getSprite().getY());
            bomb.setActive(true);
        }
    }

    @Override
    public void render() {
        if (!gameWon && !gameLost) {
            processMovement();
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                gameWon = false;
                gameLost = false;
                for (NonPlayerCharacter npc : npcList) {
                    npc.reset();
                    npc.setActive(true);
                }
                playerCharacter.reset();
            }

        }

        drawScreen();
    }

    private void drawScreen() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        playerCharacter.getSprite().draw(batch);

        if (playerMissile.isActive()) {
            playerMissile.getSprite().draw(batch);
        }

        if (bomb.isActive()) {
            bomb.getSprite().draw(batch);
        }
        for (NonPlayerCharacter npc : npcList) {
            if (npc.isActive()) {
                npc.getSprite().draw(batch);
            }
        }

        batch.end();
    }

    private void processMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerCharacter.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerCharacter.moveRight();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !playerMissile.isActive()) {
            playerMissile.sprite.setX(playerCharacter.sprite.getX());
            playerMissile.setActive(true);
        }

        if (playerMissile.isActive()) {
            for (NonPlayerCharacter npc : npcList) {
                if (npc.isActive())
                    npc.checkCollision(playerMissile);
            }
            if (npcList.stream().noneMatch(GameObject::isActive)) {
                gameWon = true;
            }
        }

        if (playerMissile.isActive()) {
            playerMissile.move();
        }

        if (!bomb.isActive()) {
            dropBomb();
//            bomb.isActive();
        }

        if (bomb.isActive()) {
            playerCharacter.checkCollision(bomb,bomb.getBoundingRectangle());

            if(playerCharacter.getLives()==0){
                gameLost=true;
            }else{
                bomb.move();
            }
        }

        for (InvaderCharacter npc : npcList) {
            npc.setDirection(invaderDirection);
            if (npc.isActive() && npc.getSprite().getY() < playerCharacter.sprite.getY() + (playerCharacter.sprite.getHeight())) {
                gameLost = true;
            } else if (moveDown) {
                npc.moveDown();
            }
        }
        moveDown = false;
        if (invaderDirection > 0) {
            ListIterator<InvaderCharacter> npcIterator = npcList.listIterator(npcList.size());
            while (npcIterator.hasPrevious()) {
                NonPlayerCharacter npc = npcIterator.previous();
                if (npc.isActive()) {
                    if (!npc.move()) {
                        invaderDirection = -1;
                        moveDown = true;
                        break;
                    }
                }
            }
        } else {
            for (NonPlayerCharacter npc : npcList) {
                if (npc.isActive()) {
                    if (!npc.move()) {
                        invaderDirection = 1;
                        moveDown = true;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

















