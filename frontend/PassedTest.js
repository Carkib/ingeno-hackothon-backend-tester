import React from "react";

class PassedTest extends React.Component {
    render() {
        var styles = {
                test: {
                    color: "white",
                    backgroundColor: "#8ee547",
                    fontSize: 24,
                    fontFamily: "Arial",
                    marginBottom: '5px'
                },
                text: {
                    margin: "0px 5px",
                }
            }
        ;
        return (
            <div style={styles.test}>
                <div style={styles.text}>{this.props.test}</div>
            </div>
        );
    }
}

export default PassedTest;
