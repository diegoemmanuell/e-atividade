$(document).ready(function(){
	
	$('#checkVideo').on('click', function(event) {
		var check = $(this);
		var checked = check[0].checked;
		
		var divUrlVideo = $('#div-url-video-submit');
		var inputUrlVideo = $('#urlVideoYoutube');
		var inputPdf = $('#pdf');
		
		if(checked == true){
			divUrlVideo.removeClass('d-none');
			inputUrlVideo.attr('required', 'required');
			inputPdf.removeAttr('required');
		}else{
			divUrlVideo.addClass('d-none');
			inputUrlVideo.val("");
			
			inputPdf.attr('required', 'required');
			inputUrlVideo.removeAttr('required');
		}
	});
	
	$('.js-deleta-atividade').on('click', function(event) {
		event.preventDefault();
		
		var atividade = $(this).data('atividade');
		var turma = $(this).data('turma');
		var url = $(this).attr('href');
		
		$.confirm({
			title: 'Tem certeza disso?',
			content: 'Deseja realmente deletar a atividade <b>' + atividade + '</b> da turma <b>' + turma + '</b>?',
			type: 'red',
			buttons:{
				confirm:{
					text: 'Sim!',
					btnClass: 'btn btn-secondary',
					keys: ['enter'],
					action: function() {
						window.location.replace(url);
					}
				},
				cancel:{
					text: 'Cancelar', 
					btnClass: 'btn btn-outline-secondary', 
					keys: ['esc']
				}
			}
		});
	});
	
	$('.js-editar-turma').on('click', function(event) {
		event.preventDefault();
		
		var id = $(this).data('turma-id');
		var nome = $(this).data('turma-nome');
		var turno = $(this).data('turma-turno');
		
		$('#idTurma').val(id);
		$('#nome').val(nome);
		$('#turno').val(turno);
		
		$('#modalNovaTurma').modal('show');
		
	});
	
	$('.js-editar-atividade').on('click', function(event) {
		event.preventDefault();
		
		var id = $(this).data('atividade-id');
		var nome = $(this).data('atividade-nome');
		var materia = $(this).data('atividade-materia');
		var turma = $(this).data('atividade-turma');
		var dataDevolucao =$(this).data('atividade-devolucao');
		var url = $(this).data('atividade-url');
		
		var checkVideo = $('.form-check');
		var divPdf = $('.js-div-pdf');
		var divVideo = $('#div-url-video-submit');
		
		$('#idAtividade').val(id);
		$('#nome').val(nome);
		$('#materia').val(materia);
		$('#turma').val(turma);
		$('#dataDevolucao').val(dataDevolucao);
		$('#urlVideoYoutube').val(url);
		
		
		$('#modalNovaAtividade').modal('show');
		
		$('#tituloModalAtividade').html('Editar atividade');
		
		checkVideo.addClass('d-none');
		divPdf.addClass('d-none');
		divVideo.removeClass('d-none');
		
		$('#edicaoHelp').removeClass('d-none');
		
		$('#pdf').removeAttr('required');
		
	});
	
	$('#modalNovaAtividade').on('hidden.bs.modal', function(event){
		location.reload();
	});
	
	
	
	
});