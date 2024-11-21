package pokemonFight.manager;

import java.util.List;

import main.logic.CollisionDetector;
import main.panel.MainPanel;
import main.panel.MainWindow;
import pokemonFight.manager.pojo.Item;
import pokemonFight.manager.pojo.Pokemon;
import pokemonFight.view.FightPanel;

public class StatusSingleton {

	private static StatusSingleton statusSingleton = null;

	private FightPanel fightPanel = null;

	private MainPanel mainPanel = null;

	private MainWindow mainWindow = null;

	private List<Pokemon> pokemonTeam = null;
	
	private List<Item> allyHeldItems = null;
	
	private Pokemon wildPokemon = null;

	private CollisionDetector collisionDetector = null;

	public static StatusSingleton getInstance() {
		if (null == statusSingleton) {
			statusSingleton = new StatusSingleton();
		}
		return statusSingleton;
	}
	
	public Pokemon getWildPokemon() {
		return wildPokemon;
	}

	public void setWildPokemon(Pokemon wildPokemon) {
		this.wildPokemon = wildPokemon;
	}

	public FightPanel getFightPanel() {
		return fightPanel;
	}

	public List<Pokemon> getPokemonTeam() {
		return pokemonTeam;
	}

	public void setPokemonTeam(List<Pokemon> pokemonTeam) {
		this.pokemonTeam = pokemonTeam;
	}

	public List<Item> getAllyHeldItems() {
		return allyHeldItems;
	}

	public void setAllyHeldItems(List<Item> allyHeldItems) {
		this.allyHeldItems = allyHeldItems;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setFightPanel(FightPanel fightPanel) {
		this.fightPanel = fightPanel;
	}

	public CollisionDetector getCollisionDetector() {
		return collisionDetector;
	}

	public void setCollisionDetector(CollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	private StatusSingleton() {
	}
}
