package site.binghai.pi.speaker.queue;

import com.alibaba.fastjson.JSONObject;
import javazoom.jl.decoder.JavaLayerException;
import site.binghai.pi.common.configs.SpeakOption;
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
        SpeakOption option = JSONObject.parseObject(msg, SpeakOption.class);
        switch (option.getSpeakType()) {
            case SPEAK_DIRECT:
                directPlay(option);
                break;
            case DELAY_PLAY:
                break;
            case LOCAL_FILE:
                break;
            case TIMED_REPEAT:
                break;
            case NUMBER2CHINESE:
                break;
            default:
                break;
        }
        return "ok";
    }

    private void directPlay(SpeakOption option) {
        boolean toSound = speakerProxy.speak(option);
        if (toSound) {
            play("tmp_output.mp3");
        } else {
            System.out.println("出错了...");
        }
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
        speakerProxy.speak(new SpeakOption("警报警报，不好了，消息消费出错啦！"));
    }
}
