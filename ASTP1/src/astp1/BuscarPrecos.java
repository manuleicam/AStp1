/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

import static java.lang.Thread.sleep;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel
 */
public class BuscarPrecos implements Runnable {
    
    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Ativos> activos;

    public BuscarPrecos(HashMap<String, Utilizador> utilizadores, HashMap<String, Ativos> activos) {
        this.utilizadores = utilizadores;
        this.activos = activos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
            String[] parts;
            String nome;
            double value;
            String url = "http://download.finance.yahoo.com/d/quotes.csv?s=GC=F,SI=F,CL=F,NVDA,IBM,GOOG,AAPL&f=sl1d1t1c1ohgv&columns='symbol,price'&e=.csv";
            try {
                URL yahooData = new URL(url);
                URLConnection data = yahooData.openConnection();
                Scanner input = new Scanner(data.getInputStream());
                String line = input.nextLine();
                while (input.hasNextLine()) {
                    //System.out.println(line);
                    parts = line.split(",");
                    nome = parts[0];
                    nome = nome.replace("\"", "");
                    value = Double.parseDouble(parts[1]);
                    atualizaPrecos(nome,value);
                    line = input.nextLine();
                    //System.out.println(nome + " " + value);
                }
                parts = line.split(",");
                    nome = parts[0];
                    value = Double.parseDouble(parts[1]);
                    atualizaPrecos(nome,value);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public void atualizaPrecos(String code, double valor){
        for(Ativos a : activos.values()){
           if(a.getCode().equals(code)){
               a.setOldValue(a.getActualValue());
               a.setActualValue(valor);
               a.setDif();
           }
        }
    }

}
