package com.lineate.timeconverter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lineate.timeconverter.entity.CitiesListEntity;
import com.lineate.timeconverter.entity.City;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 * This class need to treatment of user Cookie
 */
public class CookieStorage implements Storage {

    private static final String COOKIE_NAME = "CityTimeZoneLists";

    /**
     * valueCookie - data from cookie
     */
    private String valueCookie;

    /**
     * objectMapper - mapper for transfer data from String to list of CitiesListEntity
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor of this class
     *
     * @param request - request with info about user in cookie
     * @throws JsonProcessingException - JsonProcessingException
     */
    public CookieStorage(HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        Optional<Cookie> cookie = parseCookie(request, COOKIE_NAME);
        valueCookie = cookie.isEmpty() ? null : URLDecoder.decode(cookie.get().getValue(), StandardCharsets.UTF_8.toString());
    }

    /**
     * Method return info about user lists from cookie
     *
     * @return info about user lists from cookie
     * @throws IOException - IOException
     */
    @Override
    public List<CitiesListEntity> getCitiesLists() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper.readValue(valueCookie, new TypeReference<List<CitiesListEntity>>() {
        });
    }

    @Override
    public String getCookieValue() {
        return valueCookie;
    }

    @Override
    public void setCookieValue(String newCookie) {
        valueCookie = newCookie;
    }

    private List<CitiesListEntity> getDefault() {
        List<City> cities = new LinkedList<>();
        City city = new City();
        city.setName("Moscow");
        city.setTimezone("Europe/Moscow");
        City city1 = new City();
        city1.setName("Omsk");
        city1.setTimezone("Asia/Omsk");
        City city2 = new City();
        city2.setName("Saint Petersburg");
        city2.setTimezone("Europe/Moscow");
        City city3 = new City();
        city3.setName("Portland");
        city3.setTimezone("America/Los_Angeles");
        City city4 = new City();
        city4.setName("Saratov");
        city4.setTimezone("Asia/Yerevan");
        Collections.addAll(cities, city1, city, city2, city3, city4);
        List<CitiesListEntity> lists = new ArrayList<>();
        CitiesListEntity citiesListEntity = new CitiesListEntity();
        citiesListEntity.id = "1";
        citiesListEntity.name = "default";
        citiesListEntity.data = cities;
        citiesListEntity.currentMainCity = "Omsk";
        lists.add(citiesListEntity);
        return lists;
    }


    private Optional<Cookie> parseCookie(HttpServletRequest request, String cookieName) {
        Optional<Cookie> parsCookie = Optional.empty();
        if (request.getCookies() != null) {
            parsCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst();
        }
        if (parsCookie.isEmpty() && request.getHeader(HttpHeaders.COOKIE) != null) {
            parsCookie = HttpCookie.parse(request.getHeader(HttpHeaders.COOKIE)).stream()
                    .filter(c -> c.getName().equals((cookieName)))
                    .map(httpCookie -> new Cookie(cookieName, httpCookie.getValue()))
                    .findFirst();
        }
        if (parsCookie.isEmpty() && request.getHeader("client_storage_data") != null) {
            String[] cookie = request.getHeader("client_storage_data").split("=");
            parsCookie = Optional.of(new Cookie(cookieName, cookie[1]));
        }

        if (parsCookie.isEmpty()) {
            parsCookie = Optional.empty();
        }
        return parsCookie;
    }

    @Override
    public String createDefaultCookie() throws JsonProcessingException {
        return objectMapper.writeValueAsString(getDefault());
    }

    @Override
    public HttpHeaders addCookie(String cookie) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "CityTimeZoneLists=" + URLEncoder.encode(cookie, StandardCharsets.UTF_8.toString()));
        return headers;

    }


    @Override
    public void updateCookie(Optional<CitiesListEntity> citiesList) throws JsonProcessingException {
        CitiesListEntity c = citiesList.get();
        valueCookie = objectMapper.writeValueAsString(c);
    }
}
