/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astp1;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Manuel
 */
public class Vendedor implements Runnable, Serializable {

    private HashMap<String, Utilizador> utilizadores;

    public Vendedor(HashMap<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20050);
            } catch (InterruptedException ex) {
            }
            for (Utilizador u : utilizadores.values()) {
                for (AtivosComprados a : u.getAtivos().values()) {
                    if(a.getActualValue()>= a.getPrecoAVender() && a.getPrecoAVender()!=-1){
                        venderAtivo(u,a);
                    }
                }
            }
        }
    }
    
    public void venderAtivo(Utilizador u, AtivosComprados a){
        double win;
        int n;
        if (a.getNAVender() == a.getNAtivos()) {
            n = a.getNAVender();
            win = a.getValue(n);
            u.getAtivos().remove(a.getNome());
            u.updateDinheiroAtivos();
        } else {
            n = a.getNAVender();
            win = a.getValue(n);
            a.setNAtivos(0, n);
            a.setNAVender(0, n);
            a.setTotalPago(0, win);
            u.updateDinheiroAtivos();

        }
        u.actDinheiroAtual(1, win);
        u.setBalanco();
        a.setPrecoAVender(-1);
    }

}
