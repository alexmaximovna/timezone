package com.lineate.timeconverter.endpoint;

import com.lineate.timeconverter.entity.CityInfo;
import com.lineate.timeconverter.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "city-search")
/**
 * This class is endpoint for search data of city
 */
public class SearchCityController {

    @Autowired
    private SearchService searchService;

    /**
     * Method return first 10 cities by start substring
     * @param partialNameCity - substring of name finding city
     * @return - list of first 10 cities by start substring
     * @throws IOException - IOException
     */
    @ApiOperation(value = "getCitiesBySym", notes = "Get Cities By Symbols")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CityInfo>> getCitiesBySym(@RequestParam("str") String partialNameCity) throws IOException {
        Optional<List<CityInfo>> res = searchService.findCityBySymbols(partialNameCity);
        return (res.isEmpty()) ? new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res.get(), HttpStatus.OK);

    }
}
