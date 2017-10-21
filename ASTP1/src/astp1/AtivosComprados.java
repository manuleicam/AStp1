/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;
import java.io.Serializable;

/**
 *
 * @author Manuel
 */

public class AtivosComprados extends Ativos {
    
    private double totalPago;
    private double precoAVender;
    private int nAtivos;
    public int nAVender;
    
    public AtivosComprados(String nome, double aValue, double oValue, String code, int n){
        super(nome, aValue, oValue, code);
        this.totalPago = aValue*n;
        this.precoAVender = -1;
        this.nAtivos = n;
        this.nAVender = 0;
    }
    
    public double getTotalPago(){
        return this.totalPago;
    }
    
    public int getNAtivos(){
        return this.nAtivos;
    }
    
    public int getNAVender(){
        return this.nAVender;
    }
    
    public double getPrecoAVender(){
        return this.precoAVender;
    }
    
    public void setNAVender(int f, int n){
        if (f==0) this.nAVender -= n;
        else this.nAVender += n;
    }
    
    public void setNAtivos(int f, int n){
        if(f == 0) this.nAtivos -= n;
        else this.nAtivos += n;
    }
    
    public void setTotalPago(int f, double value){
        if(f == 0) this.totalPago -= value;
        else this.totalPago += value;
    }
    
    public void setPrecoAVender(double value){
        this.precoAVender = value;
    }
    
    public double getValue(int n){
        double val;
        val = this.getActualValue() * n;
        return val;
    }
    
    public void updateCondicoesVenda(double val, int n){
        setNAVender(1,n);
        setPrecoAVender(val);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Ativo:");
        sb.append("\n Código: ");
        sb.append(this.getCode());
        sb.append(" Nome: ");
        sb.append(this.getNome());
        sb.append("\n Valor atual: ");
        sb.append(this.getActualValue());
        sb.append(" Último valor: ");
        sb.append(this.getOldValue());
        sb.append(" Diferença do último valor: ");
        sb.append(this.getDif());
        sb.append("\n Número de Ativos: ");
        sb.append(this.nAtivos);
        sb.append(" Total Pago: ");
        sb.append(this.totalPago);
        sb.append("\n Preço a vender: ");
        sb.append(this.precoAVender);
        sb.append(" Número de ativos à venda: ");
        sb.append(this.nAVender);
        return sb.toString();
    }
    
}
