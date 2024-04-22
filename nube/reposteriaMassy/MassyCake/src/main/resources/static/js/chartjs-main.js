// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

///\\\     Pie Chart Inventario (Ingredientes)     \\\///
var ctx = document.getElementById("myPieChart");
if(ctx != null){
    var stock = parseInt(ctx.getAttribute("data-stock"));
    var agotados = parseInt(ctx.getAttribute("data-agotados"));
    var dispMin = parseInt(ctx.getAttribute("data-dispMin"));

    var myPieChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ["Disp. Mínima", "En Stock", "Disp. Máxima"],
            datasets: [{
                data: [dispMin, stock, agotados],
                backgroundColor: ['#f4c03e', '#1cc88a', '#e5493b'],
                hoverBackgroundColor: ['#dea111', '#0e8058', '#ce2618'],
                hoverBorderColor: "rgba(234, 236, 244, 1)",
            }],
        },
        options: {
            maintainAspectRatio: false,
            tooltips: {
                backgroundColor: "rgb(255,255,255)",
                bodyFontColor: "#858796",
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                displayColors: false,
                caretPadding: 10,
            },
            legend: {
                display: false
            },
            cutoutPercentage: 80,
        },
    });
}



