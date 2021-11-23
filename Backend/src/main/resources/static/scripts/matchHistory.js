const matchesTableBody = document.getElementById("matchHistory-tbody");
const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const puuid = URLParams.get("puuid");

fetch("http://localhost:8080/matches/" + puuid)
    .then(response => response.json())
    .then(matches => {
        matches.map(createMatchTableRow);
        document.getElementById("summoner-name").innerText = summoner.name;
        document.getElementById("summoner-note").innerText = summoner.note;
        document.getElementById("summoner-level").innerText = summoner.level;
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
<p class="row-matches-win">${escapeHTML(match.win.toString())}</p>
</td>
<td>
<p class="row-matches-matchStart">${escapeHTML(match.matchStart.toString())}</p>
</td>
<td>
<p class="row-matches-duration">${escapeHTML(match.duration.toString())}</p>
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
<p class="row-matches-gameMode">${escapeHTML(match.gameMode)}</p>
</td>
<td>
<p class="row-matches-champion">${escapeHTML(match.championName)}</p>
</td>
<td>
<p class="row-matches-note">${escapeHTML(match.note)}</p>
</td>
<td>
<button id="updateNote-button-${match.matchId}">📝</button>
</td>
<td>
<button style="display: none" onclick="deleteMatch(${match.id})">❌</button>
</td>
`;

    if (match.id !== null) {
        document.getElementById(`update-button-${match.id}`)
            .addEventListener("click", () => updateMatch(match))
        document.getElementById(`update-button-${match.id}`).style.display="";
    }

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
