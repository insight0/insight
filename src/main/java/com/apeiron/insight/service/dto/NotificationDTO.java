package com.apeiron.insight.service.dto;

import com.apeiron.insight.security.SecurityUtils;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {

    private String id;

    private String userName;

    private String userPictureUrl;

    private String userProfileUrl;

    @NotNull
    private String text;

    @NotNull
    private Instant date;

    private Set<String> views = new HashSet<>();
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<String> getViews() {
        return views;
    }

    public void setViews(Set<String> views) {
        this.views = views;
    }

    public boolean isSeen() {

        final Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();

        if(currentUserLogin.isPresent() && this.views.contains(currentUserLogin.get())) {
            return true;
        }
        return false;
    }
    

    public String getFrenchFormattedDate() {
        DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.FRANCE)
                .withZone(ZoneId.systemDefault());

        return formatter.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", userPictureUrl='" + getUserPictureUrl() + "'" +
            ", userProfileUrl='" + getUserProfileUrl() + "'" +
            ", text='" + getText() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
