package main.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import pokemonFight.manager.StatusSingleton;

/**
 * Clase utilizada para recibir la informacion que se le pasa por teclado
 */
public class KeyBoard implements KeyListener {

	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;
	public boolean leftPressed = false;

	@Override
	public void keyTyped(KeyEvent e) {
		// no lo usaremos
	}

	/**
	 * Metodo implementado por "KeyListener", sirve para indicar que deberia de
	 * hacerse cuando una tecla es pulsada
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_T) {
			if (null == StatusSingleton.getInstance().getPokemonTeam()) {
				try {
					StatusSingleton.getInstance().setPokemonTeam(
							StatusSingleton.getInstance().getMainPanel().selectTeamPokemons("Selecciona tu equipo!"));
//					StatusSingleton.getInstance().getMainPanel().tileManager.loadMap();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * Metodo implementado por "KeyListener", sirve para indicar que deberia de
	 * hacerse cuando una tecla deja de ser pulsada
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
	}

	public void resetKeys() {
		upPressed = false;
		downPressed = false;
		rightPressed = false;
		leftPressed = false;
	}
}
