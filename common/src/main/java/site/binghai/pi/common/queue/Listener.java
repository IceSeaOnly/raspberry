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
public abstract class Listener extends Thread implements QueueBase{


    private CloudAccount account = null;
    private MNSClient client = null;
    private CloudQueue queue = null;

    protected abstract String consume(String msg);

    protected abstract void handleException(Exception e);

    public Listener() {
        this.account = new CloudAccount(accessId, accessKey, mnsEndPoint);
        this.client = account.getMNSClient();
        this.queue = client.getQueueRef(getQueueName());
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Message popMsg = queue.popMessage(10);
                if (popMsg != null) {
//                    System.out.println("新消息:" + popMsg.getMessageBody());
                    queue.deleteMessage(popMsg.getReceiptHandle());
                    consume(popMsg.getMessageBody());
                }
            } catch (Exception e) {
                handleException(e);
            }
        }
    }
}
