package com.lineate.timeconverter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lineate.timeconverter.entity.CitiesListEntity;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * This class is interface for for keep of user data
 */
public interface Storage {
    /**
     * Method return info about user lists from cookie
     * @return info about user lists from cookie
     * @throws IOException - IOException
     */
    List<CitiesListEntity> getCitiesLists() throws IOException;

    /**
     * Method return value of cookie
     * @return value of cookie
     */
    String getCookieValue();

    /**
     * Method changes value of cookie
     * @param newCookie - new value of cookie
     */
    void setCookieValue(String newCookie);

    /**
     * Method creates default cookie
     */
    String createDefaultCookie() throws JsonProcessingException, UnsupportedEncodingException;

    /**
     * Method add cookie in headers
     */
    HttpHeaders addCookie(String cookie) throws UnsupportedEncodingException;

    /**
     * Method update cookie after add or remove city from list of city
     * @param citiesList - list of cities
     * @throws JsonProcessingException - JsonProcessingException
     */
    void updateCookie(Optional<CitiesListEntity> citiesList) throws JsonProcessingException;
}
