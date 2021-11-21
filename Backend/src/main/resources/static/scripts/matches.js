const matchesTableBody = document.getElementById("matches-tbody");

fetch("https://localhost:8080/matches")
    .then(response => response.json())
    .then(matches => {
        matches.map(createMatchTableRow);
    });

function createMatchTableRow(match) {
    const matchTableRow = document.createElement("tr");
    matchTableRow.id = match.id;

    matchesTableBody.appendChild(matchTableRow);

    constructMatchTableRow(matchTableRow, match);
}

function constructMatchTableRow(matchesTableRow, match) {
    matchesTableRow.innerHTML = `
    <td>
        <a href="./matches.html?matchesId=${match.id}">
            <p class="row-matches-outcome">${escapeHTML(match.outcome)}</p>
        </a>
    </td>
    <td>
        <p class="row-matches-match-date">${escapeHTML(match.matchDate)}</p>
    </td>
    `;

    document.getElementById(`update-button-${match.id}`)
        .addEventListener("click", () =>updateMatch(match));
}

function deleteMatch(matchId) {
    fetch("https://localhost:8080/champions/" + matchId, {
        method: "DELETE"
    }).then(response => {
        if (response.status === 200) {
            document.getElementById(matchId).remove()
        } else {
            console.log(response.status)
        }
    })
}