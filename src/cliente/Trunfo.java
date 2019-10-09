/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author 20171pf.cc0178
 */
public class Trunfo {
    private String nome;
    private String tipo;
    private int defesa;
    private int drible;
    private int velocidade;
    private int chute;

    public Trunfo() {
    }

    public Trunfo(String nome, String tipo, int defesa, int drible, int velocidade, int chute) {
        this.nome = nome;
        this.tipo = tipo;
        this.defesa = defesa;
        this.drible = drible;
        this.velocidade = velocidade;
        this.chute = chute;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getDrible() {
        return drible;
    }

    public void setDrible(int drible) {
        this.drible = drible;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getChute() {
        return chute;
    }

    public void setChute(int chute) {
        this.chute = chute;
    }

    @Override
    public String toString() {
        return nome + "{" + tipo + ", defesa:" + defesa + ", drible:" + drible + ", chute:" + chute + "}";
    }
    
    
}
