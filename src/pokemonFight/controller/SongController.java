package pokemonFight.controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SongController {

	private final String[] songs = { "contents/songs/Black.wav", "contents/songs/Blasco.wav",
			"contents/songs/Kanto.wav", "contents/songs/Rayquaza.wav", "contents/songs/Red.wav" };

	private Clip clip = null;

	public void playRandomSong() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		int randomIndex = new Random().nextInt(songs.length);
		playMusic(songs[randomIndex]);
	}

	public void playMusic(String location) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File musicPath = new File(location);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}

	public void stop() {
		if (clip.isRunning()) {
			clip.close();
		}
	}
}
