package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	public String salvarTemporariamente(MultipartFile[] files);

	public void salvar(String foto);

	public byte[] recuperarFotoTemporaria(String nome);

	public byte[] recuperarFoto(String foto);
}
