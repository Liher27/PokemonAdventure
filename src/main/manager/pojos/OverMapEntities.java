package main.manager.pojos;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class OverMapEntities {

	public int entityWorldX = 1;
	public int entityWorldY = 0;

	public BufferedImage sprite = null;

	public boolean collisioned = false;

	// La hitbox de nuestro personaje, definida como un rectangulo mas pequeño que
	// el sprite del mismo, para poder ser un poco mas permisivos a la hora de
	// chocar contra algun objeto
	public Rectangle characterHitBox = new Rectangle(8, 16, 40, 44);

	public void setCharacterHitBox(Rectangle characterHitBox) {
		this.characterHitBox = characterHitBox;
	}

	public void setCharacterWorldX(int characterWorldX) {
		this.entityWorldX = characterWorldX;
	}

	public void setCharacterWorldY(int characterWorldY) {
		this.entityWorldY = characterWorldY;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
}
