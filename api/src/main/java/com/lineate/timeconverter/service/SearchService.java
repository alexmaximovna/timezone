package com.lineate.timeconverter.service;

import com.google.gson.Gson;
import com.lineate.timeconverter.entity.CityInfo;
import com.lineate.timeconverter.exception.CityException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lineate.timeconverter.exception.ErrorCode.WRONG_CITY;

/**
 * This class finds: timezone for latitude and longitude of city,
 * list of cities by substring
 */
@Service
public class SearchService {
    private static final String TIMEZONE_URL = "http://api.timezonedb.com/v2.1/get-time-zone?" +
            "key=P01B79EPHANT&format=json&by=position&lat=${lat}&lng=${lng}";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";
    private static final String ZONE_NAME = "zoneName";
    private RestTemplate rest = new RestTemplate();
    private String cityFile = "cities.json";


    /**
     * Method finds timezone for city by coordinates (latitude and longitude)
     * @param lat - latitude of current city
     * @param lng - longitude of current city
     * @return - name of timezone for current city
     * @throws ParseException - ParseException
     */
    public String findTimezone(String lat, String lng) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String[] arrGeoData = new String[]{lat, lng};
        String[] arrCityGeoDataParams = new String[]{LATITUDE, LONGITUDE};
        String timezone = rest.exchange(replaceParams(TIMEZONE_URL, arrGeoData, arrCityGeoDataParams),
                HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
        Object obj = new JSONParser().parse(timezone);
        JSONObject jo = (JSONObject) obj;
        return (String) jo.get(ZONE_NAME);

    }

    private String replaceParams(String url, String[] param, String[] paramsName) {
        Map<String, String> replacementMap = new HashMap<>();
        for (int i = 0; i < param.length; i++) {
            replacementMap.put(paramsName[i], param[i]);
        }
        StrSubstitutor substitutor = new StrSubstitutor(replacementMap);
        return substitutor.replace(url);

    }

    /**
     * Method finds first 10 cities by substring from file
     * @param partialNameCity - substring of city name
     * @return - list of 10 cities
     * @throws IOException -IOException
     */
    public Optional<List<CityInfo>> findCityBySymbols(String partialNameCity) throws IOException {
        if (StringUtils.isEmpty(partialNameCity)){
            throw new CityException(WRONG_CITY);
        }
        CityInfo[] ar = null;
        try (BufferedReader br = new BufferedReader(new FileReader(cityFile))) {
            ar = new Gson().fromJson(br, CityInfo[].class);
        }
        List<CityInfo> list = Arrays.asList(ar);
        List<CityInfo> res = list.stream()
                 .filter(c -> (c.getValue().toLowerCase().startsWith(partialNameCity.toLowerCase())))
                 .limit(10)
                 .collect(Collectors.toList());
        return Optional.of(res);
    }
}
