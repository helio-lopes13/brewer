var Brewer = Brewer || {};

Brewer.MultiSelecao = (function() {
	function MultiSelecao() {
		this.statusBtn = $('.js-status-btn');
		this.selecaoCheckbox = $('.js-selecao');
		this.selecaoTodosCheckboxes = $('.js-selecao-todos');
	}

	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		this.selecaoTodosCheckboxes.on('click', onSelecaoTodosClicado
				.bind(this));
		this.selecaoCheckbox.on('click', onSelecaoClicado.bind(this));
	}

	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		
		var status = botaoClicado.data('status');
		
		var url = botaoClicado.data('url');

		var checkboxesSelecionados = this.selecaoCheckbox.filter(':checked');

		var codigos = $.map(checkboxesSelecionados, function(c) {
			return $(c).data('codigo');
		});

		if (codigos.length > 0) {
			$.ajax({
				url: url,
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				},
				success: function() {
					window.location.reload();
				}
			});
		}
	}

	function onSelecaoTodosClicado() {
		var status = this.selecaoTodosCheckboxes.prop('checked');
		this.selecaoCheckbox.prop('checked', status);
		
		statusBotaoAcao.call(this, status);
	}

	function onSelecaoClicado() {
		var selecaoCheckboxesChecados = this.selecaoCheckbox.filter(':checked');
		this.selecaoTodosCheckboxes.prop('checked', selecaoCheckboxesChecados.length >= this.selecaoCheckbox.length);
		statusBotaoAcao.call(this, selecaoCheckboxesChecados.length);
	}
	
	function statusBotaoAcao(ativar) {
		ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}

	return MultiSelecao;
}());

$(function() {
	var multiselecao = new Brewer.MultiSelecao();
	multiselecao.iniciar();
});