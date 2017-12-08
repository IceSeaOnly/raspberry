package site.binghai.pi.mycraft.cmds;

import org.springframework.stereotype.Component;
import site.binghai.pi.mycraft.defination.BaseCmd;

import java.util.Arrays;
import java.util.List;

/**
 * Created by binghai on 2017/12/6.
 * 默认实现，不做任何处理
 * 用户在找不到代理类时的默认实现
 *
 * @ raspberry
 */
@Component
public class Default extends BaseCmd {
    private String proxyName;

    public String getCmdName() {
        return proxyName;
    }

    public Default setProxyName(String proxyName) {
        this.proxyName = proxyName;
        return this;
    }

    public List<String> toMyCmds(String original) {
        return Arrays.asList(cmdHeader() + original);
    }

    protected void init() {
        proxyName = "default";
    }
}
