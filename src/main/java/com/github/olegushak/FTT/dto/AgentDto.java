package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDto {

    private String id;

    private String name;

    private String url;
}
