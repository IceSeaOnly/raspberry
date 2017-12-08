package site.binghai.pi.speaker.queue;

import site.binghai.pi.common.queue.Listener;

/**
 * Created by binghai on 2017/12/8.
 *
 * @ raspberry
 */
public class MessageListener extends Listener {
    @Override
    public String getQueueName() {
        return "pi-speake";
    }

    @Override
    protected String consume(String msg) {
        return null;
    }

    @Override
    protected void handleException(Exception e) {

    }
}
