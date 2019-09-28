package com.apeiron.insight.repository;

import com.apeiron.insight.domain.DayOff;
import com.apeiron.insight.domain.enumeration.DayOffStatus;
import com.apeiron.insight.service.dto.DayOffDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the DayOff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayOffRepository extends MongoRepository<DayOff, String> {

    List<DayOff> findDayOffByStatus(DayOffStatus dayOffStatus);
    List<DayOff> findDayOffByEmployeId(String login);

}
