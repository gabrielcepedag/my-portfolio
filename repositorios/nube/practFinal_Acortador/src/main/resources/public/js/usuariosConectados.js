// //Inicializando Tooltip usuarios
// $(function () {
//     $('[data-bs-toggle="tooltip"]').tooltip();
// })

//abriendo el objeto para el websocket
var webSocket;
var tiempoReconectar = 5000;

$(document).ready(function(){
    console.info("Iniciando Jquery -  Ejemplo WebServices");
    console.info("usuario es: "+$("#logguedUser").text());
    conectar();
});

/**
 *
 * @param mensaje
 */
function conectar() {


    console.info("Iniciando la comunicación...");
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/usuariosConectados");

    // webSocket.onmessage = function(event) {
    //     var message = JSON.parse(event.data);
    //     if (message.users){
    //         const numUsers = message.users;
    //         document.getElementById("cantUsuariosActivos").innerHTML = numUsers;
    //     }
    // };
    webSocket.onopen  = function(e){console.log("(ONOPEN) Conectado - status "+this.readyState);};
    webSocket.onclose = function(e){console.log("(ONCLOSE) Desconectado - status "+this.readyState);};

    // else{
    //     document.getElementById("conectados").innerHTML = "";
    // }
}

function verificarConexion(){
    if(!webSocket || webSocket.readyState == 3){
        conectar();
    }
}
setInterval(verificarConexion, tiempoReconectar); //para reconectar.