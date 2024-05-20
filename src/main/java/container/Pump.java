package container;
import controller.Game;
import exception.MyException;
import map.Map;
import menu.MyAlert;
import player.Player;
import player.Type;

import java.io.Serializable;
import java.util.Random;

/**
 * Ez a Pump osztály, ez teszi lehetővé a csövek közötti összeköttetést
 */
public class Pump extends Container implements Serializable {

	/**
	 * Ebben tároljuk, hogy a pumpában melyik cső a bemeneti cső
	 * (azaz melyikből akarjuk átpumpálni a vizet).
	 */
	private Pipe input;

	/**
	 * Ebben tároljuk a kimeneti csövet
	 * (azaz, hogy melyik csőbe akarjuk pumpálni a vizet).
	 */
	private Pipe output;

	/**
	 * Ez az attribútum tárolja, hogy az adott pumpa éppen sérült-e vagy sem.
	 */
	private boolean isDamaged;

	/**
	 * Azt az értéket tartalmazza amely meghatározza, hogy az adott pumpa mely körben fog megsérülni.
	 */
	private int randomDamageValue;

	/**
	 * Megadja, hogy a pumpához hány cső csatlakozhat maximum.
	 */
	private int maxPipeAmount;

	private Random random;


	/**
	 * Pump konstruktora
	 * @param maxPipeAmount - Ennyi cső csatlakozthat majd legfeljebb a Pumphoz.
	 */
	public Pump(int maxPipeAmount) {
		if(maxPipeAmount > 4){
			this.maxPipeAmount = 4;
		} else
			this.maxPipeAmount=maxPipeAmount;
		Random rand=new Random();
		randomDamageValue=rand.nextInt(11) + 10;
		isDamaged=false;
	}


	@Override
	/**
	 * Ez a függvény valósítja meg a pumpa megjavítását
	 * Megnézzük, hogy a pumpa el van-e romolva amennyiben igen elvégezzük a javítást más esetben kivételt dobunk
	 */
	public void mendPump() {
		if(this.isDamaged){
			this.setDamaged(false);
		} else
			MyAlert.showInvalidMoveAlert("Wasn't even a scratch on it");
	}

	@Override
	/**
	 * Hozzácsatlakoztatja a paraméterük kapott Player áttal hordozott csövet a pumpához.
	 * @param player - A játékos
	 * @throws MyException
	 */
	public void insertPipe(Player player, int xCord, int yCord){

		if(!Map.getInstance().getGameMap().isEmpty()){

			ContainerPos cp = new ContainerPos();

			for(ContainerPos containerPos : Map.getInstance().getGameMap()){

				if(containerPos.getContainer().equals(player.getPosition())){
					cp = containerPos;
				}
			}
			if(!isAllConnected() && cp.isOnNeighboringTile(xCord, yCord)){

				Map.getInstance().getContainers().add(player.getCarriedPipes().get(0));
				Map.getInstance().getGameMap().add(new ContainerPos(player.getCarriedPipes().get(0), xCord, yCord));

				Map.addAllNeighbors();

				player.getCarriedPipes().remove(player.getCarriedPipes().get(0));
			}
		}
	}

	@Override
	/**
	 * Ez a függvény felelős a pumpa outputjának illetve inputjának átváltásáért
	 * A paraméterül kapott csőről megállapítjuk, hogy szomszédos-e ezzel a pumpával amennyiben igen mélyebbre megyünk
	 * Megnézzük, hogy melyiket akarjuk változtatni: a pumpa kimenetét (output) vagy bemenetét (input), ezt a t paraméterrel adjuk meg
	 * Mind a két esetben megnézzük, hogy az egyik már nem másikhoz tartozik azaz, ha inputot akarunk változtatni nem az output-e amire változtatni akrjuk és fordítva
	 * @param x
	 * @param y
	 * @param t
	 */
	public void alterPump(int x, int y, Type t) {

		Pipe pi = new Pipe();

		for(ContainerPos containerPos : Map.getInstance().getGameMap()){
			if(containerPos.getPosX() == x && containerPos.getPosY() == y){
				pi = (Pipe) containerPos.getContainer();
			}
		}
		if(this.seeifNeighbors(pi)){
			if(t == Type.Input) {
				if (this.getOutput() != pi) {
					this.setInput(pi);
				}
				else
					MyAlert.showInvalidMoveAlert("Input pipe cannot be changed to be the output pipe of the pump");
			}
			else if(t == Type.Output){
				if(this.getInput() != pi)
					this.setOutput(pi);
				else
					MyAlert.showInvalidMoveAlert("Input pipe cannot be changed to be the output pipe of the pump");
			}
		}
		else
			MyAlert.showInvalidMoveAlert("Target Pipe doesn't neigbour your position");
	}

	@Override
	/**
	 * Elveszi a kívánt csövet a pumpától és a player-hez adja hozzá.
	 * @param player - A játékos
	 * @param xCord - Az elvételre kijelölt cső.
	 * @param yCord - Az elvételre kijelölt cső.
	 * @throws MyException
	 */
	public void extractPipe(Player player, int xCord, int yCord){
		ContainerPos cp = new ContainerPos();
		for(ContainerPos containerPos : Map.getInstance().getGameMap()){
			if(containerPos.getPosX() == xCord && containerPos.getPosY() == yCord){
				cp = containerPos;
			}
		}
		if(cp.getContainer().amIGettingDeatched()) {
			if (this.seeifNeighbors(cp.getContainer())) {
				if (cp.getContainer().isLooseEnd()) {
					if(this.getInput() != null && this.getInput().equals(cp.getContainer())){
							this.setInput(null);
					}
					if(this.getOutput() != null && this.getOutput().equals((cp.getContainer()))){
						this.setOutput(null);
					}
					cp.getContainer().getNeighbors().remove(this);
					this.getNeighbors().remove(cp.getContainer());
					player.getCarriedPipes().add(cp.getContainer());
					Map.getInstance().getGameMap().remove(cp);
					Map.getInstance().getContainers().remove(cp.getContainer());
				} else{
					cp.getContainer().getNeighbors().remove(this);

					Pump pump = (Pump) cp.getContainer().getNeighbors().get(0);

					//------Handling Input and Output Problems------//
					if(this.getInput() != null || pump.getInput() != null){
						if (this.getInput().equals(cp.getContainer())) {
							this.setInput(null);
						}
						if (pump.getInput().equals(cp.getContainer())) {
							pump.setInput(null);
						}
					}
					if(this.getOutput() != null || pump.getOutput() != null){
						if(this.getOutput().equals((cp.getContainer()))){
							this.setOutput(null);
						}
						if(pump.getOutput().equals((cp.getContainer()))){
							pump.setOutput(null);
						}
					}
					this.getNeighbors().remove(cp.getContainer());
					pump.getNeighbors().remove(cp.getContainer());
					cp.getContainer().getNeighbors().remove(pump);

					player.getCarriedPipes().add(cp.getContainer());
					Map.getInstance().getGameMap().remove(cp);
					Map.getInstance().getContainers().remove(cp.getContainer());
				}
			}
		} else
			MyAlert.showInvalidMoveAlert("Got'cha little man! You thought you could detach something other than a Pipe?");
	}


	@Override
	/**
	 * Mindig true-val tér vissza, ugyanis a Pump-ra akárhány játékos léphet.
	 * @return boolean
	 */
	public boolean steppable() {
		return true;
	}


	@Override
	/**
	 * Elrontja a pumpát a randomDamageValue és a paraméterül kapott érték alapján
	 * @param turnCount - Ha egyenéő a randomDamageValue-val és működik a pumpa, akkor elrontja.
	 */
	public void lifeCycle(int turnCount){
		if(turnCount == this.randomDamageValue && !this.isDamaged){
			this.isDamaged = true;
			this.randomDamageValue =  random.nextInt(10) + Game.getInstance().getTurnCount();
		}
	}


	/**
	 * Csatlakoztatja a paraméterül kapott csövet a pumpához.
	 * @param pi
	 */
	public void addPipe(Pipe pi) {

		if(!this.isAllConnected())
			this.neighbors.add(pi);

	}


	/**
	 * Leszedi a paraméterül kapott csövet a pumpáról.
	 * @param pi
	 */
	public void removePipe(Pipe pi) {

		if(!this.neighbors.isEmpty())
			this.neighbors.remove(pi);

	}

	@Override
	/**
	 * Azt határozza meg a paraméterben kapott Containerről, hogy megegyezik-e az input-jával
	 * @param c
	 * @return
	 */
	public boolean amInput(Container c){
		if(input != null){
			return this.input.equals(c);
		} else{
			return false;
		}
	}

	/**
	 * A Pump osztályhoz tartozó setInputState
	 * Ez a függvény egyszerűen csak annyit csinál, hogy az output Pipe-jára "továbbítja" a setInputState() függvény hívást
	 * Mivel a Pump nem változtatja az inputStatjét hanem csak símán meghívja kimenetére az ő setInputState-jét ezért a pumpán keresztül egyből végigmegy a víz
	 */
	@Override
	public void setInputState() {
		if(!this.isDamaged && output != null){
			output.setInputState();
		}
	}


	/**
	 * @return String
	 */
	@Override
	public String consolePrint() {
		return "PU\t";
	}

	@Override
	public void damageContainer() {
		this.isDamaged = true;
	}

	/**
	 * Ha teli van a pumpa, vagyis már nem lehet több csövet hozzácstolni, akkor true-val tér vissza.
	 * @return boolean
	 */
	public boolean isAllConnected() {
		return getMaxPipeAmount()==neighbors.size();
	}

	/**
	 * Vissaztér a randomDamageValue attribútum értékével.
	 * @return int
	 */
	public int getRandomDamageValue() {
		return randomDamageValue;
	}

	/**
	 * Vissaztér a maxPipeAmount attribútum értékével.
	 * @return boolean
	 */
	public int getMaxPipeAmount() {
		return maxPipeAmount;
	}

	/**
	 * Beállítja a maxPipeAmount attribútum értékét.
	 * @param maxPipeAmount
	 */
	public void setMaxPipeAmount(int maxPipeAmount) {
		this.maxPipeAmount = maxPipeAmount;
	}

	@Override
	/**
	 * Visszatér az isDamaged attribútum értékével.
	 * @return boolean
	 */
	public boolean isDamaged() {
		return isDamaged;
	}

	/**
	 * Beállítja az isDamaged attribútum értékét.
	 * @param isDamaged
	 */
	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	@Override
	/**
	 * Visszatér az output csővel.
	 * @return Pipe
	 */
	public Pipe getOutput() {
		return output;
	}

	/**
	 * Beállítja az output csövet a paraméterként akpottra
	 * @param output - A beállítani kívánt cső.
	 */
	public void setOutput(Pipe output) {
		this.output = output;
	}

	@Override
	/**
	 * Visszatér az input csővel.
	 * @return Pipe
	 */
	public Pipe getInput() {
		return input;
	}

	@Override
	/**
	 * Beállítja az input csövet a paraméterként kapottra.
	 * @param input
	 */
	public void setInput(Pipe input) {
		this.input = input;
	}

	/**
	 * Beállítja az isDamaged attribútum értékét a parméterként kapottra.
	 * @param b
	 */
    public void setisDamaged(boolean b) {
		isDamaged=b;
    }


	@Override
	public void setBreakOff(int rng) {
		randomDamageValue = rng;
	}

	@Override
	public String myIconPath() {

		//Get the current position of the element
		ContainerPos cp = new ContainerPos();
		for(ContainerPos containerPos : Map.getInstance().getGameMap()){
			if(containerPos.getContainer().equals(this)){
				cp = containerPos;
			}
		}

		int maxX = -1;
		int maxY = -1;

		// Find the maximum x and y values
		for (ContainerPos containerPos : Map.getInstance().getGameMap()) {
			if (containerPos.getPosX() > maxX) {
				maxX = containerPos.getPosX();
			}
			if (containerPos.getPosY() > maxY) {
				maxY = containerPos.getPosY();
			}
		}

		Container[][] grid = new Container[maxX+1][maxY+1];

		// Place the containers in the grid
		for (ContainerPos containerPos : Map.getInstance().getGameMap()) {
			int x = containerPos.getPosX();
			int y = containerPos.getPosY();
			grid[x][y] = containerPos.getContainer();
		}
		if(maxPipeAmount == 2)
		{
			for(ContainerPos containerPos : Map.getInstance().getGameMap())
			{
				int x = cp.getPosX();
				int y = cp.getPosY();
				if(grid[x][y]==containerPos.getContainer())
				{
					if(isDamaged)
					{
						if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
							// DownToLeft
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/PumpDownToLeft_Damaged.png";
							}

							// UpToLeft
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/PumpUpToLeft_Damaged.png";
							}

							// Vertical
							if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
								return "file:resources/container_components/PumpLeftRight_UpSide_Damaged.png";
							}
						}

						if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y]))
						{
							// DownToRight
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/PumpDownToRight_Damaged.png";
							}

							// UpToRight
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/PumpUpToRight_Damaged.png";
							}

							// Vertical
							if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
								return "file:resources/container_components/PumpLeftRight_UpSide_Damaged.png";
							}
						}
						//Horizontal
						if (y + 1 < grid[x].length && y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y + 1]) && containerPos.getContainer().seeifNeighbors(grid[x][y - 1]))
						{
							return "file:resources/container_components/PumpUpDown_RightSide_Damaged.png";
						}
					}
					else
					{
						if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
							// DownToLeft
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/PumpDownToLeft.png";
							}

							// UpToLeft
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/PumpUpToLeft.png";
							}

							// Vertical
							if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
								return "file:resources/container_components/PumpLeftRight_UpSide.png";
							}
						}

						if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y]))
						{
							// DownToRight
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/PumpDownToRight.png";
							}

							// UpToRight
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/PumpUpToRight.png";
							}

							// Vertical
							if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
								return "file:resources/container_components/PumpLeftRight_UpSide.png";
							}
						}
						//Horizontal
						if (y + 1 < grid[x].length && y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y + 1]) && containerPos.getContainer().seeifNeighbors(grid[x][y - 1]))
						{
							return "file:resources/container_components/PumpUpDown_RightSide.png";
						}
					}
					return "";
				}

			}
		}else if(maxPipeAmount == 3)
		{
			for(ContainerPos containerPos : Map.getInstance().getGameMap())
			{
				int x = cp.getPosX();
				int y = cp.getPosY();
				if(grid[x][y]==containerPos.getContainer())
				{
					if(isDamaged)
					{
						if (y + 1 < grid[x].length && y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y + 1]) && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
							// 3WayLeft
							if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
								return "file:resources/container_components/Pump3WayLeft_Damaged.png";
							}

							// 3WayRight
							if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
								return "file:resources/container_components/Pump3WayRight_Damaged.png";
							}
						}

						if (x - 1 >= 0 && x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x - 1][y]) && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
							// 3WayDown
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/Pump3WayDown_Damaged.png";
							}

							// 3WayUp
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/Pump3WayUp_Damaged.png";
							}
						}
					}
					else
					{
						if (y + 1 < grid[x].length && y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y + 1]) && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
							// 3WayLeft
							if (x - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x - 1][y])) {
								return "file:resources/container_components/Pump3WayLeft.png";
							}

							// 3WayRight
							if (x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
								return "file:resources/container_components/Pump3WayRight.png";
							}
						}

						if (x - 1 >= 0 && x + 1 < grid.length && containerPos.getContainer().seeifNeighbors(grid[x - 1][y]) && containerPos.getContainer().seeifNeighbors(grid[x + 1][y])) {
							// 3WayDown
							if (y + 1 < grid[x].length && containerPos.getContainer().seeifNeighbors(grid[x][y + 1])) {
								return "file:resources/container_components/Pump3WayDown.png";
							}

							// 3WayUp
							if (y - 1 >= 0 && containerPos.getContainer().seeifNeighbors(grid[x][y - 1])) {
								return "file:resources/container_components/Pump3WayUp.png";
							}
						}
					}
					return "file:resources/container_components/Pump3WayRight.png";
				}
			}
		}
		else if(maxPipeAmount == 4)
		{
			if(isDamaged)
				return "file:resources/container_components/PumpAll_Damaged.png";
			else
				return "file:resources/container_components/PumpAll.png";
		}
		return "";
	}

	public boolean isVerticallyConnected(){
		if(Map.getInstance().getGameMap() != null){
			for(ContainerPos cp : Map.getInstance().getGameMap()){
				if(cp.getContainer().equals(this) && cp.getContainer().seeifNeighbors(Map.getContainerAt(cp.getPosX(), cp.getPosY() + 1).getContainer())){
					return true;
				}
			}
		}
		return false;
	}
}
