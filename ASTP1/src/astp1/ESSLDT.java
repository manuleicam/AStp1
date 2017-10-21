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
    private Vendedor v;

    public void save(String file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public static ESSLDT createFromFile(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object obj = ois.readObject();
        ois.close();

        if (obj instanceof ESSLDT) {
            return (ESSLDT) obj;
        }
        return null;
    }

    public ESSLDT() {
        activos = new HashMap<>();
        utilizadores = new HashMap<>();
        bp = new BuscarPrecos(utilizadores, activos);
        v = new Vendedor(utilizadores);
    }

    public String getUtilizador() {
        return this.utilizadorAtual.toString();
    }

    public void iniciaThreads() throws InterruptedException {
        Thread actPrecos = new Thread(bp);
        Thread vende = new Thread(v);
        actPrecos.start();
        vende.start();
    }

    public void actValFromAPI() {
        bp.getFromAPI();
    }

    public Utilizador getUtilizadorAtual() {
        return this.utilizadorAtual;
    }

    public String getUtilizadorAtualString() {
        return this.utilizadorAtual.toString();
    }

    public int getSizeAtivos() {
        return this.activos.size();
    }

    public boolean verExiste(String nome) {
        return utilizadores.containsKey(nome);
    }

    public void registar(String nome, String pass, double dinh) {
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

    public HashMap<String, Ativos> getAtivos() {
        return this.activos;
    }

    public Ativos getAtivoAver(int escolha) {
        for (Ativos a : activos.values()) {
            if (escolha == 1) {
                return a;
            }
            escolha--;
        }
        return null;
    }

    public HashMap<String, AtivosComprados> getAtivosUtil() {
        return this.utilizadorAtual.getAtivos();
    }

    public boolean verificaDinheiro(Ativos a, int n) {
        return utilizadorAtual.getDinheiroActual() >= a.getActualValue() * n;
    }

    public void comprarAtivo(Ativos a, int n) {
        if (utilizadorAtual.getAtivos().containsKey((a.getNome()))) {
            utilizadorAtual.actDinheiroAtual(0,a.getActualValue()*n);
            utilizadorAtual.getAtivos().get(a.getNome()).setNAtivos(1, n);
            utilizadorAtual.getAtivos().get(a.getNome()).setTotalPago(1,a.getActualValue()*n);
            utilizadorAtual.setBalanco();
        }
        else{
            AtivosComprados aux = new AtivosComprados(a.getNome(), a.getActualValue(), a.getOldValue(), a.getCode(), n);
            utilizadorAtual.addAtivos(aux);
        }
    }

    public boolean possuiAtivo(Ativos a) {
        return utilizadorAtual.getAtivos().containsKey(a.getNome());
    }

    public double fechaAtivo(Ativos a, int n) {
        double win;
        AtivosComprados aux = utilizadorAtual.getAtivos().get(a.getNome());
        if (n >= aux.getNAtivos() || (n-aux.getNAtivos()-aux.getNAVender())<=0) {
            n = aux.getNAtivos();
            win = aux.getValue(n);
            utilizadorAtual.getAtivos().remove(a.getNome());
            utilizadorAtual.updateDinheiroAtivos();
        } else {
            win = aux.getValue(n);
            aux.setNAtivos(0, n);
            aux.setTotalPago(0, win);
            utilizadorAtual.updateDinheiroAtivos();
        }
        utilizadorAtual.actDinheiroAtual(1, win);
        utilizadorAtual.setBalanco();
        return win;
    }
    
    public String getAtivosDoUtil(Ativos a){
        String nome = a.getNome();
        return utilizadorAtual.getAtivos().get(nome).toString();
    }
    
    public int atualizarAtivo(Ativos a, double val, int n){
        AtivosComprados aux = utilizadorAtual.getAtivos().get(a.getNome());
        if (n + aux.getNAVender() <= aux.getNAtivos()){
            aux.updateCondicoesVenda(val, n);
        }
        else return 0;
        
        return 1;
    }

    public void adicionarAtivos() {
        Ativos a = new Ativos("Ouro", 100, 0, "GC=F");
        Ativos b = new Ativos("Petróleo", 100, 0, "CL=F");
        Ativos c = new Ativos("Prata", 100, 0, "SI=F");
        Ativos d = new Ativos("Google", 200, 0, "GOOG");
        Ativos e = new Ativos("Apple", 200, 0, "AAPL");
        Ativos f = new Ativos("Nvidia", 200, 0, "NVDA");
        Ativos g = new Ativos("IBM", 200, 0, "IBM");
        activos.put("ouro", a);
        activos.put("Petróleo", b);
        activos.put("Prata", c);
        activos.put("Google", d);
        activos.put("Apple", e);
        activos.put("Nvidia", f);
        activos.put("IBM", g);
    }
}
