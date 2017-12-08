package site.binghai.pi.general.cmd;

import site.binghai.pi.common.queue.Listener;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class SubClientListener extends Listener {
    public String getQueueName() {
        return "pi-soldiers";
    }

    protected String consume(String msg) {
//        System.out.println("消费士兵消息");
        System.out.println(msg);
        return msg;
    }

    protected void handleException(Exception e) {
        System.out.println("收取士兵消息报错");
        e.printStackTrace();
    }
}
