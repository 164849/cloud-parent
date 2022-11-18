package com.itck.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoCommentTask {

    @Scheduled(cron = "*/5 * * * * ?")
    public void handler() {
        System.out.println("开始每日自动评价检查");

        System.out.println("开始每日自动评价结束");
    }


}