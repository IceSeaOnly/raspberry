package site.binghai.pi.mycraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import site.binghai.pi.mycraft.connector.SubClientListener;
import site.binghai.pi.mycraft.smart.SmartCommander;

import java.util.Scanner;

/**
 * Created by binghai on 2017/12/4.
 *
 * @ raspberry
 */
@SpringBootApplication
@ComponentScan("site.binghai.*")
public class Main implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private SmartCommander smartCommander;

    /**
     * 控制台控制初始化
     * */
    public void run(String... strings) throws Exception {
        SubClientListener listener = new SubClientListener();
        listener.start();

        Scanner sc = new Scanner(System.in);
        String line;
        while ((line = sc.nextLine()) != null) {
            smartCommander.smartTypeIn(line);
        }
        sc.close();
    }
}
