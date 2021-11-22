fetch("http://localhost:8080/summoners")
    .then(response => response.json())
    .then(result => {
        result.map(createSummoner);
    });

const summonerWrapper = document.getElementById("summoner-wrapper");

function createSummoner(summoner) {

    const summonerElement = document.createElement("div");
    summonerElement.innerHTML = `
    <a href="./matchHistory.html?puuid=${summoner.puuid}">
        <p>name: ${escapeHTML(summoner.name)}</p>
        <p>note: ${escapeHTML(summoner.note)}</p>
        <p>level: ${escapeHTML(summoner.level.toString())}</p>
    </a>
    `;

    summonerWrapper.appendChild(summonerElement);
}

function createNewSummoner() {
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
            } else {
                console.log("Summoner not created.", response.status);
                throw 'Summoner not created';
            }
        })
        .then(result => {
           createSummoner(result);
        })
        .catch(error => console.log("Network problem", error));

}

document.getElementById("add-summoner-button").addEventListener("click", createNewSummoner);

