Brewer.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.boxValorTotalVenda = $('.js-valor-total-box');
		this.inputValorFrete = $('#valorFrete');
		this.inputValorDesconto = $('#valorDesconto');
		this.boxValorTotalContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.valorFrete = this.inputValorFrete.data('valor');
		this.valorDesconto = this.inputValorDesconto.data('valor');
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.inputValorFrete.on('keyup', onValorFreteAlterado.bind(this));
		this.inputValorDesconto.on('keyup', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.inputValorFrete.on('keyup', onValoresAlterados.bind(this));
		this.inputValorDesconto.on('keyup', onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}
	
	function onValorFreteAlterado(evento) {
		this.valorFrete = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValorDescontoAlterado(evento) {
		this.valorDesconto = Brewer.recuperarValor($(evento.target).val());
	}
	
	function onValoresAlterados() {
		var valorTotal = numeral(this.valorTotalItens) + numeral(this.valorFrete) - numeral(this.valorDesconto);
		
		this.boxValorTotalVenda.html(Brewer.formatarMoeda(valorTotal));
		
		this.boxValorTotalContainer.toggleClass('erro', valorTotal < 0)
	}
	
	return Venda;
	
}());

$(function() {
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();

	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new Brewer.Venda(tabelaItens);
	venda.iniciar();
});