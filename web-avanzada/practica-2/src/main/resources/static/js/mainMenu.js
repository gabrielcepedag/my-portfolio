/* For Main Menu Page Design    */
let toggle = document.querySelector('.toggle');
let navigation = document.querySelector('.navigation');
let main = document.querySelector('.main');

// To hide side menu
toggle.onclick = function (){
    navigation.classList.toggle('active');
    main.classList.toggle('active');
}
// To use logout icon
const logoutIcon = document.getElementById('logButton');
logoutIcon.addEventListener('click', function() {
    window.location.href = '/logout';
});

// Para manejo de resaltado en barra lateral
const menuItems = document.querySelectorAll("ul li[id]:not(#logo)");
let lastHoveredItem = null;

// Para recordar el elemento resaltado inicialmente
const defaultHoveredItem = document.querySelector("ul li.hovered:not(#logo)");
defaultHoveredItem.classList.add("hovered");
lastHoveredItem = defaultHoveredItem;

menuItems.forEach(item => {
    item.addEventListener("mouseover", function () {
        if (lastHoveredItem) {
            lastHoveredItem.classList.remove("hovered");
        }
        item.classList.add("hovered");
        lastHoveredItem = item;
    });
});

// Al salir del menu lateral, reiniciar el resaltado
document.querySelector("ul").addEventListener("mouseout", function () {
    if (lastHoveredItem) {
        lastHoveredItem.classList.remove("hovered");
        defaultHoveredItem.classList.add("hovered");
        lastHoveredItem = defaultHoveredItem;
    }
});


/* Modals para confirmar eliminación */