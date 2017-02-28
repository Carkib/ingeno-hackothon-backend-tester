import React from "react";
import Team from "./Team.js";

class App extends React.Component {
    render() {
        
        return (
            <div>
                {this.props.json.map(function (team) {
                    return <Team key={team.teamName} team={team}></Team>;
                })}
            </div>
        );
    }
}

export default App;
