package com.apeiron.insight.service.impl;

import com.apeiron.insight.domain.User;
import com.apeiron.insight.service.*;
import com.apeiron.insight.service.dto.ConfigDTO;
import com.apeiron.insight.service.dto.EmailTemplateDTO;
import com.apeiron.insight.service.dto.HolidayDTO;
import com.apeiron.insight.service.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class NotificationProcessor {

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
    private NotificationService notificationService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void processHolidayNotification() {


        HolidayDTO nextHolidaytoNotify = getNextHolidayToNotify();


        if (nextHolidaytoNotify != null) {

            NotificationDTO holidayNotification = getHolidayNotification(nextHolidaytoNotify);
            notificationService.save(holidayNotification);
            messagingTemplate.convertAndSend("/topic/user-notification", holidayNotification);

            List<User> users = userService.getAllAdminUsers();

            for (User user : users
            ) {
                if (!user.getEmail().equals("system@localhost")) {
                    // mailService.sendEmail(user.getEmail(), "Jour Férié", getHolidayEmailNotification(nextHolidaytoNotify), false, true);
                }
            }

        }
    }


    private HolidayDTO getNextHolidayToNotify() {

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

    private String getHolidayEmailNotification(HolidayDTO nextHolidaytoNotify) {

        Optional<EmailTemplateDTO> one = emailTemplateService.findOne();

        if (one.isPresent() && one.get().getHolidayEmailTemplate() != null && one.get().getHolidayEmailTemplate().trim().length() > 0) {

            final EmailTemplateDTO emailTemplate = one.get();

            String holidayEmailTemplate = emailTemplate.getHolidayEmailTemplate();

            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##name##", nextHolidaytoNotify.getName());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##description##", nextHolidaytoNotify.getDescription());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##date##", nextHolidaytoNotify.getDate().toString());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##duration##", nextHolidaytoNotify.getDuration().toString());
            holidayEmailTemplate = holidayEmailTemplate.replaceAll("##paid##", nextHolidaytoNotify.getPaid().toString());
            return holidayEmailTemplate;
        }

        return getHolidayNotificationText(nextHolidaytoNotify);
    }

    private NotificationDTO getHolidayNotification(HolidayDTO holiday) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setDate(new Date().toInstant());
        notificationDTO.setUserName("system");
        notificationDTO.setUserPictureUrl("system");
        notificationDTO.setUserProfileUrl("system");
        notificationDTO.setText(getHolidayNotificationText(holiday));

        return notificationDTO;
    }


    private String getHolidayNotificationText(HolidayDTO holiday) {
        String holidayText = "est un jour férié.";

        if (holiday.getDuration() > 1) {
            holidayText = "sont des jours fériés.";
        }

        if (((holiday.getDayOfWeek() == 6 || holiday.getDayOfWeek() == 7) && holiday.getDuration() == 1) || (holiday.getDayOfWeek() == 6 && holiday.getDuration() == 2)) {
            return "Veuillez noter que " + holiday.getFrenchFormattedDate() + ", " + holiday.getName() + ", " + holidayText;
        } else {
            if (holiday.isPaid()) {
                return "Veuillez noter que " + holiday.getFrenchFormattedDate() + ", " + holiday.getName() + ", " + holidayText + " payé ." +
                    "Par conséquent, les bureaux d’Apeiron Technologies seront fermés et nous reprendrons nos services habituels le " + holiday.getFrenchFormattedNextDate() + ".";
            } else {
                return "Veuillez noter que " + holiday.getFrenchFormattedDate() + ", " + holiday.getName() + ", " + holidayText + "  non payé." +
                    "Par conséquent, les bureaux d’Apeiron Technologies seront ouvert et nous travaillons normalement.";
            }
        }
    }

}
