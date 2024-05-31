
var tableProduct = localStorage.getItem("tableProductStorage");
tableProduct = JSON.parse(tableProduct);
if(tableProduct == null){
    tableProduct = [];
}

function displayProduct(){
    var rowData = '';
    let index = 0;
    if(tableProduct.length > 0){
        for(const i in tableProduct){
            var varProduct = JSON.parse(tableProduct[i]);
            rowData += "<tr>";
            rowData += "<td>"+varProduct.codigo+"</td>";
            rowData += "<td>"+varProduct.producto+"</td>";
            rowData += "<td>"+varProduct.precio+"</td>";
            // rowData += "<td>"+
            //            "<button type='button' class='btn btn-info' onclick='regProducto("+varProduct.idProducto+")'+" +
            //     ">Editar</button> <input class='btn btn-info' type='button' value='Eliminar' onclick='deleteRow(this)' ></td>";
            rowData += "<td>"+
                "<button type='button' class='btn btn-info' onclick='regProducto("+varProduct.idProducto+")'+" +
                ">Editar</button> <button type='button' class='btn btn-danger' onclick='deleteRow("+varProduct.idProducto+")'+" +
                ">Eliminar</button></td>";
            rowData += "</tr>";
            index += 1;
        }
        document.getElementById("dataProducts").innerHTML = rowData;
    }
}

function regProducto(idForm){
    localStorage.setItem("idForm", JSON.stringify(idForm));
    window.location.replace("regProducto.html");
}

var idForm = localStorage.getItem("idForm");
idForm = JSON.parse(idForm);
if(idForm == null){
    var idForm = 0;
}

loadTable();

function guardarProducto(){
    if(document.getElementById("txtCodigo").value == "" || +
            document.getElementById("txtNomProducto").value == "" ||
        document.getElementById("txtPrecio").value == ""){
        Swal.fire({
            icon: 'error',
            text: 'Debe completar todos los campos.',
            showCloseButton: true,
        })
    }else {
        var objProducto = JSON.stringify({
            idProducto: (idForm > 0)? idForm: (tableProduct.length+1),
            codigo: document.getElementById("txtCodigo").value,
            producto: document.getElementById("txtNomProducto").value,
            precio: document.getElementById("txtPrecio").value
        });

        // Para editar productos
        if(idForm > 0) {
            for (const i in tableProduct) {
                var varProduct = JSON.parse(tableProduct[i]);
                if(varProduct.idProducto == idForm){
                    tableProduct[i] = objProducto;
                    break;
                }
            }
        }else {
            //Para Registrar productos
            tableProduct.push(objProducto);
            Swal.fire({
                icon: 'success',
                text: 'Se ha guardado el producto.',
                showCloseButton: true,
            })
            localStorage.setItem("tableProductStorage", JSON.stringify(tableProduct));
            window.location.replace("./gestion.html");
        }
    }
}
function loadTable(){
    if(idForm > 0){
        for(const i in tableProduct){
            var varProduct = JSON.parse(tableProduct[i]);
            if(varProduct.idProducto == idForm){
                document.getElementById("txtCodigo").value = varProduct.codigo;
                document.getElementById("txtNomProducto").value = varProduct.producto;
                document.getElementById("txtPrecio").value = varProduct.precio;
                break;
            }
        }
    }
}

$('.btn-number').click(function(e){
    e.preventDefault();

    fieldName = $(this).attr('data-field');
    type      = $(this).attr('data-type');
    var input = $("input[name='"+fieldName+"']");
    var currentVal = parseInt(input.val());
    if (!isNaN(currentVal)) {
        if(type == 'minus') {

            if(currentVal > input.attr('min')) {
                input.val(currentVal - 1).change();
            }
            if(parseInt(input.val()) == input.attr('min')) {
                $(this).attr('disabled', true);
            }

        } else if(type == 'plus') {

            if(currentVal < input.attr('max')) {
                input.val(currentVal + 1).change();
            }
            if(parseInt(input.val()) == input.attr('max')) {
                $(this).attr('disabled', true);
            }

        }
    } else {
        input.val(0);
    }
});
$('.input-number').focusin(function(){
    $(this).data('oldValue', $(this).val());
});
$('.input-number').change(function() {

    minValue =  parseInt($(this).attr('min'));
    maxValue =  parseInt($(this).attr('max'));
    valueCurrent = parseInt($(this).val());

    name = $(this).attr('name');
    if(valueCurrent >= minValue) {
        $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
        alert('Sorry, the minimum value was reached');
        $(this).val($(this).data('oldValue'));
    }
    if(valueCurrent <= maxValue) {
        $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
        alert('Sorry, the maximum value was reached');
        $(this).val($(this).data('oldValue'));
    }


});
$(".input-number").keydown(function (e) {
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
        // Allow: Ctrl+A
        (e.keyCode == 65 && e.ctrlKey === true) ||
        // Allow: home, end, left, right
        (e.keyCode >= 35 && e.keyCode <= 39)) {
        // let it happen, don't do anything
        return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});