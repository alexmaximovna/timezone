package com.lineate.timeconverter.service;

import com.lineate.timeconverter.entity.City;
import com.lineate.timeconverter.entity.LocationTimeAdjustment;
import com.lineate.timeconverter.entity.PartialTimeZone;
import com.lineate.timeconverter.entity.TimeAdjustmentRequest;
import com.lineate.timeconverter.entity.TimeFormat;
import com.lineate.timeconverter.entity.UserListCities;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * This class is service that treatments user's lists
 */
@Service
public class TimeCombinationService {

    private final static int HOURS_24 = 24;
    private final static String HALF_HOUR = ":30";


    /**
     * Method return list of time offset for every city from user's list
     *
     * @param request - request contains info about format time and list of cities
     * @return - user's lists of cities
     * @throws ParseException - ParseException
     */
    public UserListCities getCitiesWithTimeZone(TimeAdjustmentRequest request) throws ParseException {
        List<LocationTimeAdjustment> citiesList = new LinkedList<>();
        return getCitiesTimeLists(citiesList, request);
    }

    private UserListCities getCitiesTimeLists(List<LocationTimeAdjustment> listCities,
                                              TimeAdjustmentRequest request) throws ParseException {
        List<City> cities = checkMainCity(request);
        TimeFormat timeFormat = request.getTimeFormat();
        ZonedDateTime zMainCity = ZonedDateTime.ofInstant(Clock.systemUTC().instant(), ZoneId.of(cities.get(0).getTimezone()));
        UserListCities userListCities = new UserListCities();
        userListCities.currentHourMainCity = zMainCity.getHour();
        LocationTimeAdjustment mainCity = createLocTimeAdj(cities.get(0).getName(),
                createAlignedHours(0, timeFormat, checkPartialTimezone(cities.get(0).getTimezone())),
                "");
        listCities.add(mainCity);
        for (int i = 1; i < cities.size(); i++) {
            ZonedDateTime zCurrentCity = ZonedDateTime.ofInstant(Clock.systemUTC().instant(), ZoneId.of(cities.get(i).getTimezone()));
            String offset = ZoneOffset.ofTotalSeconds(zCurrentCity.getOffset().getTotalSeconds() - zMainCity.getOffset().getTotalSeconds()).toString();
            LocationTimeAdjustment item = createLocTimeAdj(cities.get(i).getName(), createAlignedHours(getStartIndex(offset),
                    timeFormat, checkPartialTimezone(cities.get(i).getTimezone())), offset);

            listCities.add(item);
        }
        userListCities.listCities = listCities;
        return userListCities;
    }


    private List<City> checkMainCity(TimeAdjustmentRequest request) {
        List<City> cities = request.getCitiesList().data;
        String mainCity = request.getCitiesList().currentMainCity;
        if (cities.get(0).getName().equals(mainCity)){
            return cities;
        }
        City city = cities.stream().filter(c -> c.getName().equals(mainCity)).findFirst().get();
        cities.remove(city);
        cities.add(0, city);
        return cities;
    }

    private LocationTimeAdjustment createLocTimeAdj(String locName, String[] hoursAlig,
                                                    String offsetFromMainCity) {
        LocationTimeAdjustment item = new LocationTimeAdjustment();
        item.setLocationName(locName);
        item.setHoursAlignment(hoursAlig);
        item.setOffsetFromMainCity(formatOffset(offsetFromMainCity));
        return item;
    }

    private String formatOffset(String offset) {
        String newOffset = offset.equals("Z") ? "00:00" : offset;
        newOffset = newOffset.replaceFirst("0", " ");
        if (newOffset.contains(HALF_HOUR)) {
            return newOffset.split(":")[0] + ".5";
        }
        if (newOffset.contains("00:00")) {
            return "0";
        }
        return newOffset.split(":")[0];

    }

    private String[] createAlignedHours(int startIndex, TimeFormat timeFormat, boolean halfHour) throws ParseException {
        Integer[] hours = new Integer[24];
        hours[startIndex] = 0;
        fullLeft(startIndex, hours);
        fullRight(startIndex, hours);
        return timeFormatter(hours, timeFormat, halfHour);
    }

    private int getStartIndex(String betweenCities) {
        String offset = betweenCities.split(":")[0];
        int index = offset.equals("Z") ? 0 : Integer.valueOf(offset);
        if (betweenCities.contains(HALF_HOUR)) {
            return betweenCities.contains("-") ? index = index + 1 : HOURS_24 - index;
        } else {
            return betweenCities.contains("+") ? HOURS_24 - index : Math.abs(index);
        }

    }

    private String[] formatAmPm(String[] arr) throws ParseException {
        String[] timeFormatAmPm = new String[HOURS_24];
        SimpleDateFormat formatter12Hours = arr[0].contains(":") ? new SimpleDateFormat("h:mm") : new SimpleDateFormat("h");
        String formatAmPm;
        for (int i = 0; i < HOURS_24; i++) {
            if (formatter12Hours.toPattern().equals("h")) {
                arr[i] = arr[i].equals("12") ? arr[i] = "0" : (arr[i].equals("0") ? arr[i] = "12" : arr[i]);
            } else {
                arr[i] = arr[i].equals("12:30") ? arr[i] = "0:30" : (arr[i].equals("0:30") ? arr[i] = "12:30" : arr[i]);
            }
            formatAmPm = formatter12Hours.format(formatter12Hours.parse(arr[i]));
            timeFormatAmPm[i] = formatAmPm.equals(arr[i]) ? formatAmPm + " AM" : formatAmPm + " PM";
        }
        return timeFormatAmPm;
    }

    private String[] timeFormatter(Integer[] hours, TimeFormat timeFormat, boolean halfHour) throws ParseException {
        List<String> result = Arrays.stream(hours)
                .map(String::valueOf)
                .collect(Collectors.toList());
        if (halfHour) {
            result = result.stream()
                    .map(h -> h + HALF_HOUR)
                    .collect(Collectors.toList());
        }
        return timeFormat.equals(TimeFormat.TIME_FORMAT_24_HOURS) ? result.toArray(new String[0])
                : formatAmPm(result.toArray(new String[0]));

    }

    private void fullLeft(int startIndex, Integer[] hours) {
        int startValueSell = HOURS_24;
        for (startIndex = startIndex - 1; startIndex >= 0; startIndex--) {
            startValueSell--;
            hours[startIndex] = startValueSell;
        }
    }

    private void fullRight(int startIndex, Integer[] hours) {
        int index = startIndex;
        for (int j = 1; j < HOURS_24 - startIndex; j++) {
            index++;
            hours[index] = j;
        }
    }

    private boolean checkPartialTimezone(String timezone) {
        Optional<PartialTimeZone> timeZone = Arrays.stream(PartialTimeZone.values())
                .filter(t -> t.getTimeZone().equals(timezone))
                .findFirst();
        return timeZone.isPresent();
    }

}
