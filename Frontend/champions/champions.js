const championsTableBody = document.getElementById("champions-tbody");

fetch("http://localhost:8080/champions")
    .then(response => response.json())
    .then(champions => {
        champions.map(createChampionTableRow);
    })