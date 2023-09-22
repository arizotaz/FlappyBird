package com.arizotaz.flappybird;

import java.io.File;

import com.arizotaz.flappybird.decor.FlappyBirdWindowTheme;
import com.arizotaz.flappybird.game.Game;
import com.arizotaz.flappybird.menus.GameRender;
import com.arizotaz.flappybird.menus.LoseMenu;
import com.arizotaz.flappybird.menus.MainMenu;
import com.arizotaz.gameacc.KeyManager;
import com.arizotaz.lotus.Font;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.managers.WindowManager;
import com.arizotaz.lotus.struc.Process;
import com.arizotaz.lotus.texture.TextureEngine;
import com.arizotaz.lotus.theme.windows98.Windows98Theme;
import com.arizotaz.lotus.ui.MenuManager;
import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.ui.elements.ElementRenderer;
import com.arizotaz.lotus.window.Window;

public class MainProcess extends Process {

	private Window window;
	private Game game;
	private KeyManager keyManager;
	
	
	
	private ElementRenderer elm;
	private MenuManager ui;
	
	
	@Override
	public void Start() {
		WindowManager wm = Lotus.singleton.WindowManager();
		window = wm.GetWindow(wm.CreateWindow("Flappy Bird", 1280, 720, this.ID()));
		window.Init();
		Element.CreateWindowParms();
		window.IdleFps(15);
		
		
		if (!UserSettings.Exists("window.fullscreen")) { UserSettings.SetBoolean("window.fullscreen",false); }
		window.Fullscreen(UserSettings.GetBoolean("window.fullscreen"));
		
		if (Lotus.singleton.ArgumentExists("-fullscreen")) {
			window.Fullscreen(true);
		}
		
		if (!UserSettings.Exists("window.theme")) {
			UserSettings.SetInt("window.theme",0);
		}
		
		switch(UserSettings.GetInt("window.theme")) {
		case 0:
			window.SetTheme(new FlappyBirdWindowTheme(window));
			break;
		case 1:
			window.SetTheme(new Windows98Theme(window));
			break;
		
		}
				
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
		TextureEngine.AddToGlobalList("pipe_button", "com/arizotaz/flappybird/assets/pipe_button.png");
				
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
		
		elm = new ElementRenderer();
		
		ui = new MenuManager(window);
		ui.AddMenu(0, new MainMenu());
		ui.AddMenu(10, new GameRender());
		ui.AddMenu(15, new LoseMenu());

		
		Font pixels = new Font("pixels");
		pixels.ImportLocal("com/arizotaz/lotus/resources/fonts/pixels.json");
		Text.defaultFont = pixels.Name();
	
		game = new Game(window,this);
	}

	@Override
	public void Update() {
				
		Element.Reset();
		window.TextureEngine().Update();
		window.Process();
		
		if (window.KeyboardInput().IsPressed(300)) {
			UserSettings.SetBoolean("window.fullscreen",!window.fullscreen);
			window.Fullscreen(!window.fullscreen);
		}
		
		elm.Update();
		ui.Update();
		elm.Interact();
		
		ui.Render();
		elm.Render();
		
		ui.Events();
				
		window.Render(elm.HasChanged());
		if (window.WantsToClose()) {
			this.QuitProcess();
		}
	}
	
	@Override
	public void Exit() {
		window.CleanUp();
	}
	
	public KeyManager KeyManager() {
		return this.keyManager;
	}

	public Game Game() {
		return game;
	}
	
	public ElementRenderer ElementRenderer() {
		return this.elm;
	}

	public MenuManager MenuManager() {
		return this.ui;
	}
	
}
