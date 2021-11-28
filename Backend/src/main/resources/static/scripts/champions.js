
const championsCardWrapper = document.getElementById("champion-wrapper");
let champions;
let filteredChampions;


fetch("http://localhost:8080/champions")
    .then(response => response.json())
    .then(result => {
        champions = result;
        filteredChampions = champions;
        champions.map(createChampionCard);
    });

function createChampionCard(champion){
    const link = document.createElement("a");
    link.href = `./championInfo.html?championName=${champion.name}`;
    const championCard = document.createElement("img");
    championCard.src=champion.squareImagePath;


    link.appendChild(championCard);
    championsCardWrapper.appendChild(link);
}

function handleSearchName(event) {
    championsCardWrapper.innerHTML = "";
    const searchTerm = event.target.value.toLowerCase();
    filteredChampions.filter(champion => champion.name.toLowerCase().includes(searchTerm))
        .map(createChampionCard);
}

document.getElementById("name-search").addEventListener("input", handleSearchName);