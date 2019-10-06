package com.test.server.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class IdDTO implements Serializable{


    private static final long serialVersionUID = 3212058994280941617L;

    @NotNull(message = "id равно null")
    private Long id;

    public IdDTO() {
    }

    public IdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
