package com.arizotaz.flappybird.game;

import java.io.File;

import com.arizotaz.flappybird.menus.GameRender;
import com.arizotaz.flappybird.menus.LoseMenu;
import com.arizotaz.flappybird.menus.MainMenu;
import com.arizotaz.gameacc.KeyManager;
import com.arizotaz.lotus.Font;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.texture.TextureEngine;
import com.arizotaz.lotus.ui.MenuManager;
import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.ui.elements.ElementRenderer;
import com.arizotaz.lotus.window.Window;

public class MainProcess {
	
	private Window window;
	private Game game;
	private KeyManager keyManager;
	
	private ElementRenderer elm;
	private MenuManager ui;
	
	public MainProcess(Window window) {
		this.window = window;
		Element.CreateWindowParms();
		
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
		ui.AddMenu(0, new MainMenu(this));
		ui.AddMenu(10, new GameRender(this));
		ui.AddMenu(15, new LoseMenu(this));

		
		Font pixels = new Font("pixels");
		pixels.ImportLocal("com/arizotaz/lotus/resources/fonts/pixels.json");
		Text.defaultFont = pixels.Name();
	
		game = new Game(window,this);
	}
	
	public void Update() {
		if (window.KeyboardInput().IsPressed(300)) {
			window.SetFullScreen(!window.InBorderLess());
			UserSettings.SetBoolean("window.fullscreen",window.NotWindowed());

		}
				
		elm.Update();
		ui.Update();
		elm.Interact();
	}
	
	public void Render() {
		ui.Render();
		elm.Render();
	}
	public void Events() {
		ui.Events();
	}
	
	public void Exit() {
		
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
