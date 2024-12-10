package main.manager.pojos;

import java.awt.Rectangle;

public class OverMapEntities {

	public int entityWorldX = 1;
	public int entityWorldY = 0;

	// La hitbox de nuestro personaje, definida como un rectangulo mas pequeño que
	// el sprite del mismo, para poder ser un poco mas permisivos a la hora de
	// chocar contra algun objeto
	public Rectangle characterHitBox = new Rectangle(8, 16, 40, 44);

	public Rectangle getCharacterHitBox() {
		return characterHitBox;
	}

	public void setCharacterHitBox(Rectangle characterHitBox) {
		this.characterHitBox = characterHitBox;
	}

	public boolean collisioned = false;

	public int getCharacterWorldX() {
		return entityWorldX;
	}

	public void setCharacterWorldX(int characterWorldX) {
		this.entityWorldX = characterWorldX;
	}

	public int getCharacterWorldY() {
		return entityWorldY;
	}

	public void setCharacterWorldY(int characterWorldY) {
		this.entityWorldY = characterWorldY;
	}

	public boolean isCollisioned() {
		return collisioned;
	}

	public void setCollisioned(boolean collisioned) {
		this.collisioned = collisioned;
	}

}
