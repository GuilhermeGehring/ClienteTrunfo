/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 *
 * @author elder
 */
public class Cliente {

    private String nome;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    //socket para comunicar com servidor
    private Socket socket;
    
    public Cliente(String nome, String senha){
        this.nome = nome;
        this.senha = senha;
    }
    private Socket conectaComServidor(String host, int porta) throws IOException {
        try {
            this.socket = new Socket( host, porta );
            return socket;
        } catch (IOException ex) {
            System.out.println("Erro ao conectar com o servidor: " + ex.getMessage());
            throw new IIOException(" Ocorreu um erro ao criar o socket: " + ex.getMessage() );
        }
    }
    
    
    /**
     * @param args the com
     * mand line arguments
     */
    public static void main(String[] args) {
        try{
            Scanner leitura = new Scanner(System.in);
            Cliente cliente = new Cliente("Guilherme", "teste123");
            //conexao com server
            Socket socket = cliente.conectaComServidor("localhost", 5555);
            //streams de saida e entrada
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            output.flush();
            String msg;
            do {
                //protocolo
                //envio
                System.out.print("0 - Sair\n1 - Login\nInforme a opção desejada: ");
                String opcao = leitura.nextLine();
                switch (opcao) {
                    case "0":
                        output.writeUTF("tchau");
                        break;
                    case "1":
                        String texto = "LOGIN;";
                        System.out.print("Informe o usuário: ");
                        texto += leitura.nextLine();
                        System.out.print("Informe a senha: ");
                        texto += ";" + leitura.nextLine();
                        output.writeUTF(texto);
                        break;
                    default:
                        output.writeUTF(opcao);
                        break;
                        
                }
                output.flush();
                //recebimento
                msg = input.readUTF();
                System.out.println("Servidor respondeu: "  + msg);
            } while (!msg.equals("pare"));
            //fecha streams e conexão
            output.close();
            input.close();
            socket.close();
        }catch( Exception e ){
            System.out.println("Erro na comunicação: " + e.getLocalizedMessage());
        }
        
    
    }
    
}
