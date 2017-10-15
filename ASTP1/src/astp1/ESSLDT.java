/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.io.Serializable;

/**
 *
 * @author Manuel
 */
public class ESSLDT implements Serializable {

    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Ativos> activos;
    private Utilizador utilizadorAtual;
    private BuscarPrecos bp;
    
    public void save(String file) throws IOException {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
    }
    
    public static ESSLDT createFromFile(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream (fis);
        
        Object obj = ois.readObject();
        ois.close();
        
        if (obj instanceof ESSLDT) {
            return (ESSLDT)obj;
        }
        return null;
    }
    
    public ESSLDT(){
        activos = new HashMap<>();
        utilizadores = new HashMap<>();
        bp = new BuscarPrecos(utilizadores, activos);
    }
    
    public void iniciaThreads() throws InterruptedException {
        Thread actPrecos = new Thread(bp);
        actPrecos.start();
    }
    
    public void actValFromAPI(){
        bp.getFromAPI();
    }
    
    public Utilizador getUtilizadorAtual(){
        return this.utilizadorAtual;
    }
    
    public int getSizeAtivos(){
        return this.activos.size();
    }
    
    public boolean verExiste(String nome){
        return utilizadores.containsKey(nome);
    }
    
    public void registar(String nome, String pass, double dinh){
        Utilizador aux = new Utilizador(nome, pass, dinh);
        utilizadores.put(nome, aux);
    }
    
    public int login(String nome, String pass) {
        Utilizador temp;
        if (utilizadores.containsKey(nome)) {
            temp = utilizadores.get(nome);
            if (temp.getPassword().equals(pass)) {
                this.utilizadorAtual = temp;
                return 1; //ok
            } else {
                return 0; //pass mal
            }
        } else {
            return -1;//nao existe user
        }
    }
    
    public HashMap<String, Ativos> getAtivos(){
        return this.activos;
    }
    
    public Ativos getAtivoAver(int escolha){
        for(Ativos a : activos.values()){
            if (escolha == 1) return a;
            escolha--;
        }
        return null;
    }
    
    public HashMap<String, AtivosComprados> getAtivosUtil(){
        return this.utilizadorAtual.getAtivos();
    }
    
    public boolean verificaDinheiro(Ativos a,int n){
        return utilizadorAtual.getDinheiroActual() > a.getActualValue()*n;
    }
    
    public double comprarAtivo(Ativos a, int n){
        AtivosComprados aux = new AtivosComprados(a.getNome(),a.getActualValue(),a.getOldValue(),a.getCode(),n);
        utilizadorAtual.addAtivos(aux);
        return utilizadorAtual.getDinheiroActual();
    }
    
    public void adicionarAtivos(){
        Ativos a = new Ativos("Ouro",100,0,"GC=F");
        Ativos b = new Ativos("Petróleo",100,0,"CL=F");
        Ativos c = new Ativos("Prata",100,0,"SI=F");
        Ativos d = new Ativos("Google",200,0,"GOOG");
        Ativos e = new Ativos("Apple",200,0,"AAPL");
        Ativos f = new Ativos("Nvidia",200,0,"NVDA");
        Ativos g = new Ativos("IBM",200,0,"IBM");
        activos.put("ouro",a);
        activos.put("Petróleo",b);
        activos.put("Prata",c);
        activos.put("Google",d);
        activos.put("Apple",e);
        activos.put("Nvidia",f);
        activos.put("IBM",g);
    }
}
