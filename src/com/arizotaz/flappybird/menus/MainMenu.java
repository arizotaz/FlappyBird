package com.arizotaz.flappybird.menus;

import com.arizotaz.flappybird.MainProcess;
import com.arizotaz.flappybird.decor.FlappyBirdWindowTheme;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.texture.Texture;
import com.arizotaz.lotus.theme.windows98.Windows98Theme;
import com.arizotaz.lotus.ui.Menu;
import com.arizotaz.lotus.ui.elements.Button;
import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.ui.elements.ElementRenderer;
import com.arizotaz.lotus.ui.elements.Panel;
import com.arizotaz.lotus.ui.elements.TextDisplay;
import com.arizotaz.lotus.window.Window;

public class MainMenu extends Menu {

	private Window window;
	private MainProcess process;
	private ElementRenderer elm;
	private Button startButton;
	private Button optionsButton;
	private Button infoButton;
	private Button quitButton;
	
	private boolean showOptions = false;
	private float pageScale = 0;
	
	private Button basicTheme;
	private Button win98Theme;

	private Button closeOptions;
	
	
	@Override
	public void Open() {
		this.window = Lotus.singleton.WindowManager().GetCurrentWindow();
		process = (MainProcess) Lotus.singleton.ProcessManager().GetCurrentProces();
		
		elm = process.ElementRenderer();
		
		CreatePage();
	}

	
	private void CreatePage() {
		pageScale = window.CanvasWidth()/30;
		
		elm.ClearCycle();
		
		Text text;
		text = new Text("Flappy Bird");
		text.Align(1, 2);
		text.MaxWidth(window.CanvasWidth());
		text.Color(255);
		text.FontSize(pageScale*2);
		elm.AddElement(new TextDisplay(text, 0,pageScale));
		
		float bw = 8;
		float bh = 1f;
		float padding = 0.5f*pageScale;
		startButton = Element.Button();
		startButton.Set("Start Game",0, 0, pageScale*bw, pageScale*bh);
		elm.AddElement(startButton);
		
		
		optionsButton = Element.Button();
		optionsButton.Set("Options",0, (-pageScale*bh-padding), pageScale*bw, pageScale*bh);
		elm.AddElement(optionsButton);
		
		infoButton = Element.Button();
		infoButton.Set("Info",0, (-pageScale*bh-padding)*2, pageScale*bw, pageScale*bh);
		elm.AddElement(infoButton);
		
		quitButton = Element.Button();
		quitButton.Set("Quit",0, (-pageScale*bh-padding)*3, pageScale*bw, pageScale*bh);
		elm.AddElement(quitButton);
		
		if (showOptions) {
			CreateOptionsPage();
		}
		
	}
	
	public void CreateOptionsPage() {		
		elm.AddElement(new Panel(0,0,window.CanvasWidth(),window.CanvasHeight(),0,0,140));
		
		Text text;
		text = new Text("Window Theme");
		text.Align(1, 2);
		text.MaxWidth(window.CanvasWidth());
		text.Color(255);
		text.FontSize(pageScale);
		elm.AddElement(new TextDisplay(text, 0,pageScale));
		
		float bw = pageScale*10, bh = pageScale/1.5f;
		
		basicTheme = Element.Button();
		win98Theme = Element.Button();
		basicTheme.Set("Basic", -bw/2, 0, bw, bh);
		win98Theme.Set("Windows 98", bw/2, 0, bw, bh);
		elm.AddElement(basicTheme);
		elm.AddElement(win98Theme);
		
		
		bh = pageScale/2;
		closeOptions = Element.Button();
		closeOptions.Set("Close", 0, -window.CanvasHeight()/2+bh/2, window.CanvasWidth(), bh);
		elm.AddElement(closeOptions);
		
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
			RenderObjects.DrawImage("background",0, 0, 1,1,0);
		}
	}
	
	@Override
	public void Events() {
		
		
		if (this.showOptions) {
			if (basicTheme.Clicked()) {
				window.Translate().Set(0, 0);
				window.RenderOffset().Set(0, 0);
				window.SetWindowSize((int)window.Width(), (int)window.Height()-50);
				window.SetTheme(new FlappyBirdWindowTheme(window));
				CreatePage();
				UserSettings.SetInt("window.theme", 0);
			}
			if (win98Theme.Clicked()) {
				window.SetWindowSize((int)window.Width(), (int)window.Height()+50);
				window.SetTheme(new Windows98Theme(window));
				CreatePage();
				UserSettings.SetInt("window.theme", 1);
			}
			if (closeOptions.Clicked()) {
				ToggleOptionsMenu();
			}
		}
		
		if (startButton.Clicked()) {
			this.menuManager.GoTo(10);
		}
		if (optionsButton.Clicked()) {
			ToggleOptionsMenu();
		}
		if (infoButton.Clicked()) {
		}
		if (quitButton.Clicked()) {
			process.QuitProcess();
		}
		
		
	}
	
	private void ToggleOptionsMenu() {
		this.showOptions = !this.showOptions;
		CreatePage();
	}

	@Override
	public void Leave() {
		// TODO Auto-generated method stub

	}

}
