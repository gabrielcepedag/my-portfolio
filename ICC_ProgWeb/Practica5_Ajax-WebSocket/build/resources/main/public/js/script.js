// jQuery(function($){
//     $('.table').footable();
// });
//
// function updateTotal() {
//     var table = document.getElementById("listProductos");
//     let subTotal = Array.from(table.rows).slice(2).reduce((total, row) => {
//         return total + parseFloat(row.cells[1].innerHTML);
//     }, 0);
//     document.getElementById("val").innerHTML = "SubTotal = $" + subTotal.toFixed(2);
// }
//
// const myForm = document.getElementById("formCantidad");
// document.querySelector(".submit").addEventListener("click", function(){
//     myForm.submit();
// });