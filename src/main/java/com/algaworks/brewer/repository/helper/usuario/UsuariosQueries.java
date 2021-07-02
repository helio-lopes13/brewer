package com.algaworks.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;

public interface UsuariosQueries {
	public List<Usuario> filtrar(UsuarioFilter filtro);
	
//	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
}