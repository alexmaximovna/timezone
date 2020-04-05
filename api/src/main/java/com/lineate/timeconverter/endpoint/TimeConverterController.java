package com.lineate.timeconverter.endpoint;

import com.lineate.timeconverter.entity.TimeResponseDto;
import com.lineate.timeconverter.entity.TimeUnitDto;
import com.lineate.timeconverter.service.TimeConverterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class converts from one time unit to another time unit
 */
@RestController
@RequestMapping(value = "/converter")
public class TimeConverterController {

    @Autowired
    private TimeConverterService timeConverterService;

    /**
     * Method converters  number in hours,minutes,seconds
     * @param dto - object contains number and type unit
     * @return object with converting data
     */
    @ApiOperation(value = "getConverterTime", notes = "Get Converter Time")
    @PostMapping(value = "",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TimeResponseDto> getConverterTime(@RequestBody @Validated TimeUnitDto dto) {
        TimeResponseDto str = timeConverterService.getTime(dto);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
}
