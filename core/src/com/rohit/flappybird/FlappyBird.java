package com.rohit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;
	Texture topTube,bottomTube;

	Random randomgenerator;
	float maxtubeoffset;

	int flapstate = 0;
	float birdY = 0;

	float gravity = 2;
	float velocity =0;
	int gameState =0;

	float tubevelocity = 4;
	int numberofTubes = 4;
	float[] tubeX = new float[numberofTubes];
	float[] tubeoffset = new float[numberofTubes];
	float distancebetweenTubes;

	float gap =400;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube=new Texture("toptube.png");
		bottomTube=new Texture("bottomtube.png");
		randomgenerator = new Random();

		birdY = (Gdx.graphics.getHeight())/2- birds[0].getHeight()/2;
		maxtubeoffset = Gdx.graphics.getHeight()/2 - gap/2-100;
		distancebetweenTubes = Gdx.graphics.getWidth()* 1/2;

		for (int i =0 ; i<numberofTubes ; i++){
			tubeoffset[i] = (randomgenerator.nextFloat()-0.5f)*(Gdx.graphics.getHeight()/2 - gap - 100);
			tubeX[i] = Gdx.graphics.getWidth()/2-topTube.getWidth()/2 + i*distancebetweenTubes;
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState != 0) {
			if (Gdx.input.justTouched()) {

				velocity = -30;


			}
			for (int i =0;i<numberofTubes;i++) {
				if (tubeX[i]<-topTube.getWidth()){
					tubeX[i]+=distancebetweenTubes *numberofTubes;
				}
				else {
					tubeX[i] = tubeX[i] - tubevelocity;
				}
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeoffset[i]);
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

		batch.draw(birds[flapstate], (Gdx.graphics.getWidth()) / 2 - birds[flapstate].getWidth() / 2, birdY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
