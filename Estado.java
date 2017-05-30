import java.io.*;
import java.util.*;

public class Estado{
	
	private String nome; //String com o nome do estado
	private String emit_ent_zero; //Informa qual a emissão daquele estado com um bit de entrada igual a zero
	private String emit_ent_um; //Informa qual a emissão daquele estado com um bit de entrada igual a um
	private String prox_estado_zero; //Informa qual a transição daquele estado com um bit de entrada igual a zero
	private String prox_estado_um; //Informa qual a transição daquele estado com um bit de entrada igual a um

	//Método construtor
	public Estado(String nome, String emit_zero, String emit_um, String prox_estado_zero, String prox_estado_um){
		this.nome = nome;
		this.emit_ent_zero = emit_zero;
		this.emit_ent_um = emit_um;
		this.prox_estado_zero=prox_estado_zero;
		this.prox_estado_um=prox_estado_um;
	}

	// Método para pegar os bits de emissão a partir de um bit de entrada
	public String get_bits_emissao(char bit){
		if(bit == '1'){
			return this.emit_ent_um;
		}else{
			return this.emit_ent_zero;
		}
	}
	
	// Método para pegar o próximo estado a partir de um bit de entrada
	public String get_prox_estado(char bit){
		if(bit == '1'){
			return this.prox_estado_um;

		}else{
			return this.prox_estado_zero;
		}
	}



} 

