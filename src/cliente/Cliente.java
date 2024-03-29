/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import util.Estados;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.IIOException;
import util.ArquivoTexto;

/**
 *
 * @author elder
 */
public class Cliente {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //socket para comunicar com servidor
    private Socket socket;
    
    public Cliente(String nome){
        this.nome = nome;
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
            Cliente cliente = new Cliente("Guilherme");
            String protocolo;
            String status;
            ArrayList<Trunfo> baralho = new ArrayList<>();
            Trunfo carta = new Trunfo();
            //conexao com server
            Socket socket = cliente.conectaComServidor("localhost", 5555);
            //streams de saida e entrada
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            output.flush();
            Estados estado = Estados.CONECTADO;
            String msg;
            do {
                switch (estado) {

                    case CONECTADO:
                        System.out.print("0 - Sair\n1 - Login\nInforme a opção desejada: ");
                        String opcao = leitura.nextLine();
                        switch (opcao) {
                            case "0":
                                output.writeUTF("SAIR");
                                break;
                            case "1":
                                String texto = "LOGIN;";
                                System.out.print("Informe o usuário: ");
                                texto += "usuario:" + leitura.nextLine();
                                System.out.print("Informe a senha: ");
                                texto += ";senha:" + leitura.nextLine();
                                output.writeUTF(texto);
                                break;
                            case "2":
                                output.writeUTF("LOGOUT");
                                break;
                            case "3":
                                output.writeUTF("JOGAR");
                                break;
                            default:
                                output.writeUTF(opcao);
                                break;

                        }
                        break;
                    case AUTENTICADO:
                        System.out.print("0 - Logout\n1 - Jogar\n2 - Buscar e enviar Cartas\nInforme a opção desejada: ");
                        opcao = leitura.nextLine();
                        switch (opcao) {
                            case "0":
                                output.writeUTF("LOGOUT");
                                break;
                            case "1":
                                output.writeUTF("JOGAR");
                                break;
                            case "2":
                                String caminho = "/home/guilherme/NetBeansProjects/ClienteTrunfo/src/util/cartas.txt";
                                baralho = ArquivoTexto.leitor(caminho);
                                output.writeUTF("CARTAS");
                                break;
                            default:
                                output.writeUTF(opcao);
                                break;

                        }
                        break;
                    case JOGANDO:
                        try {
                            System.out.println("Você possui " + baralho.size() + " cartas");
                            carta = baralho.remove(0);
                            System.out.println("Jogador: " + carta.getNome());
                            System.out.println("1 - Chute: " + carta.getChute());
                            System.out.println("2 - Defesa: " + carta.getDefesa());
                            System.out.println("3 - Drible: " + carta.getDrible());
                            System.out.println("4 - Velocidade: " + carta.getVelocidade());
                            System.out.print("Informe o atributo: ");
                            opcao = leitura.nextLine();

                            output.writeUTF("JOGADA;opcao:" + opcao + ';' + carta.request());                        
                            break;
                        } catch (Exception e) {
                            output.writeUTF("TERMINAR");
                            break;
                        }
                }
                output.flush();
                //recebimento
                msg = input.readUTF();
                System.out.println("Servidor respondeu: "  + msg);
                protocolo = msg.split(":")[0];
                status = msg.split(":")[1];
                
                if (status.equals("OK")) {
                    switch (protocolo) {
                        case "LOGINRESPONSE":
                            estado = Estados.AUTENTICADO;
                            break;
                        case "LOGOUTRESPONSE":
                            estado = Estados.CONECTADO;
                            break;
                        case "SAIRRESPONSE":
                            estado = Estados.DESCONECTADO;
                            break;
                        case "JOGARRESPONSE":
                            estado = Estados.JOGANDO;
                            break;
                        case "TERMINARRESPONSE":
                            estado = Estados.AUTENTICADO;
                            break;
                        case "CARTASRESPONSE":
                            for (int i = 0; i <= baralho.size() / 2; i++) {
                                output.writeUTF("CARTA;" + baralho.remove(0).request());
                                output.flush();
                            }
                            output.writeUTF("CARTASEND");
                            output.flush();
                            msg = input.readUTF();
                            break;
                        case "GANHOURESPONSE":
                            baralho.add(carta);
                            msg = input.readUTF();
                            baralho.add(new Trunfo(msg.split(";")[1], msg.split(";")[2], Integer.parseInt(msg.split(";")[3]) , Integer.parseInt(msg.split(";")[4]), Integer.parseInt(msg.split(";")[5]), Integer.parseInt(msg.split(";")[6])));
                            break;
                    }
                }
            } while (!estado.equals(Estados.DESCONECTADO));
            //fecha streams e conexão
            output.close();
            input.close();
            socket.close();
        }catch( Exception e ){
            System.out.println("Erro na comunicação: " + e.getLocalizedMessage());
        }
        
    
    }
    
}
