package site.binghai.pi.soldiers.cmd;

import com.alibaba.fastjson.JSONObject;
import site.binghai.pi.common.queue.Command;
import site.binghai.pi.common.queue.Listener;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class CmdListener extends Listener {
    private ResultBackSender resultBackSender;
    private CmdExecutor cmdExecutor;

    public String getQueueName() {
        return "pi-general";
    }

    public CmdListener(String startCmd) {
        this.resultBackSender = new ResultBackSender();
        this.cmdExecutor = new CmdExecutor(startCmd, resultBackSender);
    }

    protected String consume(String msg) {
        Command command = JSONObject.parseObject(msg, Command.class);
        switch (command.getType()) {
            case NORMAL:
                cmdExecutor.insertCommand(command.getCommandBody());
                break;
            case NEWSTART:
                resultBackSender.publish("方法未定义");
                break;

        }
        return null;
    }

    protected void handleException(Exception e) {
        e.printStackTrace();
    }
}
