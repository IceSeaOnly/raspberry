package site.binghai.pi.speaker.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by IceSea on 2017/12/8.
 * GitHub: https://github.com/IceSeaOnly
 */
public class Mp3Player {
    private String filePath;

    public Mp3Player(String filePath) {
        this.filePath = filePath;
    }

    public void play() throws JavaLayerException, FileNotFoundException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filePath));
        Player player = new Player(buffer);
        player.play();
    }
}
