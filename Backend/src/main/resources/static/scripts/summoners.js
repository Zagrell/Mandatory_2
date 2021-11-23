fetch("http://localhost:8080/summoners")
    .then(response => response.json())
    .then(result => {
        result.map(createSummonerCard);
    });

const summonerWrapper = document.getElementById("summoner-wrapper");

function createSummonerCard(summoner) {
    const summonerElement = document.createElement("div");
    summonerElement.className = "card";
    summonerElement.innerHTML = `
    <td>
        <a href="./matchHistory.html?puuid=${summoner.puuid}">
            <p>name: ${escapeHTML(summoner.name)}</p>
        </a>
    </td>
    <td>
        <p>note: ${escapeHTML(summoner.note)}</p>
    </td>
    <td>
        <p>level: ${escapeHTML(summoner.level.toString())}</p>
    </td>
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
                throw 'Summoner not created';
            }
        })
        .then(result => {
           createSummonerCard(result);
        })
        .catch(error => console.log("Network problem", error));

}

document.getElementById("add-summoner-button").addEventListener("click", createNewSummoner);

