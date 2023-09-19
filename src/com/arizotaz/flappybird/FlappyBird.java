package com.arizotaz.flappybird;

import java.io.File;

import com.arizotaz.gameacc.KeyManager;
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
	private Pipe pipe;
	
	private float renderScaling = 0;
	
	private KeyManager keyManager;
	
	private float parallax = 0;
	
	
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
				
		keyManager = new KeyManager(window);
		
		File file = new File(new File("").getAbsolutePath()+"/keys");
		if (file.exists()) {
			keyManager.LoadFromFile(file);
		} else {
			keyManager.RegisterKey("bird.jump", 32);//Space
			keyManager.RegisterKey("bird.jump", 87);//W
			keyManager.RegisterKey("bird.jump", 265);//Up Arrrow
			keyManager.SaveToFile(file);
		}
		
		
		
		bird = new Bird();
		pipe = new Pipe(15,6,4);

	}

	@Override
	public void Update() {
		window.TextureEngine().Update();
		window.Process();
		
		
		this.parallax += 1f*(window.Time().DeltaTime()/1000);
		
		//Process
		bird.Tick();

		pipe.Tick();
		
		
		//Draw
		
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

		
		pipe.Render();
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
	
	public KeyManager KeyManager() {
		return this.keyManager;
	}

	public Bird Bird() {
		return this.bird;
	}

	public void AddPoint() {
		// TODO Auto-generated method stub
		
	}

	public void Lose() {
		this.QuitProcess();
	}
	
}
