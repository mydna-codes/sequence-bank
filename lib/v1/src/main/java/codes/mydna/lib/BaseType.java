package codes.mydna.lib;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

public class BaseType {

    @Schema(hidden = true)
    private String id;

    @Schema(hidden = true)
    private Date created;

    @Schema(hidden = true)
    private Date lastModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
