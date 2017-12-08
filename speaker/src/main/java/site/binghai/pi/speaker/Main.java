package site.binghai.pi.speaker;

import site.binghai.pi.speaker.queue.MessageListener;

/**
 * Created by binghai on 2017/12/8.
 *
 * @ raspberry
 */
public class Main {
    public static void main(String[] args) {
        MessageListener messageListener = new MessageListener();
        messageListener.start();
    }
}
