import java.io.*;
import java.util.*;


public class TransicaoEstado{

	private Map<String, Estado> estados; //Hash Map usado para armazenar os estados, suas respectivas emissões e trocas de estado
	private String entrada; //String com os bits de entrada
	private String bits_emissao; //String para armazenar os bits emitidos
	private float ruido; //Armazena o ruido
	private HashMap<String, ValoresDecodifica> valores_decodifica;

	private static String estado_inicial = "00";

	//Método construtor
	public TransicaoEstado(String bits_entrada, float ruido){
		this.valores_decodifica = new HashMap<String, ValoresDecodifica>();
		this.valores_decodifica.put("00", new ValoresDecodifica("00", "", 0));
		this.estados = new HashMap<String, Estado>();
		this.estados.put("00", new Estado("00","00","11","00","10"));
		this.estados.put("01", new Estado("01","11","00","00","10"));
		this.estados.put("10", new Estado("10","10","01","01","11"));
		this.estados.put("11", new Estado("11","01","10","01","11"));
		
		this.entrada = bits_entrada + "00";
		this.ruido = ruido;
		this.bits_emissao = "";
	
	}

	//Faz a chamada para as fases do algoritmo
	public void processos(){
	
		codificacao(estado_inicial, entrada);
		estabelece_ruido();
		decodifica(bits_emissao);
	}

	//Função que faz a codificação dos bits de entrada
	public void codificacao(String est, String ent){
		int i;
		for(i=0; i < ent.length(); i++){
			bits_emissao = bits_emissao + (estados.get(est).get_bits_emissao(ent.charAt(i)));
			est = estados.get(est).get_prox_estado(ent.charAt(i));		
		}

		System.out.println("-> Codificação : " + bits_emissao);
	}

	//Função que estabelece o ruído nos bits da emissão
	public void estabelece_ruido(){
		String str_ruido = "";	
		Random percentual = new Random();
	
		for(int i=0; i < this.bits_emissao.length(); i++){
			
			
			if(percentual.nextDouble() >= this.ruido){
				
				if(this.bits_emissao.charAt(i) == '1') str_ruido = str_ruido + "0";
				else str_ruido = str_ruido + "1";
			
			}else str_ruido = str_ruido + this.bits_emissao.charAt(i);
			
		} 
		
		this.bits_emissao = str_ruido;

		System.out.println("-> Após Ruído :  " + bits_emissao);

	}

	//Função que faz a decodificação dos bits codificados
	public void decodifica(String codificada){
       	ValoresDecodifica val, zero, um, aux;
       	String subs_codificada;

        for(int i=0; i < codificada.length(); i+=2){
        	
        	HashMap<String, ValoresDecodifica> iter_valores = new HashMap<String, ValoresDecodifica>();
        	
        	for(String chave : valores_decodifica.keySet()){
            	val = valores_decodifica.get(chave);
            	subs_codificada = codificada.substring(i, i+2);

            	zero = this.pega_proximo(val, subs_codificada, '0');
            	um  = this.pega_proximo(val, subs_codificada, '1');

            	aux = iter_valores.get(zero.estado);
            	if(aux == null){ 
            		iter_valores.put(zero.estado, zero);
            	  
            	}else{
            	    if(zero.erro <= aux.erro)
            	        iter_valores.put(zero.estado, zero);
            	}    

            	aux = iter_valores.get(um.estado);
            	if(aux == null){
            	    iter_valores.put(um.estado, um);
            	
            	}else{
            		if(um.erro <= aux.erro){
            	    	iter_valores.put(um.estado, um);
            		}
        		}
        	}
        	valores_decodifica.putAll(iter_valores);	
    	}
    		
    }


    public ValoresDecodifica pega_proximo(ValoresDecodifica val, String subs, char bit){;
        Integer erro = 0;
        Estado s = this.estados.get(val.estado);
        

        if(subs.charAt(0) != s.get_bits_emissao(bit).charAt(0)) erro += 1;
        if(subs.charAt(1) != s.get_bits_emissao(bit).charAt(1)) erro += 1;

        return new ValoresDecodifica(s.get_prox_estado(bit), val.bits_decodificados + bit, val.erro + erro);
    }
	
	
    //Função que compara os resultados obtidos pelo processo de codificação/decodificação
	public void compara(String entrada){
		
		String string_decod = "";
		ValoresDecodifica val;
        int difer;
        

        System.out.printf ("\n%-10s %-40s %-10s %s\n","Estado", "Valor Decodificado", "Diferença", "Erro");
       

        for(String chave : valores_decodifica.keySet()){

            difer = 0;
            val = valores_decodifica.get(chave);
            string_decod = val.bits_decodificados.substring(0, val.bits_decodificados.length()-2);

            for(int i = 0; i < string_decod.length(); i++){
                
            	//Testa quantos bits são diferentes em relação os bits de entrada
                if(entrada.charAt(i) != string_decod.charAt(i)){
                    
                    difer += 1;
                    
                }
               
            }
            System.out.printf ("%-10s %-40s %-10d %d\n", val.estado, val.bits_decodificados.substring(0, val.bits_decodificados.length()-2), difer, val.erro);
           
        }

        System.out.println("\n\n");
	}
}
