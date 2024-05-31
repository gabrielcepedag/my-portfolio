/*  Inicializando Wow JS */
new WOW().init();

/*  Sticky Navbar  */
$(function (){
    "use strict"
    $(window).on('scroll', function (event){
        var scroll = $(window).scrollTop();
        if(scroll < 20){
            $(".navbar-area").removeClass("sticky");
        }else{
            $(".navbar-area").addClass("sticky");
        }
    })
})

