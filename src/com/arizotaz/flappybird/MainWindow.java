package com.arizotaz.flappybird;

import com.arizotaz.flappybird.decor.FlappyBirdWindowTheme;
import com.arizotaz.flappybird.game.MainProcess;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.UserSettings;
import com.arizotaz.lotus.LSDT.LotusSystemDebugTool;
import com.arizotaz.lotus.managers.WindowManager;
import com.arizotaz.lotus.struc.Process;
import com.arizotaz.lotus.theme.windows98.Windows98WindowTheme;
import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.window.Window;
import com.arizotaz.lotus.window.theme.abyss.AbyssWindowTheme;

public class MainWindow extends Process {

	private Window window;
	private MainProcess main;
	
	@Override
	public void Start() {
		WindowManager wm = Lotus.singleton.WindowManager();
		window = wm.GetWindow(wm.CreateWindow("Flappy Bird", 1280, 720, this.ID()));
		window.Init();
		Element.CreateWindowParms();
		window.IdleFps(15);
		window.LimitFPS(165);

		
		
		
		if (Lotus.singleton.ArgumentExists("-fullscreen")) {
			window.SetFullScreen(true);
		} else {
			if (UserSettings.GetBoolean("window.fullscreen")) { window.SetFullScreen(UserSettings.GetBoolean("window.fullscreen")); } 
		}
		
		window.Process();
		window.RenderObjects().SetColor(40);
		window.RenderObjects().DrawRect(0, 0, window.CanvasWidth(), window.CanvasHeight(), 0);
		window.Render(true);
				
		if (!UserSettings.Exists("window.theme")) {
			UserSettings.SetInt("window.theme",0);
		}
		
		switch(UserSettings.GetInt("window.theme")) {
		case 0:
			window.SetTheme(new FlappyBirdWindowTheme(window));
			break;
		case 1:
			window.SetTheme(new Windows98WindowTheme(window));
			break;
		case 2:
			window.SetTheme(new AbyssWindowTheme(window));
			break;
		}
				
		try {
			window.SetIcon("com/arizotaz/flappybird/assets/bird.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//window.SetTheme(new Windows98Theme(window));
		main = new MainProcess(window);
		
		
		Lotus.singleton.AddProcess(new LotusSystemDebugTool());
	}

	@Override
	public void Update() {
				
		Element.Reset();
		window.TextureEngine().Update();
		window.Process();
		
		if (window.drawToWindow > 0) {
			window.RenderObjects().SetColor(40);
			window.RenderObjects().DrawRect(0, 0, window.CanvasWidth(), window.CanvasHeight(), 0);
		}
		
		main.Update();
		main.Render();
		main.Events();
				
		window.Render(main.ElementRenderer().HasChanged());
		if (window.WantsToClose()) {
			this.QuitProcess();
		}
	}
	
	@Override
	public void Exit() {
		main.Exit();
		window.CleanUp();
	}
	
}
