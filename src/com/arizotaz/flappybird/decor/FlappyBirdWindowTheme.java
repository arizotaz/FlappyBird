package com.arizotaz.flappybird.decor;

import com.arizotaz.lotus.ui.elements.Element;
import com.arizotaz.lotus.window.Window;
import com.arizotaz.lotus.window.WindowTheme;

public class FlappyBirdWindowTheme extends WindowTheme {

	public FlappyBirdWindowTheme(Window window) {
		super(window);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Init() {
		Element.CreateWindowParms();
		window.Border(true);
		window.RenderOffset().Set(0, 0);
		
		Element.SetDefaultButton(new FlappyBirdButton());
	}

	@Override
	public void SetupSpace() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void RenderBackground() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RenderForeground() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Restore() {
		// TODO Auto-generated method stub
		
	}

}
