package com.shelter.test;

import java.io.IOException;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShelterApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShelterApplication.class, args);
	}
	
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String url = "http://localhost:8080";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Windows 系統
                ProcessBuilder processBuilder = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url);
                processBuilder.start();
                System.out.println("瀏覽器應該已經開啟了。");
            } else if (os.contains("mac")) {
                // macOS 系統
                ProcessBuilder processBuilder = new ProcessBuilder("open", url);
                processBuilder.start();
                System.out.println("瀏覽器應該已經開啟了。");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux 系統
                String[] browsers = {"google-chrome", "firefox", "opera", "safari"};
                for (String browser : browsers) {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder(browser, url);
                        processBuilder.start();
                        break;
                    } catch (IOException e) {
                        // 如果無法找到瀏覽器，則繼續嘗試其他瀏覽器
                    }
                }
                System.out.println("瀏覽器應該已經開啟了。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
