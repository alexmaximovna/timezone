import React, {useEffect, useState} from "react";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {connect} from 'react-redux'
import {timeCombinationFetch, deleteCity, getCurrentTime, changeHomeCity} from "../../actions/Actions";
import {FloodCell} from "./FloodCell";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from '@material-ui/icons/Delete';
import HomeOutlinedIcon from '@material-ui/icons/HomeOutlined';
import HomeIcon from '@material-ui/icons/Home';
import { Rnd } from "react-rnd";

const style = {

    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    border: "solid 5px black",
    backgroundColor: "transparent"
};

export function CitiesTable({data, getCombinations, format, cookie, listId,removeCity,getCurrTime,time,changeMainCity}) {//
    useEffect(() => {
        if (cookie) {
            console.log(time);
            if(time){
                getCurrTime(format=='TIME_FORMAT_24_HOURS'?"24h":"AM/PM");
            }
            getCombinations(format, listId);

        }
    }, [format, cookie,time]);//
    var count = 0;
    var numRow = 0;
    var sizeTable = 0;
    return (

        <Paper style={{marginTop: "20px"}}>

            <div style={{height:"100%"}}>
                <Rnd
                    bounds={"parent"}
                    style={style}
                    default={{
                        x: 130,
                        y: 0,
                        width: 60,
                        height: 550,
                    }}

                >
                </Rnd>
                <Table >
                    <TableBody>
                        {data.listCities.map((item, i) => {
                            sizeTable = data.listCities.length;
                            numRow++;
                            count = -1;
                            return (
                                <div>
                                    <TableRow key={item.locationName}>
                                        <div class="font-icon-wrapper" onClick={() => {
                                            removeCity(item.locationName,'TIME_FORMAT_24_HOURS');
                                        }}>
                                            <IconButton size="small" aria-label="delete">
                                                <DeleteIcon />
                                            </IconButton>
                                        </div>
                                        <div className="font-icon-wrapper" onClick={() => {
                                            changeMainCity(item.locationName,'TIME_FORMAT_24_HOURS');
                                        }}>
                                            {item.offsetFromMainCity == "" ?
                                                <HomeIcon/>   : <HomeOutlinedIcon/>}
                                        </div>
                                        <TableCell component="th" scope="row" style={{minWidth: 100, maxWidth: 100}}>
                                            {item.locationName}<br/>{item.offsetFromMainCity == "" ?
                                            <br/> : item.offsetFromMainCity}<br/>
                                            <h6>{time.currentTime.map((t)=>{
                                                if(t.name==item.locationName){
                                                    return t.currTime;
                                                }
                                            })}</h6>
                                        </TableCell>
                                        {item.hoursAlignment.map(num => (<TableCell key={count++} style={FloodCell
                                        (count, data.currentHourMainCity, num,numRow,sizeTable)} >{num}</TableCell>))}

                                    </TableRow>
                                </div>
                            );
                        })}
                    </TableBody>
                </Table>
            </div>

        </Paper>
    );

}


export const mapStateToProps = (state) => ({
    data: state.reducer.combination,
    format: state.reducer.typeFormat,
    cookie: state.reducer.cookie,
    listId: state.reducer.listPage,
    time: state.reducer.currTime
});
export const mapDispatchToTime = (dispatch) => {
    return {
        getCombinations: (format, listId) => dispatch(timeCombinationFetch(format, listId)),
        removeCity:(city,format) => dispatch(deleteCity(city,format)),
        getCurrTime: (format) => dispatch(getCurrentTime(format)),
        changeMainCity: (city,format) => dispatch(changeHomeCity(city,format)),

    }
};
export default connect(mapStateToProps, mapDispatchToTime)(CitiesTable);
