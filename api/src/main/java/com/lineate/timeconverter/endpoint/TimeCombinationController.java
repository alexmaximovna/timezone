package com.lineate.timeconverter.endpoint;

import com.lineate.timeconverter.entity.TimeAdjustmentRequest;
import com.lineate.timeconverter.entity.UserListCities;
import com.lineate.timeconverter.service.TimeCombinationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * This class is endpoint for work time offset
 */
@RestController
@Api(value = "Time combination endpoint", description = "This endpoint is used for combination time between cities")
@RequestMapping(value = "time-combination")
public class TimeCombinationController {

    @Autowired
    /**
     * timeCombinationService - service for treatment of time offset for city from list
     */
    private TimeCombinationService timeCombinationService;


    /**
     * Method return list of time offset for every city from user's list
     *
     * @param request - request contains info about format time and list of cities
     * @return - user's lists of cities
     * @throws ParseException -ParseException
     */
    @ApiOperation(value = "getTimeCombination", notes = "Get list with time combination")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserListCities> getTimeCombination(@RequestBody TimeAdjustmentRequest request)
            throws ParseException {
        UserListCities res = timeCombinationService.getCitiesWithTimeZone(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
