package site.binghai.pi.common.queue;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public abstract class Sender implements QueueBase{
    private CloudAccount account = null;
    private MNSClient client = null;
    private CloudQueue queue = null;

    public Sender() {
        this.account = new CloudAccount(accessId, accessKey, mnsEndPoint);
        this.client = account.getMNSClient();
        this.queue = client.getQueueRef(getQueueName());
    }

    public Message publish(String msgBody){
        Message message = new Message();
        message.setMessageBody(msgBody);
        return queue.putMessage(message);
    }
}
