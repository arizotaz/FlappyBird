package com.arizotaz.flappybird;

import com.arizotaz.lotus.ApplicationInfo;
import com.arizotaz.lotus.Lotus;
import com.arizotaz.lotus.struc.Version;

public class Main {
	
	public static void main(String[] args) {
		
		//Create Object
		Lotus lotus = new Lotus(args);
		
		//Config
		ApplicationInfo info = new ApplicationInfo("flappybird", new Version("1.0.0"), "Flappy Bird", null);
		lotus.Config(info);
		
		//Setup
		lotus.Setup();
		
		//Init
		lotus.Init();
		
		//AddProcess
		lotus.AddProcess(null);
		
		//Start
		lotus.Start();
	}
	
}
