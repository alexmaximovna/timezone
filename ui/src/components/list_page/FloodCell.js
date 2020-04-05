import React from "react";

var blue = ['12 AM', '1 AM', '2 AM', '3 AM', '4 AM', '5 AM', '10 PM', '11 PM',
    '1:30 AM', '2:30 AM', '3:30 AM', '4:30 AM', '5:30 AM','12:30 AM', '10:30  PM', '11:30  PM',
    '0:30','1:30','2:30','3:30','4:30','5:30','22:30','23:30'];
var yellow = ['8 AM', '9 AM', '10 AM', '11 AM', '12 PM', '1 PM', '2 PM', '3 PM', '4 PM', '5 PM',
    '8:30 AM', '9:30 AM', '10:30 AM', '11:30 AM',
    '12:30 PM', '1:30 PM', '2:30 PM', '3:30 PM', '4:30 PM', '5:30 PM',
    '8:30','9:30','10:30','11:30','12:30','13:30','14:30','15:30','16:30','17:30'];
var ligthBlue = ['6 AM', '7 AM', '8 PM', '9 PM', '5 AM', '10 PM', '11 PM', '6 PM', '7 PM',
    '6:30 AM', '7:30 AM', '6:30 PM', '7:30 PM',
    '8:30 PM', '9:30 PM', '5:30 AM', '10:30 PM', '11:30 PM', '6:30 PM', '7:30 PM',
    '6:30','7:30','18:30','19:30','20:30','21:30'];

export function FloodCell(count, curHour, num, numRow, sizeTable) {
    if (curHour == count) {
        if(numRow == 1){
            if (num == 22 || num == 23 || (num < 6 && num > -1) || blue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    backgroundColor: "#8FA8CF",
                    borderTopStyle: 'dotted',
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                };
            }
            if ((num > 7 && num < 18) || yellow.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderTopStyle: 'dotted',
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    backgroundColor: "#ffffcc",
                };
            }

            if (num == 6 || num == 7 || num > 17 && num < 22 || ligthBlue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderTopStyle: 'dotted',
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    backgroundColor: "#EDFBFF"
                };
            }
        }
        if(numRow == sizeTable){
            if (num == 22 || num == 23 || (num < 6 && num > -1) || blue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    borderBottom: '4px black dotted',
                    backgroundColor: "#8FA8CF"

                };
            }
            if ((num > 7 && num < 18) || yellow.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    borderBottom: '4px black dotted',
                    backgroundColor: "#ffffcc",
                };
            }

            if (num == 6 || num == 7 || num > 17 && num < 22 || ligthBlue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    borderBottom: '4px black dotted',
                    backgroundColor: "#EDFBFF"
                };
            }
        }else{
            if (num == 22 || num == 23 || (num < 6 && num > -1) || blue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    backgroundColor: "#8FA8CF"

                };
            }
            if ((num > 7 && num < 18) || yellow.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    backgroundColor: "#ffffcc",
                };
            }

            if (num == 6 || num == 7 || num > 17 && num < 22 || ligthBlue.includes(num)) {
                return {
                    minHeight: 100, maxHeight: 100,
                    minWidth: 50, maxWidth: 50,
                    padding: "10px",
                    textAlign: "center",
                    borderRightStyle: 'dotted',
                    borderLeftStyle: 'dotted',
                    backgroundColor: "#EDFBFF"
                };
            }
        }
    } else {
        if (num == 22 || num == 23 || (num < 6 && num > -1) || blue.includes(num)) {
            return {
                minHeight: 100, maxHeight: 100,
                minWidth: 50, maxWidth: 50,
                padding: "10px",
                textAlign: "center",
                backgroundColor: "#8FA8CF",
                borderLeft:'1px white solid',
                borderRight:'1px white solid',
            };
        }
        if ((num > 7 && num < 18) || yellow.includes(num)) {
            return {
                minHeight: 100, maxHeight: 100,
                minWidth: 50, maxWidth: 50,
                padding: "10px",
                textAlign: "center",
                backgroundColor: "#ffffcc",
                borderLeft:'1px white solid',
                borderRight:'1px white solid',
            };
        }
        if (num == 6 || num == 7 || num > 17 && num < 22 || ligthBlue.includes(num)) {
            return {
                minWidth: 50, maxWidth: 50,
                padding: "10px",
                textAlign: "center",
                backgroundColor: "#EDFBFF",
                borderLeft:'1px white solid',
                borderRight:'1px white solid',
            };
        }
    }

}
