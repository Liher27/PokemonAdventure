package main.manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.logic.KeyBoard;
import main.manager.pojos.Character;
import main.panel.MainPanel;

public class PlayerManager extends Character {

	private KeyBoard keyBoard = null;

	private MainPanel gamePanel = null;

	public int playerPositionXInPanel;
	public int playerPositionYInPanel;

	/**
	 * Constructor de la clase
	 * 
	 * @param keyBoard
	 * @throws IOException
	 */
	public PlayerManager(KeyBoard keyBoard, MainPanel gamePanel) throws IOException {
		this.keyBoard = keyBoard;

		this.gamePanel = gamePanel;

		//Para colocar a nuestro personaje en esas coordenadas (en este caso, 10/9)
		entityWorldX = gamePanel.tileSize * 10;
		entityWorldY = gamePanel.tileSize * 9;

		//Para colocar la camara en el centro de la pantalla
		playerPositionXInPanel = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
		playerPositionYInPanel = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

		// Definimos cual va a ser la hitbox de nuestro personaje
		speed = 4;
		getPlayerImage();
	}

	/**
	 * Para obtener las imagenes de los sprites del personaje
	 * 
	 * @throws IOException
	 */
	public void getPlayerImage() throws IOException {
		redStill = ImageIO.read(new File("contents/sprites/player/redStill.png"));
		redMovesDown1 = ImageIO.read(new File("contents/sprites/player/redMoves1.png"));
		redMovesDown2 = ImageIO.read(new File("contents/sprites/player/redMoves2.png"));
		redStillLeft = ImageIO.read(new File("contents/sprites/player/redStillLeft.png"));
		redMovesLeft = ImageIO.read(new File("contents/sprites/player/redMovesLeft.png"));
		redMovesLeft2 = ImageIO.read(new File("contents/sprites/player/redMovesLeft2.png"));
		redStillRight = ImageIO.read(new File("contents/sprites/player/redStillRight.png"));
		redMovesRight = ImageIO.read(new File("contents/sprites/player/redMovesRight.png"));
		redMovesRight2 = ImageIO.read(new File("contents/sprites/player/redMovesRight2.png"));
		redStillUp = ImageIO.read(new File("contents/sprites/player/redStillUp.png"));
		redMovesUp1 = ImageIO.read(new File("contents/sprites/player/redMovesUp1.png"));
		redMovesUp2 = ImageIO.read(new File("contents/sprites/player/redMovesUp2.png"));
		waterPokemon1 = ImageIO.read(new File("contents/sprites/player/waterPokemon1.png"));
		waterPokemon2 = ImageIO.read(new File("contents/sprites/player/waterPokemon2.png"));
	}

	/**
	 * Para hacer que el player se mueva en una direccion u otra
	 * 
	 */
	public void updateSprite() {
		if (keyBoard.upPressed == true) {
			direction = "up";
		} else if (keyBoard.downPressed == true) {
			direction = "down";
		} else if (keyBoard.rightPressed == true) {
			direction = "right";
		} else if (keyBoard.leftPressed == true) {
			direction = "left";
		} else {
			direction = null;
		}
		spriteCounter++;

		// Para comprobar si nuestro personaje ha chocado contra una pared o no, le
		// pasamos el Player, que es una clase hija de Character, por lo que en el
		// metodo podra operar con el player
		collisioned = false;
		gamePanel.collisionDetector.checkHitBox(this);

		// Hacemos que solo se pueda mover si el personaje no ha colisionado
		if (direction != null) {
			if (collisioned == false) {
				switch (direction) {
				case "up":
					entityWorldY -= speed;
					break;
				case "down":
					entityWorldY += speed;
					break;
				case "left":
					entityWorldX -= speed;
					break;
				case "right":
					entityWorldX += speed;
					break;
				}
			}
		}

		wrapForSpriteMovement();

	}

	/**
	 * para ir cambiando el sprite del personaje: Changer=1, una imagen. Changer=2,
	 * otra ...
	 */
	private void wrapForSpriteMovement() {
		if (spriteCounter > 10) {
			if (spriteChanger == 1) {
				spriteChanger = 2;
			} else if (spriteChanger == 2) {
				spriteChanger = 3;
			} else if (spriteChanger == 3) {
				spriteChanger = 1;
			}
			spriteCounter = 0;
		}
	}

	/**
	 * Para dibujar el player e ir cambiando sus sprites por cada ciclo del anterior
	 * metodo
	 * 
	 * @param graphics2D
	 * @param tileSize
	 */
	public void draw(Graphics2D graphics2D) {

		BufferedImage sprite = null;

		if (direction == null) {
			if (spriteChanger == 1) {
				sprite = redStill;
			}
			if (spriteChanger == 2) {
				sprite = redStill;
			}
			if (spriteChanger == 3) {
				sprite = redStill;
			}
		} else {
			switch (direction) {
			case "up":
				if (spriteChanger == 1) {
					sprite = redStillUp;
				}
				if (spriteChanger == 2) {
					sprite = redMovesUp1;
				}
				if (spriteChanger == 3) {
					sprite = redMovesUp2;
				}
				break;
			case "down":
				if (spriteChanger == 1) {
					sprite = redStill;
				}
				if (spriteChanger == 2) {
					sprite = redMovesDown1;
				}
				if (spriteChanger == 3) {
					sprite = redMovesDown2;
				}
				break;
			case "left":
				if (spriteChanger == 1) {
					sprite = redStillLeft;
				}
				if (spriteChanger == 2) {
					sprite = redMovesLeft;
				}
				if (spriteChanger == 3) {
					sprite = redMovesLeft2;
				}
				break;
			case "right":
				if (spriteChanger == 1) {
					sprite = redStillRight;
				}
				if (spriteChanger == 2) {
					sprite = redMovesRight;
				}
				if (spriteChanger == 3) {
					sprite = redMovesRight2;
				}
				break;

			}
		}
		graphics2D.drawImage(sprite, playerPositionXInPanel, playerPositionYInPanel, 60, 67, null);
	}
}
