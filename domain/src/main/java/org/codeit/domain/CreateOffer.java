package org.codeit.domain;

import java.io.Serializable;
import java.time.Instant;

public class CreateOffer implements Serializable {
    private String id;
    private Instant createTime;
    private String name;

    public CreateOffer() {
    }

    public CreateOffer(String id, Instant createTime, String name) {
        this.id = id;
        this.createTime = createTime;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
