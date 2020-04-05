package com.lineate.timeconverter.service;

import com.lineate.timeconverter.entity.AdjustmentList;
import com.lineate.timeconverter.entity.CitiesListEntity;
import com.lineate.timeconverter.entity.City;
import com.lineate.timeconverter.entity.CityCurrTime;
import com.lineate.timeconverter.entity.CurrentTimeList;
import com.lineate.timeconverter.entity.TimeFormat;
import com.lineate.timeconverter.exception.CityException;
import com.lineate.timeconverter.exception.ErrorCode;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lineate.timeconverter.exception.ErrorCode.EXIST_CITY;
import static com.lineate.timeconverter.exception.ErrorCode.OVERRIDE_LIMIT;

/**
 * This class is service that work with user storage
 */
@Service
public class CityTimezoneListsService {

    private static final Integer LIMIT_COUNT_CITIES = 10;
    private final DateTimeFormatter Formatter24 = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter FormatterAmPm = DateTimeFormatter.ofPattern("h:mm a");


    @Autowired
    private SearchService searchService;

    /**
     * Method return user's lists from storage
     *
     * @param storage - storage with data of user
     * @return - lists cities
     * @throws IOException - IOException
     */
    public List<AdjustmentList> getLists(Storage storage) throws IOException {
        if (storage.getCookieValue() == null || storage.getCookieValue().equals("")) {
            storage.setCookieValue(storage.createDefaultCookie());
        }
        return storage.getCitiesLists().stream()
                .map(item -> new AdjustmentList(item.id, item.name))
                .collect(Collectors.toList());
    }

    /**
     * Method return user's lists from storage by id
     *
     * @param storage - storage with data of user
     * @param id      - id of list cities
     * @return - list with cities
     * @throws IOException - IOException
     */
    public Optional<CitiesListEntity> getListCities(Storage storage, String id) throws IOException {
        if (storage.getCookieValue() == null) {
            throw new CityException(ErrorCode.NOT_EXIST_LIST);
        }
        return Optional.of(storage.getCitiesLists().stream()
                .filter(item -> item.id.equals(id))
                .findFirst()
                .orElseThrow(()-> new CityException(ErrorCode.NOT_EXIST_LIST)));
    }

    /**
     * Method adds city to list
     *
     * @param storage  - storage with data of user
     * @param cityName - name of city
     * @param lat      - latitude
     * @param lng      - longitude
     * @return - list with cities
     * @throws ParseException - ParseException
     * @throws IOException    - IOException
     */
    public Optional<CitiesListEntity> addCityToList(Storage storage, String cityName, String lat, String lng) throws ParseException, IOException {
        //TODO(fix for every listId)
        CitiesListEntity listEntity = getListCities(storage, "1").get();
        List<City> citiesList = listEntity.data;
        if (citiesList.size() >= LIMIT_COUNT_CITIES) {
            throw new CityException(OVERRIDE_LIMIT);
        }
        if (citiesList.stream()
                .anyMatch(city -> city.getName().toLowerCase().equals(cityName.toLowerCase()))) {
            throw new CityException(EXIST_CITY);

        }
        String timezone = searchService.findTimezone(lat, lng);
        citiesList.add(new City(cityName, timezone));
        listEntity.data = citiesList;
        return Optional.of(listEntity);
    }

    /**
     * Method removes city from list
     * @param storage - storage with data of user
     * @param cityName - name of city
     * @return - list with cities
     */
    public Optional<CitiesListEntity> deleteCityFromList(Storage storage, String cityName) throws IOException {
        CitiesListEntity listEntity = getListCities(storage, "1").get();
        List<City> cities = listEntity.data;
        if (cities == null || cities.size() <= 1){
            throw new CityException(ErrorCode.CANT_DELETE_CITY);
        }
        String mainCity = listEntity.currentMainCity;
        if (mainCity.equals(cityName)){
            listEntity.currentMainCity = cities.get(1).getName();
        }
        listEntity.data = cities.stream()
                .filter(city -> !city.getName().equals(cityName))
                .collect(Collectors.toList());
        return Optional.of(listEntity);
    }

    /**
     * Method changes mainCity in user's list of cities
     * @param mainCity - new mainCity
     * @param storage - storage with data of user
     * @return - list of cities
     * @throws IOException -IOException
     */
    public Optional<CitiesListEntity> changeMainCity(Storage storage, String mainCity) throws IOException {
        CitiesListEntity listEntity = getListCities(storage, "1").get();
        listEntity.currentMainCity = mainCity;
        return Optional.of(listEntity);
    }

    /**
     * Method return list of current time for each city from city list
     * @param format - format of time
     * @param storage - storage with data of user
     * @return - list of current time for each city in list
     * @throws IOException -IOException
     */
    public Optional<CurrentTimeList> getCurrentTime(Storage storage, String format) throws IOException {
        CitiesListEntity listEntity = getListCities(storage, "1").get();
        List<CityCurrTime> list = listEntity.data.stream().map(t -> new CityCurrTime(t.getName(), getTime(t, format))).collect(Collectors.toList());
        CityCurrTime[] arr = list.toArray(new CityCurrTime[0]);
        return Optional.of(new CurrentTimeList(arr));

    }
    private String getTime(City city, String format){
        ZonedDateTime zCity = ZonedDateTime.ofInstant(Clock.systemUTC().instant(), ZoneId.of(city.getTimezone()));
        return  zCity.format(format.equals(TimeFormat.TIME_FORMAT_24_HOURS.getTimeFormat()) ? Formatter24 : FormatterAmPm);
    }
}
