import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Alert from '@material-ui/lab/Alert';
import {connect} from "react-redux";


const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        '& > * + *': {
            marginTop: theme.spacing(2),
        },
    },
}));


export function Message({data}) {
    const classes = useStyles();
    if (!data) {
        return (
            <div>
            </div>
        );
    } else {
        return (
            <div className={classes.root}>
                <Alert severity="error">{data}</Alert>
            </div>
        );
    }

}

const mapStateToProp = state => ({
    data: state.reducer.messageError
});

export default connect(mapStateToProp, null)(Message)