import React, {useEffect} from "react";
import {connect} from "react-redux";
import {numberOfListPage} from "../../actions/Actions";
import {MainCitiesTable} from "./MainCitiesTable";


function ListPage({id, addListId}) {
    useEffect(() => {
        if (id) {
            addListId(id);
        }
    }, []);
    return (
        <MainCitiesTable/>
    );
}

export const mapDispatchListId = (dispatch) => {
    return {
        addListId: (id) => dispatch(numberOfListPage(id)),
    }
};
export const mapStateToProp = (state) => ({
    listId: state.reducer.listPage
});

export default connect(mapStateToProp, mapDispatchListId)(ListPage);