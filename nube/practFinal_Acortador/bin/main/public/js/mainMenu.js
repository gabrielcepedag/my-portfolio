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
//// To keep side menu option hovered
//  let list = document.querySelectorAll('.navigation li');
// function activeLink(){
//     list.forEach((item, index) => {
//         if (index !== 0) {
//             item.classList.remove('hovered');
//         }
//     });
//     this.classList.add('hovered');
// }
// list.forEach((item) => {
//         item.addEventListener('mouseover', activeLink);
// });


/* Modals for confirming deletion */
function showModalLinksAdmin(index) {
    //To delete URL from /gestion
    const myLink = document.getElementById('eliminarUrl-'+index);
    var codigo = myLink.getAttribute('data-id');

    Swal.fire({
        title: 'Are you sure you want to delete this URL?',
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Delete`,
        cancelButtonText: `Cancel`,
    }).then((result) => {
        if (result.isConfirmed) {
            // window.location.href = "/url/"+codigo;
            window.location.href = "/gestion/links/delete/"+codigo;
            // const form = document.createElement('form');
            // form.method = 'POST';
            // form.action = '/gestion/url/' + codigo;
            //
            // const methodField = document.createElement('input');
            // methodField.type = 'hidden';
            // methodField.name = '_method';
            // methodField.value = 'DELETE';
            //
            // form.appendChild(methodField);
            //
            // document.body.appendChild(form);
            // form.submit();
        }
    })
}
function showModalLinksMenu(index) {
    //To delete URL from /menu
    const myLink = document.getElementById('eliminarUrl-'+index);
    var codigo = myLink.getAttribute('data-id');

    Swal.fire({
        title: 'Are you sure you want to delete this URL?',
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Delete`,
        cancelButtonText: `Cancel`,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/menu/links/delete/"+codigo;
        }
    })
}
function showModalLinksHome(index) {
    //To delete URL from /home session
    const myLink = document.getElementById('eliminarUrl-'+index);
    var codigo = myLink.getAttribute('data-id');

    Swal.fire({
        title: 'Are you sure you want to delete this URL?',
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Delete`,
        cancelButtonText: `Cancel`,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/home/converter/delete/"+codigo;
        }
    })
}
function showModalUser(index) {
    //To delete User
    const myLink = document.getElementById('eliminarUser-'+index);
    var codigo = myLink.getAttribute('data-id');

    Swal.fire({
        title: 'Are you sure you want to delete this user?',
        titleColor: "#0000",
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Delete`,
        cancelButtonText: `Cancel`,
    }).then((result) => {
        if (result.isConfirmed) {
        // window.location.href = "/usuarios/"+codigo;
        window.location.href = "/gestion/users/delete/"+codigo;
        }
    })
}