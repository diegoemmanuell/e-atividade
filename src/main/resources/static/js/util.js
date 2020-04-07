$(document).ready(function(){
	$(function () { $('[data-toggle="tooltip"]').tooltip() });
	$('#perfisUsuario').multiSelect();
	
	$(".colapsar").collapse()
	
});



function formatar_mascara(src, mascara) {
	var campo = src.value.length;
	var saida = mascara.substring(0, 1);
	var texto = mascara.substring(campo);
	if (texto.substring(0, 1) != saida) {
		src.value += texto.substring(0, 1);
	}
}