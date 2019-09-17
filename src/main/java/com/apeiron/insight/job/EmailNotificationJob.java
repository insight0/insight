package com.apeiron.insight.job;

import com.apeiron.insight.service.impl.NotificationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EmailNotificationJob {

    @Autowired
    private NotificationProcessor notificationProcessor;

    @Async
    @Scheduled(fixedRate = 100000)
    public void scheduleFixedRateTaskAsync() {

        notificationProcessor.processHolidayNotification();
    }

}
