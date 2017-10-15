
import astp1.Ativos;
import astp1.AtivosComprados;
import astp1.BuscarPrecos;
import astp1.ESSLDT;
import astp1.Utilizador;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;

public class Menu {
    // variáveis de instância
    private List<String> opcoes;
    private String[] menuPrinc = {"LogIn", "Registar", "Actualizar da API", "Criar Activos", "Guardar estado"};
    private String[] menuUtil = {"Ver portfólio", "Ver Activos"};
    private String[] menuDetalhesAtivos = {"Comprar"};
    private static final String OBJECT_FILE = "essLDT.obj";
    
    private int op, esc;
    public ESSLDT essldt;
    public Scanner escolha; 
    
    public Menu(){
        escolha = new Scanner(System.in);
        essldt = new ESSLDT();
    }

    public static void main(String[] args) throws InterruptedException{
        System.out.println('\f');
        new Menu().run();
    }
    
    public void run() throws InterruptedException{
        
        try {
            essldt = ESSLDT.createFromFile(OBJECT_FILE);
            essldt.iniciaThreads();
            menuPrinc();
        }
        catch (Exception e) {
            System.out.println("NO OBJECT FILE");
            essldt.iniciaThreads();
            menuPrinc();
        }
        
    }
    
    public void save() {
        try {
            essldt.save(OBJECT_FILE);
        }
        catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
    
    public void menuPrinc(){
        boolean x = true;
        while(x){
            setOpcoes(menuPrinc);
            executa();
            esc = getOpcao();
            switch (esc) {
                case 1:
                    logIN();
                    break;
                case 2:
                    registar();
                    break;
                case 3:
                    //escreveAtivos();
                    essldt.actValFromAPI();
                    //menuAtivos();
                    break;
                case 4:
                    essldt.adicionarAtivos();
                    break;
                case 5:
                    save();
                    break;
                case 0:
                    x = false;
                    System.out.println("Adeus!");
                    break;
            }
        }
   }
    
    public void menuUtil(){
        boolean x = true;
        Utilizador aux;
        
        //System.out.println("\n"+aux.toString());
        while(x){
            aux = essldt.getUtilizadorAtual();
            System.out.println("\n"+aux.toString());
            setOpcoes(menuUtil);
            executa();
            esc = getOpcao();
            switch (esc) {
                case 1:
                    verPotefolio();
                    break;
                case 2:
                    escreveAtivos();
                    menuAtivos();
                    break;
                case 0:
                    x = false;
                    System.out.println("Saiu com sucesso");
                    break;
            }
        }
    }
    
    public void menuAtivos(){
        boolean x = true;
        int e;
        Ativos a;
        while(x){
            e = escolha.nextInt();
            if(e>0 && e<=essldt.getSizeAtivos()){
                a = new Ativos(essldt.getAtivoAver(e));
                menuAtivoDetalhes(a);
                x=false;
            }
            else {
                menuUtil();
                x = false;
            }
        }
   }
    
    public void menuAtivoDetalhes(Ativos a){
        System.out.println("\n" + a.toString());
        boolean x = true;
        while(x){
            setOpcoes(menuDetalhesAtivos);
            executa();
            esc = getOpcao();
            switch (esc) {
                case 1:
                    comprarAtivo(a);
                    x=false;
                    break;
                case 0:
                    x = false;
                    break;
            }
        }
        
    }
    
    public void setOpcoes(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    /**
     * Método para apresentar o menu e ler uma opção.
     * 
     */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }
    
    /** Apresentar o menu */
    private void showMenu() {
        System.out.println("\n *** Menu *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }
    
    /** Ler uma opção válida */
    private int lerOpcao() {
        int op; 
        
        System.out.print("Opção: ");
        try {
            op = escolha.nextInt();
        }
        catch (InputMismatchException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }
    
    /**
     * Método para obter a última opção lida
     */
    public int getOpcao() {
        return this.op;
    }

    
    public void registar(){
        String nome, password;
        double dinheiro;
        escolha.nextLine();
        System.out.println("Insira o user que pretende");
        nome = escolha.nextLine();
        while(essldt.verExiste(nome)){
            System.out.println("Nome já em uso, por favor escolha outro");
            nome = escolha.nextLine();
        }
        System.out.println("Insira a password");
        password = escolha.nextLine();
        System.out.println("Insira o dinheiroIniciar");
        dinheiro = escolha.nextDouble();
        essldt.registar(nome, password, dinheiro);
    }
    
    public void logIN() {
        String user, password;
        int flagLog;

        escolha.nextLine();
        System.out.println("Insira o seu Nome");
        user = escolha.nextLine();

        System.out.println("Insira a sua password");
        password = escolha.nextLine();

        flagLog = essldt.login(user, password);
        while (flagLog != 1 && flagLog != 2 && flagLog != 3) {
            if (flagLog == 0) {
                System.out.println("Password não corresponde ao Nome inserido");
            } else {
                System.out.println("O email inserido não se encontra registado");
            }
            System.out.println("Insira o seu Nome");
            user = escolha.nextLine();

            System.out.println("Insira a sua password");
            password = escolha.nextLine();

            flagLog = essldt.login(user, password);
        }
        menuUtil();
    }
    
    public void escreveAtivos(){
        int x = 0;
        HashMap<String, Ativos> eativos;
        eativos = essldt.getAtivos();
        for(Ativos a : eativos.values()){
            x++;
            System.out.println(x + "- " + a.toString());
        }
    }
    
    public void verPotefolio(){
        System.out.println();
        HashMap<String, AtivosComprados> eativos;
        eativos = essldt.getAtivosUtil();
        for(AtivosComprados a : eativos.values()){
            System.out.println(a.toString());
        }
    }
    
    public void comprarAtivo(Ativos a){
        escolha.nextLine();
        int n;
        System.out.println("Insira o número de ativos que deseja comprar");
        n = escolha.nextInt();
        if(essldt.verificaDinheiro(a,n)){
            System.out.print("Preço que pagou " + a.getActualValue()*n);
            essldt.comprarAtivo(a,n);
        }
        else{
            System.out.println("Dinheiro Insuficiente!! Compra Cancelada!!");
        }
    }
    
    
}
