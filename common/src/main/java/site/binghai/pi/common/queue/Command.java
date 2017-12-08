package site.binghai.pi.common.queue;

import lombok.Data;
import site.binghai.pi.common.enums.CommandType;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
@Data
public class Command {
    private CommandType Type;
    private String commandBody;

    public Command(CommandType type, String commandBody) {
        Type = type;
        this.commandBody = commandBody;
    }

    public Command() {
    }
}
