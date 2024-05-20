package container;

import java.io.Serializable;

/**
 * Ez a hegyi forrás, ebből származik a víz, amelyért a játékosok versengenek
 */
public class MountainSpring extends Container implements Serializable {

	/**
	 * Tárolja azt a csövet, amely kivezeti a vizet a forrásból.
	 */
	private Pipe output;

	/**
	 * A hegyi forrásvíz kapacitása, azaz mennyi vizet képes tárolni a hegyi forrás
	 */
	private int waterCapac = 50;

	public MountainSpring(Pipe output){
		this.output = output;
		this.neighbors.add(output);
		output.neighbors.add(this);
	}

	public MountainSpring(){
		output = null;
	}

	/**
	 * A hegyi forrásban lévő víz mennyiségét csökkenti
	 */
	public void decreaseWaterAm() {
		waterCapac--;
	}

	@Override
	/**
	 * @return String
	 */
	public String writeInputState(){

		return "MountainSpring inputStatjének első illetve második eleme: " + this + ": "+ inputState[0] + ',' + inputState[1];
	}

	@Override
	/**
	 * A hegyi forráshoz tartozó kiértékelő függvény
	 * Ez a függvény egyszerűen csak egy feltételben megnézi, hogy van-e benne víz ha van megyünk mélyebbre
	 * Mélyebben meghívjuk benne a setInputState-et önmagára, illetve csökkentjük a víz tartalmát (mert folyik ki belőle víz)
	 * És legvégül az outputján lévő Pipe-ra meghívjuk a setInputState-et
	 */
	public void eval() {
		if(waterCapac != 0) {
			decreaseWaterAm();
			this.setInputState();
			if(output != null)
				output.setInputState();
		}
		else{
			inputState[0] = false;
			inputState[1] = false;
		}
	}

	@Override
	/**
	 * A Mountain Spring ezt a függvényt nem valósítja meg, úgyhogy erről többet nem is beszélek
	 */
	public void setInputState() {
		inputState[0] = true;
		inputState[1] = true;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String consolePrint() {
		return "MS\t";
	}

	@Override
	public Pipe getOutput() {
		return output;
	}


	/**
	 * Visszatér a waterCapac attribűtum értékével
	 * @return int - A waterCapac attribútum értéke
	 */
	public int getWaterCapac() {
		return waterCapac;
	}

	/**
	 * Beállítja a waterCapac attribútum értékét a paraméterként kapottra
	 * @param waterCapac - A beállítani kívánt érték
	 */
	public void setWaterCapac(int waterCapac) {
		this.waterCapac = waterCapac;
	}

	/**
	 * Beállítja az output attribútum értékét a paraméterként kapottra
	 * @param output - A beállítani kívánt érték
	 */
	public void setOutput(Pipe output){
		this.output = output;
	}

	@Override
	public int mountainSpringQuery() {
		return waterCapac;
	}

	@Override
	public String myIconPath() {
		return "file:resources/container_components/ms.png";
	}
}
