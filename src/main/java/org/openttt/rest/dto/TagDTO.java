package org.openttt.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Integer id;
    private String title;
    private String primaryColor;
    private String secondaryColor;
}
