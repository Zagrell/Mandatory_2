createFire();

setInterval(createFire,1000)

function createFire(){
    const fire = document.createElement(`i`);
    fire.classList.add(`fas`);
    fire.classList.add(`fa-fire`);
    fire.style.left = Math.random() * window.innerWidth + `px`;
    fire.style.animationDuration = Math.random() * 15 + 7 + `s`;
    // between 2 - 5 seconds
    fire.style.opacity = Math.random();
    fire.style.fontSize = Math.random() * 10 + 10 + `px`;

    document.body.appendChild(fire);
    setTimeout(() =>{
        fire.remove()
    },10000)
}