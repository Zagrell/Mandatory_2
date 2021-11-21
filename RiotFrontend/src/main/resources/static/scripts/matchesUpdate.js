function updateMatch(match) {
    const tableRowToUpdate = document.getElementById(match.id);

    tableRowToUpdate.innerHTML = `
    <td>
        <input id="update-match-outcome-${match.id}" value="${escapeHTML(match.outcome)}">
    </td>
    <td>
        <input id="update-match-date-${match.id}" value="${escapeHTML(match.matchDate)}">
    </td>
    <td>
        <button id="cancel-update-${match.id}">✖️</button>
        <button onclick="updateMatchInBackEnd(${match.id})">✅</button>
    </td>
    <td>
        <button onclick="deleteMatch(${match.id})">✖</button>
    </td>
    `;

    document.getElementById(`cancel-update-${match.id}`)
        .addEventListener("click", () => undoUpdateTableRow(match));
}

function undoUpdateTableRow(match) {
    const matchTableRow = document.getElementById(match.id);

    constructMatchTableRow(matchTableRow, match);
}

function updateMatchInBackEnd(matchId) {
    const tableRowUpdate = document.getElementById(matchId);

    const matchToUpdate = {
        id: matchId,
        outcome: document.getElementById(`update-match-outcome-${matchId}`).value,
        matchDate: document.getElementById(`update-match-date-${matchId}`).value
    };

    fetch("https://localhost:8080/matches/" + matchId, {
        method: "PATCH",
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify(matchToUpdate)
    }).then(response => {
        if (response.status === 200) {
            constructMatchesTableRow(tableRowUpdate, matchToUpdate);
        }
    });
}