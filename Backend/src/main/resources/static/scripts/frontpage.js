// SNOWFLAKES med hjælp fra https://www.youtube.com/watch?v=_ARGxz_cU_o&ab_channel=FlorinPop (fordi det snart er jul)
createSnowFlakes();

setInterval(createSnowFlakes,25)

function createSnowFlakes(){
    const snow_flake = document.createElement(`i`);
    snow_flake.classList.add(`fas`);
    snow_flake.classList.add(`fa-snowflake`);
    snow_flake.style.left = Math.random() * window.innerWidth + `px`;
    snow_flake.style.animationDuration = Math.random() * 3 + 2 + `s`;
    // between 2 - 5 seconds
    snow_flake.style.opacity = Math.random();
    snow_flake.style.fontSize = Math.random() * 10 + 10 + `px`;


    document.body.appendChild(snow_flake);
    setTimeout(() =>{
        snow_flake.remove()
    },5000)
}