package site.binghai.pi.mycraft.cmds;

import org.springframework.stereotype.Component;
import site.binghai.pi.mycraft.defination.BaseCmd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binghai on 2017/12/6.
 *
 * @ raspberry
 */
@Component
public class Help extends BaseCmd {

    public String getCmdName() {
        return "help";
    }

    public List<String> toMyCmds(String original) {
        List<String> arr = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            arr.add(cmdHeader() + i);
        }
        return arr;
    }

    protected void init() {

    }
}
