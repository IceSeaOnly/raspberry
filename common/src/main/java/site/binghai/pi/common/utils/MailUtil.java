package site.binghai.pi.common.utils;

import java.io.IOException;

/**
 * Created by binghai on 2017/12/25.
 *
 * @ raspberry
 */
public class MailUtil {
    public static void mail(String title, String content) {
        try {
            content = content.replace(" ", "_");
            Runtime.getRuntime().exec(String.format("/home/pi/mailer.sh %s %s", title, content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
