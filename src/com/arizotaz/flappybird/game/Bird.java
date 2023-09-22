package com.arizotaz.flappybird.game;

import com.arizotaz.flappybird.MainProcess;
import com.arizotaz.gameacc.BoxCollider2D;
import com.arizotaz.gameacc.KeyManager;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.struc.Vector2;
import com.arizotaz.lotus.utils.Tools;
import com.arizotaz.lotus.window.Window;

public class Bird {

	//Flappy Bird Host Process
	private MainProcess process;
	//Window Instance
	private Window window;
	//Key Manager
	private KeyManager kbi;
	//Is key down?
	private boolean keyDown = false;
	//Game Instance
	private Game game;

	
	//Size of the bird on screen
	private float birdSize = 0;
	
	
	//position and force values
	private float x = 0, y = 0;
	private float forceX = 0,forceY = 0;
	private float gravity = 9.5f*4;
	
	private BoxCollider2D collider;	
	
	private boolean freed = false;
	
	public Bird() {
		//Store the flappybird host process
		process = (MainProcess) Lotus.singleton.ProcessManager().GetCurrentProces();
		
		//Grab the current window
		window = Lotus.singleton.WindowManager().GetCurrentWindow();
		
		//Grab Game
		game = process.Game();
		
		//Gets keymanager
		kbi = process.KeyManager();
				
		x = 0;
		y = 7;
		
		collider = new BoxCollider2D(x,y,birdSize,birdSize);
	}
	
	//Game Processing
	public void Tick() {
		//Set Bird Size
		birdSize = 1f;
		
		//Update Collider Size
		collider.SetSize(birdSize, birdSize*(4f/5f));
		
		//Store deltaTime so we do not need to do the calculation over and over again
		float deltaTime = window.Time().DeltaTime()/1000;
		
		//Apply Gravity with delta time so only a certain amount of gravity is applied at each frame
		forceY -= gravity*deltaTime;
		
		
		//Keyboard input
		if (kbi.KeyDown("bird.jump")) {
			freed = true;
			// If keys are not down
			if (!keyDown) {
				
				Jump();
			}
			keyDown = true;
		} else {
			
			//Keys are no longer down
			keyDown = false;
		}
		
		if (!Freed()) {
			forceX = 0;
			forceY = 0;
		}
		
		// Add Forces With deltatime so that force is applied evenly between frames
		x += forceX * deltaTime;
		y += forceY * deltaTime;		
		
		// Set the smallest x value
		y = Tools.ClampVar(y, 0, 14);
		//if bird is on the floor, clear force value
		if (y <= 0) { forceY = 0;game.Lose(); }
		if (y >= 14) { forceY = 0; }

		
		collider.SetPosition(x, y);
		
		
	}
	
	//Draw to screen
	public void Render() {
		
		//RenderBird
		RenderObjects.SetColor(255);
		RenderObjects.DrawImage("bird", collider.x()*game.RenderScale(), collider.y()*game.RenderScale(), collider.w()*game.RenderScale(), collider.h()*game.RenderScale(), Rotation(), false, true);
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
		forceY += 10;
	}
	
	public BoxCollider2D Collider() {
		return this.collider;
	}
	
	// Get Position
	public Vector2 Position() {
		return new Vector2(x,y);
	}
	
	public boolean Freed() {
		return this.freed;
	}
	
}
