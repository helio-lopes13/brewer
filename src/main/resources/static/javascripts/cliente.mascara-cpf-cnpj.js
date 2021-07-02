var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.inputCpfCnpj = $('#cpfOuCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		var tipoPessoaSelecionado = this.radioTipoPessoa.filter(':checked')[0];
		if (tipoPessoaSelecionado) {
			aplicarMascara.call(this, $(tipoPessoaSelecionado));
		}
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionado = $(evento.currentTarget);
		aplicarMascara.call(this, tipoPessoaSelecionado);
		this.inputCpfCnpj.val('');
	}
	
	function aplicarMascara(tipoPessoaSelecionado) {
		this.labelCpfCnpj.text(tipoPessoaSelecionado.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionado.data('mascara'));
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MascaraCpfCnpj;
}());

$(function() {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});