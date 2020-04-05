import React, {useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import {connect} from "react-redux";
import {getAllLists} from "../../actions/Actions";
import {Link} from "react-router-dom";


const useStyles = makeStyles(theme => ({
    root: {
        width: '50%',
        maxWidth: 150,
        backgroundColor: theme.palette.background.paper,
        border: "1px solid black"
    },
}));

function ListItemLink(props) {
    let url = ("/lists/" + props.listId).toString();
    return <ListItem button component={Link} to={"url"}>{props.children}</ListItem>;
}

function SimpleList({getLists, data}) {
    const classes = useStyles();
    useEffect(() => {
        getLists();
    }, []);
    return (
        <div>
            {/*<h6>Lists</h6>*/}
            {/*<div className={classes.root}>*/}
                {/*<List>*/}
                    {/*{data.map(item => (*/}
                        {/*<ListItemLink listId={item.id} key={item.id}>*/}
                            {/*<ListItemText primary={item.name}/>*/}
                        {/*</ListItemLink>))*/}
                    {/*}*/}
                {/*</List>*/}
            {/*</div>*/}
        </div>
    );
}

const mapStateToProp = state => ({
    data: state.reducer.lists
});
const mapDispatch = (dispatch) => {
    return {
        getLists: () => dispatch(getAllLists()),
    }
};
export default connect(mapStateToProp, mapDispatch)(SimpleList)