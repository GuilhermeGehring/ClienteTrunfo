/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import cliente.Trunfo;
/**
 *
 * @author guilherme
 */
public class ArquivoTexto {
    public static ArrayList<Trunfo> leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        ArrayList<Trunfo> cartas = new ArrayList<>();
        while (true) {
            if (linha != null) {
                String carta[] = linha.split(";");
                if (carta.length == 6)
                    cartas.add(new Trunfo(carta[0], carta[1], Integer.parseInt(carta[2]), Integer.parseInt(carta[3]), Integer.parseInt(carta[4]), Integer.parseInt(carta[5])));
            } else
                break;
            linha = buffRead.readLine();
        }
        buffRead.close();
        
        return cartas;
    }
 
    public static void escritor(String path) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        String linha = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Escreva algo: ");
        linha = in.nextLine();
        buffWrite.append(linha + "\n");
        buffWrite.close();
    }
}
