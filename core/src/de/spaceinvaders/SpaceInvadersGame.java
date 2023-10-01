package de.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SpaceInvadersGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private boolean gameLost;
	private boolean gameWon;

	private Viewport viewport;
	private OrthographicCamera camera;

	private PlayerCharacter playerCharacter;
	private Missile playerMissile;

	static final private String ASSET_DIRECTORY = "assets";
	private List<InvaderCharacter> npcList = new LinkedList<>();

	private int invaderDirection = 1;
	private boolean moveDown = false;

	@Override
	public void create () {
		setupDisplay();
		playerCharacter = new PlayerCharacter(ASSET_DIRECTORY + "/player.png", 160, 8, 16, 16);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				npcList.add(new InvaderCharacter(ASSET_DIRECTORY + "/invader.png", j * 24 + 8, 160-(i*16), 16, 16));
			}
		}

		playerMissile = new Missile(ASSET_DIRECTORY + "/missile.png", 160, 10, 16, 16);
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

	@Override
	public void render () {
		if (!gameWon && !gameLost) {
			processMovement();
		} else {
			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
				gameWon = false;
				gameLost = false;
				for (NonPlayerCharacter npc : npcList){
					npc.reset();
					npc.setActive(true);
				}
			}

		}

		drawScreen();
	}

	private void drawScreen() {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		playerCharacter.getSprite().draw(batch);

		if(playerMissile.isActive()){
			playerMissile.getSprite().draw(batch);
		}
		for (NonPlayerCharacter npc : npcList){
			if(npc.isActive()){
				npc.getSprite().draw(batch);
			}
		}

		batch.end();
	}

	private void processMovement() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerCharacter.moveLeft(viewport);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerCharacter.moveRight(viewport);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)&& !playerMissile.isActive()) {
			playerMissile.sprite.setX(playerCharacter.sprite.getX());
			playerMissile.setActive(true);
		}

		if(playerMissile.isActive()){
			for (NonPlayerCharacter npc : npcList){
				if(npc.isActive())
					npc.checkCollision(playerMissile);
			}
			if(npcList.stream().filter(npc -> npc.isActive()).count()==0){
				 gameWon = true;
			}
		}

		if(playerMissile.isActive()){
			playerMissile.move(viewport);
		}

		for(InvaderCharacter npc: npcList){
			npc.setDirection(invaderDirection);
			if(npc.isActive() && npc.getSprite().getY()<playerCharacter.sprite.getY()+(playerCharacter.sprite.getHeight())){
				gameLost = true;
			}else
			if(moveDown){
				npc.moveDown();
			}
		}
		moveDown = false;
		if(invaderDirection>0){
			ListIterator<InvaderCharacter> npcIterator = npcList.listIterator(npcList.size());
			while(npcIterator.hasPrevious()){
				NonPlayerCharacter npc = npcIterator.previous();
				if(npc.isActive()){
					if(!npc.move(viewport)){
						invaderDirection = -1;
						moveDown= true;
						break;
					}
				}
			}
		}else{
			Iterator<InvaderCharacter> npcIterator = npcList.iterator();
			while(npcIterator.hasNext()){
				NonPlayerCharacter npc = npcIterator.next();
				if(npc.isActive()){
					if(!npc.move(viewport)){
						invaderDirection = 1;
						moveDown= true;
						break;
					}
				}
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}

















