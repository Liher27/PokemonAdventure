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

	public final String[] songs = { "fights/contents/songs/Black.wav", "fights/contents/songs/Blasco.wav",
			"fights/contents/songs/Kanto.wav", "fights/contents/songs/Rayquaza.wav", "fights/contents/songs/Red.wav" };

	private Clip clip = null;

	
	public static void playMusic(String location)
			throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File musicPath = new File(location);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}

	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
			clip.setFramePosition(0);
		}
	}

	public void playRandomSong() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Random random = new Random();
		int randomIndex = random.nextInt(songs.length);
		playMusic(songs[randomIndex]);
	}
}
