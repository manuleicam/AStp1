/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

import java.util.HashMap;
import java.io.Serializable;
/**
 *
 * @author Manuel
 */
public class Utilizador implements Serializable {
    
    private String nome;
    private String password;
    private double dinheiroActual;
    private double dinheiroInvestido;
    private double balanco;
    private double dinheiroNosActivos;
    private HashMap<String, AtivosComprados> ativos;
    
    public Utilizador(String nome, String password, double dinheiro){
        this.nome = nome;
        this.password = password;
        this.dinheiroActual = dinheiro;
        this.dinheiroInvestido = dinheiro;
        this.balanco = 0;
        this.ativos = new HashMap<>();
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

    public double getBalanco() {
        return balanco;
    }
    
    public HashMap<String, AtivosComprados> getAtivos(){
        return this.ativos;
    }

    public void setDinheiroActual(double dinheiroActual) {
        this.dinheiroActual = dinheiroActual;
    }

    public void setDinheiroInvestido(double dinheiroInvestido) {
        this.dinheiroInvestido = dinheiroInvestido;
    }

    public void setBalanco() {
        this.balanco = this.dinheiroActual + this.dinheiroNosActivos - this.dinheiroInvestido;
    }

    public void setDinheiroNosActivos(double dinheiroNosActivos) {
        this.dinheiroNosActivos = dinheiroNosActivos;
    }
    
    public void addAtivos(AtivosComprados a){
        String nomeA;
        nomeA = a.getNome();
        if(ativos.containsKey(nomeA)){
            ativos.get(nomeA).setTotalPago(1,a.getTotalPago());
            ativos.get(nomeA).setNAtivos(1, a.getNAtivos());
        }
        else{
            ativos.put(nomeA,a);
        }
        dinheiroActual -= a.getTotalPago();
        setBalanco();
        updateDinheiroAtivos();
    }
    
    public void actDinheiroAtual(int f, double val){
        if (f == 0) this.dinheiroActual -= val;
        else this.dinheiroActual += val;
    }
    
    public void updateDinheiroAtivos(){
        this.dinheiroNosActivos = 0;
        for(AtivosComprados a : ativos.values()){
            this.dinheiroNosActivos = this.dinheiroNosActivos + (a.getActualValue()* a.getNAtivos());
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ");
        sb.append(this.nome);
        sb.append(" Dinheiro Atual: ");
        sb.append(this.dinheiroActual);
        sb.append("\n Balanco atual: ");
        sb.append(this.balanco);
        sb.append(" Dinheiro Invesido: ");
        sb.append(this.dinheiroInvestido);
        sb.append(" Dinheiro nos ativos: ");
        sb.append(this.dinheiroNosActivos);
        return sb.toString();
    }

}
