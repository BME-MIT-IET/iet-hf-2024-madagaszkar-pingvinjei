package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exception.MyException;
import player.*;

/**
 * Abstract osztály.
 * Egy általános tárolót valósít meg (Pipe, Pump, Mountain Spring, Cistern)
 */
public abstract class Container implements Serializable {
	/**
	 * A Container mellett található Containerek ArrayList-je
	 */
	protected ArrayList<Container> neighbors = new ArrayList<>();

	/**
	 * Ez az attribútum felelős a víz mozgásáért
	 * Ebben az attribútumban tárolunk két értéket:
	 * 								-0.index-en tároljuk az előző evaluation kimenetét
	 * 								-1.index-en tároljuk a mostani evaluation kimenetét
	 * Ez az attribútum egy kis naplóként értelmezhető, mivel tároljuk, hogy előző körben mi történt, amellett, hogy most mi történik
	 * A false (hamis) érték azt jelenti, hogy a Containerhez nem jutott víz
	 * Míg a true (igaz) érték azt jelenti, hogy a Containerhez jutott víz
	 * A víz mozgásának részletes leírása a Controller waterFlow() függvényénél található meg (ide túl sok lett volna)
	 */
	protected boolean[] inputState = { false, false };

	/**
	 * Megnézi, hogy apraméterként kapott Container a szomszédja-e a Cotainernek
	 * @param neighbor - A megvizsgálni kívánt Container
	 * @return boolean - Szomszédja-e vagy sem
	 */
	public boolean seeifNeighbors(Container neighbor){

		return this.neighbors.contains(neighbor);
	}

	public void setInput(Pipe pipe){

	}

	public boolean amIGettingDeatched(){
		return false;
	}

	
	/**
	 * Ez a függvény felelős a waterFlow() függvényhez kapcsolódó kiiratásokra
	 * @return String
	 */
	public String writeInputState(){

		return null;

	}

	/**
	 * Ezt a függvényt implementálja a Pump és Cistern osztályok
	 * A függvény azért felelős, hogy egy adott idő után megtörténjen valami a container objektumunkkal
	 * @param turnCount
	 */
	public void lifeCycle(int turnCount){

	}

	/**
	 * Ezt a függvényt implementálja a Pump és Cistern osztályok
	 * A függvény egy boolean értéket ad vissza abból adódóan, hogy az argumentumban megadott Container inputja-e az adott Containernek (this)
	 * @param c
	 * @return
	 */
	public boolean amInput(Container c){
		return false;
	}

	public void getsOccupied(){

	}

	/**
	 *  Ezt a függvényt implementálja a Pipe osztály
	 *	A függvény azt a feladatot látja el, hogy amikor a játékos ellép egy Container-ről (Pipe-ról), beállítsa, hogy a Pipe nem occupied
	 */
	public void movedFrom(){

	}

	/**
	 * Ezt a függvényt a Pump osztály valósítja meg
	 * Ez a függvény felelős a pumpa output illetve inputjának átállításáért
	 * @param x
	 * @param y
	 * @param t
	 * @throws MyException
	 */
	public void alterPump(int x, int y, Type t){

	}

	/**
	 * Ezt a függvényt a Pipe osztály valósítja meg
	 * Ez a függvény felelős a cső megjavításáért
	 * @throws MyException
	 */
	public void mendPipe(){

	}

	/**
	 * Ezt a függvényt a Pump osztály valósítja meg
	 * Ez a függvény felelős a pumpa megjavításáért
	 * @throws MyException
	 */
	public void mendPump(){

	}

	/**
	 * Ezt a függvényt a Pipe osztály valósítja meg
	 * Ez a függvény felelős a cső meglékeléséért
	 * @throws MyException
	 */
	public void puncturePipe(){

	}

	/**
	 * Ezt az függvényt a Pipe osztály valósítja meg
	 * Ez az függvény felelős pumpa csőhöz való illesztéséért
	 * @param player
	 * @throws MyException
	 */
	public void insertPump(Player player){

	}

	/**
	 * Ezt a függvényt a Pump osztály valósítja meg
	 * Ez a függvény felelős a csőhöz tartoző szabadvégű csőnek leillesztéséért
	 * @param player
	 * @param xCord
	 * @param yCord
	 * @throws MyException
	 */
	public void extractPipe(Player player, int xCord, int yCord){

	}

	/**
	 * Ezt a függvényt a Pump osztály valósítja meg
	 * Ez a függvény felelős a cső pumpához illesztéséért
	 *
	 * @param player
	 * @param xCord
	 * @param yCord
	 * @throws MyException
	 */
	public void insertPipe(Player player, int xCord, int yCord){

	}

	/**
	 * Ezt a függvényt a Pipe osztály valósítja meg. Ez a függvény felelős a cső csúszóssá válik
	 */
	public void pipeGetsSlippery(){

	}
	/**
	 * Ezt a függvényt a Pipe (és Pump (false default))osztályok valósulnak meg. Ez a függvény felelős visszaadni csúszóssá-e Pipe (getter)
	 * @return
	 */

	/*
	 * Ezt a függvényt a Pipe osztály valósítja meg.
	 * Ez a függvény felelős visszaadni csúszóssá-e Pipe (getter)
	 */
	public boolean getIsSlippery(){
		return false;
	}

	/*
	 * Ezt a függvényt a Pipe osztály valósítja meg. 
	 * Ez a függvény felelős a cső  ragadóssá válik
	 */
	public void pipeGetsSticky(){

	}

	public void takePipeFromCs(Player player){

	}

	public void takePumpFromCs(Player player){

	}
	/*
	 * Ezt a függvényt a Pipe osztály valósítja meg.
	 *  Ez a függvény felelős visszaadni ragadóssá-e Pipe (getter)
	 */
	public boolean getIsSticky(){
		return false;
	}

	/**
	 * Ezt a függvényt megvalósítja a Pump, Pipe, Cistern illetve MountaiSpring osztályok
	 * Ez a függvény visszaad egy boolean értéket az alapján, hogy az adott Container-re lehet-e lépni
	 * @return
	 */
	public boolean steppable(){
		return false;
	}

	/**
	 * Ezt a függvényt megvalósítja a Pipe, Cistern, MountainSpring
	 * Ez a függvény felelős a víz mozgásáért való kiértékelésekért
	 */
	public void eval(){

	}

	public int hasPipes(){
		return -1;
	}

	public boolean hasPump(){
		return false;
	}

	/**
	 * Ezt a függvényt megvalósítja a Pump, Pipe, Cistern illetve MountaiSpring osztályok
	 * Ez a függvény felelős az inputState megváltoztatásáért, ez felelős a víz tényleges mozgásáért
	 */
	public void setInputState(){

	}

	/**
	 * Itt tároljuk a mozgatható pumpát.
	 * ez csak teszteléshez kell most lekérdezni
	 */

	public void setFreePump(Pump freePump) {
	}

	public abstract String consolePrint();

	public void damageContainer(){

	}

	public boolean isLooseEnd(){
		return false;
	}

	/**
	 * Az evaluation-t (kiértékelést) követően az inputState értékeit megváltoztatjuk
	 */
	public void makeHistory() {
		inputState[0] = inputState[1];
		inputState[1] = false;
	}

	public boolean isWaterFlowing(){
		return inputState[0];
	}

	/**
	 * Visszatér a neighbors attribúmmal
	 * @return ArrayList - neighbors attribútum
	 */
	public List<Container> getNeighbors() {
		return neighbors;
	}

	/**
	 * Beállítja a neigbors attribútumot a paraméterként kapottra
	 * @param neighbors - A beállítani kívánt attribútum
	 */
	public void setNeighbors(List<Container> neighbors) {
		this.neighbors = (ArrayList<Container>) neighbors;
	}

	public boolean isDamaged(){
		return false;
	}

	public Container getInput(){
		return null;
	}
	public boolean isEmpty(){
		return !inputState[0] && !inputState[1];
	}
	public Container getOutput(){
		return null;
	}

	public void setBreakOff(int rng){

	}

	public int queryCistern(){
		return -1;
	}

	public int mountainSpringQuery(){
		return -1;
	}

	public abstract String myIconPath();
}
