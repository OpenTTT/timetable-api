package org.openttt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    private String primaryColor;
    @NotNull
    private String secondaryColor;
}
