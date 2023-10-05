package com.arizotaz.flappybird.menus;

import com.arizotaz.flappybird.game.Game;
import com.arizotaz.flappybird.game.MainProcess;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.ui.Menu;
import com.arizotaz.lotus.window.Window;

public class GameRender extends Menu {

	Window window;
	MainProcess process;
	Game game;
	
	public GameRender(MainProcess process) {
		this.process = process;
	}
	@Override
	public void Open() {
		this.window = Lotus.singleton.WindowManager().GetCurrentWindow();
		
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
