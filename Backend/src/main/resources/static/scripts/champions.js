const championsTableBody = document.getElementById("champions-tbody");
const championsCardWrapper = document.getElementById("champion-wrapper");

fetch("http://localhost:8080/champions")
    .then(response => response.json())
    .then(champions => {
        console.log(champions)
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


function createChampionTableRow(champion) {
    const championTableRow = document.createElement("tr");
    championTableRow.id = champion.id;

    championsTableBody.appendChild(championTableRow);

    constructChampionTableRow(championTableRow, champion);
}

function constructChampionTableRow(championTableRow, champion) {
    championTableRow.innerHTML = `
    <td>
        <a href="./champions.html?championId=${champion.id}">
            <p class="row-champion-name">${escapeHTML(champion.name)}</p>
        </a>
    <td>
        <p class="row-champion-role">${escapeHTML(champion.role)}</p>
    </td>
    `;

}