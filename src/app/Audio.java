package app;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Audio {
	
	private static Clip currentClip;
	static final URL PLAYER = Audio.class.getResource("/playerBeep.wav");
	static final URL AI = Audio.class.getResource("/aiBeep.wav");
	static final URL FAILURE = Audio.class.getResource("/failure.wav");
	static final URL VICTORY = Audio.class.getResource("/victory.wav");
	static final URL MATCH = Audio.class.getResource("/startMatch.wav");
	static final URL DRAW = Audio.class.getResource("/draw.wav");
	static final URL PRESSED = Audio.class.getResource("/buttonPressed.wav");
	
	Audio() {
		
	}
	
	void sound(URL clap) {
		
		try {
			currentClip = AudioSystem.getClip();
			currentClip.open(AudioSystem.getAudioInputStream(clap));
			currentClip.start();

			Thread.sleep(currentClip.getMicrosecondLength() / 1000);

		} catch (Exception e) {

		}
				
	}
	
}
