package main.logic;

import java.util.Random;

import main.manager.pojos.Character;
import main.panel.MainPanel;
import pokemonFight.manager.StatusSingleton;

public class CollisionDetector {

	MainPanel gamePanel = null;

	public CollisionDetector(MainPanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void checkHitBox(Character character) {
		// Definimos las distancias de los lados del rectangulo de nuestra hitbox (al
		// ser el sprite(CharacterWorldX/Y) mayor que nuestra hitbox(charcterHitBox),
		// tenemos que hacer unos calculos).

		int hitBoxLeftSideXCoord = character.entityWorldX + character.characterHitBox.x;
		int hitBoxRightSideXCoord = character.entityWorldX + character.characterHitBox.x
				+ character.characterHitBox.width;
		int hitBoxTopSideYCoord = character.entityWorldY + character.characterHitBox.y;
		int hitBoxBottomSideYCoord = character.entityWorldY + character.characterHitBox.y
				+ character.characterHitBox.height;

		// una vez obtenidos los valores de nuestra hitbox, calcularemos donde esta
		// nuestro caracter en los terminos de columna y linea del mapa

		int characterLeftCol = hitBoxLeftSideXCoord / gamePanel.tileSize;
		int characterRightCol = hitBoxRightSideXCoord / gamePanel.tileSize;
		int characterTopRow = hitBoxTopSideYCoord / gamePanel.tileSize;
		int characterBottomRow = hitBoxBottomSideYCoord / gamePanel.tileSize;

		int tileNum1 = 0;
		int tileNum2 = 0;

		// Y ahora, predecirmos donde va a estar la hitbox de nuestro personaje una vez
		// el usuario presiona una tecla, para poder despues definir si el bloque que va
		// a pisar tras presionar una tecla sera un bloque solido o no. el tileNum se
		// utiliza ya que a la hora de calcular una hitbox solo necesitamos dos lados,
		// ya que la hitbox no puede chocar con cuatro lados ni tres lados
		// simultaneamente.
		if (null != character.direction) {
			switch (character.direction) {
			case "up":
				characterTopRow = (hitBoxTopSideYCoord - character.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapCoords[characterLeftCol][characterTopRow];
				tileNum2 = gamePanel.tileManager.mapCoords[characterRightCol][characterTopRow];
				if (collisionDetected(tileNum1, tileNum2)) {
					character.collisioned = true;
				}
				if (checkForCombat(tileNum1, tileNum2)) {
					pokemonAppear();
				}
				break;
			case "down":
				characterBottomRow = (hitBoxBottomSideYCoord + character.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapCoords[characterLeftCol][characterBottomRow];
				tileNum2 = gamePanel.tileManager.mapCoords[characterRightCol][characterBottomRow];
				if (collisionDetected(tileNum1, tileNum2)) {
					character.collisioned = true;
				}
				if (checkForCombat(tileNum1, tileNum2)) {
					pokemonAppear();
				}
				break;
			case "left":
				characterLeftCol = (hitBoxLeftSideXCoord - character.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapCoords[characterLeftCol][characterTopRow];
				tileNum2 = gamePanel.tileManager.mapCoords[characterLeftCol][characterBottomRow];
				if (collisionDetected(tileNum1, tileNum2)) {
					character.collisioned = true;
				}
				if (checkForCombat(tileNum1, tileNum2)) {
					pokemonAppear();
				}
				break;
			case "right":
				characterRightCol = (hitBoxRightSideXCoord + character.speed) / gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.mapCoords[characterRightCol][characterTopRow];
				tileNum2 = gamePanel.tileManager.mapCoords[characterRightCol][characterBottomRow];
				if (collisionDetected(tileNum1, tileNum2)) {
					character.collisioned = true;
				}
				if (checkForCombat(tileNum1, tileNum2)) {
					pokemonAppear();
				}
				break;
			}
		}
	}

	private boolean checkForCombat(int tileNum1, int tileNum2) {
		return ((null != StatusSingleton.getInstance().getPokemonTeam())
				&& (gamePanel.tileManager.tileImagesMap.get(tileNum1).grass == true
						|| gamePanel.tileManager.tileImagesMap.get(tileNum2).grass == true));
	}

	private boolean collisionDetected(int tileNum1, int tileNum2) {
		return (gamePanel.tileManager.tileImagesMap.get(tileNum1).collision == true
				|| gamePanel.tileManager.tileImagesMap.get(tileNum2).collision == true);
	}

	private void pokemonAppear() {
		int probability = 1;
		int encounterRatio = new Random().nextInt(187);
		if (probability > encounterRatio) {
			StatusSingleton.getInstance().getMainWindow().setFightPanel();
		}
	}

}
