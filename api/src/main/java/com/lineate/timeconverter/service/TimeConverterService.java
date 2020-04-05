package com.lineate.timeconverter.service;

import com.lineate.timeconverter.entity.TimeResponseDto;
import com.lineate.timeconverter.entity.TimeUnitDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
/**
 * Class is service for work with converter time
 */
public class TimeConverterService {

    private static final String FORMAT_TIME_UNIT = "%02d-%02d-%02d";

    /**
     * Method converters  number in hours,minutes,seconds
     * @param dto - object contains number and type unit
     * @return object with converting data
     */
    public TimeResponseDto getTime(TimeUnitDto dto) {
        String str = dto.getTypeUnit().equals("minutes") ? getFormatTimeUnit(Duration.ofMinutes(Long.valueOf(dto.getNumber())))
                : (dto.getTypeUnit().equals("seconds") ? getFormatTimeUnit(Duration.ofSeconds(Long.valueOf(dto.getNumber())))
                : getFormatTimeUnit(Duration.ofMillis(Long.valueOf(dto.getNumber()))));
        String[] duration = str.split("-");
        return new TimeResponseDto(Long.valueOf(duration[0]), Long.valueOf(duration[1]), Long.valueOf(duration[2]));
    }

    private String getFormatTimeUnit(Duration duration) {
        long milliseconds = TimeUnit.MILLISECONDS.convert(duration);
        return String.format(FORMAT_TIME_UNIT,
                TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

    }
}
