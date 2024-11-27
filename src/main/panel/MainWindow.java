package main.panel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pokemonFight.manager.FightManager;
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
			System.exit(0);
		}
		pack();

		setLocationRelativeTo(null);

		StatusSingleton.getInstance().setMainWindow(this);
	}

	public void setMainPanel() {
		// Primero, paramos el hilo del combate y ocultamos el panel
		fightPanel.setVisible(false);

		// despues, mostramos el siguiente panel y reanudamos el hilo
		mainPanel.run();
		mainPanel.setVisible(true);
	}

	public void setFightPanel() {
		try {
			// Si el panel no esta creado, el Manager tampoco lo esta, por lo que deberemos
			// crear ambos
			if (null == fightPanel) {
				fightPanel = new FightPanel();
				fightPanel.fightManager = new FightManager();
				add(fightPanel);
			}
			// Primero, ocultamos un panel, paramos el hilo, y reanudamos el hilo anterior,
			// y mostramos el panel
			mainPanel.exploring = false;
			mainPanel.setVisible(false);
			
			fightPanel.fightManager.trainerBattle();
			fightPanel.setVisible(true);

			// Para cerrar la ventana
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					fightPanel.stopBattle();
					System.exit(0);
				}
			});
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar el panel de combate", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}

}
