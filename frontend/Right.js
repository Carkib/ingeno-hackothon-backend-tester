import React from "react";
import PassedTest from "./PassedTest.js";
import FailedTest from "./FailedTest.js";

class Right extends React.Component {
    render() {
        var styles = {
            name: {
                fontSize: 16,
                display: "block"
            },
            score: {
                heigth: "200px"
            }
        };
        return (
            <div>
                {this.props.passedTests.map(function (test) {
                    return <PassedTest test={test}></PassedTest>;
                })}
                {this.props.failedTests.map(function (test) {
                    return <FailedTest test={test}></FailedTest>;
                })}
            </div>
        );
    }
}

export default Right;
