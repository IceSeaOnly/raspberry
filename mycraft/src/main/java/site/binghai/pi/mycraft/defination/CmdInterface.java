package site.binghai.pi.mycraft.defination;

import java.util.List;

/**
 * Created by binghai on 2017/12/6.
 *
 * @ raspberry
 */
public interface CmdInterface {
    String getCmdName();
    List<String> toMyCmds(String original);
}
