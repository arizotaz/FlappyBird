package com.arizotaz.flappybird.game;

import com.arizotaz.gameacc.BoxCollider2D;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.RenderObjects;
import com.arizotaz.lotus.window.Window;

public class Pipe {
	
	private MainProcess process;
	private Game game;
	private Window window;

	private BoxCollider2D good;
	private BoxCollider2D topPipe;
	private BoxCollider2D bottomPipe;
	
	private Bird bird;
	
	private boolean collected = false;
	
	private float pipeSpeed = 1;
	
	private float x, y, mouthSize = 0;
	public Pipe(MainProcess process, float x, float y, float mouthSize) {
		this.process = process;
		window = Lotus.singleton.WindowManager().GetCurrentWindow();
		game = process.Game();
		bird = game.Bird();
		
		this.x = x;
		this.y = y;
		this.mouthSize = mouthSize;
		
		
	}
	
	public void Tick() {
		float pipeWidth = 2,pipeHeight = 10;
		
		
		
		topPipe = 	new BoxCollider2D((x-game.xDistance()), (y+mouthSize/2+pipeHeight/2), pipeWidth, pipeHeight);
		bottomPipe= new BoxCollider2D((x-game.xDistance()), (y-mouthSize/2-pipeHeight/2), pipeWidth, pipeHeight);
		good = 		new BoxCollider2D((x-game.xDistance()), y,pipeWidth,mouthSize);
		
		if (topPipe.IsColliding(bird.Collider()) || bottomPipe.IsColliding(bird.Collider())) {
			//Dead
			game.Lose();
		}
		if (good.IsColliding(bird.Collider()) && !collected) {
			collected = true;
			//add Point
			game.AddPoint();

		}
		
	}
	
	public void Render() {
		float pipeWidth = 2,pipeHeight = 10;
		
		window.RenderObjects().SetColor(255);
		
		//Top Pipe
		window.RenderObjects().DrawImage("pipe", (x-game.xDistance())*game.RenderScale(), (y+pipeHeight/2+mouthSize/2)*game.RenderScale(), pipeWidth*game.RenderScale(), pipeHeight*game.RenderScale(), 180, true);
		
		//Bottom Pipe
		window.RenderObjects().DrawImage("pipe", (x-game.xDistance())*game.RenderScale(), (y-pipeHeight/2-mouthSize/2)*game.RenderScale(), pipeWidth*game.RenderScale(), pipeHeight*game.RenderScale(), 0, true);

	}
	
	public boolean HasPast() {
		return (x-game.xDistance()) < -3;
	}
}
