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

		wildPokemon = new PokemonManager().getPokemons().get(new Random().nextInt(151));

		allyPokemonTeam = StatusSingleton.getInstance().getPokemonTeam();

		turn = allyPokemonTeam.get(0).getPokemonSpeed() >= wildPokemon.getPokemonSpeed();

		StatusSingleton.getInstance().setWildPokemon(wildPokemon);
	}

	public void loadInfo() throws NullPointerException, IOException, Exception {
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

	public void trainerBattle() {
		battleWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				boolean continueBattle = true;
				do {
					SwingUtilities.invokeLater(() -> handleTurn(turn));
					if (battleEnded()) {
						continueBattle = false;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (continueBattle);
				return null;
			}
		};
		battleWorker.execute();
	}

	private void handleTurn(boolean isAllyTurn) {
		if (isAllyTurn) {
			fightPanel.decissionTextLbl.setText("¿Qué debería hacer " + allyPokemonTeam.get(0).getPokemonName() + "?");
			setupAttackButtons(allyPokemonTeam.get(0), isAllyTurn);
		} else {
			// Cambiar por ataque random de pokemon salvaje
			wildPokemonAttack();
		}
	}

	private void wildPokemonAttack() {
		doDamage(wildPokemon.getPokemonAttack(new Random().nextInt(4)), false);
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

	private boolean battleEnded() {
		if (wildPokemon.getPokemonHP() <= 0) {
			JOptionPane.showMessageDialog(null, "El equipo local ha ganado el combate!!!", "Enhorabuena!!!",
					JOptionPane.INFORMATION_MESSAGE);
			StatusSingleton.getInstance().getMainWindow().setMainPanel();
			System.exit(0);
			return true;
		}

		if (allyPokemonTeam.get(0).getPokemonHP() <= 0) {
			if (allyPokemonTeam.size() > 1) {
				allyPokemonTeam.remove(0);
				fightPanel.changePokemon(allyPokemonTeam);
				refreshOverlayData(true);
				turn = !turn;
			} else {
				JOptionPane.showMessageDialog(null, "El enemigo ha debilitado a todos tus pokemon...", "GAME OVER",
						JOptionPane.INFORMATION_MESSAGE);
				StatusSingleton.getInstance().setPokemonTeam(allyPokemonTeam);
				
				StatusSingleton.getInstance().getMainWindow().setMainPanel();
				return true;
			}
		}
		return false;
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

	// ELIMINAR BOOLEAN PARA QUE NO DEPENDA DE SI ES TURNO DEL USUARIO O DEL
	// POKEMON. ESO SE EMPEZARA A CREAR UN METODO PARA VER QUE MOVIMIENTO ELIGE.
	private void doDamage(Attack pokemonAttack, boolean ally) {
		long dealtAttack = 0;
		Pokemon targetPokemon = null;
		Pokemon attackingPokemon = null;
		if (ally) {
			attackingPokemon = allyPokemonTeam.get(0);
			targetPokemon = wildPokemon;
		} else {
			attackingPokemon = wildPokemon;
			targetPokemon = allyPokemonTeam.get(0);
		}

		dealtAttack = calculateAttackDamage(pokemonAttack, targetPokemon, attackingPokemon);

		if (dealtAttack > 0) {
			calculatePokemonLife(dealtAttack, ally);
			writeLog(ally, (pokemonAttack.getAttackName() + " ha hecho " + dealtAttack + " de daño! \n"));
		} else {
			writeLog(ally, (pokemonAttack.getAttackName() + " ha fallado! \n"));
		}
	}

	private void calculatePokemonLife(long dealtAttack, boolean isAlly) {
		int newHealth = 0;
		if (isAlly) {
			newHealth = wildPokemon.getPokemonHP() - (int) dealtAttack;
			wildPokemon.setPokemonHP(newHealth);
			fightPanel.enemyPokemonLifeBar.setValue(newHealth);
			fightPanel.enemyPokemonLifeBar.revalidate();
			fightPanel.enemyPokemonLifeBar.repaint();
		} else {
			newHealth = allyPokemonTeam.get(0).getPokemonHP() - (int) dealtAttack;
			allyPokemonTeam.get(0).setPokemonHP(newHealth);
			fightPanel.allyPokemonLifeBar.setValue(newHealth);
			fightPanel.allyPokemonLifeBar.revalidate();
			fightPanel.allyPokemonLifeBar.repaint();
		}
	}

	private long calculateAttackDamage(Attack usedAttack, Pokemon deffensorPokemon, Pokemon attackingPokemon) {
		boolean hit = new Random().nextInt(100) < usedAttack.getAttackPrecission();
		if (!hit)
			return 0;

		long baseDamage = Math.round((((0.2 * attackingPokemon.getPokemonLvl() + 1) * usedAttack.getAttackPotency()
				* attackingPokemon.getPokemonAttackStat()) / (25 * deffensorPokemon.getPokemonDefense() + 2)));

		return baseDamage;
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

	private void writeLog(boolean isAlly, String log) {
		if (isAlly) {
			fightPanel.textArea.setText("");
			fightPanel.textArea.append(log);
		} else {
			fightPanel.enemyTextArea.setText("");
			fightPanel.enemyTextArea.append(log);
		}

	}
}