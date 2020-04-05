import React from "react";
import CitiesTable from "./CitiesTable";
import FormatTime from "./FormatTime";
import SetList from "./SetList";
import SearchMainBar from "./SearchMainBar";
import Message from "./Message";


export function MainCitiesTable() {
    return (
        <div style={{
            width: "1700px",
            height: "1200px",
            alignContent: "center",
            backgroundColor: "white",
            marginTop: "11px"
        }}>
            <FormatTime/>
            <SetList/>
            <Message/>
            <SearchMainBar/>
            <CitiesTable/>

        </div>

    );
}

