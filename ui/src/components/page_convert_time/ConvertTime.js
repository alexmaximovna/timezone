import React from "react";
import { makeStyles, withStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import NativeSelect from '@material-ui/core/NativeSelect';
import InputBase from '@material-ui/core/InputBase';
import { converter} from "../../actions/Actions";
import {connect} from "react-redux";
import Message from "../list_page/Message";


const BootstrapInput = withStyles(theme => ({
        root: {
            'label + &': {
                marginTop: theme.spacing(3),
            },
        },
        input: {
            borderRadius: 4,
            position: 'relative',
            backgroundColor: theme.palette.background.paper,
            border: '1px solid #ced4da',
            fontSize: 16,
            padding: '10px 26px 10px 12px',
            transition: theme.transitions.create(['border-color', 'box-shadow']),
            // Use the system font instead of the default Roboto font.
            fontFamily: [
                '-apple-system',
                'BlinkMacSystemFont',
                '"Segoe UI"',
                'Roboto',
                '"Helvetica Neue"',
                'Arial',
                'sans-serif',
                '"Apple Color Emoji"',
                '"Segoe UI Emoji"',
                '"Segoe UI Symbol"',
            ].join(','),
            '&:focus': {
                borderRadius: 4,
                borderColor: '#80bdff',
                boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
            },
        },
    }))(InputBase);

    const useStyles = makeStyles(theme => ({
        margin: {
            margin: theme.spacing(1),
        },
    }));

function ConvertTime({getUnitTime,data,error}) {
    const classes = useStyles();
    const [number, setNumber] = React.useState('');
    const [type, setType] = React.useState("");
    const handleChange = event => {
        setNumber(event.target.value);
    };
    return (
        <div>
            <Message/>
            <FormControl className={classes.margin}>
                <InputLabel htmlFor="demo-customized-textbox"></InputLabel>
                <BootstrapInput  min="0" onChange={handleChange} type="number" id="demo-customized-textbox" />
            </FormControl>
            <FormControl className={classes.margin}>
                <InputLabel htmlFor="demo-customized-select-native"></InputLabel>
                <NativeSelect
                    id="demo-customized-select-native"
                    onChange={(e)=>{
                        getUnitTime(number,e.target.value);
                    }

                    }
                    input={<BootstrapInput/>}
                >
                    <option value="" />
                    <option value={"minutes"}>Minutes</option>
                    <option value={"seconds"}>Seconds</option>
                    <option value={"ms"}>Milliseconds</option>
                </NativeSelect>
            </FormControl>
            <div style={{marginLeft:"100px"}}>
                <input value={data.hours}/> Hours<br/>
                <input value={data.minutes}/> Minutes<br/>
                <input value={data.seconds}/> Seconds<br/>
            </div>
        </div>
    );
}
export const mapStateToProps = (state) => ({
    data: state.reducer.timeUnit,
    error:state.reducer.messageError,
});
export const mapDispatchToTime = (dispatch) => {
    return {
        getUnitTime: (number, unit) => dispatch(converter(number, unit)),
    }
};
export default connect(mapStateToProps, mapDispatchToTime)(ConvertTime);