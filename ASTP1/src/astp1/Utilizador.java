/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

import java.util.HashMap;

/**
 *
 * @author Manuel
 */
public class Utilizador {
    
    private String nome;
    private String password;
    private double dinheiroActual;
    private double dinheiroInvestido;
    private double lucro;
    private double dinheiroNosActivos;
    private HashMap<String, AtivosComprados> ativos;
    
    public Utilizador(String nome, String password, double dinheiro){
        this.nome = nome;
        this.password = password;
        this.dinheiroActual = dinheiro;
        this.dinheiroInvestido = dinheiro;
        this.lucro = 0;
        ativos = new HashMap<>();
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public double getDinheiroNosActivos(){
        return this.dinheiroNosActivos;
    }

    public String getPassword() {
        return password;
    }

    public double getDinheiroActual() {
        return dinheiroActual;
    }

    public double getDinheiroInvestido() {
        return dinheiroInvestido;
    }

    public double getLucro() {
        return lucro;
    }
    
    public void addAtivos(AtivosComprados a){
        String nomeA;
        nomeA = a.getNome();
        ativos.put(nomeA,a);
    }
    
}
