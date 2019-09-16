package com.apeiron.insight.job;

import com.apeiron.insight.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EmailNotificationJob {

    @Autowired
    private NotificationService notificationService;

    @Async
    @Scheduled(fixedRate = 20000000)
    public void scheduleFixedRateTaskAsync() {

        notificationService.processHolidayNotification();
    }

}
