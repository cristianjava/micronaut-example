package com.example.scheduled;

import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class CronJobScheduled {

    private static final Logger LOG = LoggerFactory.getLogger(CronJobScheduled.class);

    //@Scheduled(cron = "0 0 * * *")
    @Scheduled(fixedDelay = "999999999s")
    public void scheduleExample() {
        LOG.info("proof every 10 seconds " + new Date());
    }
}
