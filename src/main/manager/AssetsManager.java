package main.manager;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import main.manager.pojos.Item;
import main.manager.pojos.OverMapEntities;
import main.panel.MainPanel;
import pokemonFight.controller.ItemController;
import pokemonFight.manager.pojo.FightItem;

public class AssetsManager {

	MainPanel gamePanel = null;

	List<FightItem> availableItems = null;

	File[] items = null;

	File[] assets = null;

	public AssetsManager(MainPanel gamePanel) {
		this.gamePanel = gamePanel;

		items = new File("contents/sprites/items/").listFiles();

		assets = new File("contents/sprites/tiles/assets/").listFiles();

		availableItems = new ItemController().loadItems();
	}

	// Añado items de forma aleatoria al mapa, para pintarlos como una capa superior
	// al mapa en si
	public void setItems() throws IOException {
		for (int i = 0; i < items.length; i++) {
			gamePanel.items.add(new Item());
			gamePanel.items.get(i).setSprite(ImageIO.read(items[i]));
			gamePanel.items.get(i).entityWorldX = new Random().nextInt(100) * gamePanel.tileSize;
			gamePanel.items.get(i).entityWorldY = new Random().nextInt(100) * gamePanel.tileSize;
		}
	}

	// Añado assets como la casa, npcs, arboles, hierba etc... de forma aleatoria al
	// mapa, para pintarlos como una capa superior
	// al mapa en si
	public void setAssets() throws IOException {
		int[] houseCoords = { 15, 10, 17, 10, 15, 8, 17, 8 };

		// Como solo quiero leer cuatro archivos del fichero de assets, que son los de
		// house1..., pongo solo 4, y asi si en un futuro tengo que leer otros distintos
		// de este fichero llamo al mismo metodo pero a otros ficheros.
		for (int i = 0; i < 4; i++) {
			gamePanel.assets.add(new OverMapEntities());
			gamePanel.assets.get(i).setSprite(ImageIO.read(assets[i]));
			gamePanel.assets.get(i).entityWorldX = houseCoords[i * 2] * gamePanel.tileSize;
			gamePanel.assets.get(i).entityWorldY = houseCoords[i * 2 + 1] * gamePanel.tileSize;
		}
	}

	public void drawItems(Graphics2D graphics2D, Item item) {

		int worldX = item.entityWorldX;
		int worldY = item.entityWorldY;
		int screenX = worldX - gamePanel.player.entityWorldX + gamePanel.player.playerPositionXInPanel;
		int screenY = worldY - gamePanel.player.entityWorldY + gamePanel.player.playerPositionYInPanel;

		if (item != null) {
			graphics2D.drawImage(item.sprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
		}
	}

	public void drawAssets(Graphics2D graphics2D, OverMapEntities assets) {

		int worldX = assets.entityWorldX;
		int worldY = assets.entityWorldY;
		int screenX = worldX - gamePanel.player.entityWorldX + gamePanel.player.playerPositionXInPanel;
		int screenY = worldY - gamePanel.player.entityWorldY + gamePanel.player.playerPositionYInPanel;

		if (assets != null) {
			graphics2D.drawImage(assets.sprite, screenX, screenY, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);
		}
	}

}
