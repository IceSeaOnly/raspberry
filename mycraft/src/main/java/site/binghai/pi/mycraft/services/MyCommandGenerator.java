package site.binghai.pi.mycraft.services;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import site.binghai.pi.mycraft.cmds.Default;
import site.binghai.pi.mycraft.defination.CmdInterface;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by binghai on 2017/12/6.
 *
 * @ raspberry
 */
@Service
public class MyCommandGenerator implements InitializingBean {
    private ConcurrentHashMap<String, CmdInterface> myCommands;

    public void reg(CmdInterface cmdInterface) {
        myCommands.put(cmdInterface.getCmdName(), cmdInterface);
    }

    public CmdInterface getCmd(String cmdName) {
        return myCommands.containsKey(cmdName) ? myCommands.get(cmdName) : ((Default) myCommands.get("default")).setProxyName(cmdName);
    }

    public void afterPropertiesSet() throws Exception {
        myCommands = new ConcurrentHashMap<String, CmdInterface>();
    }
}
