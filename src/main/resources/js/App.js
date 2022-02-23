var app = function () {
    return {
        mostrar: function(){
        var info
        var valores;
            var city = $('#city').val();
            $.get('/consulta?ciudad=' + city, function(data){
            inf = JSON.stringify(data, null);
            valores = JSON.parse(data);
            console.log(valores);
            })
        }
    }
}