
/**
 * Función encargada de verificar si un código se encuentra en el formato correcto (000-000-0).
 * @para codigo: Código al que se le verificará el formato.
 */
function verificarFormatoCodigo( codigo )
{
	var re = /^[0-9][0-9][0-9]-[0-9]{3,3}-[0-9]$/;  
    return re.test(codigo);
}

/**
 * Función encargada de formatear los nùmeros con una mínima cantidad de digistos
 * Tomada de
 * http://stackoverflow.com/questions/2998784/how-to-output-integers-with-leading-zeros-in-javascript
 * función encargada de formatear con ceros
 */
function zeroPad(num, places)
{
	var zero = places - num.toString().length + 1;
	return Array(+(zero > 0 && zero)).join("0") + num;
}

/**
 * Función encargada de obtener el nùmero que representa el código
 */
function obtenerNumeroAPartirDeCodigo(codigo)
{
	var numeros = codigo.split("-");
	return parseInt(numeros[0] + numeros[1]);
}

/**
 * Retorna el checksum que tiene un codigo
 */
function darCheckSumDeCodigo(codigo)
{
	return codigo.split("-")[2];
}

/**
 * Función encargada de obtener el nùmero escrito como un codigo valido
 */
function darCodigoFormateado(numero)
{
	numero = parseInt(numero);
	var inicio = Math.floor(numero / 1000);
	var fin = numero % 1000;
	var codigo = zeroPad(inicio,3) + "-" + zeroPad(fin,3) +"-";
	codigo += obtenerCheckSum(numero)
	return codigo;
}

/**
 * Función encargada de calcular un checksum a partir de un código.
 * @param codigo: Codigo, CON formato, del articulo al que se le calcula el digito de verificación.  
 */
function obtenerCheckSumConFormato( codigo )
{
	var numero = obtenerNumeroAPartirDeCodigo(codigo);
	return obtenerCheckSum(numero);
}

/**
 * Función encargada de calcular un checksum a partir de un código.
 * @param codigo: Codigo, SIN formato, del articulo al que se le calcula el digito de verificación.  
 */
function obtenerCheckSum(numero)
{
	var primero = numero % 10;
	var segundo = Math.floor( ( numero % 100 ) / 10 );
	var tercero = Math.floor( ( numero % 1000 ) / 100 );
	var cuarto = Math.floor( ( numero % 10000 ) / 1000 );
	var quinto = Math.floor( ( numero % 100000 ) / 10000 );
	var sexto = Math.floor( ( numero % 1000000 ) / 100000 );	
	var check = 10 -  ( ( ( primero + tercero + quinto ) + (2*(segundo + cuarto + sexto ) ) ) % 10 ) ;
	return check;
}

/**
 * función encargada de serializar un formulario y volverlo un objeto JS
 */
$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};
/**
 * cambiar la configuración de XHR de Jquery
 */
$.ajaxSetup({
	  xhrFields: {
	    withCredentials: true
	  }
});
/**
 * Da el valor de llave con nombre name de los parámetros GET de la URL
 * Obtenido de http://stackoverflow.com/questions/831030/how-to-get-get-request-parameters-in-javascript  usuario Rafael
 */
function get(name)
{
	   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
	      return decodeURIComponent(name[1]);
}

/**
 * Función que convierte @fecha en
 * el formato dd/mm/yyyy.
 */
function formatearFecha( fecha )
{
	var fechaActual = fecha.split("-");
	var dd = fechaActual[2];
    var mm = fechaActual[1];
    var yyyy = fechaActual[0];
    return ""+dd+"/"+mm+"/"+yyyy;
}

/**
 * Función que convierte @fecha en
 * el formato ISO yyyy/mm/dd.
 */
function formatearFechaISO( fecha )
{
	var fechaActual = fecha.split("/");
	var dd = fechaActual[0];
    var mm = fechaActual[1];
    var yyyy = fechaActual[2];
    return ""+yyyy+"-"+mm+"-"+dd;
}

/**
 * Función de regresa la fecha actual como una cadena
 * en formato dd/mm/yyyy
 */
function darFechaActual()
{
	var fecha = new Date();
	var dd = zeroPad(fecha.getDate(), 2);
    var mm = zeroPad(fecha.getMonth()+1, 2);
    var yyyy = fecha.getFullYear();
    return ""+yyyy+"-"+mm+"-"+dd;
}

/**
 * Función que se encarga de poner en un input type text
 * con nombre llace, el valor valor
 */
function llenarDatoFormulario(llave, valor)
{
	var input = $("form input[name="+ llave + "]").first();
	input.attr("value", valor);
}

/**
 * función que funciona como un wrapper para los requet ajax
 * @param object, objecto a agregar
 * @param url, URI que identifica el recurso
 * @param todo, función  asociada a la creación exitossa
 * @param error, función asociada al error
 */
function getForObject(object, url, toDo)
{
	if(object != null){
		url = url + "?" + $.param(object)
	}
	$.ajax({
		url : url,
		success : function(data){
			toDo(data);	
		},
		error: function(data){
			console.log("Hubo un error en la consulta " + url);
		}
	});
}

/**
 * función que funciona como un wrapper para los requet ajax
 * @param object, objecto a agregar
 * @param url, URI que identifica el recurso
 * @param todo, función  asociada a la creación exitossa
 * @param error, función asociada al error
 */
function getForObject(object, url, toDo,error)
{
	if(object != null){
		url = url + "?" + $.param(object);
	}
	$.ajax({
		url : url,
		success : function(data){
			toDo(data);	
		},
		error: function(data){
			error(data);
		}
	});
}

/**
 * función que funciona como un wrapper para los requet ajax
 * @param1 object, objecto a agregar
 * @param url, URI que identifica el recurso
 * @param todo, función  asociada a la creación exitossa
 * @param error, función asociada al error
 */
function postForObject(object, url, todo, error)
{
	$.ajax({
		type : "post",
		url : url,
		data : JSON.stringify(object),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			todo(data);
		},
		error :function(data){
			error(data);
		}
	});
}
/**
 * función que funciona como un wrapper para los requet ajax
 * @param object, objecto a agregar
 * @param url, URL que identifica el recurso
 * @param todo, función  asociada a la creación exitossa
 * @param error, función asociada al error
 */
function putForObject(object, url, todo, error)
{
	$.ajax(
	{
		type : "put",
		url : url,
		data : JSON.stringify(object),
	    contentType: 'application/json; charset=utf-8',
		success : todo,
		error :function(data)
		{
			error( data );
		}
	});
}

function deleteForObject(url, todo, error){
	$.ajax(
	{
		type : "delete",
		url : url,
	    contentType: 'application/json; charset=utf-8',
		success : todo,
		error : error
	});
}
