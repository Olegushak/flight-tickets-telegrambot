package com.github.olegushak.FTT.repository.entity;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = "airport")
@Setting(settingPath = "es-config/elastic-analyzer.json")
@Setter
@Getter
@Builder
public class Airport {

    @Id
    @Field
    private String id;

    @Field
    private String iata;

    @Field
    private String icao;

    @Field
    private String name;

    @Field(type = FieldType.Text, analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private String location;

    @Field
    private String time;

    @Field
    private String skyId;

}
