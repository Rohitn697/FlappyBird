package com.rohit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;

	int flapstate = 0;
	float birdY = 0;

	float gravity = 2;
	float velocity =0;
	int gameState =0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");

		birdY = (Gdx.graphics.getHeight())/2- birds[0].getHeight()/2;
	}

	@Override
	public void render () {
		if(gameState != 0) {

			if (Gdx.input.justTouched()) {
				velocity = -30;
			}

			if (birdY>0 || velocity<0) {
				velocity += gravity;
				birdY -= velocity;
			}
		}
		else {
			if (Gdx.input.justTouched()) {
			gameState=1;
			}
		}

		if (flapstate == 0) {
			flapstate = 1;
		}
		else {
			flapstate = 0;
		}
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(birds[flapstate], (Gdx.graphics.getWidth()) / 2 - birds[flapstate].getWidth() / 2, birdY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
