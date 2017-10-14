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
public class AtivosComprados extends Ativos {
    
    private double precoCompra;
    
    public AtivosComprados(String nome, double aValue, double oValue, String code){
        super(nome, aValue, oValue, code);
        this.precoCompra = aValue;
    }
    
    public double getPrecoCompra(){
        return this.precoCompra;
    }
    
    public void setPrecoCompra(double value){
        this.precoCompra = value;
    }
    
}
