import React from "react";

class Left extends React.Component {
    render() {
        var styles = {
            name: {
                fontSize: 45,
                display: "block",
                textAlign: "center",
                fontWeight: "bold",
                padding: "10px 0",
                color: "coral",
                backgroundColor: "FloralWhite"
            },
            score: {
                heigth: "200px",
                fontSize: 100,
                fontWeight: "bold",
                textAlign: "center",
                padding: "15px 0",
                color: "white",
                backgroundColor: "green"
            }
        };
        return (
            <div>
                <div style={styles.name}>
                    {this.props.teamName}
                </div>
                <div style={styles.score}>
                    {this.props.teamScore}
                </div>
            </div>
        );
    }
}

export default Left;
