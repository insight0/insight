package com.apeiron.insight.service;

import com.apeiron.insight.domain.Notification;
import com.apeiron.insight.repository.NotificationRepository;
import com.apeiron.insight.repository.search.NotificationSearchRepository;
import com.apeiron.insight.service.dto.NotificationDTO;
import com.apeiron.insight.service.mapper.NotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final NotificationSearchRepository notificationSearchRepository;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper, NotificationSearchRepository notificationSearchRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.notificationSearchRepository = notificationSearchRepository;
    }

    /**
     * Save a notification.
     *
     * @param notificationDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationDTO save(NotificationDTO notificationDTO) {
        log.debug("Request to save Notification : {}", notificationDTO);
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        NotificationDTO result = notificationMapper.toDto(notification);
        notificationSearchRepository.save(notification);
        return result;
    }

    /**
     * Get all the notifications.
     *
     * @return the list of entities.
     */
    public List<NotificationDTO> findAll() {
        log.debug("Request to get all Notifications");
        return notificationRepository.findAll().stream()
            .map(notificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one notification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<NotificationDTO> findOne(String id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepository.findById(id)
            .map(notificationMapper::toDto);
    }

    /**
     * Delete the notification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.deleteById(id);
        notificationSearchRepository.deleteById(id);
    }

    /**
     * Search for the notification corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    public List<NotificationDTO> search(String query) {
        log.debug("Request to search Notifications for query {}", query);
        return StreamSupport
            .stream(notificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(notificationMapper::toDto)
            .collect(Collectors.toList());
    }
}
