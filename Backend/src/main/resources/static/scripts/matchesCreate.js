const matchFormParentDiv = document.getElementById("create-match-form");
const matchFormExpandButton = document.getElementById("expand-match-form");

const createMatchForm = `
<div>
    <label>Outcome</label>
    <input id="create-match-outcome" placeholder="outcome">
    <label>Date</label>
    <input id="create-match-date" placeholder="date">
    <button onclick="removeMatchForm()">Cancel</button>
    <button onclick="createMatch()">Create A New Match</button>
</div> `;

function showMatchForm() {
    matchFormExpandButton.style.display = "none";
    matchFormParentDiv.innerHTML = createMatchForm;
}

function removeMatchForm() {
    matchFormExpandButton.style.display = "block";
    matchFormParentDiv.innerHTML = "";
}


function createMatch() {
    const matchToCreate = {
        outcome: document.getElementById("create-match-outcome").value,
        matchDate: document.getElementById("create-match-date").value
    };

    fetch("https://localhost:8080/matches", {
        method: "POST",
        headers: { "Content-type": "application/json; charset=UTF-8" },
        body: JSON.stringify(matchToCreate)
    }).then(response => response.json())
        .then(match => {
            removeMatchForm();
            createMatchTableRow(match);
        }).catch(error => console.log(error));
}


document.getElementById("expand-match-form")
    .addEventListener("click", showMatchForm);