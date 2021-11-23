const baseURL = "http://localhost:8080";


const navBar = document.getElementById("nav-bar");

navBar.innerHTML=`

<a href="../html/index.html"><img class="logo" src="../pngegg.png" alt="logo"></a>
<div>
    <a href="../html/summoners.html">
        <p>Summoners</p>
    </a>
    <a href="../html/champions.html">
        <p>Champions</p>
    </a>
    <a href="../html/matches.html">
        <p>Matches</p>
    </a>
</div>
    <a href="/logout">
        <p>Log out</p>
    </a>
`;

// SNOWFLAKES

createSnowFlakes();

setInterval(createSnowFlakes,25)

function createSnowFlakes(){
    const snow_flake = document.createElement(`i`);
    snow_flake.classList.add(`fas`);
    snow_flake.classList.add(`fa-snowflake`);
    snow_flake.style.left = Math.random() * window.innerWidth + `px`;
    snow_flake.style.animationDuration = Math.random() * 3 + 2 + `s`;
    // between 2 - 5 seconds
    snow_flake.style.opacity = Math.random()
    snow_flake.style.fontSize = Math.random() * 10 + 10 + `px`;


    document.body.appendChild(snow_flake);
    setTimeout(() =>{
        snow_flake.remove()
    },5000)
}