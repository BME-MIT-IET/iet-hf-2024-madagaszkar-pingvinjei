package map;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import container.*;

/**
 * A Map osztály felelőssége a pályán elhelyezett pumpák és csövek tárolása.
 */
public class Map implements Serializable{

	/**
	 * Az eddig elfolyt víz értéke.
	 */
	private static int leakedWater;

	private static Map map;

	private static final long serialVersionUID = 1L;

	/**
	 * A pályán lévő játékosok ArrayList-je.
	 */

	/**
	 * A pályán lévő Containerek ArrayList-je.
	 */
	private static ArrayList<Container> containers = new ArrayList<>();
	private static ArrayList<ContainerPos> gameMap = new ArrayList<>();

	/**
	 * Map osztály konstruktora.
	 */
	private Map(){
	}

	
	/** 
	 * @return Map
	 */
	public static Map getInstance() {
		if (map == null) {
			map = new Map();
		}
		return map;
	}

	/**
	 * Növeli a leakedWater attribútum értékét.
	 */
	public static void increaseLeakedWater() {

		leakedWater++;

	}

	/**
	 * Hozzáad egy Containert a játéktérhez.
	 * @param c - A hozzáadni kívánt Container
	 */
	public static void addElement(Container c) {
		containers.add(c);
	}

	/**
	 * Visszatér a containers attribútum értékével.
	 * @return Arraylist - A containerek
	 */
	public List<Container> getContainers() {
		return containers;
	}

	/**
	 * Visszatér a player attribútum értékével.
	 * @return ArrayList - A playerek
	 */

	/**
	 * Beállítja a player attribútumot a paraméterként kapottra
	 * @param players - A beállítani kívánt ArrayList
	 */
	
	/** 
	 * @param cp
	 */
	public static void addNeighbors(ContainerPos cp){
		if(!Map.getInstance().getGameMap().isEmpty()) {
			for (ContainerPos containerPos : Map.getInstance().getGameMap()) {
				if (containerPos.isOnNeighboringTile(cp.getPosX(), cp.getPosY()) && (!(containerPos.getContainer().getNeighbors().contains(cp.getContainer())))) {
						cp.getContainer().getNeighbors().add(containerPos.getContainer());
						containerPos.getContainer().getNeighbors().add(cp.getContainer());
				}
			}
		}
	}

	public static void removeNeighbors(ContainerPos cp){
		if(!Map.getInstance().getGameMap().isEmpty()) {
			for (ContainerPos containerPos : Map.getInstance().getGameMap()) {
				if (containerPos.isOnNeighboringTile(cp.getPosX(), cp.getPosY()) && (!(containerPos.getContainer().getNeighbors().contains(cp.getContainer())))) {
						cp.getContainer().getNeighbors().remove(containerPos.getContainer());
						containerPos.getContainer().getNeighbors().remove(cp.getContainer());
				}
			}
		}
	}

	public static void addAllNeighbors(){
		for(ContainerPos cp : gameMap){
			addNeighbors(cp);
		}
	}

	public static int getLeakedWater() {
		return leakedWater;
	}

	public List<ContainerPos> getGameMap(){
		return gameMap;
	}

	public static void makeMap(){

		map = new Map();

		Pipe p1 = new Pipe();
		Pump pu1 = new Pump(2);
		Pipe p2 = new Pipe();
		Pump pu2 = new Pump(2);
		Pipe p3 = new Pipe();
		Pump pu3 = new Pump(3);
		Pipe p4 = new Pipe();
		Cistern cs = new Cistern(p4);
		MountainSpring ms = new MountainSpring(p1);

		containers.add(ms);
		containers.add(p1);
		containers.add(pu1);
		containers.add(p2);
		containers.add(pu2);
		containers.add(p3);
		containers.add(pu3);
		containers.add(p4);
		containers.add(cs);

		gameMap.add(new ContainerPos(ms, 0,0));
		gameMap.add(new ContainerPos(p1, 1,0));
		gameMap.add(new ContainerPos(pu1, 2,0));
		gameMap.add(new ContainerPos(p2, 3,0));
		gameMap.add(new ContainerPos(pu2, 4,0));
		gameMap.add(new ContainerPos(p3, 4,1));
		gameMap.add(new ContainerPos(pu3, 4,2));
		gameMap.add(new ContainerPos(p4, 4,3));
		gameMap.add(new ContainerPos(cs, 4,4));

		pu1.setInput(p1);
		pu1.setOutput(p2);
		pu2.setInput(p2);
		pu2.setOutput(p3);
		pu3.setInput(p3);
		pu3.setOutput(p4);

		addAllNeighbors();
	}

	public static void resetMap(){
		map = new Map();
		containers.clear();
		gameMap.clear();
		leakedWater = 0;
	}

	public static void makeTestMap(){

        resetMap();

		Pipe p1 = new Pipe();
		Pipe p2 = new Pipe();
		Pump pu = new Pump(3);
		Pipe p3 = new Pipe();
		Cistern cs = new Cistern(p2);
		MountainSpring ms = new MountainSpring(p1);

		containers.add(ms);
		containers.add(p1);
		containers.add(pu);
		containers.add(p2);
		containers.add(p3);
		map.containers.add(cs);

		gameMap.add(new ContainerPos(ms, 0, 0));
		gameMap.add(new ContainerPos(p1, 1, 0));
		gameMap.add(new ContainerPos(pu, 2, 0)); // Assuming pu here refers to both (2,0) and (2,1)
		gameMap.add(new ContainerPos(p2, 3, 0));
		gameMap.add(new ContainerPos(cs, 4, 0));
		gameMap.add(new ContainerPos(p3, 2, 1));

		pu.setInput(p1);
		pu.setOutput(p2);

		addAllNeighbors();
	}

	public static void makeTestMapPipe(){

		resetMap();

		Pipe p1 = new Pipe();
		Pipe p2 = new Pipe();
		Pump pu = new Pump(3);
		Cistern cs = new Cistern(p2);
		MountainSpring ms = new MountainSpring(p1);

		containers.add(ms);
		containers.add(p1);
		containers.add(pu);
		containers.add(p2);
		map.containers.add(cs);

		gameMap.add(new ContainerPos(ms, 0, 0));
		gameMap.add(new ContainerPos(p1, 1, 0));
		gameMap.add(new ContainerPos(pu, 2, 0)); // Assuming pu here refers to both (2,0) and (2,1)
		gameMap.add(new ContainerPos(p2, 3, 0));
		gameMap.add(new ContainerPos(cs, 4, 0));

		pu.setInput(p1);
		pu.setOutput(p2);

		addAllNeighbors();
	}

	public static void makeOrientationMap(){

		Pipe p1 = new Pipe();
		Pump pu1 = new Pump(2);
		Pipe p2 = new Pipe();
		Pump pu2 = new Pump(2);
		Pipe p3 = new Pipe();
		Pump pu3 = new Pump(3);
		Pipe p4 = new Pipe();
		Cistern cs = new Cistern(p4);
		MountainSpring ms = new MountainSpring(p1);

		containers.add(ms);
		containers.add(p1);
		containers.add(pu1);
		containers.add(p2);
		containers.add(pu2);
		containers.add(p3);
		containers.add(pu3);
		containers.add(p4);
		containers.add(cs);

		gameMap.add(new ContainerPos(ms, 0,0));
		gameMap.add(new ContainerPos(p1, 1,0));
		gameMap.add(new ContainerPos(pu1, 2,0));
		gameMap.add(new ContainerPos(p2, 3,0));
		gameMap.add(new ContainerPos(pu2, 3,1));
		gameMap.add(new ContainerPos(p3, 3,2));
		gameMap.add(new ContainerPos(pu3, 4,2));
		gameMap.add(new ContainerPos(p4, 4,3));
		gameMap.add(new ContainerPos(cs, 4,4));

		pu1.setInput(p1);
		pu1.setOutput(p2);
		pu2.setInput(p2);
		pu2.setOutput(p3);
		pu3.setInput(p3);
		pu3.setOutput(p4);

		addAllNeighbors();
	}

	public static ContainerPos getContainerAt(int x, int y){
		for(ContainerPos containerPos : gameMap){
			if(containerPos.getPosX() == x && containerPos.getPosY() == y){
				return containerPos;
			}
		}
		return null;
	}

}
