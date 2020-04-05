import axios from 'axios';


export const timeCombinationAmPm = "TIME_COMBINATION_AM_PM_HOURS";

export const timeCombination24 = "TIME_COMBINATION_24_HOURS";

export const listNumber = "LIST_NUMBER";

export const allLists = "SET_ALL_LISTS";

export const errMessage = "ERROR_MESSAGE";

export const timeCurr ="TIME";

export const unitsTime ="TIME_UNITS";

export const clientStorageFetchData = "CLIENT_STORAGE_DATA";

export const timeCombinationFetchData = "TIME_COMBINATION_FETCH_DATA_SUCCESS";

export const getAllListsCities = 'http://localhost:8088/api/city-timezone/lists';

export const getListById = 'http://localhost:8088/api/city-timezone/lists/';

export const getCombinationTime = 'http://localhost:8088/api/time-combination/';

export const convert = 'http://localhost:8088/api/converter';


export function timeUnits(data) {
    return {
        type: unitsTime,
        timeData: data
    }
}

export function timeCombinationFetchDataSuccess(combination) {
    return {
        type: timeCombinationFetchData,
        timeCombination: combination
    }
}

export function clientStorageData(cookie) {
    return {
        type: clientStorageFetchData,
        cookie: cookie
    }
}

export function allListsCities(lists) {
    return {
        type: allLists,
        payload: lists
    }
}

export function errorMessage(message) {
    return {
        type: errMessage,
        message: message
    }
}

export function timeCombinationAmPmHours(type) {
    return {
        type: timeCombinationAmPm,
        format: type
    }
}

export function timeCombination24Hours(type) {
    return {
        type: timeCombination24,
        format: type
    }
}

export function numberOfListPage(id) {
    return {
        type: listNumber,
        listNum: id
    }
}

export function time(data) {
    return {
        type: timeCurr,
        time: data
    }
}

export function getCurrentTime(format = '24h') {
    return (dispatch) => {
        getTime(getListById+"time",format).then(
            response => {
                dispatch(time(response.data));
            })
    }
}


export function getAllLists() {
    return (dispatch) => {
        getListCitiesJson(getAllListsCities).then(
            (response) => {
                dispatch(clientStorageData(response.data));
                dispatch(allListsCities(response.data));
            })
    }
}

function getListCitiesJsonWithConf(url) {
    return axios.get(url)

}

function addCityToList(url, lat, lng) {
    return axios.post(url, null, {
        params: {
            lat, lng
        }
    });
}
function changeMainCity(url) {
    return axios.put(url);
}

function getListCitiesJson(url) {
    return axios.get(url);
}

function getCombinationCityTable(url, data) {
    return axios.post(url, data, {withCredentials: true});
}
function deleteCityFromList(url){
    return axios.delete(url);

}
function getUnitTime(url,number,unit) {
    return axios.post(url,{
        number: number,
        typeUnit: unit
    });
}
function getTime(url,format) {
    return axios.get(url,{
        params: {
            format
        }
    });
}

export function converter(number,unit){
    return (dispatch) => {
        getUnitTime(convert,number,unit)
            .catch(function (error) {
            if (error.response) {
                dispatch(errorMessage(error.response.data.message));

            } else if (error.request) {
                //console.log(error.request);
            } else {
                //console.log('Error', error.message);
            }
        })
            .then(
                response => dispatch(timeUnits(response.data)));
    }
}

export function timeCombinationFetch(format = '24h', listId = 1) {
    return (dispatch) => {//, getState
        getListCitiesJsonWithConf(getListById + listId) .catch(function (error) {
            if (error.response) {
                dispatch(errorMessage(error.response.data.message));
                // console.log(error.response.data);
                // console.log(error.response.status);
                // console.log(error.response.headers);
            } else if (error.request) {
                //console.log(error.request);
            } else {
                //console.log('Error', error.message);
            }
        })
            .then(//,getState().reducer.cookie
            response => getCombinationCityTable(getCombinationTime, {
                citiesList: response.data,
                timeFormat: format
            }).then(
                response => dispatch(timeCombinationFetchDataSuccess(response.data))));
    }
}


export function addCity(city = "", format = 'TIME_COMBINATION_24_HOURS') {
    return (dispatch) => {
        addCityToList(getListById + city.value, city.lat, city.lng)
            .catch(function (error) {
                if (error.response) {
                    dispatch(errorMessage(error.response.data.message));
                    // console.log(error.response.data);
                    // console.log(error.response.status);
                    // console.log(error.response.headers);
                } else if (error.request) {
                    //console.log(error.request);
                } else {
                    //console.log('Error', error.message);
                }
            })
            .then(
                response => getCombinationCityTable(getCombinationTime, {
                    citiesList: response.data,
                    timeFormat: format
                }).then(
                    response => dispatch(timeCombinationFetchDataSuccess(response.data))));
    }
}

export function deleteCity(city = "", format = 'TIME_COMBINATION_24_HOURS') {
    return (dispatch) => {
        console.log(city);
        deleteCityFromList(getListById + city)
            .catch(function (error) {
                if (error.response) {
                    dispatch(errorMessage(error.response.data.message));
                    // console.log(error.response.data);
                    // console.log(error.response.status);
                    // console.log(error.response.headers);
                } else if (error.request) {
                    //console.log(error.request);
                } else {
                    //console.log('Error', error.message);
                }
            })
            .then(
                response => getCombinationCityTable(getCombinationTime, {
                    citiesList: response.data,
                    timeFormat: format
                }).then(
                    response => dispatch(timeCombinationFetchDataSuccess(response.data))));
    }
}
export function changeHomeCity(city = "", format = 'TIME_COMBINATION_24_HOURS') {
    return (dispatch) => {
        changeMainCity(getListById + city)
            .catch(function (error) {
                if (error.response) {
                    dispatch(errorMessage(error.response.data.message));
                    // console.log(error.response.data);
                    // console.log(error.response.status);
                    // console.log(error.response.headers);
                } else if (error.request) {
                    //console.log(error.request);
                } else {
                    //console.log('Error', error.message);
                }
            })
            .then(
                response => getCombinationCityTable(getCombinationTime, {
                    citiesList: response.data,
                    timeFormat: format
                }).then(
                    response => dispatch(timeCombinationFetchDataSuccess(response.data))));
    }
}

var call;

export function autocompleteSearchTest(value) {
    if (call) {
        call.cancel();
    }
    call = axios.CancelToken.source();

    return axios.get(`http://localhost:8088/api/city-search`, {
        params: {
            str: value
        },
        cancelToken: call.token,

    })

}
