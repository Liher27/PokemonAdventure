package main.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import main.logic.CollisionDetector;
import main.logic.KeyBoard;
import main.logic.LoadingAssets;
import main.manager.PlayerManager;
import main.manager.TileManager;
import pokemonFight.manager.PokemonManager;
import pokemonFight.manager.StatusSingleton;
import pokemonFight.manager.pojo.Pokemon;

/**
 * Clase en la que se crea el panel
 */
public class MainPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public Thread gameThread = null; // Hilo sobre el cual correra el juego

	public KeyBoard keyBoard = null;
	public PlayerManager player = null;
	public LoadingAssets loader = null;
	
	// definir un sprite de 16x16 bloques
	private final int originalTileSize = 16;
	private final int playerTileSize = 18;
	public boolean exploring = false;

	// tamaño de escalada para los bloques de sprites, ya que un sprite de 16x16
	// se vería demasiado pequeño en las pantallas de 1920x1080
	private final int scale = 3;
	public final int tileSize = originalTileSize * scale;// tamaño escalado
	public final int playerSize = playerTileSize * scale;//Tamaño del jugado escalado

	// Resolución de la pantalla, 4:3 en este caso.
	public final int maxScreenColumn = 16;
	public final int maxScreenRow = 14;
	public final int screenWidth = tileSize * maxScreenColumn; // Para que los 16 bloques ocupen en total 760 pixeles
	public final int screenHeight = tileSize * maxScreenRow; // Para que los 12 bloques originales ocupen 576 pixeles

	public final int maxWorldColumn = 200;
	public final int maxWorldRow = 200;
	public final int worldScreenWidth = tileSize * maxWorldColumn;
	public final int worldScreenHeight = tileSize * maxWorldRow;

	// Para delimitar el ratio de refresco de la pantalla
	private final int FPS = 60;

	/**
	 * Constructor de la clase GamePanel
	 * 
	 * @throws IOException
	 */
	public MainPanel() throws Exception {
		keyBoard = new KeyBoard();
		player = new PlayerManager(keyBoard, this);
		loader = new LoadingAssets();

		setPreferredSize(new Dimension(800, 700));
		setBackground(Color.white);
		setDoubleBuffered(true);
		addKeyListener(keyBoard);
		StatusSingleton.getInstance().setMainPanel(this);

		//Para modular un poco el codigo, he decidido separar la carga de contenido a otra clase
		loader.loadAssests();
		startGameThread();
	}

	/**
	 * Crea el hilo y se inicia
	 * 
	 * @throws Exception
	 */
	public void startGameThread() throws Exception {
		// instanciar el hilo
		gameThread = new Thread(this);

		gameThread.start();
	}

	/**
	 * Metodo que implementa "Runnable", para iniciar el hilo
	 */
	@Override
	public void run() {
		exploring = true;
		setFocusable(true);
		requestFocus();
		while (null != gameThread) {
			while (exploring) {
				// Aquí se ejecutará el bucle principal sobre el que el juego se inicia,
				// mecanica basica sobre la cual funcionan muchos juegos.

				// Primero se refresca la informacion, y después se pinta la informacion
				// recogida.
				update();
				repaint();
				refrescoPantalla();
			}
		}
	}

	/**
	 * Para limitar cada cuanto tiempo se refresca la pantalla (60FPS) , sino se
	 * refrescaria millones de veces por segundo
	 */
	private void refrescoPantalla() {
		double drawInterval = 1000000000.0 / FPS;
		long lastTime = System.nanoTime();

		while (gameThread != null) {
			long now = System.nanoTime();
			double delta = (now - lastTime) / drawInterval;

			if (delta >= 1) {
				update();
				repaint();
				lastTime = now;
			}
		}
	}

	/**
	 * Metodo usado normalmente con los hilos para recoger la nueva informacion que
	 * se le pase por teclado
	 */
	private void update() {
		player.updateSprite();

	}

	/**
	 * Al igual que update, se usa mucho con los hilos y se utiliza para pintar por
	 * pantalla la informacion conseguida en el anterior metodo
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D graphics2D = (Graphics2D) graphics;
		if (null != loader.tileManager)
			loader.tileManager.drawTiles(graphics2D);
		player.draw(graphics2D);
		graphics2D.dispose();
	}
}
