package site.binghai.pi.speaker.sound;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

import java.io.IOException;
import java.util.HashMap;

import static site.binghai.pi.speaker.sound.PassWord.API_KEY;
import static site.binghai.pi.speaker.sound.PassWord.APP_ID;
import static site.binghai.pi.speaker.sound.PassWord.SECRET_KEY;

/**
 * Created by IceSea on 2017/12/8.
 * GitHub: https://github.com/IceSeaOnly
 */
public class SpeakerProxy {
    private AipSpeech client;

    public SpeakerProxy() {
        client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    }

    public boolean speak(String ctx) {
        // 初始化一个AipSpeech


        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 设置可选参数
        HashMap<String, Object> options = new HashMap<>();
        options.put("spd", "5"); // 语速
        options.put("pit", "5"); // 音调
        options.put("per", "4"); // 发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
        // 调用接口
        TtsResponse res = client.synthesis(ctx, "zh", 1, options);
        byte[] data = res.getData();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "tmp_output.mp3");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (res.getResult() != null) {
            JSONObject res1 = JSONObject.parseObject(res.getResult().toString());
            if (res1 != null) {
                System.out.println(res1.toString());
                return false;
            }
        }
        return true;
    }
}
