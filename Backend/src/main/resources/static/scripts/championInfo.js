const matchesTableBody = document.getElementById("championInfo-tbody");
const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const name = URLParams.get("name");

fetch("http://localhost:8080/champions/" + name)
    .then(response => response.json())
    .then(champions => {
        console.log(champions[0])
        champions.map(createChampionTableRow);
        document.getElementById("champion-name").innerText = champions.name;
        document.getElementById("champion-note").innerText = champions.note;
        document.getElementById("champion-lore").innerText = champions.lore;
    })

function createChampionTableRow(champions) {
    const championTableRow = document.createElement("tr");
    championTableRow.id = champions.id;

    matchesTableBody.appendChild(championTableRow);

    constructMatchesTableRow(championTableRow, champions);

}

function constructChampionsTableRow(championsTableRow, champion) {
    championsTableRow.innerHTML = `
<td>
    <p class="row-champion-name">${escapeHTML(champion.name)}</p>
</td>
<td>
    <p class="row-champion-note">${escapeHTML(champion.note)}</p>
</td>
<td>
    <p class="row-champion-title">${escapeHTML(champion.title)}</p>
</td>
<td>
    <p class="row-champion-tags">${escapeHTML(champion.tags)}</p>
</td>
<td>
    <p class="row-champion-lore">${escapeHTML(champion.lore)}</p>
</td>
`;

}