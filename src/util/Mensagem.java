/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elder
 */
public class Mensagem implements Serializable{
    
    private String operacao;
    
    Map<String, String> params;
    
    public Mensagem(String operacao)
    {
       this.operacao = operacao;
       params = new HashMap<>();
    }
    
    public Mensagem()
    {
       params = new HashMap<>();
    }
    
    public String getOperacao()
    {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    
    public void setParam( String chave, String valor )
    {
        params.put( chave, valor );
    }
    
    public String getParam( String chave )
    {
        return params.get(chave);
    }
    
    public static Mensagem parseString(String protocolo){
        String p[] = protocolo.split(";");
        Mensagem m = new Mensagem(p[0]);
        
        try {
            if (protocolo.length() > 1) {
                for (int i = 1; i < p.length; i++) {
                    String chaveValor[] = p[i].split(":");
                    m.setParam(chaveValor[0], chaveValor[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Falha no Parser de mensagem: " + e.getMessage());
            return null;
        }
        
        return m;
    }
    
}
