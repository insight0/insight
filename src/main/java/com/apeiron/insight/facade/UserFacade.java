package com.apeiron.insight.facade;

import com.apeiron.insight.domain.User;
import com.apeiron.insight.domain.enumeration.DayOffType;
import com.apeiron.insight.service.DayOffService;
import com.apeiron.insight.service.HolidayService;
import com.apeiron.insight.service.UserService;
import com.apeiron.insight.service.dto.DayOffDTO;
import com.apeiron.insight.service.dto.HolidayDTO;
import com.apeiron.insight.service.dto.UserDTO;
import com.apeiron.insight.service.dto.UserDayOffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private DayOffService dayOffService;

    @Autowired
    private HolidayService holidayService;


    public Optional<UserDayOffDTO> getUserDayOffs(String login) {

        Optional<UserDTO> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(login);

        UserDayOffDTO userDayOffDTO = new UserDayOffDTO();

        if (userWithAuthoritiesByLogin.isPresent()) {
            final List<DayOffDTO> byUserLogin = dayOffService.findByUserLogin(userWithAuthoritiesByLogin.get().getLogin());

            float sicknessDayOff = 0;
            float annualDayOff = 0;
            float unpaidDayOff = 0;

            for (DayOffDTO dayOffDTO : byUserLogin) {
                if (dayOffDTO.getType() != null && dayOffDTO.getType().equals(DayOffType.ANNUAL)) {
                    annualDayOff += dayOffDTO.getDays();
                } else if (dayOffDTO.getType() != null && dayOffDTO.getType().equals(DayOffType.SICKNESS)) {
                    sicknessDayOff += dayOffDTO.getDays();
                } else if (dayOffDTO.getType() != null && dayOffDTO.getType().equals(DayOffType.UNPAID)) {
                    unpaidDayOff += dayOffDTO.getDays();
                }
            }

            userDayOffDTO.setAnnualDayOffConsumed(annualDayOff);
            userDayOffDTO.setSicknessDayOffConsumed(sicknessDayOff);
            userDayOffDTO.setUnpaidDayOffConsumed(unpaidDayOff);

            if (userWithAuthoritiesByLogin.get().getContract() != null && userWithAuthoritiesByLogin.get().getContract().size() > 0) {
                userDayOffDTO.setAnnualDayOffAuthorised(userWithAuthoritiesByLogin.get().getContract().get(0).getDayOffNamber());
            }

            userDayOffDTO.setSicknessDayOffAuthorised(5f);
            userDayOffDTO.setSicknessDayOffAuthorised(0f);
            userDayOffDTO.setUser(userWithAuthoritiesByLogin.get());
            userDayOffDTO.setDayOffs(byUserLogin);
        }

        return Optional.of(userDayOffDTO);
    }


    public void calculateDayOffs(String login, Instant startDate, Instant endDate) {

        String msg = "";

        Optional<UserDTO> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(login);


        if (startDate == null || endDate == null) {
            msg = "Veuillez selectionner date début et date fin !";
        }

        if (startDate.compareTo(endDate) > 0) {
            msg = "Date début supérieur à la date fin !";
        }


        Duration between = Duration.between(startDate, endDate);
        long allDays = between.abs().toDays();
        long nonWorkingDays = 0;

        ZoneId z = ZoneId.of("Africa/Tunis");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(startDate, z);

        if (zdt.getDayOfWeek().getValue() == 6 || zdt.getDayOfWeek().getValue() == 7) {
            nonWorkingDays++;
        }

        for (int i = 0; i < allDays; i++) {
            startDate = startDate.plus(1, ChronoUnit.DAYS);
            if (ZonedDateTime.ofInstant(startDate, z).getDayOfWeek().getValue() == 6 || ZonedDateTime.ofInstant(startDate, z).getDayOfWeek().getValue() == 7) {
                nonWorkingDays++;
            }
        }

        List<HolidayDTO> holidays = holidayService.findBetween(startDate, endDate);

        if(holidays != null) {
            for(HolidayDTO holidayDTO : holidays) {

            }
        }



        if (userWithAuthoritiesByLogin.isPresent()) {
            UserDTO user = userWithAuthoritiesByLogin.get();
        }

    }
}
