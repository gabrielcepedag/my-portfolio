// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
    // *     example: number_format(1234.56, 2, ',', ' ');
    // *     return: '1 234,56'
    number = (number + '').replace(',', '').replace(' ', '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

// Bar Chart Example
var ctx = document.getElementById("myDoubleBarChart");
if (ctx != null){
    var myBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"],
            datasets: [{
                label: "Ventas Totales",
                backgroundColor: "#6762a1",
                hoverBackgroundColor: "#3d3a67",
                borderColor: "#6762a1",
                data: [4215, 6500, 8900, 12000, 15000, 21000, 18000],
                maxBarThickness: 35,
                barPercentage: .95,
                categoryPercentage: .80, // Change this value as needed to adjust the width of each category
            }, {
                label: "Pedidos por encargo",
                backgroundColor: "#1cc689",
                hoverBackgroundColor: "#187553",
                borderColor: "#1cc689",
                data: [2000, 3000, 4000, 5000, 6000, 7000, 9200],
                maxBarThickness: 35,
                barPercentage: .95,
                categoryPercentage: .80,
            }, {
                label: "Pedidos Cabina",
                backgroundColor: "#e06d2f",
                hoverBackgroundColor: "#9d4414",
                borderColor: "#e06d2f",
                data: [1000, 2000, 3000, 4000, 5000, 6000, 8800],
                maxBarThickness: 35,
                barPercentage: .95,
                categoryPercentage: .80,
            }],
        },
        options: {
            maintainAspectRatio: false,
            layout: {
                padding: {
                    left: 10,
                    right: 25,
                    top: 25,
                    bottom: 0
                }
            },
            scales: {
                xAxes: [{
                    time: {
                        unit: 'month'
                    },
                    gridLines: {
                        display: false,
                        drawBorder: false
                    },
                    ticks: {
                        maxTicksLimit: 6
                    },
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        maxTicksLimit: 7,
                        padding: 10,
                        // Include a dollar sign in the ticks
                        callback: function(value, index, values) {
                            return '$' + number_format(value);
                        }
                    },
                    gridLines: {
                        color: "rgb(234, 236, 244)",
                        zeroLineColor: "rgb(234, 236, 244)",
                        drawBorder: false,
                        borderDash: [2],
                        zeroLineBorderDash: [2]
                    }
                }],
            },
            legend: {
                display: true,
                labels: {
                    fontColor: '#1a1919'
                }
            },
            tooltips: {
                titleMarginBottom: 10,
                titleFontColor: '#2f3033',
                titleFontSize: 14,
                backgroundColor: "rgb(255,255,255)",
                bodyFontColor: "#232325",
                borderColor: '#b1b3c2',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                displayColors: false,
                caretPadding: 10,
                callbacks: {
                    label: function(tooltipItem, chart) {
                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                        return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
                    }
                }
            },
        }
    });
}


// Bar Chart Example 2
var ctx = document.getElementById("mySingleBarChart");
var myBarChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Gabriel Cepeda", "Lucas Lopez", "Melany Arias", "Jose Reyes", "Arturo Rodriguez"],
        datasets: [{
            label: "Consumo:",
            backgroundColor: "#36b8ca",
            hoverBackgroundColor: "#167581",
            borderColor: "#36b8ca",
            data: [39000, 21500, 19850, 12000, 11800],
            maxBarThickness: 35,
            barPercentage: .95,
            categoryPercentage: .80, // Change this value as needed to adjust the width of each category
        }],
    },
    options: {
        maintainAspectRatio: false,
        layout: {
            padding: {
                left: 10,
                right: 25,
                top: 25,
                bottom: 0
            }
        },
        scales: {
            xAxes: [{
                time: {
                    unit: 'month'
                },
                gridLines: {
                    display: false,
                    drawBorder: false
                },
                ticks: {
                    maxTicksLimit: 5
                },
            }],
            yAxes: [{
                ticks: {
                    min: 0,
                    maxTicksLimit: 7,
                    padding: 10,
                    // Include a dollar sign in the ticks
                    callback: function(value, index, values) {
                        return '$' + number_format(value);
                    }
                },
                gridLines: {
                    color: "rgb(234, 236, 244)",
                    zeroLineColor: "rgb(234, 236, 244)",
                    drawBorder: false,
                    borderDash: [2],
                    zeroLineBorderDash: [2]
                }
            }],
        },
        legend: {
            display: false,
        },
        tooltips: {
            titleMarginBottom: 10,
            titleFontColor: '#2f3033',
            titleFontSize: 14,
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#232325",
            borderColor: '#b1b3c2',
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
            callbacks: {
                label: function(tooltipItem, chart) {
                    var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                    return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
                }
            }
        },
    }
});