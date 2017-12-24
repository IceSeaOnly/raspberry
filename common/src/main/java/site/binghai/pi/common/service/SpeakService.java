package site.binghai.pi.common.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.Message;
import site.binghai.pi.common.configs.SpeakOption;
import site.binghai.pi.common.queue.Sender;

/**
 * Created by IceSea on 2017/12/9.
 * 发声服务
 * GitHub: https://github.com/IceSeaOnly
 */
public class SpeakService {
    private SpeakSender speakSender;

    public SpeakService() {
        this.speakSender = new SpeakSender();
    }

    public boolean speak(SpeakOption option) {
        Message message = speakSender.publish(JSONObject.toJSONString(option));
        return message.getReceiptHandle() != null;
    }

    public boolean speak(String content) {
        Message message = speakSender.publish(content);
        return message.getReceiptHandle() != null;
    }

    private class SpeakSender extends Sender {
        @Override
        public String getQueueName() {
            return "pi-speaker";
        }
    }
}
