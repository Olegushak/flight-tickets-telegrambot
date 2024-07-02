package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto<T> {

    private List<T> data;

    private boolean status;

    private String message;
}
