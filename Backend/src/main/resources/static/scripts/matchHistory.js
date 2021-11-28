const matchesTableBody = document.getElementById("matchHistory-tbody");
const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const puuid = URLParams.get("puuid");

fetch("http://localhost:8080/matches/" + puuid)
    .then(response => response.json())
    .then(matches => {
        console.log(matches[0].summoner);
        matches.map(createMatchTableRow);
        document.getElementById("summoner-name").innerText = matches[0].summoner.name;
        document.getElementById("summoner-note").innerText = matches[0].summoner.note;
        document.getElementById("summoner-level").innerText = matches[0].summoner.level;
    })

function createMatchTableRow(matches) {
    const matchTableRow = document.createElement("tr");
    matchTableRow.id = matches.id;

    matchesTableBody.appendChild(matchTableRow);

    constructMatchesTableRow(matchTableRow, matches);

}


function constructMatchesTableRow(matchesTableRow, match) {
    matchesTableRow.innerHTML = `
<td>
    <p class="row-matches-win">${(match.win? 'Win' : 'Loss')}</p>
</td>
<td>
    <p class="row-matches-matchStart">${timeConverter(match.matchStart)}</p>
</td>
<td>
    <p class="row-matches-duration">${secondsToMinutesAndSeconds(match.duration)}</p>
</td>
<td>
    <p class="row-matches-kills">${escapeHTML(match.kills.toString())}</p>
</td>
<td>
    <p class="row-matches-deaths">${escapeHTML(match.deaths.toString())}</p>
</td>
<td>
    <p class="row-matches-assists">${escapeHTML(match.assists.toString())}</p>
</td>
<td>
    <p class="row-matches-gameMode">${escapeHTML(match.gameMode.toLocaleLowerCase())}</p>
</td>
<td>
    <p class="row-matches-champion">${escapeHTML(match.championName)}</p>
</td>
`;
    const actionTd = document.createElement("td");

    const saveButton = document.createElement("button");
    saveButton.innerText = "save";

    const deleteButton = document.createElement("button");
    deleteButton.innerText = "delete";
    if(match.id == null){
        deleteButton.style.display = "none";
    }else{
        saveButton.style.display = "none";
    }


    saveButton.addEventListener("click", () => {
        fetch("http://localhost:8080/matches/", {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(match)
        }).then(response => {
            if (response.status === 200) {
                saveButton.style.display = "none";
                deleteButton.style.display = "";
            }
        });
    });

    deleteButton.addEventListener("click", () => {
        fetch("http://localhost:8080/matches/" + match.id, {
            method: "DELETE"
        }).then(response => {
            if (response.status === 200) {
                saveButton.style.display = "";
                deleteButton.style.display = "none";
            } else {
                console.log(response.status);
            }
        });
    })

    actionTd.appendChild(saveButton);
    actionTd.appendChild(deleteButton);
    matchesTableRow.appendChild(actionTd)
}

function deleteMatch(matchId) {
    fetch("http://localhost:8080/matches/" + matchId, {
        method: "DELETE"
    }).then(response => {
        if (response.status === 200) {
            document.getElementById(matchId).remove();
        } else {
            console.log(response.status);
        }
    });
}

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var time = date + ' ' + month + ' ' + year + ' ' + (hour < 10 ? '0' + hour : hour) + ':' + (min < 10 ? '0' + min : min);
    return time;
}
function secondsToMinutesAndSeconds(s){return(s-(s%=60))/60+(9<s?':':':0')+s}
