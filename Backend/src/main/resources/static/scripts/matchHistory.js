const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const puuid = URLParams.get("puuid");

fetch("https://localhost:8080/matches/" + puuid)
    .then(response => response.json())
    .then(summoner => {
        document.getElementById("summoner-name").innerText = summoner.name;
        document.getElementById("summoner-note").innerText = summoner.note;
        document.getElementById("summoner-level").innerText = summoner.level;
    })