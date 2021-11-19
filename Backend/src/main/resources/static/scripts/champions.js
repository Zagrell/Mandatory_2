const championsTableBody = document.getElementById("champions-tbody");

fetch("https://localhost:8080/champions")
    .then(response => response.json())
    .then(champions => {
        champions.map(createChampionTableRow);
    });

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