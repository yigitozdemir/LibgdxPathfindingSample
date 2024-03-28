package com.yigitozdemir.pathfinding;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	private PathfindingScreen pathfindingScreen;
	
	@Override
	public void create() {
		this.pathfindingScreen = new PathfindingScreen(this);
		setScreen(pathfindingScreen);
	}
	
}
