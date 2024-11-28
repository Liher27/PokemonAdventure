package main.panel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pokemonFight.manager.StatusSingleton;
import pokemonFight.view.FightPanel;

/**
 * Clase para crear el frame y añadirle el Panel
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	FightPanel fightPanel = null;
	MainPanel mainPanel = null;

	/**
	 * constructor
	 */
	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Rojo se mueve (Ayuda)");
		try {
			mainPanel = new MainPanel();
			add(mainPanel);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar el panel de aventura correctamente", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		pack();

		setLocationRelativeTo(null);

		StatusSingleton.getInstance().setMainWindow(this);
	}

	public void setMainPanel() {
		// Primero, paramos el hilo del combate y ocultamos el panel
		fightPanel.setVisible(false);
		fightPanel.songController.stop();
		// despues, mostramos el siguiente panel y reanudamos el hilo
		mainPanel.setVisible(true);
		mainPanel.keyBoard.resetKeys();
		mainPanel.run();
	}

	public void setFightPanel() {
		try {
			// Si el panel no esta creado, lo deberemos crear
			if (null == fightPanel) {
				fightPanel = new FightPanel();
				add(fightPanel);
			}
			// Primero, paramos el hilo, ocultamos el panel de aventura, ejecutamos la logica de combate y de musica, y mostramos el panel
			mainPanel.exploring = false;
			mainPanel.setVisible(false);

			fightPanel.fightManager.trainerBattle();
			fightPanel.songController.playRandomSong();
			fightPanel.setVisible(true);

			// Para cerrar la ventana
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					fightPanel.stopBattle();
					System.exit(0);
				}
			});
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar el panel de combate", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}

}
