package com.lineate.timeconverter.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lineate.timeconverter.TimeConverterApplication;
import com.lineate.timeconverter.entity.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import java.time.Duration;
import java.util.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEndpoints extends TestBase {


    @LocalServerPort
    private int port;

    @Test
    public void userLists() {
        String citiesList1 = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}]";
        String citiesList2 = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList1 + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}," +
                "{" +
                "\"id\":\"2\"," +
                "\"name\":\"myName1\"," +
                "\"data\":" + citiesList2 + "," +
                "\"currentMainCity\":\"0\"" +
                "}"+
                "]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 2, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        assertEquals("Lists", "myName1", cities[1].name);
    }
    @Test
    public void listCitiesByCookie() throws JsonProcessingException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);

        UserListCities response  = post(port, "/time-combination/", citiesListEntity, "24h");
        ObjectMapper o = new ObjectMapper();
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);
        String[] omsk = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] moscowSP = new String[]{"21", "22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(moscowSP), Arrays.asList(timeCombination[1].getHoursAlignment()));

    }

    @Ignore
    @Test
    public void defaultListByEmptyCookie() {
        String citiesLists = "";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);

        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "default", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("default", citiesListEntity.name);

        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);
        assertEquals("TimeCombination result", 5, timeCombination.length);
        String[] omsk = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] moscowSP = new String[]{"21", "22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        String[] portland = new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21", "22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] saratov = new String[]{"22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21"};
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(moscowSP), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(moscowSP), Arrays.asList(timeCombination[2].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(portland), Arrays.asList(timeCombination[3].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(saratov), Arrays.asList(timeCombination[4].getHoursAlignment()));

    }

    @Test
    public void listCitiesByCookieHomeMoscow() {
        String citiesList = "[{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
               "{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Pacific/Rarotonga\",\"name\":\"Honolulu\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Moscow\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] moscow = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] omsk = new String[]{ "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "0", "1", "2"};
        String[] hawaii = new String[]{"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(hawaii), Arrays.asList(timeCombination[2].getHoursAlignment()));

    }


    @Test
    public void listCitiesFormatAmPm() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Yerevan\",\"name\":\"Saratov\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);

        UserListCities response  = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);
        String[] omsk = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        String[] moscow = new String[]{"9 PM", "10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM"};
        String[] saratov = new String[]{"10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM"};

        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(saratov), Arrays.asList(timeCombination[2].getHoursAlignment()));


    }

    @Test
    public void listCitiesFormatAmPmHomeHolulu() {
        String citiesList = "[{\"timezone\":\"Pacific/Rarotonga\",\"name\":\"Honolulu\"}," +
                "{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Honolulu\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response  = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] haw = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        String[] omsk = new String[]{"4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM"};

        assertEquals("TimeCombination result", Arrays.asList(haw), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[1].getHoursAlignment()));

    }

    @Test
    public void listCitiesFormatAmPmHOmrOmsk() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Pacific/Rarotonga\",\"name\":\"Honolulu\"}," +
                "{\"timezone\":\"America/Los_Angeles\",\"name\":\"Portland\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] omsk = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        String[] haw = new String[]{"8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM"};
        String[] port = new String[]{"10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM"};

        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(haw), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(port), Arrays.asList(timeCombination[2].getHoursAlignment()));
    }


    @Test
    public void notPartialTimezoneHomeMoscow() {
        String citiesList = "[{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}," +
                "{\"timezone\":\"Australia/Darwin\",\"name\":\"Darwin\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Moscow\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] moscow = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] colombo = new String[]{"2:30", "3:30", "4:30", "5:30", "6:30", "7:30", "8:30", "9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30", "20:30", "21:30", "22:30", "23:30", "0:30", "1:30"};
        String[] darwin = new String[]{"6:30", "7:30", "8:30", "9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30", "20:30", "21:30", "22:30", "23:30", "0:30", "1:30", "2:30", "3:30", "4:30", "5:30"};
        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(colombo), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(darwin), Arrays.asList(timeCombination[2].getHoursAlignment()));

    }


    @Test
    public void notPartialTimezoneOmskColomboDarwin24() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}," +
                "{\"timezone\":\"Australia/Darwin\",\"name\":\"Darwin\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] omsk = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] colombo = new String[]{"23:30", "0:30", "1:30", "2:30", "3:30", "4:30", "5:30", "6:30", "7:30", "8:30", "9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30", "20:30", "21:30", "22:30"};
        String[] darwin = new String[]{"3:30", "4:30", "5:30", "6:30", "7:30", "8:30", "9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30", "16:30", "17:30", "18:30", "19:30", "20:30", "21:30", "22:30", "23:30", "0:30", "1:30", "2:30"};
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(colombo), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(darwin), Arrays.asList(timeCombination[2].getHoursAlignment()));

    }


    @Test
    public void notPartialTimezoneOmskColomboDarwinAmPm() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}," +
                "{\"timezone\":\"Australia/Darwin\",\"name\":\"Darwin\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] omsk = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        String[] colombo = new String[]{"11:30 PM", "12:30 AM", "1:30 AM", "2:30 AM", "3:30 AM", "4:30 AM", "5:30 AM", "6:30 AM", "7:30 AM", "8:30 AM", "9:30 AM", "10:30 AM", "11:30 AM", "12:30 PM", "1:30 PM", "2:30 PM", "3:30 PM", "4:30 PM", "5:30 PM", "6:30 PM", "7:30 PM", "8:30 PM", "9:30 PM", "10:30 PM"};
        String[] darwin = new String[]{"3:30 AM", "4:30 AM", "5:30 AM", "6:30 AM", "7:30 AM", "8:30 AM", "9:30 AM", "10:30 AM", "11:30 AM", "12:30 PM", "1:30 PM", "2:30 PM", "3:30 PM", "4:30 PM", "5:30 PM", "6:30 PM", "7:30 PM", "8:30 PM", "9:30 PM", "10:30 PM", "11:30 PM", "12:30 AM", "1:30 AM", "2:30 AM"};

        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(colombo), Arrays.asList(timeCombination[1].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(darwin), Arrays.asList(timeCombination[2].getHoursAlignment()));

    }


    @Test
    public void notPartialTimezoneMoscowDarwin() {
        String citiesList = "[{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Australia/Darwin\",\"name\":\"Darwin\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Moscow\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] moscow = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        String[] darwin = new String[]{"6:30 AM", "7:30 AM", "8:30 AM", "9:30 AM", "10:30 AM", "11:30 AM", "12:30 PM", "1:30 PM", "2:30 PM", "3:30 PM", "4:30 PM", "5:30 PM", "6:30 PM", "7:30 PM", "8:30 PM", "9:30 PM", "10:30 PM", "11:30 PM", "12:30 AM", "1:30 AM", "2:30 AM", "3:30 AM", "4:30 AM", "5:30 AM"};
        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(darwin), Arrays.asList(timeCombination[1].getHoursAlignment()));

    }

    @Test
    public void listCitiesFormatAmPmOmskColombo() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "AM/PM");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        String[] omsk = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[0].getHoursAlignment()));
    }


    @Test
    public void listCitiesOffset() {
        String citiesList = "[{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Pacific/Rarotonga\",\"name\":\"Honolulu\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Moscow\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        assertEquals("Lists", "+ 3", timeCombination[1].getOffsetFromMainCity());
        assertEquals("Lists", "-13", timeCombination[2].getOffsetFromMainCity());

    }

    @Test
    public void listCitiesOffsetPartical() {
        String citiesList = "[{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}," +
                "{\"timezone\":\"Pacific/Rarotonga\",\"name\":\"Honolulu\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Moscow\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);

        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        UserListCities response = post(port, "/time-combination/", citiesListEntity, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);

        assertEquals("Lists", "+ 2.5", timeCombination[1].getOffsetFromMainCity());
        assertEquals("Lists", "-13", timeCombination[2].getOffsetFromMainCity());

    }

    @Test
    public void testAddCityToList() throws IOException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        String cityForAdding = "Moscow";
        String lat = "55.75222";
        String lng = "37.61556";
        CitiesListEntity res = post(port,"/city-timezone/lists/",citiesListEntity,cityForAdding,lat,lng);
        assertEquals("Lists to add city", 3, res.data.size());
    }
    @Test
    public void testDeleteCityFromList() throws IOException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        String cityForDel = "Colombo";
        CitiesListEntity res = delete(port,"/city-timezone/lists/",citiesListEntity,cityForDel);
        assertEquals("Lists to remove city", 1, res.data.size());
    }

    @Test
    public void changeMainCity() throws JsonProcessingException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        String newMainCity = "Moscow";
        CitiesListEntity res = put(port,"/city-timezone/lists/",citiesListEntity,newMainCity);
        UserListCities response = post(port, "/time-combination/", res, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);
        String[] moscow = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] omsk = new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21", "22", "23", "0", "1", "2"};
        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(omsk), Arrays.asList(timeCombination[1].getHoursAlignment()));
    }
    @Test
    public void deleteMainCity() throws JsonProcessingException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Novosibirsk\",\"name\":\"Novosibirsk\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        String cityForDel = "Omsk";
        CitiesListEntity res = delete(port,"/city-timezone/lists/",citiesListEntity,cityForDel);
        UserListCities response = post(port, "/time-combination/", res, "24h");
        LocationTimeAdjustment[] timeCombination = response.listCities.toArray(new LocationTimeAdjustment[0]);
        String[] moscow = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] novosibirsk = new String[]{ "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21", "22", "23", "0", "1", "2","3",};
        assertEquals("TimeCombination result", Arrays.asList(moscow), Arrays.asList(timeCombination[0].getHoursAlignment()));
        assertEquals("TimeCombination result", Arrays.asList(novosibirsk), Arrays.asList(timeCombination[1].getHoursAlignment()));
    }


    @Test
    public void testSearchCities() {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Asia/Colombo\",\"name\":\"Colombo\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"0\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        String strForSearch = "Mo";
        CityInfo[] foundCity = getCities(port, "/city-search", citiesLists,strForSearch);
        assertNotNull(foundCity);
        assertEquals("Search cities",10, foundCity.length);

    }
    @Test
    public void getCurrTime() throws JsonProcessingException {
        String citiesList = "[{\"timezone\":\"Asia/Omsk\",\"name\":\"Omsk\"}," +
                "{\"timezone\":\"Europe/Moscow\",\"name\":\"Moscow\"}," +
                "{\"timezone\":\"Asia/Novosibirsk\",\"name\":\"Novosibirsk\"}]";
        String citiesLists = "[{" +
                "\"id\":\"1\"," +
                "\"name\":\"myName\"," +
                "\"data\":" + citiesList + "," +
                "\"currentMainCity\":\"Omsk\"" +
                "}]";
        AdjustmentList[] cities = get(port, "/city-timezone/lists", citiesLists);
        assertEquals("Lists", 1, cities.length);
        assertEquals("Lists", "myName", cities[0].name);
        CitiesListEntity citiesListEntity = get(port, "/city-timezone/lists/", "1", citiesLists);
        assertNotNull(citiesListEntity);
        assertEquals("myName", citiesListEntity.name);
        CurrentTimeList ctl = get(port,"/city-timezone/lists/time/",citiesListEntity,"24h");
        assertEquals("ListTime result", 3, ctl.getCurrentTime().length);

    }
    @Test
    public void converterTime() {
        TimeUnitDto dto = new TimeUnitDto("456789","minutes");
        HttpHeaders headers = createHeaders();
        String fullUrl = "http://localhost:" + port + "/converter";
        ResponseEntity<TimeResponseDto> response = rest.exchange(fullUrl, HttpMethod.POST, new HttpEntity<>(dto,headers),TimeResponseDto.class);
        assertEquals("Converter", Optional.of(7613).get(), response.getBody().getHours());
        assertEquals("Converter", Optional.of(9).get(), response.getBody().getMinutes());


    }

}
