package pokemonFight.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import pokemonFight.controller.SongController;
import pokemonFight.manager.FightManager;
import pokemonFight.manager.ImageManager;
import pokemonFight.manager.StatusSingleton;
import pokemonFight.manager.pojo.Pokemon;
import javax.swing.JTextArea;

public class FightPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Pokemon allyPokemon = null;
	public Pokemon enemyPokemon = null;

	public FightManager fightManager = null;

	public JLabel allyLvlLbl = null;
	public JLabel enemyLvlLbl = null;
	public JLabel enemyPokemonName = null;
	public JLabel allyPokemonName = null;
	public JLabel enemySprite = null;
	public JLabel allySprite = null;
	public JLabel decissionTextLbl = null;
	public JLabel attackBtn = null;
	public JLabel attackBtn_1 = null;
	public JLabel attackBtn_2 = null;
	public JLabel attackBtn_3 = null;
	public JLabel attackBtn_4 = null;
	public JLabel swapBtn = null;
	public JLabel itemBtn = null;
	public JLabel defendBtn = null;

	public JTextArea textArea = null;

	public JProgressBar allyPokemonLifeBar = null;
	public JProgressBar enemyPokemonLifeBar = null;

	public ImageIcon scaledEnemyIcon = null;
	public ImageIcon scaledAllyIcon = null;

	public int newWidth = 150;
	public int newHeight = 150;

	public SongController songController = null;

	public FightPanel() throws IOException {
		setLayout(null);
		setDoubleBuffered(true);
		setFocusable(false);

		textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setForeground(new Color(107, 142, 35));
		textArea.setBounds(10, 126, 210, 50);
		add(textArea);

		enemyLvlLbl = new JLabel();
		enemyLvlLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		enemyLvlLbl.setBounds(244, 11, 28, 51);
		add(enemyLvlLbl);

		allyLvlLbl = new JLabel();
		allyLvlLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		allyLvlLbl.setBounds(747, 310, 28, 51);
		add(allyLvlLbl);

		allyPokemonName = new JLabel();
		allyPokemonName.setFont(new Font("Tahoma", Font.BOLD, 18));
		allyPokemonName.setHorizontalAlignment(SwingConstants.CENTER);
		allyPokemonName.setBounds(510, 316, 163, 37);
		add(allyPokemonName);

		enemyPokemonLifeBar = new JProgressBar();
		enemyPokemonLifeBar.setForeground(Color.green);
		enemyPokemonLifeBar.setBounds(114, 53, 144, 20);
		add(enemyPokemonLifeBar);

		allyPokemonLifeBar = new JProgressBar();
		allyPokemonLifeBar.setForeground(Color.green);
		allyPokemonLifeBar.setBounds(622, 350, 144, 20);
		add(allyPokemonLifeBar);

		enemyPokemonName = new JLabel();
		enemyPokemonName.setHorizontalAlignment(SwingConstants.CENTER);
		enemyPokemonName.setFont(new Font("Tahoma", Font.BOLD, 18));
		enemyPokemonName.setBounds(-10, 11, 163, 37);
		add(enemyPokemonName);

		scaledEnemyIcon = new ImageIcon();

		enemySprite = new JLabel("");
		enemySprite.setBounds(432, 122, 164, 145);
		add(enemySprite);

		scaledAllyIcon = new ImageIcon();

		allySprite = new JLabel("");
		allySprite.setBounds(136, 309, 164, 145);
		add(allySprite);

		JLabel allyPokemonLifeBarLbl = new JLabel();
		allyPokemonLifeBarLbl.setBounds(488, 306, 299, 111);
		allyPokemonLifeBarLbl.setIcon(new ImageIcon("contents/pokemonStatus/pokemonLifeBar.png"));
		add(allyPokemonLifeBarLbl);

		JLabel enemyLifeBarLbl = new JLabel();
		enemyLifeBarLbl.setBounds(0, 11, 299, 80);
		enemyLifeBarLbl.setIcon(new ImageIcon("contents/pokemonStatus/enemyLifeBar.png"));
		add(enemyLifeBarLbl);

		JLabel fightBackgroundLbl = new JLabel("");
		fightBackgroundLbl.setIcon(getBackgroundImage());
		fightBackgroundLbl.setBounds(0, 0, 800, 454);
		add(fightBackgroundLbl);

		decissionTextLbl = new JLabel();
		decissionTextLbl.setForeground(new Color(255, 255, 255));
		decissionTextLbl.setHorizontalAlignment(SwingConstants.CENTER);
		decissionTextLbl.setFont(new Font("Tahoma", Font.BOLD, 24));
		decissionTextLbl.setBounds(10, 443, 458, 157);
		add(decissionTextLbl);

		attackBtn_1 = new JLabel();
		attackBtn_1.setHorizontalAlignment(SwingConstants.CENTER);
		attackBtn_1.setForeground(Color.RED);
		attackBtn_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		attackBtn_1.setBounds(68, 463, 163, 55);
		add(attackBtn_1);
		attackBtn_1.setVisible(false);

		attackBtn_2 = new JLabel();
		attackBtn_2.setHorizontalAlignment(SwingConstants.CENTER);
		attackBtn_2.setForeground(Color.RED);
		attackBtn_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		attackBtn_2.setBounds(68, 530, 163, 55);
		add(attackBtn_2);
		attackBtn_2.setVisible(false);

		attackBtn_3 = new JLabel();
		attackBtn_3.setHorizontalAlignment(SwingConstants.CENTER);
		attackBtn_3.setForeground(Color.RED);
		attackBtn_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		attackBtn_3.setBounds(337, 463, 163, 55);
		add(attackBtn_3);
		attackBtn_3.setVisible(false);

		attackBtn_4 = new JLabel();
		attackBtn_4.setHorizontalAlignment(SwingConstants.CENTER);
		attackBtn_4.setForeground(Color.RED);
		attackBtn_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		attackBtn_4.setBounds(337, 530, 163, 55);
		add(attackBtn_4);
		attackBtn_4.setVisible(false);

		swapBtn = new JLabel("Cambiar");
		swapBtn.setHorizontalAlignment(SwingConstants.CENTER);
		swapBtn.setForeground(new Color(237, 230, 80));
		swapBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		swapBtn.setBounds(652, 463, 133, 55);
		add(swapBtn);

		itemBtn = new JLabel("Objetos");
		itemBtn.setHorizontalAlignment(SwingConstants.CENTER);
		itemBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		itemBtn.setBounds(667, 530, 133, 55);
		add(itemBtn);

		defendBtn = new JLabel("Defenderse");
		defendBtn.setHorizontalAlignment(SwingConstants.CENTER);
		defendBtn.setForeground(new Color(0, 128, 255));
		defendBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		defendBtn.setBounds(510, 530, 133, 55);
		add(defendBtn);

		JLabel backBtn = new JLabel("Atras");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				decissionTextLbl.setVisible(true);
				itemBtn.setVisible(true);
				swapBtn.setVisible(true);
				defendBtn.setVisible(true);
				attackBtn.setVisible(true);
				if (checkAttackButtons()) {
					attackBtn_1.setVisible(false);
					attackBtn_2.setVisible(false);
					attackBtn_3.setVisible(false);
					attackBtn_4.setVisible(false);
				}
				backBtn.setVisible(false);
			}
		});
		backBtn.setHorizontalAlignment(SwingConstants.CENTER);
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		backBtn.setBounds(667, 530, 133, 55);
		add(backBtn);
		backBtn.setVisible(false);

		attackBtn = new JLabel("Atacar");
		attackBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				decissionTextLbl.setVisible(false);
				itemBtn.setVisible(false);
				swapBtn.setVisible(false);
				defendBtn.setVisible(false);
				attackBtn.setVisible(false);
				backBtn.setVisible(true);
				if (checkAttackButtons()) {
					attackBtn_1.setVisible(true);
					attackBtn_2.setVisible(true);
					attackBtn_3.setVisible(true);
					attackBtn_4.setVisible(true);
				}
			}
		});

		attackBtn.setHorizontalAlignment(SwingConstants.CENTER);
		attackBtn.setForeground(new Color(255, 0, 0));
		attackBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		attackBtn.setBounds(488, 464, 133, 55);
		add(attackBtn);

		JLabel layoutFirstClrLbl = new JLabel("");
		layoutFirstClrLbl.setForeground(new Color(0, 0, 0));
		layoutFirstClrLbl.setFont(new Font("Tahoma", Font.BOLD, 28));
		layoutFirstClrLbl.setHorizontalAlignment(SwingConstants.CENTER);
		layoutFirstClrLbl.setIcon(new ImageIcon("contents/layoutColours/DecissionMenuClr.png"));
		layoutFirstClrLbl.setBounds(0, 443, 800, 157);
		add(layoutFirstClrLbl);

		StatusSingleton.getInstance().setFightPanel(this);

		setPreferredSize(new Dimension(800, 600));

		if (!StatusSingleton.getInstance().getPokemonTeam().isEmpty()) {
			fightManager = new FightManager();
			songController = new SongController();
		} else {
			JOptionPane.showMessageDialog(null, "No tienes pokemon en el equipo!", "ERROR!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Metodo para conseguir un fondo random de la galeria
	 * 
	 * @return Imagen de fondo
	 */
	public ImageIcon getBackgroundImage() {
		ImageIcon background = null;
		List<ImageIcon> backgrounds = new ArrayList<>();
		Random randomNumberToExecute = new Random();
		int randomBackground = randomNumberToExecute.nextInt(5);
		backgrounds = new ImageManager().getBackgrounds();
		background = backgrounds.get(randomBackground);
		return background;
	}

	/**
	 * Para cambiar los pokemon del arrayList y sacar a otro.
	 * 
	 * @param team
	 * @return el equipo modificado
	 */
	public List<Pokemon> changePokemon(List<Pokemon> team) {
		String selectablePokemonNames = null;

		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < team.size(); i++) {
			selectablePokemonNames = team.get(i).getPokemonName();
			comboBoxModel.addElement(selectablePokemonNames);
		}

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(comboBoxModel);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Selecciona un Pokémon:"));
		panel.add(comboBox);

		for (int i = 0; i < 6; i++) {
			int result = JOptionPane.showConfirmDialog(null, panel, "Seleccionar Pokémon",
					JOptionPane.OK_CANCEL_OPTION);

			if (result == 0) {
				int selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex != -1) {
					Collections.swap(team, 0, selectedIndex);
					return team;
				}
			} else {
				break;
			}
		}
		return team;
	}

	public void stopBattle() {
		if (fightManager.battleWorker != null && !fightManager.battleWorker.isDone()) {
			fightManager.battleWorker.cancel(true);
		}
	}

	private boolean checkAttackButtons() {
		if (attackBtn_1 != null && attackBtn_1.getText() != null && attackBtn_2 != null && attackBtn_2.getText() != null
				&& attackBtn_3 != null && attackBtn_3.getText() != null && attackBtn_4 != null
				&& attackBtn_4.getText() != null)
			return true;
		else
			return false;
	}
}