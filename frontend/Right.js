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
                    return <PassedTest key={test} test={test}></PassedTest>;
                })}
                {this.props.failedTests.map(function (test) {
                    return <FailedTest key={test} test={test}></FailedTest>;
                })}
            </div>
        );
    }
}

export default Right;
