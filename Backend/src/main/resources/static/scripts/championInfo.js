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
            <img src=${champion.squareImagePath}>
            <button onclick="setNote()">set note</button>
            <input id="set-note-input" type="text">
            <p>${escapeHTML(champion.name)}</p>
            <p>${escapeHTML(champion.title)}</p>
            <p id="champion-note">${escapeHTML(champion.note)}</p>
            <p>${escapeHTML(champion.tags.toString())}</p>
            <p>${escapeHTML(champion.lore)}</p>
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
