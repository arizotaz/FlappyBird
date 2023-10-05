package com.arizotaz.flappybird.menus;

import com.arizotaz.flappybird.game.Game;
import com.arizotaz.flappybird.game.MainProcess;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.texture.Texture;
import com.arizotaz.lotus.ui.Menu;
import com.arizotaz.lotus.ui.elements.Button;
import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.ui.elements.ElementRenderer;
import com.arizotaz.lotus.ui.elements.TextDisplay;
import com.arizotaz.lotus.window.Window;

public class LoseMenu extends Menu {

	Window window;
	MainProcess process;
	ElementRenderer elm;
	Game game;
	
	Button mainMenu;
	
	int highscore;
	
	public LoseMenu(MainProcess process) {
		this.process = process;
	}
	
	@Override
	public void Open() {
		this.window = Lotus.singleton.WindowManager().GetCurrentWindow();
		
		elm = process.ElementRenderer();
		game = process.Game();
		
		highscore = UserSettings.GetInt("highscore");
		
		CreatePage();
	}

	@Override
	public void Update() {
		if (window.SizeChanged()) {
			CreatePage();
		}
	}

	@Override
	public void Render() {
		Texture tex = window.TextureEngine().GetTexture("background");
		if (tex != null && tex.GLCreated()) {
			float ratio = ((float)tex.Width())/((float)tex.Height());
			RenderObjects.SetColor(255);
			RenderObjects.DrawImage("background",0, 0, window.CanvasHeight()*ratio, window.CanvasHeight(),0);
		} else {
			RenderObjects.DrawImage("background",0, 0, 0,0,0);
		}
	}

	@Override
	public void Events() {
		if (mainMenu.Clicked()) {
			this.menuManager.GoTo(0);
			this.menuManager.ClearHistory();
		}
	}

	@Override
	public void Leave() {
		// TODO Auto-generated method stub

	}
	
	public void CreatePage() {
		elm.ClearCycle();
		
		float renderScale = window.Width()/30;
				
		Text text;
		
		text = new Text("Your bird died a horrible death");
		text.Align(1, 2);
		text.MaxWidth(window.CanvasWidth());
		text.Color(255);
		text.FontSize(renderScale);
		elm.AddElement(new TextDisplay(text, 0,renderScale));
		
		text = new Text("Score: " + game.Points());
		text.Align(1, 2);
		text.Color(255);
		text.FontSize(renderScale/2);
		elm.AddElement(new TextDisplay(text, 0,renderScale/4));
		
		text = new Text("Highscore: " + highscore);
		text.Align(1, 2);
		text.Color(255);
		text.FontSize(renderScale/2);
		elm.AddElement(new TextDisplay(text, 0,-renderScale/4));
		
		mainMenu = Element.Button();
		mainMenu.Set("Back to main menu", 0, -renderScale, renderScale*10, renderScale/1.5f);
		elm.AddElement(mainMenu);
	}

}
