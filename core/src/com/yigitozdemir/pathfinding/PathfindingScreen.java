package com.yigitozdemir.pathfinding;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.yigitozdemir.pathfinding.data.Unit;
import com.yigitozdemir.pathfinding.data.UnitGraph;

public class PathfindingScreen implements Screen{
	private MyGdxGame game;
	/**
	 * map data
	 */
	private int[][] map = new int[][] {
		new int[] {1, 1, 1, 1, 1, 1},
		new int[] {1, 1, 1, 1, 1, 1},
		new int[] {1, 1, 2, 2, 1, 1},
		new int[] {1, 1, 2, 2, 1, 1},
		new int[] {1, 1, 1, 1, 1, 1},
		new int[] {1, 1, 1, 1, 1, 1},
	};
	/**
	 * map the integer to color
	 */
	private HashMap<Integer, Color> mapColorMap = new HashMap<Integer, Color>() {{
		put(1, Color.LIGHT_GRAY);
		put(2, Color.DARK_GRAY);
	}};
		
	/**
	 * size of each square
	 */
	private int squareSize = 50;
	/**
	 * shape renderer to render squares 
	 */
	private ShapeRenderer shapeRenderer;
	/**
	 * beginning position of the agent
	 */
	private Vector2 firstPosition  = new Vector2(50, 50);
	/**
	 * target position of the agent
	 */
	private Vector2 targetPosition = new Vector2(250, 250);
	UnitGraph unitGraph = new UnitGraph();
	GraphPath<Unit> path;
	public PathfindingScreen(MyGdxGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		
		unitGraph.createConnections(map);
		path = unitGraph.getPath(unitGraph.getUnitByCoordinate(1, 1), unitGraph.getUnitByCoordinate(5, 5));
		
	}

	@Override
	public void render(float delta) {
		shapeRenderer.begin(ShapeType.Filled);
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				Color c = mapColorMap.get(map[i][j]);
				shapeRenderer.setColor(c);
				shapeRenderer.rect(i * squareSize, j * squareSize, squareSize, squareSize);
			}
		}
		
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(firstPosition.x, firstPosition.y, 10);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.circle(targetPosition.x, targetPosition.y, 10);
		
		
		shapeRenderer.setColor(Color.RED);
		
		
		path = unitGraph.getPath(unitGraph.getUnitByCoordinate(1, 1), unitGraph.getUnitByCoordinate(5, 5));
		Iterator<Unit> iterator = path.iterator();
		Vector2 start = null;
		
		while(iterator.hasNext()) {
			Unit u = iterator.next();
			if(start == null) {
				start = new Vector2(u.getX() * squareSize, u.getY() * squareSize);
			}
			else {
				shapeRenderer.rectLine(start, new Vector2(u.getX() * squareSize, u.getY() * squareSize), 5);
				start = new Vector2(u.getX() * squareSize, u.getY() * squareSize);
			}
			
		}
		
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}
