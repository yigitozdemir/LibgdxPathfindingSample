package com.yigitozdemir.pathfinding.data;

import java.util.Iterator;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class UnitGraph implements IndexedGraph<Unit> {
	UnitHeuristic unitHeuristic = new UnitHeuristic();
	Array<Unit> units = new Array<>();
	Array<UnitConnection> connections = new Array<>();
	
	
	ObjectMap<Unit, Array<Connection<Unit>>> connectionsMap = new ObjectMap<>();
	
	public void createConnections(int[][] mapData) {
		for(int i = 0; i < mapData.length; i++) {
			for(int j = 0;  j < mapData[i].length; j++) {
				Unit unit = new Unit(i, j, lastNodeIndex);
				lastNodeIndex++;
				units.add(unit);
			}
		}
		
		for(int i = 0; i < mapData.length; i++) {
			for(int j = 0;  j < mapData[i].length; j++) {
				//right
				try {
					int costTemplate = mapData[i+1][j];
					int cost = (costTemplate == 1) ? 1 : 100;
					
					Unit from = getUnitByCoordinate(i, j);
					Unit to = getUnitByCoordinate(i+1, j);
					
					UnitConnection connection = new UnitConnection(from, to, cost);
					if(!connectionsMap.containsKey(from)) {
						connectionsMap.put(from, new Array<Connection<Unit>>());
					}
					connectionsMap.get(from).add(connection);
				}catch (ArrayIndexOutOfBoundsException e) {
				}
				
				//left
				try {
					int costTemplate = mapData[i-1][j];
					int cost = (costTemplate == 1) ? 1 : 100;
					
					Unit from = getUnitByCoordinate(i, j);
					Unit to = getUnitByCoordinate(i-1, j);
					
					UnitConnection connection = new UnitConnection(from, to, cost);
					if(!connectionsMap.containsKey(from)) {
						connectionsMap.put(from, new Array<Connection<Unit>>());
					}
					connectionsMap.get(from).add(connection);
				}catch (ArrayIndexOutOfBoundsException e) {
				}
				
				//top
				try {
					int costTemplate = mapData[i][j + 1];
					int cost = (costTemplate == 1) ? 1 : 100;
					
					Unit from = getUnitByCoordinate(i, j);
					Unit to = getUnitByCoordinate(i, j + 1);
					
					UnitConnection connection = new UnitConnection(from, to, cost);
					if(!connectionsMap.containsKey(from)) {
						connectionsMap.put(from, new Array<Connection<Unit>>());
					}
					connectionsMap.get(from).add(connection);
				}catch (ArrayIndexOutOfBoundsException e) {
				}
				
				//down
				try {
					int costTemplate = mapData[i][j-1];
					int cost = (costTemplate == 1) ? 1 : 100;
					
					Unit from = getUnitByCoordinate(i, j);
					Unit to = getUnitByCoordinate(i, j-1);
					
					UnitConnection connection = new UnitConnection(from, to, cost);
					if(!connectionsMap.containsKey(from)) {
						connectionsMap.put(from, new Array<Connection<Unit>>());
					}
					connectionsMap.get(from).add(connection);
				}catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
	}
	
	public GraphPath<Unit> getPath(Unit from, Unit to){
		GraphPath<Unit> path = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchNodePath(from, to, unitHeuristic, path);
		return path;
	}
	
	/**
	 * search for unit by the coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public Unit getUnitByCoordinate(int x, int y) {
		Iterator<Unit> i = units.iterator();
		while(i.hasNext()) {
			Unit u = i.next();
			if(u.getX() == x && u.getY() == y) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * add a new unit to units list
	 * @param unit
	 */
	public void addUnit(Unit unit) {
		unit.setIndex(lastNodeIndex);
		lastNodeIndex++;
		
		units.add(unit);
	}
	
	int lastNodeIndex = 0;
	@Override
	public Array<Connection<Unit>> getConnections(Unit fromNode) {
		if(connectionsMap.containsKey(fromNode)) {
			return connectionsMap.get(fromNode);
		}
		return new Array<>(0);
	}

	@Override
	public int getIndex(Unit node) {
		return node.getIndex();
	}

	@Override
	public int getNodeCount() {
		return lastNodeIndex;
	}

}
