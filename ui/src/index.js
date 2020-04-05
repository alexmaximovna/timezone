import React from "react";
import ReactDOM from 'react-dom';
import App from "./components/App";
import {Provider} from "react-redux";

import {applyMiddleware, createStore} from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers/rootReducer";
import {composeWithDevTools} from "redux-devtools-extension";

const store = createStore(
    rootReducer,
    composeWithDevTools(applyMiddleware(thunk))
);

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('root')
);


