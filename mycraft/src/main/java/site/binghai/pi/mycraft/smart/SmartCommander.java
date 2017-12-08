package site.binghai.pi.mycraft.smart;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.pi.common.enums.CommandType;
import site.binghai.pi.common.queue.Command;
import site.binghai.pi.mycraft.connector.CmdPoster;
import site.binghai.pi.mycraft.services.MyCommandGenerator;

import java.util.List;

/**
 * Created by binghai on 2017/12/6.
 *
 * @ raspberry
 */
@Service
public class SmartCommander {
    private CommandType commandType = CommandType.NORMAL;
    private CmdPoster poster = new CmdPoster();

    @Autowired
    private MyCommandGenerator commandGenerator;

    public void smartTypeIn(String myCmd) {
        if (myCmd.contains(" ")) {
            int firstBlank = myCmd.indexOf(" ");
            batchInsert(myCmd.substring(0, firstBlank), myCmd.substring(firstBlank + 1, myCmd.length()));
        } else {
            batchInsert(myCmd, "");
        }
    }


    private void batchInsert(String name, String original) {
        List<String> cmds = commandGenerator.getCmd(name).toMyCmds(original);
        cmds.forEach(v -> insert(v));
    }

    /**
     * 内部发送方法
     */
    private void insert(String cmd) {
        Command command = new Command(commandType, cmd);
        Message message = poster.publish(JSONObject.toJSONString(command));
        if (message == null || message.getMessageId() == null) {
            System.out.println("发送失败!");
        }
    }
}
