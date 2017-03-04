import React from "react";
import Team from "./Team.js";

class App extends React.Component {
    render() {
        let styles = {
            teams: {
                display: "flex",
                listStyle: "none",
                margin: "0",
                padding: "10px"
            }
        };

        return (
            <div>
                <ul style={styles.teams}>
                    {this.props.json.map(function (team) {
                        return <Team key={team.teamName} team={team}></Team>;
                    })}
                </ul>
            </div>
        );
    }
}

export default App;
