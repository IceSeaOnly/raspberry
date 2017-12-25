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
    private static boolean detailOpen = false;
    private static int sleepTime = 20000;
    private static int MAX = 14888;
    private static int MIN = 11000;


    public static void main(String[] args) {
        System.out.println("args:" + args.length);
        for (String arg : args){
            if(arg.startsWith("-D")){
                detailOpen = true;
            }
            if(arg.startsWith("-S")){
                sleepTime = Integer.parseInt(arg.substring(2,arg.length()));
            }
            if(arg.startsWith("-MIN")){
                MIN = Integer.parseInt(arg.substring(4,arg.length()));
            }
            if(arg.startsWith("-MAX")){
                MAX = Integer.parseInt(arg.substring(4,arg.length()));
            }
        }
        new Thread() {
            @Override
            public void run() {
                SpeakService speakService = new SpeakService();
                while (true) {
                    try {
                        String text = work();
                        if (text != null) {
                            speakService.speak(text);
                        } else {
//                            speakService.speak("火币网服务器崩溃!");
                        }
                        sleep(sleepTime);
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
            if (min > MIN  && avgd < MAX) {
                sb.append(avg);
                sb.append("美元!");
                System.out.println(sb.toString());
                return detailOpen ? sb.toString() : null;
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

            System.out.println(sb.toString());
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
