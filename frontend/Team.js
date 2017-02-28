import React from "react";
import Left from "./Left.js";
import Right from "./Right.js";

class Team extends React.Component {

    render() {
        var styles = {
                teamBox: {
                    padding: 10,
                    color: "#333"
                },
                left: {
                    width: "30%",
                    display: "inline-block"
                },
                right: {
                    width: "70%",
                    display: "inline-block",
                    verticalAlign: "top"
                }
            }
        ;
        return (
            <div style={styles.teamBox}>
                <div style={styles.left}>
                    <Left teamName={this.props.team.teamName}
                          teamScore={this.props.team.score}></Left>
                </div>
                <div style={styles.right}>
                    <Right passedTests={this.props.team.passedTest} failedTests={this.props.team.failedTest}></Right>
                </div>
            </div>
        );
    }
}

export default Team;
