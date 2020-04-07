$(document).ready(function(){
	$('#modalVideo').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		
		var atividade = button.data('atividade');
		var materia = button.data('materia');
		var turma = button.data('turma');
		var url = button.data('url');
		
		var modal = $(this);
		
		modal.find('.modal-title').html('Vídeo da atividade ' + atividade);
		
	});
});