package com.arizotaz.flappybird;

import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.struc.Vector2;
import com.arizotaz.lotus.utils.Tools;
import com.arizotaz.lotus.window.KeyboardInput;
import com.arizotaz.lotus.window.Window;

public class Bird {

	//Flappy Bird Host Process
	private FlappyBird process;
	//Window Instance
	private Window window;
	//Window keyboard Listener
	private KeyboardInput kbi;
	//Is key down?
	private boolean keyDown = false;

	
	//Size of the bird on screen
	private float birdSize;
	
	
	//position and force values
	private float x = 0, y = 0;
	private float forceX = 0,forceY = 0;
	private float gravity = 9.5f*500f;
	
	
	
	public Bird() {
		//Store the flappybird host process
		process = (FlappyBird) Lotus.singleton.ProcessManager().GetCurrentProces();
		
		//Grab the current window
		window = Lotus.singleton.WindowManager().GetCurrentWindow();
		
		//Get input from that window
		kbi = window.KeyboardInput();
				
		x = 0;
		y = 700;
	}
	
	//Game Processing
	public void Tick() {
		
		//Store deltaTime so we do not need to do the calculation over and over again
		float deltaTime = window.Time().DeltaTime()/1000;
		
		//Apply Gravity with delta time so only a certain amount of gravity is applied at each frame
		forceY -= gravity*deltaTime;
		
		
		//Keyboard input
		if (kbi.IsKeyDown(32) || kbi.IsKeyDown(265)) {
			// If keys are not down
			if (!keyDown) {
				
				Jump();
			}
			keyDown = true;
		} else {
			
			//Keys are no longer down
			keyDown = false;
		}
		
		
		// Add Forces With deltatime so that force is applied evenly between frames
		x += forceX * deltaTime;
		y += forceY * deltaTime;
		
		
		// Set the smallest x value
		y = Tools.SetSmallest(y, 0);
		//if bird is on the floor, clear force value
		if (y <= 0) { forceY = 0; }
		
		
	}
	
	//Draw to screen
	public void Render() {
		//
		birdSize = process.RenderScale();
		
		//RenderBird
		RenderObjects.SetColor(255);
		RenderObjects.DrawImage("bird", x, y, birdSize, birdSize*(4f/5f), Rotation(), false, true);
	}
	
	//Get the render rotation of the bird
	private float Rotation() {
		
		//Return value
		float rotation = 0;
		
		//Get the radian of (x,y) to (x+birdSize*50,y+forccY)
		double angle = Math.atan2(y-(y+forceY), x-(x+birdSize*50));
		
		//Convert Radian to Degree and add 180
		rotation = (float) Math.toDegrees(angle)+180;
		
		//Return value
		return rotation;
	}
	
	private void Jump() {
		//Jump force is not special, it was found by trial and error
		forceY += 1000;
	}
	
	
	// Get Position
	public Vector2 Position() {
		return new Vector2(x,y);
	}
	
}
