package com.arizotaz.flappybird.game;

import java.util.ArrayList;

import com.arizotaz.flappybird.MainProcess;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.texture.TextureEngine;
import com.arizotaz.lotus.utils.Tools;
import com.arizotaz.lotus.window.Window;

public class Game {
	private Window window;
	private Bird bird;
	private MainProcess process;

	private float renderScaling = 0;
	private float parallax = 0;
	private float xDistance = 1;
	
	private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
	private int pipeX = 10;
	
	private float gameSpeed = 1;
	private int points = 0;
	
	public Game(Window window, MainProcess flappyBird) {
		this.window = window;
		this.process = flappyBird;
		
		Reset();
	}
	
	public void Reset() {
		bird = new Bird();
		renderScaling = 0;
		parallax = 0;
		xDistance = 0;
		pipes = new ArrayList<Pipe>();
		pipeX = 10;
		gameSpeed = 0.5f;
		points = 0;
	}
	public void Tick() {
		if (bird.Freed()) {
			this.parallax += (gameSpeed)*(window.Time().DeltaTime()/1000);
			this.xDistance += (gameSpeed*2)*(window.Time().DeltaTime()/1000);
			
			gameSpeed += 0.1f*(window.Time().DeltaTime()/1000);
		}
		
		
		bird.Tick();
		
		
		for (int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			if (pipe.HasPast()) {
				pipes.remove(i);
				i--;
			} else {
				pipe.Tick();
			}
		}
		
		
		while (pipes.size() < 10) {
			float y = Tools.ClampVar((float) (Math.random()*7+3), 3, 10);
			pipes.add(new Pipe(pipeX,y,(float) (Math.random()*3+3)));
			pipeX += 8;
		}
		
	}
	public void Render() {
		RenderObjects.SetColor(255);
		int bgsize = 512;
		if (TextureEngine.GetGlobalTexture("backgrouund") != null) {
			bgsize = TextureEngine.GetGlobalTexture("backgrouund").Width();
		}
		RenderObjects.DrawImageFromSheet("background",0, 0, window.CanvasWidth(), window.CanvasHeight(), parallax, 0, 1, bgsize, 0);
		
		//window.renderscale is the windows DPI
		
		renderScaling = window.CanvasHeight()/15;

		
		//Create padding
		window.Translate().Move(-window.CanvasWidth()/2+renderScaling, -window.CanvasHeight()/2+renderScaling/2);
		
		
		for (int i = 0; i < pipes.size(); i++) {
			pipes.get(i).Render();
		}
		bird.Render();
		
		
		
		
		window.Translate().Set(0, 0);
		
		if (!bird.Freed()) {
			Text text = new Text("Jump to Start");
			text.Align(0,2);
			text.Color(255);
			text.FontSize(this.RenderScale()/2);
			text.Render(-window.CanvasWidth()/2+this.RenderScale()/2,this.RenderScale());
		}
		
		
		Text text = new Text("Points: " + this.points);
		text.Align(0,0);
		text.Color(255);
		text.FontSize(this.RenderScale()/3);
		text.Render(-window.CanvasWidth()/2, window.CanvasHeight()/2);
	}
	
	public float RenderScale() {
		return this.renderScaling;
	}
	public float xDistance() {
		return this.xDistance;
	}

	public Bird Bird() {
		return bird;
	}
	
	public void AddPoint() {
		points++;
	}

	public void Lose() {
		
		if (!UserSettings.Exists("highscore")) {
			UserSettings.SetInt("highscore",0);
		}
		int highScore = UserSettings.GetInt("highscore");
		highScore = Math.round(Tools.SetSmallest(highScore, points));
		UserSettings.SetInt("highscore",highScore);
		
		process.MenuManager().GoTo(15);
	}

	public int Points() {
		return points;
	}
	
}
