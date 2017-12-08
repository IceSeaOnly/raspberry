package site.binghai.pi.mycraft.defination;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import site.binghai.pi.mycraft.services.MyCommandGenerator;

/**
 * Created by binghai on 2017/12/6.
 *
 * @ raspberry
 */

public abstract class BaseCmd implements CmdInterface, InitializingBean {
    @Autowired
    private MyCommandGenerator commander;

    protected abstract void init();

    public String cmdHeader() {
        return getCmdName() + " ";
    }

    public final void afterPropertiesSet() throws Exception {
        init();
        commander.reg(this);
    }
}
