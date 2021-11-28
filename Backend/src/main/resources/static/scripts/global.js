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
        <p>Saved Matches</p>
    </a>
</div>
    <a href="/logout">
        <p>Log out</p>
    </a>
`;