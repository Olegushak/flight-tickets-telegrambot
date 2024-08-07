package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.repository.AirportRepository;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService{

    private final AirportRepository airportRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    private final DtoMapper dtoMapper;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository, ElasticsearchOperations elasticsearchOperations, DtoMapper dtoMapper) {
        this.airportRepository = airportRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.dtoMapper = dtoMapper;
    }


    @Override
    public void saveAll(List<AirportDto> airports) {
        airports.stream().map(dtoMapper::airportDtoToEntity).forEach(airportRepository::save);

    }

    @Override
    public Optional<Airport> findAirportByLocation(String city) {
        return Optional.of(airportRepository.getAirportByLocationContaining(city).get());
    }

    @Override
    public void deleteAll() {
       airportRepository.deleteAll();
    }

    @Override
    public List<String> search(String city) {
         MatchQueryBuilder query = new MatchQueryBuilder("location",city)
                 .operator(Operator.OR);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        SearchHits<Airport> productHits =
                elasticsearchOperations
                        .search(searchQuery,
                                Airport.class,
                                IndexCoordinates.of("airport"));
        List<String> ticketMatches = new ArrayList<>();
        productHits.forEach(searchHit -> ticketMatches.add(searchHit.getContent().getLocation()));
        return ticketMatches;

    }

}
