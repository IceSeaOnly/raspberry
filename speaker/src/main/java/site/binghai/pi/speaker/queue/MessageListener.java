package site.binghai.pi.speaker.queue;

import javazoom.jl.decoder.JavaLayerException;
import site.binghai.pi.common.queue.Listener;
import site.binghai.pi.speaker.sound.Mp3Player;
import site.binghai.pi.speaker.sound.SpeakerProxy;
import java.io.FileNotFoundException;

/**
 * Created by binghai on 2017/12/8.
 *
 * @ raspberry
 */
public class MessageListener extends Listener {
    private SpeakerProxy speakerProxy;

    public MessageListener() {
        this.speakerProxy = new SpeakerProxy();
    }

    @Override
    public String getQueueName() {
        return "pi-speaker";
    }

    @Override
    protected String consume(String msg) {
        boolean toSound = speakerProxy.speak(msg);
        if(toSound){
            play("tmp_output.mp3");
        }else{
            System.out.println("出错了...");
        }
        return "ok";
    }

    private void play(String filePath) {
        try {
            new Mp3Player(filePath).play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleException(Exception e) {
        e.printStackTrace();
        speakerProxy.speak("警报警报，不好了，消息消费出错啦！");
    }
}
