import java.io.*;
import java.util.*;

public class Main{

	public static void main(String[] args) {
		
		if(args.length == 2){
			
			//Pega via argumento os bits de entrada e o ruido 
			String entrada = args[0];
			float ruido = Float.valueOf(args[1]);

			System.out.println("\nBits de Entrada : " + entrada);
			System.out.println("Ruído : " + ruido + "\n\n");

			
			TransicaoEstado te = new TransicaoEstado(entrada, ruido);
			
			//Inicia processos do algoritmo
			te.processos();

			//Compara os resultados obtidos
			te.compara(entrada);
		
		}else{
			System.out.println("\nErro: Entrada Inválida !!!\n");
			System.out.println("Formato esperado : java Main Bits_de_Entrada Ruido\n");
			System.exit(-1);
		}	


	}


}
