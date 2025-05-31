package main.manager;

import java.awt.Graphics2D;

import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import main.panel.MainPanel;

public class TileManager extends Tile {

	private MainPanel gamePanel;
	public int[][] mapCoords;
	private TMXMapReader mapReader = null;
	private Map map = null;
	private int screenTileWidth = 0;
	private int screenTileHeight = 0;

	/**
	 * Constructor de la clase
	 * 
	 * @param gamePanel
	 * @throws Exception
	 */
	public TileManager(MainPanel gamePanel) throws Exception {
		this.gamePanel = gamePanel;
		this.mapCoords = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
		
		mapReader = new TMXMapReader();
		map = mapReader.readMap("contents/sprites/map/worldMap.tmx");

		screenTileWidth = gamePanel.screenWidth / gamePanel.tileSize;
		screenTileHeight = gamePanel.screenHeight / gamePanel.tileSize;
	}

	/**
	 * Metodo creado para guardar nuestras imagenes en un array de tipo Tile. esto
	 * es util ya que si una tile tiene algun atributo especial, como es el caso de
	 * la colision, este no puede ser guardado directamente dentro del hashmap, por
	 * lo que despues insertaremos dentro del hashmap todo el array que se ha habra
	 * cargado con este metodo, en el cual cada tile contara con un tipo de colision
	 * Metodo para guardar las imagenes dentro de un hashmap de tipo BufferedImage,
	 * con un numero de código.
	 * 
	 * @param tilesInfo
	 * 
	 * @throws IOException
	 */

	/**
	 * Método usado para leer el fichero de texto del mapa, en el que cada numero es
	 * un bloque del array tipo tile. lee columna a columna, y una vez llega al
	 * final de la columna pasa a la siguiente fila...
	 * 
	 * Metodo para dibujar los bloques en el mapa del fichero de texto, dependiendo
	 * del valor asignado en el fichero se le otrga una imagen u otra del array de
	 * tiles. continua igual que el anterior metodo
	 * 
	 * @param graphics2D
	 */
	public void drawTiles(Graphics2D graphics2D) {
		int playerOffsetX = gamePanel.player.entityWorldX - gamePanel.player.playerPositionXInPanel;
		int playerOffsetY = gamePanel.player.entityWorldY - gamePanel.player.playerPositionYInPanel;

		int startCol = Math.max(0, playerOffsetX / gamePanel.tileSize);
		int endCol = Math.min(gamePanel.maxWorldColumn - 1, startCol + screenTileWidth + 1);

		int startRow = Math.max(0, playerOffsetY / gamePanel.tileSize);
		int endRow = Math.min(gamePanel.maxWorldRow - 1, startRow + screenTileHeight + 1);

		for (MapLayer mapLayer : map.getLayers()) {
			if (mapLayer instanceof TileLayer) {
				TileLayer tileLayer = (TileLayer) mapLayer;
				drawLayer(graphics2D, tileLayer, startRow, endRow, startCol, endCol, playerOffsetX, playerOffsetY);
			}
		}
	}

	private void drawLayer(Graphics2D g, TileLayer layer, int startRow, int endRow, int startCol, int endCol,
			int offsetX, int offsetY) {
		for (int worldRow = startRow; worldRow <= endRow; worldRow++) {
			for (int worldColumn = startCol; worldColumn <= endCol; worldColumn++) {

				int worldX = worldColumn * gamePanel.tileSize;
				int worldY = worldRow * gamePanel.tileSize;
				int screenX = worldX - offsetX;
				int screenY = worldY - offsetY;

				Tile tile = layer.getTileAt(worldColumn, worldRow);
				if (tile != null) {
					g.drawImage(tile.getImage(), screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
				}
			}
		}
	}

}
