package pokemonFight.manager;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import pokemonFight.manager.pojo.Attack;
import pokemonFight.manager.pojo.Pokemon;
import pokemonFight.view.FightPanel;

public class FightManager {

	private boolean turn = false;

	public SwingWorker<Void, Void> battleWorker = null;

	public List<Pokemon> allyPokemonTeam = null;

	private FightPanel fightPanel = null;

	private Pokemon wildPokemon = null;

	public FightManager() throws IOException {
		fightPanel = StatusSingleton.getInstance().getFightPanel();

		allyPokemonTeam = StatusSingleton.getInstance().getPokemonTeam();
	}

	public void createWildPokemon() throws IOException {
		wildPokemon = new PokemonManager().getPokemons().get(new Random().nextInt(151));

		StatusSingleton.getInstance().setWildPokemon(wildPokemon);

		turn = allyPokemonTeam.get(0).getPokemonSpeed() >= wildPokemon.getPokemonSpeed();
	}

	public void loadInfo() throws NullPointerException, IOException {
		fightPanel.allyPokemonLifeBar.setMaximum(allyPokemonTeam.get(0).getPokemonHP());
		fightPanel.allyPokemonLifeBar.setValue(allyPokemonTeam.get(0).getPokemonHP());
		fightPanel.enemyPokemonLifeBar.setMaximum(wildPokemon.getPokemonHP());
		fightPanel.enemyPokemonLifeBar.setValue(wildPokemon.getPokemonHP());
		fightPanel.enemyLvlLbl.setText(wildPokemon.getPokemonLvl() + "");
		fightPanel.allyLvlLbl.setText(allyPokemonTeam.get(0).getPokemonLvl() + "");
		fightPanel.enemyPokemonName.setText(wildPokemon.getPokemonName());
		fightPanel.allyPokemonName.setText(allyPokemonTeam.get(0).getPokemonName());
		new ImageManager().loadImages();
	}

	public void trainerBattle() throws IOException {
		createWildPokemon();
		loadInfo();

		battleWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				boolean continueBattle = true;
				while (continueBattle) {
					if (battleEnded()) {
						continueBattle = false;
					}
					SwingUtilities.invokeLater(() -> handleTurn(turn));
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						break;
					}
				}
				return null;
			}
		};

		battleWorker.execute();
	}

	private boolean battleEnded() {

		boolean ret = false;
		if (wildPokemon.getPokemonHP() <= 0) {
			JOptionPane.showMessageDialog(null, "El equipo local ha ganado el combate!!!", "Enhorabuena!!!",
					JOptionPane.INFORMATION_MESSAGE);

			StatusSingleton.getInstance().getMainWindow().setMainPanel();
			ret = true;
		}

		if (allyPokemonTeam.get(0).getPokemonHP() <= 0) {
			if (allyPokemonTeam.size() > 1) {
				allyPokemonTeam.remove(0);
				fightPanel.changePokemon(allyPokemonTeam);
				refreshOverlayData(true);
				turn = !turn;
			} else {
				JOptionPane.showMessageDialog(null,
						"El enemigo ha debilitado a todos tus pokemon, No podras entrar mas en combate...", "GAME OVER",
						JOptionPane.INFORMATION_MESSAGE);

				allyPokemonTeam.remove(0);

				StatusSingleton.getInstance().getMainWindow().setMainPanel();

				ret = true;
			}
		}
		StatusSingleton.getInstance().setPokemonTeam(allyPokemonTeam);
		return ret;
	}

	private void handleTurn(boolean isAllyTurn) {
		if (isAllyTurn) {
			fightPanel.decissionTextLbl.setText("¿Qué debería hacer " + allyPokemonTeam.get(0).getPokemonName() + "?");
			setupAttackButtons(allyPokemonTeam.get(0), isAllyTurn);
		} else if (!isAllyTurn) {
			wildPokemonAttack(isAllyTurn);
		}
	}

	private void setupAttackButtons(Pokemon pokemon, boolean isAlly) {
		JLabel[] buttonList = { fightPanel.attackBtn_1, fightPanel.attackBtn_2, fightPanel.attackBtn_3,
				fightPanel.attackBtn_4, fightPanel.swapBtn, fightPanel.defendBtn };
		removeAllMouseListeners(buttonList);

		if (pokemon.getPokemonAttack1() != null) {
			fightPanel.attackBtn_1.setText(pokemon.getPokemonAttack1().getAttackName());
			fightPanel.attackBtn_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doDamage(pokemon.getPokemonAttack1(), isAlly);
					turn = !turn;
				}
			});
		} else
			fightPanel.attackBtn_1.setText("");

		if (pokemon.getPokemonAttack2() != null) {
			fightPanel.attackBtn_2.setText(pokemon.getPokemonAttack2().getAttackName());
			fightPanel.attackBtn_2.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					doDamage(pokemon.getPokemonAttack2(), isAlly);
					turn = !turn;
				}
			});
		} else
			fightPanel.attackBtn_2.setText("");

		if (pokemon.getPokemonAttack3() != null) {
			fightPanel.attackBtn_3.setText(pokemon.getPokemonAttack3().getAttackName());
			fightPanel.attackBtn_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doDamage(pokemon.getPokemonAttack3(), isAlly);
					turn = !turn;
				}
			});
		} else
			fightPanel.attackBtn_3.setText("");

		if (pokemon.getPokemonAttack4() != null) {
			fightPanel.attackBtn_4.setText(pokemon.getPokemonAttack4().getAttackName());
			fightPanel.attackBtn_4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doDamage(pokemon.getPokemonAttack4(), isAlly);
					turn = !turn;
				}
			});
		} else
			fightPanel.attackBtn_4.setText("");

		fightPanel.defendBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleDefense(isAlly);
				turn = !turn;
			}
		});

		fightPanel.swapBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (allyPokemonTeam.size() > 1) {
					allyPokemonTeam = fightPanel.changePokemon(allyPokemonTeam);
					refreshOverlayData(isAlly);
				} else {
					writeLog(isAlly, "No quedan pokemon en tu equipo...!!! \n");
				}
			}

		});
	}

	private void removeAllMouseListeners(JLabel[] buttonList) {
		for (int i = 0; i < buttonList.length; i++) {
			for (MouseListener ml : buttonList[i].getMouseListeners()) {
				buttonList[i].removeMouseListener(ml);
			}
		}
	}

	private void wildPokemonAttack(boolean isAlly) {
		int moveCount = 0;
		if (wildPokemon.getPokemonAttack1() != null)
			moveCount++;
		if (wildPokemon.getPokemonAttack2() != null)
			moveCount++;
		if (wildPokemon.getPokemonAttack3() != null)
			moveCount++;
		if (wildPokemon.getPokemonAttack4() != null)
			moveCount++;

		doDamage(wildPokemon.getPokemonAttack(new Random().nextInt(moveCount)), isAlly);
		turn = !turn;
	}

	/**
	 * Refresca la pantalla con nueva información de los pokemon.
	 * 
	 * @param allyPokemonTeam
	 */
	public void refreshOverlayData(boolean ally) {
		if (ally) {
			fightPanel.allyPokemonLifeBar.setMaximum(allyPokemonTeam.get(0).getPokemonHP());
			fightPanel.allyPokemonLifeBar.setMinimum(0);
			fightPanel.allyPokemonLifeBar.repaint();
			fightPanel.allyPokemonName.setText(allyPokemonTeam.get(0).getPokemonName());
			fightPanel.allyLvlLbl.setText(allyPokemonTeam.get(0).getPokemonLvl() + "");
			fightPanel.decissionTextLbl.setText("¿Qué debería hacer " + allyPokemonTeam.get(0).getPokemonName() + "?");
			fightPanel.scaledAllyIcon = new ImageIcon(allyPokemonTeam.get(0).getPokemonBack()
					.getScaledInstance(fightPanel.newWidth, fightPanel.newHeight, Image.SCALE_SMOOTH));
			fightPanel.allySprite.setIcon(fightPanel.scaledAllyIcon);
			turn = !turn;
		} else {
			fightPanel.enemyPokemonLifeBar.setMaximum(wildPokemon.getPokemonHP());
			fightPanel.enemyPokemonLifeBar.setMinimum(0);
			fightPanel.enemyPokemonLifeBar.repaint();
			fightPanel.enemyPokemonName.setText(wildPokemon.getPokemonName());
			turn = !turn;
		}
	}

	private void doDamage(Attack attack, boolean isAlly) {
		Pokemon attacker = isAlly ? allyPokemonTeam.get(0) : wildPokemon;
		Pokemon target = isAlly ? wildPokemon : allyPokemonTeam.get(0);

		long damage = calculateAttackDamage(attack, target, attacker);
		if (damage > 0) {
			updatePokemonHP(target, damage, isAlly);
			writeLog(isAlly, attack.getAttackName() + " ha hecho " + damage + " de daño! \n");
		} else {
			writeLog(isAlly, attack.getAttackName() + " ha fallado! \n");
		}
	}

	private long calculateAttackDamage(Attack usedAttack, Pokemon deffensorPokemon, Pokemon attackingPokemon) {
		boolean hit = new Random().nextInt(100) < usedAttack.getAttackPrecission();
		if (!hit)
			return 0;

		long baseDamage = Math.round((((0.2 * attackingPokemon.getPokemonLvl() + 1) * usedAttack.getAttackPotency()
				* attackingPokemon.getPokemonAttackStat()) / (40 * deffensorPokemon.getPokemonDefense() + 2)));

		return baseDamage;
	}

	private void updatePokemonHP(Pokemon target, long damage, boolean isAlly) {
		int newHealth = Math.max(0, target.getPokemonHP() - (int) damage);
		target.setPokemonHP(newHealth);

		if (isAlly) {
			fightPanel.enemyPokemonLifeBar.setValue(newHealth);
			fightPanel.enemyPokemonLifeBar.repaint();
		} else {
			fightPanel.allyPokemonLifeBar.setValue(newHealth);
			fightPanel.allyPokemonLifeBar.repaint();
		}
	}

	private void writeLog(boolean isAlly, String log) {
		if (isAlly) {
			fightPanel.textArea.setText("");
			fightPanel.textArea.append(log);
		} else {
			fightPanel.decissionTextLbl.setText(log);
		}
	}

	private void handleDefense(boolean isAlly) {
		if (isAlly) {
			int defenseUp = (int) (allyPokemonTeam.get(0).getPokemonDefense() * 1.75);
			allyPokemonTeam.get(0).setPokemonDefense(defenseUp);
		} else {
			int defenseUp = (int) (wildPokemon.getPokemonDefense() * 1.75);
			wildPokemon.setPokemonDefense(defenseUp);
		}
		writeLog(isAlly, "Tu defensa ha aumentado!!! \n");
	}
}