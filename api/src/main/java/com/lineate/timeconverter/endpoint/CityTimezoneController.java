package com.lineate.timeconverter.endpoint;

import com.lineate.timeconverter.entity.AdjustmentList;
import com.lineate.timeconverter.entity.CitiesListEntity;
import com.lineate.timeconverter.entity.CurrentTimeList;
import com.lineate.timeconverter.service.CityTimezoneListsService;
import com.lineate.timeconverter.service.CookieStorage;
import com.lineate.timeconverter.service.Storage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


/**
 * This class is endpoint for work with user's lists
 */
@RestController
@Api(value = "City Timezone endpoint", description = "This endpoint is used for operation with list of cities")
@RequestMapping(value = "city-timezone/lists")
public class CityTimezoneController {

    @Autowired
    /**
     * listsService - service for treatment of lists
     */
    private CityTimezoneListsService listsService;

    /**
     * Method return all user's lists
     *
     * @param request - request with info about user in cookie
     * @return - info about user's lists of cities
     * @throws IOException - IOException
     */
    @ApiOperation(value = "getLists", notes = "Get all user's lists")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AdjustmentList>> getLists(HttpServletRequest request) throws IOException {
        Storage storage = new CookieStorage(request);
        List<AdjustmentList> adjustmentLists = listsService.getLists(storage);
        String headerValue = storage.getCookieValue();
        return new ResponseEntity<>(adjustmentLists, storage.addCookie(headerValue), HttpStatus.OK);
    }

    /**
     * Method return  user's list by id
     *
     * @param id          - id of user list of cities
     * @param HttpRequest - request with info about user in cookie
     * @return - list with cities
     * @throws IOException - IOException
     */
    @ApiOperation(value = "getCitiesListById", notes = "Get list user's list by id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CitiesListEntity> getCitiesListById(@PathVariable(value = "id") String id,
                                                              HttpServletRequest HttpRequest) throws IOException {
        Storage storage = new CookieStorage(HttpRequest);
        Optional<CitiesListEntity> res = listsService.getListCities(storage, id);
        return (res.isEmpty()) ? new ResponseEntity<>(new CitiesListEntity(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res.get(), HttpStatus.OK);

    }

    /**
     * Method add new city by name in list of user
     *
     * @param cityName - new city for adding in list
     */
    @ApiOperation(value = "addCityInList", notes = "Add city")
    @PostMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CitiesListEntity> addCityInList(@PathVariable("name") String cityName,
                                                          @RequestParam("lat") String lat,
                                                          @RequestParam("lng") String lng,
                                                          HttpServletRequest HttpRequest) throws ParseException, IOException {
        Storage storage = new CookieStorage(HttpRequest);
        Optional<CitiesListEntity> res = listsService.addCityToList(storage, cityName, lat, lng);
        storage.updateCookie(res);
        String headerValue = storage.getCookieValue();
        return (res.isEmpty()) ? new ResponseEntity<>(new CitiesListEntity(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res.get(), storage.addCookie(headerValue), HttpStatus.OK);
    }


    /**
     * Method delete new city by name in list of user
     *
     * @param cityName new city for removing from list
     */
    @ApiOperation(value = "deleteCityFromList", notes = "Delete city")
    @DeleteMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CitiesListEntity> deleteCityFromList(@PathVariable("name") String cityName,
                                                               HttpServletRequest HttpRequest) throws IOException {
        Storage storage = new CookieStorage(HttpRequest);
        Optional<CitiesListEntity> res = listsService.deleteCityFromList(storage, cityName);
        storage.updateCookie(res);
        String headerValue = storage.getCookieValue();
        return (res.isEmpty()) ? new ResponseEntity<>(new CitiesListEntity(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res.get(), storage.addCookie(headerValue), HttpStatus.OK);
    }

    /**
     * Method changes mainCity in user's list of cities
     * @param mainCity - new mainCity
     * @param HttpRequest -  HttpRequest
     * @return - list of cities
     * @throws IOException -IOException
     */
    @ApiOperation(value = "changeMainCity", notes = "Change Main City")
    @PutMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CitiesListEntity> changeMainCity(@PathVariable("name") String mainCity,
                                                          HttpServletRequest HttpRequest) throws  IOException {
        Storage storage = new CookieStorage(HttpRequest);
        Optional<CitiesListEntity> res = listsService.changeMainCity(storage, mainCity);
        storage.updateCookie(res);
        String headerValue = storage.getCookieValue();
        return (res.isEmpty()) ? new ResponseEntity<>(new CitiesListEntity(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(res.get(), storage.addCookie(headerValue), HttpStatus.OK);
    }

    /**
     * Method return list of current time for each city from city list
     * @param format - format of time
     * @param HttpRequest -  HttpRequest
     * @return - list of current time for each city in list
     * @throws IOException -IOException
     */
    @ApiOperation(value = "getCurrentTime", notes = "Get Current Time")
    @GetMapping(value = "/time", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CurrentTimeList> getCurrentTime(@RequestParam("format") String format,
                                                          HttpServletRequest HttpRequest) throws IOException {
        Storage storage = new CookieStorage(HttpRequest);
        Optional<CurrentTimeList> res = listsService.getCurrentTime(storage, format);
        return new ResponseEntity<>(res.get(), HttpStatus.OK);

    }


}

