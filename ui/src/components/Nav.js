import React from "react";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import HomePage from "./page_home/HomePage";
import ListPage from "./list_page/ListPage";
import ConvertTime from "./page_convert_time/ConvertTime";


function Nav() {
    return (
        <Router>
            <div>
                <ul style={{
                    listStyle: "none",
                    margin: "0 auto",
                    overflow: "hidden",
                    padding: "0"
                }}>
                    <li style={{float: "left"}}><Link style={{
                        display: "block",
                        padding: "1em",
                        borderRight: "1px solid #ADC0CE",
                        background: "#D4E7EE",
                        color: "#29838C"
                    }} to="/">Homepage</Link></li>
                    <li style={{float: "left"}}><Link style={{
                        display: "block",
                        padding: "1em",
                        borderRight: "1px solid #ADC0CE",
                        background: "#D4E7EE",
                        color: "#29838C"
                    }} to="/converter-time">ConvertTime</Link></li>
                </ul>
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    {/*<Route path="/lists/:id" render={(props) => (<ListPage id={props.match.params.id}>*/}
                    {/*</ListPage>)}/>*/}
                    <Route exact path="/converter-time" component={ConvertTime} />
                </Switch>
            </div>
        </Router>
    );
}

export default Nav;
