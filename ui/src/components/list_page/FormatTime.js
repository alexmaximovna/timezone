import React from 'react';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import {connect} from "react-redux";
import {timeCombination24Hours, timeCombinationAmPmHours} from "../../actions/Actions";

function GroupedButtons(props) {

    function selectFormat24() {
        props.postFormat('TIME_FORMAT_24_HOURS')
    }

    function selectFormatAmPm() {
        props.postFormat('TIME_FORMAT_AM_PM')
    }

    return (
        <Grid container spacing={3}>
            <Grid item xs={12} md={6}>
                <Grid container spacing={1} direction="column" alignItems="center">
                    <Grid item>
                        <ButtonGroup size="small" aria-label="small outlined button group">
                            <Button onClick={selectFormat24}>24</Button>
                            <Button onClick={selectFormatAmPm}>am/pm</Button>
                        </ButtonGroup>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>);
}

const mapDispatchToTime = (dispatch) => {
    return {
        postFormat: (format) => dispatch(format == 'TIME_FORMAT_24_HOURS' ? timeCombination24Hours(format) : timeCombinationAmPmHours(format)),
    }
};
export default connect(null, mapDispatchToTime)(GroupedButtons);
