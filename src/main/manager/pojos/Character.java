package main.manager.pojos;

import java.awt.image.BufferedImage;

/**
 * Pojo de los personajes del programa, para añadir mas personajes proximamente
 */
public class Character extends OverMapEntities {

	public int speed = 0;

	public BufferedImage redStill = null;
	public BufferedImage redMovesDown1 = null;
	public BufferedImage redMovesDown2 = null;
	public BufferedImage redStillLeft = null;
	public BufferedImage redMovesLeft = null;
	public BufferedImage redMovesLeft2 = null;
	public BufferedImage redStillRight = null;
	public BufferedImage redMovesRight = null;
	public BufferedImage redMovesRight2 = null;
	public BufferedImage redStillUp = null;
	public BufferedImage redMovesUp1 = null;
	public BufferedImage redMovesUp2 = null;
	public BufferedImage waterPokemon1 = null;
	public BufferedImage waterPokemon2 = null;

	public String direction = null;

	public int spriteCounter = 0;

	public int spriteChanger = 1;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public BufferedImage getRedStill() {
		return redStill;
	}

	public void setRedStill(BufferedImage redStill) {
		this.redStill = redStill;
	}

	public BufferedImage getRedMovesDown1() {
		return redMovesDown1;
	}

	public void setRedMovesDown1(BufferedImage redMovesDown1) {
		this.redMovesDown1 = redMovesDown1;
	}

	public BufferedImage getRedMovesDown2() {
		return redMovesDown2;
	}

	public void setRedMovesDown2(BufferedImage redMovesDown2) {
		this.redMovesDown2 = redMovesDown2;
	}

	public BufferedImage getRedStillLeft() {
		return redStillLeft;
	}

	public void setRedStillLeft(BufferedImage redStillLeft) {
		this.redStillLeft = redStillLeft;
	}

	public BufferedImage getRedMovesLeft() {
		return redMovesLeft;
	}

	public void setRedMovesLeft(BufferedImage redMovesLeft) {
		this.redMovesLeft = redMovesLeft;
	}

	public BufferedImage getRedMovesLeft2() {
		return redMovesLeft2;
	}

	public void setRedMovesLeft2(BufferedImage redMovesLeft2) {
		this.redMovesLeft2 = redMovesLeft2;
	}

	public BufferedImage getRedStillRight() {
		return redStillRight;
	}

	public void setRedStillRight(BufferedImage redStillRight) {
		this.redStillRight = redStillRight;
	}

	public BufferedImage getRedMovesRight() {
		return redMovesRight;
	}

	public void setRedMovesRight(BufferedImage redMovesRight) {
		this.redMovesRight = redMovesRight;
	}

	public BufferedImage getRedStillUp() {
		return redStillUp;
	}

	public void setRedStillUp(BufferedImage redStillUp) {
		this.redStillUp = redStillUp;
	}

	public BufferedImage getRedMovesUp1() {
		return redMovesUp1;
	}

	public void setRedMovesUp1(BufferedImage redMovesUp1) {
		this.redMovesUp1 = redMovesUp1;
	}

	public BufferedImage getRedMovesUp2() {
		return redMovesUp2;
	}

	public void setRedMovesUp2(BufferedImage redMovesUp2) {
		this.redMovesUp2 = redMovesUp2;
	}

	public BufferedImage getWaterPokemon1() {
		return waterPokemon1;
	}

	public void setWaterPokemon1(BufferedImage waterPokemon1) {
		this.waterPokemon1 = waterPokemon1;
	}

	public BufferedImage getWaterPokemon2() {
		return waterPokemon2;
	}

	public BufferedImage getRedMovesRight2() {
		return redMovesRight2;
	}

	public void setRedMovesRight2(BufferedImage redMovesRight2) {
		this.redMovesRight2 = redMovesRight2;
	}

	public void setWaterPokemon2(BufferedImage waterPokemon2) {
		this.waterPokemon2 = waterPokemon2;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getSpriteCounter() {
		return spriteCounter;
	}

	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}

	public int getSpriteChanger() {
		return spriteChanger;
	}

	public void setSpriteChanger(int spriteChanger) {
		this.spriteChanger = spriteChanger;
	}

}
