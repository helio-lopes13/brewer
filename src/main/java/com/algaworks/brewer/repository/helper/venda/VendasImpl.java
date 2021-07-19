package com.algaworks.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.dto.VendaOrigem;
import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	@Override
	public BigDecimal valorTotalNoAno() {
		Optional<BigDecimal> optional = Optional
			.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano and status = :status", BigDecimal.class)
			.setParameter("ano", Year.now().getValue())
			.setParameter("status", StatusVenda.EMITIDA)
			.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTotalNoMes() {
		Optional<BigDecimal> optional = Optional
			.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status", BigDecimal.class)
			.setParameter("mes", MonthDay.now().getMonthValue())
			.setParameter("status", StatusVenda.EMITIDA)
			.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTicketMedioNoAno() {
		Optional<BigDecimal> optional = Optional
			.ofNullable(manager.createQuery("select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :ano and status = :status", BigDecimal.class)
			.setParameter("ano", Year.now().getValue())
			.setParameter("status", StatusVenda.EMITIDA)
			.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaMes> totalPorMes() {
		List<VendaMes> vendasMes = manager.createNamedQuery("Vendas.totalPorMes").getResultList();
		
		LocalDate hoje = LocalDate.now();
		for (int i = 1; i <= 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());
			
			boolean possuiMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			
			if (!possuiMes) {
				vendasMes.add(i - 1, new VendaMes(mesIdeal, 0));
			}
			
			hoje = hoje.minusMonths(1);
		}
		
		return vendasMes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendaOrigem> porOrigem() {
		List<VendaOrigem> vendasOrigem = manager.createNamedQuery("Vendas.porOrigem").getResultList();
		
		LocalDate hoje = LocalDate.now();
		for (int i = 1; i <= 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());
			
			boolean possuiMes = vendasOrigem.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			
			if (!possuiMes) {
				vendasOrigem.add(i - 1, new VendaOrigem(mesIdeal, 0, 0));
			}
			
			hoje = hoje.minusMonths(1);
		}
		
		return vendasOrigem;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Venda) criteria.uniqueResult();
	}

	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		criteria.createAlias("cliente", "c");

		if (filtro != null) {
			if ((!StringUtils.isEmpty(filtro.getCodigo())) || (filtro.getStatus() != null)
					|| (filtro.getDesde() != null) || (filtro.getAte() != null) || (filtro.getValorMinimo() != null)
					|| (filtro.getValorMaximo() != null) || (!StringUtils.isEmpty(filtro.getNomeCliente()))
					|| (!StringUtils.isEmpty(filtro.getCpfCnpjCliente()))) {
				if (!StringUtils.isEmpty(filtro.getCodigo())) {
					criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
				}
				if (filtro.getStatus() != null) {
					criteria.add(Restrictions.eq("status", filtro.getStatus()));
				}
				if (filtro.getDesde() != null) {
					criteria.add(
							Restrictions.ge("dataCriacao", LocalDateTime.of(filtro.getDesde(), LocalTime.of(0, 0))));
				}
				if (filtro.getAte() != null) {
					criteria.add(
							Restrictions.le("dataCriacao", LocalDateTime.of(filtro.getAte(), LocalTime.of(23, 59))));
				}
				if (filtro.getValorMinimo() != null) {
					criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
				}
				if (filtro.getValorMaximo() != null) {
					criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
				}
				if (!StringUtils.isEmpty(filtro.getNomeCliente())) {
					criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
				}
				if (!StringUtils.isEmpty(filtro.getCpfCnpjCliente())) {
					criteria.add(
							Restrictions.eq("c.cpfOuCnpj", TipoPessoa.removerFormatacao(filtro.getCpfCnpjCliente())));
				}
			} else {
				criteria.addOrder(Order.asc("codigo"));
			}
		}
	}

}
