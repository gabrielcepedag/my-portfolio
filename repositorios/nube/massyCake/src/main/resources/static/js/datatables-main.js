$(document).ready(function() {
    $('#tableEmpleados').DataTable({
        columnDefs: [
            {width: '15%', targets: 6, className: 'text-center' }
        ],
        autoWidth: false
    });

    $('#tableProductosInventario').DataTable({
        columnDefs: [
            {width: '15%', targets: 6, className: 'text-center'}
        ],
    });
    $('#tableProductosPersonalizados').DataTable({

    });
    $('#tableProductosCabina').DataTable({
        columnDefs: [
            {width: '15%', targets: 7, className: 'text-center'}
        ],
        autoWidth: false,
    });

    $('#tableRecetas').DataTable({
        columnDefs: [
            { width: '15%', targets: 5, className: 'text-center' }
        ],
        autoWidth: false,
    });
    $('#tableIngredientesDispCrear').DataTable({
        searching: false, // Remove the search bar
        columnDefs: [
            { width: '5%', targets: 3 },
            { width: '5%', targets: 4, className: 'text-center' }
        ],
        autoWidth: false,
    });
    $('#tableIngredientesDispEditar').DataTable({
        searching: false, // Remove the search bar
        columnDefs: [
            { width: '5%', targets: 3 },
            { width: '5%', targets: 4, className: 'text-center' }
        ],
        autoWidth: false,
    });
    $('#tableRecetasDispCrear').DataTable({
        columnDefs: [
            {width: '5%', targets: 4, className: 'text-center'}
        ],
        autoWidth: false,
        lengthMenu: [5, 10, 15],
    });
    $('#dataTable').DataTable();

    $('#tableFacturas').DataTable({
        columnDefs: [
            {width: '18%', targets: 6, className: 'text-center'}
        ],
        autoWidth: false
    });
    $('#tableDetalleFactura').DataTable({

    });
    $('#tableOrdenesCompra').DataTable({

    });
});