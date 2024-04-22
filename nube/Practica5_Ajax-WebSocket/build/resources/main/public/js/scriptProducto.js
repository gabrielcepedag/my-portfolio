function updateTotal() {
    var table = document.getElementById("listProductos");
    let subTotal = Array.from(table.rows).slice(2).reduce((total, row) => {
        return total + parseFloat(row.cells[1].innerHTML);
    }, 0);
    document.getElementById("val").innerHTML = "SubTotal = $" + subTotal.toFixed(2);
}

function onClickRemove(deleteButton) {
    let row = deleteButton.parentElement.parentElement;
    row.parentNode.removeChild(row);
    updateTotal(); // Call after delete
}

const myForm = document.getElementById("formCantidad");
document.querySelector(".submit").addEventListener("click", function(){
    myForm.submit();
});

function showModal() {
    const myLink = document.getElementById('lnkEliminar');
    var codigo = myLink.getAttribute('data-cod-prod');
    var id =myLink.getAttribute("data-id-foto");

    Swal.fire({
        title: '¿Seguro que desea eliminar esta imagen?',
        titleColor: "#0000",
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Eliminar`,
        cancelButtonText: `Cancelar`,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/gestion/update/product/"+codigo+"/eliminar/"+id;
        }
    })
}

function showModalProducto(index) {
    const myLink = document.getElementById('eliminarProd-'+index);
    var codigo = myLink.getAttribute('data-codigo-producto');

    Swal.fire({
        title: '¿Seguro que desea eliminar este producto?',
        titleColor: "#0000",
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Eliminar`,
        cancelButtonText: `Cancelar`,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/gestion/remove/product/"+codigo;
        }
    })
}

function showModalUsuario(index) {
    const myLink = document.getElementById('eliminarUser-'+index);
    const user = myLink.getAttribute('data-username');
    console.log("USERNAME: "+user);

    Swal.fire({
        title: '¿Seguro que desea eliminar este usuario?',
        titleColor: "#0000",
        showConfirmButton: true,
        showDenyButton: false,
        showCancelButton: true,
        confirmButtonColor: "#b00000",
        confirmButtonText: `Eliminar`,
        cancelButtonText: `Cancelar`,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/gestion/remove/usuario/"+user;
            //gestion/remove/usuario/' + ${user.username}
        }
    })
}

jQuery(function($){
    $('.table').footable();
});