package site.binghai.pi.general;

import com.alibaba.fastjson.JSONObject;
import site.binghai.pi.common.service.SpeakService;
import site.binghai.pi.common.utils.HttpUtils;
import site.binghai.pi.common.utils.TimeFormat;

import java.text.NumberFormat;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class Main {
    private static double lastPrice = 1.0;

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                SpeakService speakService = new SpeakService();
                while (true) {
                    try {
                        String text = work();
                        if (text != null) {
                            System.out.println(text);
                            speakService.speak(text);
                        } else {
                            speakService.speak("火币网服务器崩溃!");
                        }
                        sleep(20000);
                    } catch (Exception e) {
                    }

                }
            }
        }.start();

//        SubClientListener listener = new SubClientListener();
//        listener.start();
//
//        CommandType commandType = CommandType.NORMAL;
//
//        CmdPoster poster = new CmdPoster();
//
//        Scanner sc = new Scanner(System.in);
//        String line;
//        while ((line = sc.nextLine()) != null) {
//            Command command = new Command(commandType, line);
//            Message message = poster.publish(JSONObject.toJSONString(command));
//            System.out.println("SUCCESS:" + message.getMessageId());
//        }
//        sc.close();
    }

    private static String work() {
        JSONObject data = HttpUtils.sendJSONGet("http://api.huobi.pro/market/history/kline", "period=1min&size=1&symbol=btcusdt", null);

        if (data != null) {
            double max = data.getJSONArray("data").getJSONObject(0).getDouble("high");
            double min = data.getJSONArray("data").getJSONObject(0).getDouble("low");

            double avgd = (min + max) / 2;
            String avg = String.valueOf(avgd);

            StringBuilder sb = new StringBuilder();
            if (avgd > lastPrice) {
                sb.append("上涨!涨幅");
                sb.append(persent100(avgd, lastPrice));
            } else if (avgd < lastPrice) {
                sb.append("下跌!跌幅");
                sb.append(persent100(avgd, lastPrice));
            } else {
                sb.append("持平");
            }
            lastPrice = avgd;

            sb.append(" 。");
            if (avg.length() > 5) {
                avg = avg.substring(0, 5);
            }
            if (min >= 11000) {
                sb.append(avg);
                sb.append("美元!");
                return sb.toString();
            }

            sb.append(avg);
            sb.append("美元!当前时间:");
            sb.append(TimeFormat.onlyTime(System.currentTimeMillis()));
            sb.append("最近一分钟，您的比特币价格最高为");
            sb.append(max);
            sb.append("美元,价格最低为");
            sb.append(min);
            sb.append("美元,均价");
            sb.append(avg);
            sb.append("美元!");

            return sb.toString();
        }
        return null;
    }


    private static String persent100(double a, double b) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位

        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(Math.abs(100.0 - a / b * 100)) + "%";
    }
}
