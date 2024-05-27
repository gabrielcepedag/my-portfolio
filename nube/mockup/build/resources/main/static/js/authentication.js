const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

// sign_up_btn.addEventListener("click", () => {
//     container.classList.add("sign-up-mode");
// });
sign_in_btn.addEventListener("click", () => {
    container.classList.remove("sign-up-mode");
});

function showModalRegister() {
    Swal.fire({
        icon: 'error',
        text: 'This username already exists. Try again.',
    })
}
function showModalLogin() {
    Swal.fire({
        icon: 'error',
        text: 'Username or password is incorrect. Try again.',
    })
}