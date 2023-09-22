package com.arizotaz.flappybird.menus;

import com.arizotaz.flappybird.MainProcess;
import com.arizotaz.flappybird.game.Game;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.ui.Menu;
import com.arizotaz.lotus.window.Window;

public class GameRender extends Menu {

	Window window;
	MainProcess process;
	Game game;
	
	
	@Override
	public void Open() {
		this.window = Lotus.singleton.WindowManager().GetCurrentWindow();
		process = (MainProcess) Lotus.singleton.ProcessManager().GetCurrentProces();
		
		process.ElementRenderer().ClearCycle();

		game = process.Game();
		game.Reset();
	}

	@Override
	public void Update() {
		game.Tick();
	}

	@Override
	public void Render() {
		window.ForceWindowDraw();
		game.Render();
	}
	
	@Override
	public void Events() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Leave() {
		// TODO Auto-generated method stub

	}
	
}
