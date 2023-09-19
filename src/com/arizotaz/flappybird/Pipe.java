package com.arizotaz.flappybird;

import com.arizotaz.gameacc.BoxCollider2D;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.window.Window;

public class Pipe {
	
	private FlappyBird process;
	private Window window;

	private BoxCollider2D good;
	private BoxCollider2D topPipe;
	private BoxCollider2D bottomPipe;
	
	private Bird bird;
	
	private boolean collected = false;
	
	private float pipeSpeed = 1;
	
	private float x, y, mouthSize = 0;
	public Pipe(float x, float y, float mouthSize) {
		process = (FlappyBird) Lotus.singleton.ProcessManager().GetCurrentProces();
		window = Lotus.singleton.WindowManager().GetCurrentWindow();
		
		bird = process.Bird();
		
		this.x = x;
		this.y = y;
		this.mouthSize = mouthSize;
		
		
	}
	
	public void Tick() {
		float pipeWidth = 2,pipeHeight = 10;
		
		x -= pipeSpeed*(window.Time().DeltaTime()/1000);
		
		topPipe = 	new BoxCollider2D(x, (y+mouthSize/2+pipeHeight/2), pipeWidth, pipeHeight);
		bottomPipe= new BoxCollider2D(x, (y-mouthSize/2-pipeHeight/2), pipeWidth, pipeHeight);
		good = 		new BoxCollider2D(x, y,pipeWidth,mouthSize);
		
		if (topPipe.IsColliding(bird.Collider()) || bottomPipe.IsColliding(bird.Collider())) {
			//Dead
			process.Lose();
		}
		if (good.IsColliding(bird.Collider()) && !collected) {
			collected = true;
			//add Point
			process.AddPoint();

		}
		
	}
	
	public void Render() {
		float pipeWidth = 2,pipeHeight = 10;
		
		RenderObjects.SetColor(255);
		
		//Top Pipe
		RenderObjects.DrawImage("pipe", x*process.RenderScale(), (y+pipeHeight/2+mouthSize/2)*process.RenderScale(), pipeWidth*process.RenderScale(), pipeHeight*process.RenderScale(), 180, false, true);
		
		//Bottom Pipe
		RenderObjects.DrawImage("pipe", x*process.RenderScale(), (y-pipeHeight/2-mouthSize/2)*process.RenderScale(), pipeWidth*process.RenderScale(), pipeHeight*process.RenderScale(), 0, false, true);

	}
}
