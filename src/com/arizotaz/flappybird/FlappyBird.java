package com.arizotaz.flappybird;

import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.managers.WindowManager;
import com.arizotaz.lotus.struc.Process;
import com.arizotaz.lotus.texture.TextureEngine;
import com.arizotaz.lotus.window.Window;

public class FlappyBird extends Process {

	private Window window;
	
	private Bird bird;
	
	private float renderScaling = 0;
	
	@Override
	public void Start() {
		WindowManager wm = Lotus.singleton.WindowManager();
		window = wm.GetWindow(wm.CreateWindow("Flappy Bird", 1280, 720, this.ID()));
		window.Init();
				
		try {
			window.SetIcon("com/arizotaz/flappybird/assets/bird.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//window.SetTheme(new Windows98Theme(window));
		TextureEngine.AddToGlobalList("background", "com/arizotaz/flappybird/assets/background.png");
		TextureEngine.AddToGlobalList("bird", "com/arizotaz/flappybird/assets/bird.png");
		TextureEngine.AddToGlobalList("pipe", "com/arizotaz/flappybird/assets/pipe_up.png");
		
		bird = new Bird();
	}

	@Override
	public void Update() {
		window.TextureEngine().Update();
		window.Process();
		
		
		//Process
		bird.Tick();

		
		
		//Draw
		
		RenderObjects.SetColor(255);
		RenderObjects.DrawImage("background",0, 0, window.CanvasWidth(), window.CanvasHeight(), 0);
		
		//window.renderscale is the windows DPI
		
		renderScaling = window.CanvasWidth()/20;

		
		//Create padding
		window.Translate().Move(-window.CanvasWidth()/2+renderScaling, -window.CanvasHeight()/2+renderScaling/2);

		
		
		bird.Render();
		
		
		
		
		window.Translate().Set(0, 0);
		
		Text text = new Text("Y: " + bird.Position().y());
		text.Align(0,0);
		text.Color(255,0,0);
		text.FontSize(24*window.RenderScale());
		text.Render(-window.CanvasWidth()/2, window.CanvasHeight()/2);
		

		
		//End Draw
		
		window.Render(true);
		if (window.WantsToClose()) {
			this.QuitProcess();
		}
	}
	
	@Override
	public void Exit() {
		window.CleanUp();
	}

	
	public String Test() {
		return "Hello";
	}
	
	public float RenderScale() {
		return this.renderScaling;
	}
	public float RS() {
		return RenderScale();
	}
	
}
