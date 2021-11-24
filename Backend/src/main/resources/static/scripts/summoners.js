fetch("http://localhost:8080/summoners")
    .then(response => response.json())
    .then(result => {
        result.map(createSummonerCard);
    });

const summonerWrapper = document.getElementById("summoner-wrapper");

function createSummonerCard(summoner) {
    const summonerElement = document.createElement("div");
    const summonerActions = document.createElement("div");
    const summonerInfo = document.createElement("div");
    const summmonerImage = document.createElement("img");
    summonerElement.style.textDecoration = "none";
    summonerElement.className = "card";

    summonerActions.className = "cardaction";
    summonerInfo.className = "cardinfo";
    summmonerImage.className = "cardimage";

    const editButton = document.createElement("button");
    editButton.innerText = "✏️";
    const deleteButton = document.createElement("button");
    deleteButton.innerText = "❌";

    summonerActions.appendChild(editButton);
    summonerActions.appendChild(deleteButton);

    console.log(summoner);
    summonerInfo.innerHTML = `
        <p>name: ${escapeHTML(summoner.name)}</p>
        <p class="note">note: ${escapeHTML(summoner.note)}</p>
        <p>level: ${escapeHTML(summoner.level.toString())}</p>
    `;
    summmonerImage.src = summoner.profileIconPath;

    summonerElement.appendChild(summonerActions);
    summonerElement.appendChild(summonerInfo);
    summonerElement.appendChild(summmonerImage);

    summonerWrapper.appendChild(summonerElement);

    deleteButton.addEventListener("click", () => {
        fetch("http://localhost:8080/summoners/" + summoner.puuid, {
            method: "DELETE",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
            .then(() => summonerWrapper.removeChild(summonerElement))
    });

    editButton.addEventListener("click", editNote)

    function editNote() {
        summonerInfo.innerHTML = `
        <p>name: ${escapeHTML(summoner.name)}</p>
        <input id="edit-${summoner.puuid}" placeholder="summoner note">
        <p>level: ${escapeHTML(summoner.level.toString())}</p>
    `;
        editButton.removeEventListener("click", editNote);
        editButton.innerText = "✔️";
        editButton.addEventListener("click", confirmEdit)
    }

    function confirmEdit() {
        const newNote = document.getElementById("edit-" + summoner.puuid).value;
        fetch("http://localhost:8080/summoners/" + summoner.puuid, {
            method: 'PATCH',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify({note: newNote})
        }).then(response => {
            if (response.status === 200) {
                summonerInfo.innerHTML = `
                         <p>name: ${escapeHTML(summoner.name)}</p>
                         <p class="note">note: ${escapeHTML(newNote)}</p>
                         <p>level: ${escapeHTML(summoner.level.toString())}</p>
                    `;
            } else {
                summonerInfo.innerHTML = `
                         <p>name: ${escapeHTML(summoner.name)}</p>
                         <p class="note">note: ${escapeHTML(summoner.note)}</p>
                         <p>level: ${escapeHTML(summoner.level.toString())}</p>
                      `;
            }
        });
        editButton.removeEventListener("click", confirmEdit);
        editButton.innerText = "✏️";
        editButton.addEventListener("click", editNote)
    }

    summmonerImage.addEventListener("click", matchHistory);

    function matchHistory() {
        window.location.href = `./matchHistory.html?puuid=${summoner.puuid}`;
    }
}

function createNewSummoner() {
    document.getElementById("error-label").innerText = "";
    const name = document.getElementById("add-summoner-name").value;

    const newSummoner = {
        name: name,
    }

    fetch("http://localhost:8080/summoners", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(newSummoner)
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 404) {
                document.getElementById("error-label").innerText = "no summoner with that name";
                throw new Error('Summoner not created');
            } else {
                throw 'Summoner not created';
            }
        })
        .then(createSummonerCard)
        .catch(error => console.log("Network problem", error));

}

document.getElementById("add-summoner-button").addEventListener("click", createNewSummoner);

