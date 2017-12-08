package site.binghai.pi.soldiers.cmd;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by binghai on 2017/10/26.
 *
 * @ MoGuJie
 */
public class CmdExecutor {
    private LinkedBlockingQueue<String> queue;
    private ResultBackSender sender;
    private String initCmd;

    public CmdExecutor(String startCmd, final ResultBackSender sender) {
        this.queue = new LinkedBlockingQueue<String>();
        this.sender = sender;
        this.initCmd = startCmd;
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    works();
                    sender.publish("新的shell已开启");
                }
            }
        }.start();
    }

    public void insertCommand(String command) {
        queue.add(command);
    }

    private void works() {
        Process process = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            process = Runtime.getRuntime().exec(initCmd);
            out = new BufferedOutputStream(process.getOutputStream());
            in = new BufferedInputStream(process.getInputStream());
            readWrite(in, out, sender);  //该方法借用common-net的测试例子部分的一个工具类的方法
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                    out = null;
                }
                if (null != in) {
                    in.close();
                    in = null;
                }
                int value = process.waitFor();
                if (null != process)
                    process.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sender.publish(">>> 当前shell已结束.");
        }
    }

    private void readWrite(final InputStream remoteInput,
                           final OutputStream remoteOutput,
                           final ResultBackSender sender) {
        Thread reader, writer;

        reader = new Thread() {
            @Override
            public void run() {
                String line;
                try {
                    while (!interrupted() && (line = queue.take()) != null) {
                        for (char c : line.toCharArray()) {
                            remoteOutput.write(c);
                        }
                        remoteOutput.write('\n');
                        remoteOutput.flush();
                        System.out.println(">>> " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.publish(e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    sender.publish(e.getMessage());
                }
            }
        };

        writer = new Thread() {
            @Override
            public void run() {
                Scanner sc = new Scanner(remoteInput);
                String line;
                while ((line = sc.nextLine()) != null) {
                    sender.publish(line);
                    System.out.println(line);
                }
                sc.close();
            }
        };

        writer.setPriority(Thread.currentThread().getPriority() + 1);
        writer.start();
        reader.setDaemon(true);
        reader.start();

        try {
            writer.join();
            reader.interrupt();
        } catch (InterruptedException e) {
        }
    }
}
