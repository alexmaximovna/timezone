package com.lineate.timeconverter.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lineate.timeconverter.entity.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class TestBase {
    protected RestTemplate rest = new RestTemplate();


    protected HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    protected AdjustmentList[] get(int port, String url, String info) {
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + info);
        String fullUrl = "http://localhost:" + port + url;
        ResponseEntity<AdjustmentList[]> response = rest.exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), AdjustmentList[].class);
        return response.getBody();
    }

    protected CitiesListEntity get(int port, String url, String id, String info) {
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + info);
        String fullUrl = "http://localhost:" + port + url + id;
        ResponseEntity<CitiesListEntity> response = rest.exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(headers), CitiesListEntity.class);
        return response.getBody();
    }

    protected UserListCities post(int port, String url, CitiesListEntity cities, String format) {
        String fullUrl = "http://localhost:" + port + url;
        TimeAdjustmentRequest request = new TimeAdjustmentRequest();
        request.setCitiesList(cities);
        request.setTimeFormat(TimeFormat.fromText(format));
        ResponseEntity<UserListCities> response = rest.exchange(fullUrl,
                HttpMethod.POST, new HttpEntity<>(request, createHeaders()), UserListCities.class);
        return response.getBody();
    }

    protected CitiesListEntity post(int port, String url, CitiesListEntity cities,String city,String lat,String lng) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueCookie = objectMapper.writeValueAsString(cities);
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + valueCookie);
        String fullUrl = "http://localhost:" + port + url + city;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl)
                .queryParam("lat", lat)
                .queryParam("lng", lng);
        ResponseEntity<CitiesListEntity> response = rest.exchange(builder.toUriString(),
                HttpMethod.POST, new HttpEntity<>(headers), CitiesListEntity.class);
        return response.getBody();
    }

    protected CityInfo[] getCities(int port, String url, String info, String str) {
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + info);
        String fullUrl = "http://localhost:" + port + url;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl)
                .queryParam("str", str);
        ResponseEntity<CityInfo[]> response = rest.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), CityInfo[] .class);
        return response.getBody();
    }

    protected CitiesListEntity delete(int port, String url, CitiesListEntity cities,String city) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueCookie = objectMapper.writeValueAsString(cities);
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + valueCookie);
        String fullUrl = "http://localhost:" + port + url + city;
        ResponseEntity<CitiesListEntity> response = rest.exchange(fullUrl,
                HttpMethod.DELETE, new HttpEntity<>(headers), CitiesListEntity.class);
        return response.getBody();
    }
    protected CitiesListEntity put(int port, String url, CitiesListEntity cities, String newMainCity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueCookie = objectMapper.writeValueAsString(cities);
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + valueCookie);
        String fullUrl = "http://localhost:" + port + url + newMainCity;
        ResponseEntity<CitiesListEntity> response = rest.exchange(fullUrl,
                HttpMethod.PUT, new HttpEntity<>(headers), CitiesListEntity.class);
        return response.getBody();
    }
    protected CurrentTimeList get(int port, String url, CitiesListEntity cities,String format) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueCookie = objectMapper.writeValueAsString(cities);
        HttpHeaders headers = createHeaders();
        headers.add(HttpHeaders.COOKIE, "CityTimeZoneLists" + "=" + valueCookie);
        String fullUrl = "http://localhost:" + port + url;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl)
                .queryParam("format", format);
        ResponseEntity<CurrentTimeList> response = rest.exchange(builder.toUriString(),
                HttpMethod.GET, new HttpEntity<>(headers), CurrentTimeList.class);
        return response.getBody();
    }
}
