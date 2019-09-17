package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.dto.NotificationDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NotificationMapper {


    public Notification toEntity(NotificationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notification notification = new Notification();

        notification.setId( dto.getId() );
        notification.setUserName( dto.getUserName() );
        notification.setUserPictureUrl( dto.getUserPictureUrl() );
        notification.setUserProfileUrl( dto.getUserProfileUrl() );
        notification.setText( dto.getText() );
        notification.setDate( dto.getDate() );
        notification.setViews(dto.getViews());

        return notification;
    }


    public NotificationDTO toDto(Notification entity) {
        if ( entity == null ) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setId( entity.getId() );
        notificationDTO.setUserName( entity.getUserName() );
        notificationDTO.setUserPictureUrl( entity.getUserPictureUrl() );
        notificationDTO.setUserProfileUrl( entity.getUserProfileUrl() );
        notificationDTO.setText( entity.getText() );
        notificationDTO.setDate( entity.getDate() );
        notificationDTO.setViews(entity.getViews());

        return notificationDTO;
    }

    public List<Notification> toEntity(List<NotificationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Notification> list = new ArrayList<Notification>( dtoList.size() );
        for ( NotificationDTO notificationDTO : dtoList ) {
            list.add( toEntity( notificationDTO ) );
        }

        return list;
    }


    public List<NotificationDTO> toDto(List<Notification> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotificationDTO> list = new ArrayList<NotificationDTO>( entityList.size() );
        for ( Notification notification : entityList ) {
            list.add( toDto( notification ) );
        }

        return list;
    }


    public Notification fromId(String id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
