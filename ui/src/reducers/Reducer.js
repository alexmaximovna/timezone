import {
    allLists,
    clientStorageFetchData,
    errMessage,
    listNumber,
    timeCombination24,
    timeCombinationAmPm,
    timeCombinationFetchData,
    timeCurr, unitsTime
} from "../actions/Actions"

const initialState = {
    combination: {
        currentHourMainCity: 0,
        listCities: []
    },
    typeFormat: 'TIME_FORMAT_24_HOURS',
    listPage: 1,
    lists: [],
    cookie: "",
    messageError: "",
    currTime: [],
    timeUnit:{
        hours: 0,
        minutes: 0,
        seconds: 0
    }
};

export function reducer(state = initialState, action) {
    switch (action.type) {
        case timeCombinationFetchData:
            return {
                ...state,
                combination: action.timeCombination,
            };
        case unitsTime:
            return {
                ...state,
                timeUnit: action.timeData,
            };
        case clientStorageFetchData:
            return {
                ...state,
                cookie: action.cookie,
            };
        case timeCombination24:
            return {
                ...state,
                typeFormat: action.format,
            };
        case timeCombinationAmPm:
            return {
                ...state,
                typeFormat: action.format,
            };
        case allLists:
            return {
                ...state,
                lists: action.payload
            };
        case listNumber:
            return {
                ...state,
                listPage: action.listNum
            };

        case errMessage:
            return {
                ...state,
                messageError: action.message
            };
        case timeCurr:
            return {
                ...state,
                currTime: action.time
            };
        default:
            return state;
    }
}
