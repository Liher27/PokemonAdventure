package main.manager;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import main.manager.pojos.Item;
import main.panel.MainPanel;
import pokemonFight.controller.ItemController;
import pokemonFight.manager.pojo.FightItem;

public class ItemManager {

	MainPanel gamePanel = null;
	List<FightItem> availableItems = null;

	public ItemManager(MainPanel gamePanel) {
		this.gamePanel = gamePanel;

		availableItems = new ItemController().loadItems();
	}

	public void setItems() throws IOException {
		gamePanel.items[0] = new Item();
		gamePanel.items[0].setItemName("Potion");
		gamePanel.items[0].setItemSprite(ImageIO.read(new File("contents/sprites/items/Potion.png")));
		gamePanel.items[0].entityWorldX = 6 * gamePanel.tileSize;
		gamePanel.items[0].entityWorldY = 8 * gamePanel.tileSize;

		gamePanel.items[1] = new Item();
		gamePanel.items[1].setItemName("PokeBall");
		gamePanel.items[1].setItemSprite(ImageIO.read(new File("contents/sprites/items/PokeBall.png")));
		gamePanel.items[1].entityWorldX = 7 * gamePanel.tileSize;
		gamePanel.items[1].entityWorldY = 8 * gamePanel.tileSize;

		gamePanel.items[2] = new Item();
		gamePanel.items[2].setItemName("Pokedex");
		gamePanel.items[2].setItemSprite(ImageIO.read(new File("contents/sprites/items/Pokedex.png")));
		gamePanel.items[2].entityWorldX = 8 * gamePanel.tileSize;
		gamePanel.items[2].entityWorldY = 8 * gamePanel.tileSize;
	}

	public void drawItems(Graphics2D graphics2D, Item item) {

		int worldX = item.entityWorldX;
		int worldY = item.entityWorldY;
		int screenX = worldX - gamePanel.player.entityWorldX + gamePanel.player.playerPositionXInPanel;
		int screenY = worldY - gamePanel.player.entityWorldY + gamePanel.player.playerPositionYInPanel;

		if (item != null) {
			graphics2D.drawImage(item.itemSprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
		}
	}

}
