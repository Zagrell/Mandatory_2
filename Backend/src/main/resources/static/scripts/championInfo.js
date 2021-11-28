const matchesTableBody = document.getElementById("championInfo-tbody");
const queryString = window.location.search;
const URLParams = new URLSearchParams(queryString);
const name = URLParams.get("championName");

const mainDiv = document.getElementById("champion-data-div");


fetch("http://localhost:8080/champions/" + name)
    .then(response => response.json())
    .then(champion => {
        console.log(champion)
        const divInfo = document.createElement("div");
        divInfo.innerHTML = `
            <p id="name">${escapeHTML(champion.name)}</p>
            <p id="title">${escapeHTML(champion.title)}</p>
            <p id="tags">Tags: ${escapeHTML(champion.tags.toString())}</p>
            <img src=${champion.squareImagePath}>
            <br>
            <input id="set-note-input" type="text">
            <br>
            <button onclick="setNote()">Add note</button>
            <p id="champion-note">${escapeHTML(champion.note)}</p>
            <p id="lore-header">Lore</p>
            <p id="lore">${escapeHTML(champion.lore)}</p>
        `;
        mainDiv.appendChild(divInfo);
    })

function setNote(){

        const champion = {
                name : name,
                note : document.getElementById("set-note-input").value
        };
        fetch("http://localhost:8080/champions/" + name,{
                method: "POST",
                headers: {
                        "Content-type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(champion)
        }).then(response => {
                if (response.status === 200){
                        document.getElementById("champion-note").innerText = champion.note;
                }
        })

}
