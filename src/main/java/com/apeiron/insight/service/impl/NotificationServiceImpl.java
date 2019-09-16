package com.apeiron.insight.service.impl;

import com.apeiron.insight.domain.User;
import com.apeiron.insight.service.*;
import com.apeiron.insight.service.dto.ConfigDTO;
import com.apeiron.insight.service.dto.EmailTemplateDTO;
import com.apeiron.insight.service.dto.HolidayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void processHolidayNotification() {


        HolidayDTO nextHolidaytoNotify = getNextHolidaytoNotify();
        Optional<EmailTemplateDTO> one = emailTemplateService.findOne();

        if (nextHolidaytoNotify != null && one.isPresent()) {

            final EmailTemplateDTO emailTemplate = one.get();
            String holidayEmailTemplate = emailTemplate.getHolidayEmailTemplate();

            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##name##", nextHolidaytoNotify.getName());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##description##", nextHolidaytoNotify.getDescription());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##date##", nextHolidaytoNotify.getDate().toString());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##duration##", nextHolidaytoNotify.getDuration().toString());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##paid##", nextHolidaytoNotify.getPaid().toString());

            List<User> users = userService.getAllAdminUsers();

            for (User user : users
            ) {
                if(!user.getEmail().equals("system@localhost")) {
                    mailService.sendEmail(user.getEmail(), "Jour Férié", holidayEmailTemplate, false, true);
                }
            }

        }
    }


    private HolidayDTO getNextHolidaytoNotify() {

        final List<HolidayDTO> nextHoliday = holidayService.findNextHoliday();

        Optional<ConfigDTO> one = configService.findOne();

        Calendar calOne = Calendar.getInstance();
        int dayOfYear = calOne.get(Calendar.DAY_OF_YEAR);
        int year = calOne.get(Calendar.YEAR);

        if (nextHoliday != null && nextHoliday.size() > 0 && one.isPresent()) {

            ConfigDTO config = one.get();

            for (HolidayDTO holiday : nextHoliday) {

                if (holiday.getYear() == year && ((holiday.getDayOfYear() - dayOfYear) == config.getHolidayEmailSendDate())) {
                    return holiday;
                }

            }
        }
        return null;
    }

}
