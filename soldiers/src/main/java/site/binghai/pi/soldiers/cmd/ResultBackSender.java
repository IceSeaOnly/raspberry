package site.binghai.pi.soldiers.cmd;

import site.binghai.pi.common.queue.Sender;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class ResultBackSender extends Sender {
    public String getQueueName() {
        return "pi-soldiers";
    }
}
