import React from "react";
import {BrowserRouter as Router, Route, Redirect} from "react-router-dom";
import ListPage from "../list_page/ListPage";


function HomePage() {
    return (
        <ListPage id ="1"/>
    );
}

export default HomePage;
