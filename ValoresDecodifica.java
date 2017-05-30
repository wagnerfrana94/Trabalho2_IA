import java.io.*;
import java.util.*;

public class ValoresDecodifica{
	public String estado;
    public String bits_decodificados;
    public Integer erro;


    //Método Construtor
    public ValoresDecodifica(String estado, String bits_decodificados, Integer erro){
        this.estado = estado;
        this.bits_decodificados = bits_decodificados;
        this.erro = erro;
    }

}

