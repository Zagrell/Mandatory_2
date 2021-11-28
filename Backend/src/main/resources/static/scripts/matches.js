const matchesTableBody = document.getElementById("matches-tbody");

fetch("http://localhost:8080/matches")
    .then(response => response.json())
    .then(matches => {
        matches.map(createMatchTableRow);
    });


function createMatchTableRow(match) {
    const matchTableRow = document.createElement("tr");
    matchTableRow.id = match.id;

    matchesTableBody.appendChild(matchTableRow);
    console.log(match)
    constructMatchesTableRow(matchTableRow, match);

}

function constructMatchesTableRow(matchesTableRow, match) {
    matchesTableRow.innerHTML = `
        <td>
            <a href="./matchHistory.html?puuid=${match.summoner.puuid}">summoner</a>
        </td>
        <td>
            <p class="row-matches-win">${(match.win ? 'Win' : 'Loss')}</p>
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
        <td id="note-${match.id}">
            <p>${escapeHTML(match.note)}</p>
        </td>
`;

    const editTd = document.createElement("td");

    const editButton = document.createElement("button");
    editButton.innerText = "edit";
    const confirmButton = document.createElement("button");
    confirmButton.innerText = "confirm";
    confirmButton.style.display = "none";

    editButton.addEventListener("click", () => {
        const td = document.getElementById("note-" + match.id);
        td.innerHTML = `
            <input id="note-${match.id}-input" type="text" value="${escapeHTML(match.note)}" placeholder="note">
        `;
        editButton.style.display = "none";
        confirmButton.style.display = "";
    });

    confirmButton.addEventListener("click", () => {
        const td = document.getElementById("note-" + match.id);
        const newNote = document.getElementById(`note-${match.id}-input`).value;

        fetch("http://localhost:8080/matches/" + match.id, {
            method: 'PATCH',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify({note: newNote})
        }).then(response => {
            if (response.status === 200) {
                td.innerHTML = `
                         <p>${escapeHTML(newNote)}</p>
                    `;
            } else {
                td.innerHTML = `
                          <p>${escapeHTML(match.note)}</p>
                      `;
            }
            editButton.style.display = "";
            confirmButton.style.display = "none";
        });
    })


    editTd.appendChild(editButton);
    editTd.appendChild(confirmButton);
    matchesTableRow.appendChild(editTd);

    const deleteTd = document.createElement("td");
    const deleteButton = document.createElement("button");
    deleteButton.innerText = "Delete";
    deleteButton.addEventListener("click", () => {
        fetch("http://localhost:8080/matches/" + match.id, {
            method: "DELETE"
        }).then(response => {
            if (response.status === 200) {
                document.getElementById(match.id).remove();
            } else {
                console.log(response.status);
            }
        });
    });
    deleteTd.appendChild(deleteButton);
    matchesTableRow.appendChild(deleteTd);
}

function timeConverter(UNIX_timestamp) {
    var a = new Date(UNIX_timestamp);
    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var time = date + ' ' + month + ' ' + year + ' ' + (hour < 10 ? '0' + hour : hour) + ':' + (min < 10 ? '0' + min : min);
    return time;
}

function secondsToMinutesAndSeconds(s) {
    return (s - (s %= 60)) / 60 + (9 < s ? ':' : ':0') + s
}