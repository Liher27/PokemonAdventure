package main.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.manager.TileManager;
import pokemonFight.manager.PokemonManager;
import pokemonFight.manager.StatusSingleton;
import pokemonFight.manager.pojo.Pokemon;

public class LoadingAssets {

	public TileManager tileManager = null;
	
	public void loadAssests() throws IOException {
		new Thread(() -> {
			try {
				tileManager = new TileManager(StatusSingleton.getInstance().getMainPanel());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		selectTeamPokemons();
	};
	
	/**
	 * metodo inicial para seleccionar los pokemon
	 * 
	 * @param message
	 * @return equipo inicial para el combate
	 * @throws IOException
	 */
	private List<Pokemon> selectTeamPokemons() throws IOException {
		JOptionPane.showMessageDialog(null, "Selecciona tu equipo!", "Bienvenido!!", JOptionPane.INFORMATION_MESSAGE);
		List<Pokemon> selectablePokemon = new PokemonManager().getPokemons();
		
		JPanel panel = new JPanel();
		String[] options;
		
		panel.add(new JLabel("Selecciona un Pokémon:"));

		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
		for (Pokemon p : selectablePokemon) {
			comboBoxModel.addElement(p.getPokemonName());
		}

		JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
		panel.add(comboBox);

		List<Pokemon> selectedPokemons = new ArrayList<>();

		for (int i = 0; i < 6; i++) {

			if (selectedPokemons.isEmpty()) {
				options = new String[] { "OK" };
			} else {
				options = new String[] { "OK", "Cancelar" };
			}

			int result = JOptionPane.showOptionDialog(null, panel, "Seleccionar Pokémon", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if (result == 0) {
				int selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex != -1) {
					Pokemon cloningPokemon = selectablePokemon.get(selectedIndex);
					selectedPokemons.add(new Pokemon(cloningPokemon.getPokemonLvl(), cloningPokemon.getPokemonHP(),
							cloningPokemon.getPokemonSpeed(), cloningPokemon.getPokemonDefense(),
							cloningPokemon.getPokemonAttackStat(), cloningPokemon.getPokemonName(),
							cloningPokemon.getPokemonAttack1(), cloningPokemon.getPokemonAttack2(),
							cloningPokemon.getPokemonAttack3(), cloningPokemon.getPokemonAttack4(),
							cloningPokemon.getPokemonFront(), cloningPokemon.getPokemonBack()));
				}
			} else {
				break;
			}
		}
		return selectedPokemons;
	}
	
}
