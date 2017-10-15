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
public class Ativos implements Serializable {
    
    private String nome;
    private String code;
    private double actualValue;
    private double oldValue;
    private double dif;
    
    public Ativos(String nome){
        this.nome = nome;
        this.actualValue = 0;
        this.oldValue = -1;
        this.dif = 0;
    }
    
    public Ativos(Ativos a){
        this.nome = a.getNome();
        this.code = a.getCode();
        this.actualValue = a.getActualValue();
        this.oldValue = a.getOldValue();
        this.dif = a.getDif();
    }
    
    public Ativos(String nome, double aValue, double oValue, String code){
        this.nome = nome;
        this.actualValue = aValue;
        this.oldValue = oValue;
        this.code = code;
        this.dif = 0;
    }
    
    public double getActualValue(){
        return this.actualValue;
    }
    
    public String getCode(){
        return this.code;
    }
    
    
    public String getNome(){
        return this.nome;
    }
    
    public double getOldValue(){
        return this.oldValue;
    }
    
    public double getDif(){
        return this.dif;
    }
    
    public void setActualValue(double value){
        this.actualValue = value;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public void setOldValue(double value){
        this.oldValue = value;
    }
    
    public void setDif(){
        this.dif = this.actualValue - this.oldValue;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Ativo:");
        sb.append("\n Código: ");
        sb.append(this.code);
        sb.append(" Nome: ");
        sb.append(this.nome);
        sb.append("\n Valor atual: ");
        sb.append(this.actualValue);
        sb.append(" Último valor: ");
        sb.append(this.oldValue);
        sb.append(" Diferença do último valor: ");
        sb.append(this.dif);
        return sb.toString();
    }
}
