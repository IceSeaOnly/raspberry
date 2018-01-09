package site.binghai.pi.cron;

import com.alibaba.fastjson.JSONObject;
import site.binghai.pi.common.utils.HttpUtils;
import site.binghai.pi.common.utils.MailUtil;
import site.binghai.pi.common.utils.TimeFormat;

import static java.lang.Thread.sleep;

/**
 * Created by binghai on 2018/1/9.
 *
 * @ raspberry
 */
public class App {
    public static void main(String[] args) {
        Runnable run = () -> {
            while (true) {
                try {
                    work();
                    sleep(5000);
                } catch (Exception e) {
                }
            }
        };

        new Thread(run).start();
    }

    private static String lastWords = "";

    private static void speak(String title, String content) {
        if (title.equals(lastWords)) {
            return;
        }
        lastWords = title;
        MailUtil.mail(title, content);
        MailUtil.mail(title, content);
        System.out.println(title + ";" + content);
    }

    private static int last = 0;

    private static void work() {
        JSONObject resp = HttpUtils.sendJSONGet("http://api.huobi.pro/market/history/kline", "period=1min&size=1&symbol=btcusdt", "");

        if (null == resp) {
            return;
        }

        JSONObject data = resp.getJSONArray("data").getJSONObject(0);
        double close = data.getDouble("close");
        int intv = (int) (close / 1000);
        intv *= 1000;
        speak((intv < last ? "BTC跌破" : "BTC涨破") + intv + "美元!",
                "BTC现价" + close + "请注意!时间:" + TimeFormat.format(System.currentTimeMillis()));

        last = intv;
    }
}
