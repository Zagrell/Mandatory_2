fetch("http://localhost:8080/summoners")
    .then(response => response.json())
    .then(result => {
        result.map(createSummoner);
    });

const summonerWrapper = document.getElementById("summoner-wrapper");

function createSummoner(summoner) {

    const summonerElement = document.createElement("div");
    summonerElement.innerText = ` 
    <p>name: ${escapeHTML(summoner.name)}</p>
    <p>note: ${escapeHTML(summoner.note)}</p>
    <p>level: ${escapeHTML(summoner.level.toString())}</p>
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
            if(response.status === 200){
                createSummoner(newSummoner);
            } else {
                console.log("Summoner not created.", response.status);
            }
        })
        .catch(error => console.log("Network problem", error));

}

document.getElementById("add-summoner-button").addEventListener("click", createNewSummoner);

