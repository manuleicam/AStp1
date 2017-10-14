/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

/**
 *
 * @author Manuel
 */
public class Ativos {
    
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
    
}
