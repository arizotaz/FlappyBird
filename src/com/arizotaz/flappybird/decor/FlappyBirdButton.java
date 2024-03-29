package com.arizotaz.flappybird.decor;

import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.Text;
import com.arizotaz.lotus.ui.elements.Button;
import com.arizotaz.lotus.utils.Tools;

public class FlappyBirdButton extends Button {

	float multiplier = 0;
	
	public FlappyBirdButton() {
		this.font = "pixels";
	}
	
	public void Render() {
		float padding = this.window.RenderScale()*(height/6);
		
		window.RenderObjects().SetColor(255);
		window.RenderObjects().DrawImage("pipe_button", x, y, width + (multiplier*(width/10)), height, 0, true);
		
		if (this.IsHovering()) {
			multiplier += window.Time().DeltaTime()/100;
		} else {
			multiplier -= window.Time().DeltaTime()/100;
		}
		multiplier = Tools.ClampVar(multiplier, 0, 1);
		if (multiplier > 0) {
			this.ForceScreenUpdate();
		}
		
		Text text = new Text(this.text);
		text.FontSize(height-height/6-padding);
		text.Align(1, 1);
		text.SetFont(font);
		text.Color(255);
		text.Render(x, y);
	}
}
