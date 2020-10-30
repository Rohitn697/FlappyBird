package com.rohit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	BitmapFont font;

	Texture[] birds;
	Texture topTube,bottomTube;
	ShapeRenderer shapeRenderer ;
	Random randomgenerator;
	float maxtubeoffset;
	Texture gameover;
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
	Circle birdCircle;
	Rectangle[] topRectangle;
	Rectangle[] bottomRectangle;

	int score = 0;
	int scoringTube = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		gameover = new Texture("gameover.png");
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		topRectangle = new Rectangle[numberofTubes];
		bottomRectangle = new Rectangle[numberofTubes];

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");

		topTube=new Texture("toptube.png");
		bottomTube=new Texture("bottomtube.png");
		randomgenerator = new Random();


		maxtubeoffset = Gdx.graphics.getHeight()/2 - gap/2-100;
		distancebetweenTubes = Gdx.graphics.getWidth()* 1/2;
		startGame();

	}
	public void startGame(){
		birdY = (Gdx.graphics.getHeight())/2- birds[0].getHeight()/2;
		for (int i =0 ; i<numberofTubes ; i++){
			tubeoffset[i] = (randomgenerator.nextFloat()-0.5f)*(Gdx.graphics.getHeight()/2 - gap - 100);
			tubeX[i] = Gdx.graphics.getWidth()/2-topTube.getWidth()/2+ Gdx.graphics.getWidth() + i*distancebetweenTubes;

			topRectangle[i] = new Rectangle();
			bottomRectangle[i] = new Rectangle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState == 1) {
			if (tubeX[scoringTube]<Gdx.graphics.getWidth()/2){
				score++;
				if (scoringTube<numberofTubes-1){
					scoringTube++;
				}
				else {
					scoringTube=0;
				}
			}
			if (Gdx.input.justTouched()) {

				velocity = -30;


			}
			for (int i =0;i<numberofTubes;i++) {
				if (tubeX[i]<-topTube.getWidth()){
					tubeX[i]+=distancebetweenTubes *numberofTubes;
					tubeoffset[i] = (randomgenerator.nextFloat()-0.5f)*(Gdx.graphics.getHeight()/2 - gap - 100);
				}
				else {
					tubeX[i] = tubeX[i] - tubevelocity;
				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeoffset[i]);

				topRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],topTube.getWidth(),topTube.getHeight());
				bottomRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeoffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			}
			if (birdY>0) {
				velocity += gravity;
				birdY -= velocity;
			}
			else {
				gameState=2;
			}
		}
		else if (gameState==0){
			if (Gdx.input.justTouched()) {
			gameState=1;
			}
		}
		else if (gameState==2){
			batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2);
			if (Gdx.input.justTouched()) {
				gameState=1;
				startGame();
				score=0;
				scoringTube=0;
				velocity=0;
			}
		}

		if (flapstate == 0) {
			flapstate = 1;
		}
		else {
			flapstate = 0;
		}

		batch.draw(birds[flapstate], (Gdx.graphics.getWidth()) / 2 - birds[flapstate].getWidth() / 2, birdY);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

		birdCircle.set(Gdx.graphics.getWidth()/2,birdY + birds[flapstate].getHeight()/2,birds[flapstate].getWidth()/2);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		for (int i =0;i<numberofTubes;i++){
		//	shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],topTube.getWidth(),topTube.getHeight());
		//	shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeoffset[i],bottomTube.getWidth(),bottomTube.getHeight());

			if (Intersector.overlaps(birdCircle,topRectangle[i]) || Intersector.overlaps(birdCircle,bottomRectangle[i])){
			gameState = 2;
			}
		}
		// shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
