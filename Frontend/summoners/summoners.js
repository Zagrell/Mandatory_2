fetch("https://localhost:8080/summoners")
.then(response => response.json())
    .then(result => {
        result.map(createSummoner);
    });

const summonerWrapper = document.getElementById("summoner-wrapper");

function createSummoner(summoner) {
    const summonerElement = document.createElement("div");
    summonerElement.innerText = summoner.name;
}


