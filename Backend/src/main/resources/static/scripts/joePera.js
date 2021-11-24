createMoon();
createStar();

setInterval(createStar,2500)
setInterval(createMoon,4500)

function createMoon(){
    const moon = document.createElement(`i`);
    moon.classList.add(`fas`);
    moon.classList.add(`fa-moon`);
    moon.style.left = Math.random() * window.innerWidth + `px`;
    moon.style.animationDuration = Math.random() * 20 + 15 + `s`;
    // between 2 - 5 seconds
    moon.style.opacity = Math.random();
    moon.style.fontSize = Math.random() * 10 + 10 + `px`;

    document.body.appendChild(moon);
    setTimeout(() =>{
        moon.remove()
    },15000)
}


function createStar(){
    const star = document.createElement(`i`);
    star.classList.add(`fas`);
    star.classList.add(`fa-star`);
    star.style.left = Math.random() * window.innerWidth + `px`;
    star.style.animationDuration = Math.random() * 20 + 15 + `s`;
    // between 2 - 5 seconds
    star.style.opacity = Math.random();
    star.style.fontSize = Math.random() * 7 + 7 + `px`;

    document.body.appendChild(star);
    setTimeout(() =>{
        star.remove()
    },15000)
}