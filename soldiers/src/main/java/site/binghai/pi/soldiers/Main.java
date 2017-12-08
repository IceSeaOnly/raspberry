package site.binghai.pi.soldiers;

import site.binghai.pi.soldiers.cmd.CmdListener;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
public class Main {
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("no start command input...");
            return;
        }
        System.out.println("starting...");
        new CmdListener(args[0]).start();
        System.out.println("started");
    }
}
