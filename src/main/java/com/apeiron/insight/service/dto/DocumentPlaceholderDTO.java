package com.apeiron.insight.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.DocumentPlaceholder} entity.
 */
public class DocumentPlaceholderDTO implements Serializable {

    private String id;

    /**
     * TODO: private List<DocumentHistory> versions; TODO: private List<ChangeLog> changeLogs;
     */
    @NotNull
    @ApiModelProperty(value = "TODO: private List<DocumentHistory> versions; TODO: private List<ChangeLog> changeLogs;", required = true)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String version;

    @NotNull
    private String url;

    @NotNull
    private String type;

    @NotNull
    private String size;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentPlaceholderDTO documentPlaceholderDTO = (DocumentPlaceholderDTO) o;
        if (documentPlaceholderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentPlaceholderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentPlaceholderDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            ", url='" + getUrl() + "'" +
            ", type='" + getType() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
}
