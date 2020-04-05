import React, {useState} from "react";
import {addCity, errorMessage} from "../../actions/Actions";
import {connect} from "react-redux";
import Icon from '@material-ui/core/Icon';
import ReactSearchBox from "react-search-box";
import axios from 'axios';




export function autocompleteSearchTest(value) {
    var call;
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


export function SearchMainBar({getCity,updateMessage}) {
    const [arr, setArr] = useState([]);
    // var listId = location.href;
    // var path = listId.split( '/' );
    // console.log(path);
    return (
        <div className="active-pink-3 active-pink-4 mb-4" style={{height:"120px",width:"500px", position: "relative",
            left: "600px",
            top: "5px",
            zindex: "5",overflow:"auto"}}>
            <ReactSearchBox
                placeholder="Search"
                onChange={(value) => {
                    if(value.length < 1){
                        setArr([]);
                    }
                    if(value.length >= 1){
                        autocompleteSearchTest(value).then(response => {
                            setArr(response.data);

                        }).catch(function (thrown) {
                            if (axios.isCancel(thrown)) {
                            } else {
                            }
                        });
                        updateMessage("");
                    }
                }}

            />

            <div className="active-pink-3 active-pink-4 mb-4" style={{width:"500px", margin: "auto"}}>
                {arr.map(suggestion => {
                    var item = suggestion;

                    return (
                        <div style={{borderBottom:"1px black solid",
                            borderLeft:"1px black solid"}} onClick={(e) => {clickMe(item, e, getCity)}}>
                            {suggestion.value}
                            <Icon fontSize="small" color="primary">add_circle</Icon>
                        </div>
                    );
                })}

            </div>
        </div>
        );
}



function clickMe(city, event,getCity){
    getCity(city);

}

export const mapDispatchSearchCity = (dispatch) => {
    return {
        getCity: (city) => dispatch(addCity(city,'TIME_FORMAT_24_HOURS')),
        updateMessage:(msg) =>  dispatch(errorMessage(msg)),
    }
};


export default connect(null,mapDispatchSearchCity)(SearchMainBar);

