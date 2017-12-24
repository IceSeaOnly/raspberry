package site.binghai.pi.general.cmd;

import site.binghai.pi.common.queue.Sender;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class CmdPoster extends Sender{

//    public String getQueueName() {
//        return "pi-general";
//    }
    public String getQueueName() {
        return "pi-speaker";
    }
}
