package container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import player.Player;

/**
 * Először is úgy döntöttünk, hogy a Cistern is felvesszük Objektum katalógusba,
 * mert az ugyanolyan fontos szerepet játszik ebben a feladatban, mint a Pump.
 * A Cistern tehát a csőrendszer aktív elemei, amely víztároló tartályként működik.
 * Felelős a csövek készítéséért, a víz tárolásáért és a ciszternában lévő vízszint növeléséért.
 */
public class Cistern extends Container implements Serializable {

	/**
	 * Ez a bemeneti cső, innen érkezik a víz a ciszternába,
	 * ha ezen a csövön keresztül megy víz.
	 */
	private Pipe input;

	/**
	 * Itt tároljuk a ciszternába került víz mennyiségét.
	 */
	private int collectedWater;



	/**
	 * Ez felel a csővek véletlenszerű időközönkénti készítéséért/létrehozásáért.
	 */
	private int randomPipeCreationTime;

	/**
	 * Itt tároljuk a ciszterna által készített csöveket.
	 */
	private ArrayList<Pipe> madePipes;

	/**
	 * Cistern osztály konstruktora
	 * @param input - A Cistern bemeneti csöve
	 */
	public Cistern(Pipe input) {
        this.input = input;
		this.neighbors.add(input);
		input.neighbors.add(this);
        this.collectedWater = 0;
        this.freePump = null;
        this.madePipes = new ArrayList<>();
    }

	public Cistern(){
		input = null;
		collectedWater = 0;
		freePump = null;
		madePipes = new ArrayList<>();
	}

	/**
	 * Ezzel a metódussal tudjuk növelni a ciszternában lévő víz mennyiségét.
	 */
	public void increaseCollectedWater() {
		collectedWater++;
	}


	@Override
	/**
	 * Minden harmadik körben létrehoz egy csövet
	 * @param turnCount
	 */
	public void lifeCycle(int turnCount) {
		if(turnCount != 0 && turnCount%8==0)
			madePipes.add(new Pipe());
		else if (turnCount != 0 && turnCount%15==0) {
			freePump = new Pump(4);
		}
	}

	@Override
	/**
	 * Mindig true-val tér vissza, ugyanis a Cistern-re mindig rá lehet lépni,
	 * az ot lévő játékosok számától függetlenül
	 * @return boolean - true
	 */
	public boolean steppable(){
		return true;
	}

	@Override
	/**
	 * Az inputState-hez tartozó kiírást valósítja meg, ez különösebben csak a víz mozgásának "grafikus" szemléltetésére kell
	 * @String
	 */
	public String writeInputState(){

		return "Cistern inputStatjének első illetve második eleme: " + this + ": "+ inputState[0]+ ',' + inputState[1];
	}

	@Override
	/**
	 * Ez a függvény valósítja meg a ciszternához tartozó kiértékelést
	 * Amennyiben folyik bele víz (inputState[0]) növeljük a belekerült víz mennyiségét
	 */
	public void eval() {
		if(inputState[0]){
			increaseCollectedWater();
		}
	}

	@Override
	public int hasPipes() {
		return madePipes.size();
	}

	private Pump freePump;
	@Override
	public boolean hasPump() {
		return freePump != null;
	}

	@Override
	/**
	 * Azt mondja meg ez a függvény, hogy a paraméterében kapott Container megegyezik-e a ciszterna inputjával-e
	 * @param c
	 * @return
	 */
	public boolean amInput(Container c){
		return this.input.equals(c);
	}

	/**
	 * @return boolean
	 */
	@Override
	public boolean getIsSlippery() {
		return false;
	}

	@Override
	public void takePipeFromCs(Player player) {
		if(madePipes != null){
			player.getCarriedPipes().add(madePipes.remove(0));
		}
	}

	@Override
	public void takePumpFromCs(Player player) {
		if(freePump != null){
			player.setCarriedPump(freePump);
			freePump = null;
		}
	}

	@Override
	/**
	 * A Cistern osztály nem valósítja meg ezt a függvényt ezért erről tőbbet nem is beszélek
	 */
	public void setInputState() {
		inputState[1] = true;
	}


	/**
	 * @return String
	 */
	@Override
	public String consolePrint() {
		return "CS\t";
	}

	@Override
	/**
	 * Vissaztér a Cistern bemeneti csövével
	 * @return - A bemeneti cső
	 */
	public Pipe getInput() {
		return input;
	}

	@Override
	/**
	 * Beállítja a Cistern bemeneti csövét
	 * @param input - A beállítani kívánt cső
	 */
	public void setInput(Pipe input) {
		this.input = input;
	}

	@Override
	public boolean amIGettingDeatched() {
		return false;
	}

	/**
	 * Visszatér a CollectedWater attribútum,
	 * vagyis a Cistern által gyűjtött víz értékével.
	 * @return int - A CollectedWater attribútum értéke
	 */
	public int getCollectedWater() {
		return collectedWater;
	}

	/**
	 * Beállítja a CollectedWater attribútum értékét
	 * @param collectedWater - A beállítani kívánt érték
	 */
	public void setCollectedWater(int collectedWater) {
		this.collectedWater = collectedWater;
	}

	/**
	 * Vissaztér a szabadon mozgatható Pump-al
	 * @return Pump - A szabadon mozgatható Pump
	 */
	public Pump getFreePump() {
		return freePump;
	}

	/**
	 * Beállítja a FreePump attribútumot a paraméterként átadott értékre
	 * @param freePump - Az beállítani kívánt Pump
	 */
	@Override
	public void setFreePump(Pump freePump) {
		this.freePump = freePump;
	}

	/**
	 * Vissaztér a RandomPipeCreationTime attribútum értékével
	 * @return int - A RandomPipeCreationTime attribútum értéke
	 */
	public int getRandomPipeCreationTime() {
		return randomPipeCreationTime;
	}

	/**
	 * Beállítja a RandomPipeCreationTime attribútum értékét
	 * @param randomPipeCreationTime - A beállítani kívánt érték
	 */
	public void setRandomPipeCreationTime(int randomPipeCreationTime) {
		this.randomPipeCreationTime = randomPipeCreationTime;
	}

	/**
	 * Visszatér Cisternben keletkezett Pipe-ok ArrayList-jével
	 * @return ArrayList - A keletkezett Pipe-ok
	 */
	public List<Pipe> getMadePipes() {
		return madePipes;
	}

	/**
	 * Beállítja a MadePipes attribútum értékét
	 * @param madePipes - A beállítani kívánt Pipe ArrayList
	 */
	public void setMadePipes(List<Pipe> madePipes) {
		this.madePipes = (ArrayList<Pipe>) madePipes;
	}

	@Override
	public int queryCistern() {
		return collectedWater;
	}

	@Override
	public String myIconPath() {
		return "file:resources/container_components/cistern.png";
	}
}
