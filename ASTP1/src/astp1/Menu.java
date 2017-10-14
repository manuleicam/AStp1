
import astp1.BuscarPrecos;
import astp1.ESSLDT;
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
import java.io.ObjectOutputStream;

public class Menu {
    // variáveis de instância
    private List<String> opcoes;
    private String[] menuPrinc = {"LogIn", "Registar", "Ver Preços", "Criar Activos"};
    private String[] menuUtil = {"Ver portfólio", "Logout"};
    private String[] menuViagem = {"Viatura mais próxima","Escolher viatura"};
    private String[] menuEstatistica = {"Top 10 clientes gastadores", "Piores 5 motoristas", "Total facturado por uma empresa", "Total facturado por um veiculo"};
    private String[] menuMotoristaComEmpresa = {"Associar-se a uma viatura","Ver Viagens Efectuadas", "Mudar o estado", "Libertar Carro"};
    private String[] menuMotoristaPrivado = {"Registar Nova Viatura", "Ver Viagens Efectuadas", "Associar-se a uma empresa", "Mudar o estado"};
    private String[] menuEmpresa = {"Registar Nova Viatura","Ver Frota", "Ver Viagens Efectuadas"};
    private static final String OBJECT_FILE = "umerTaxis.obj";
    
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
                    essldt.verPrecos();
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
        while(x){
            setOpcoes(menuUtil);
            executa();
            esc = getOpcao();
            switch (esc) {
                case 1:
                    logIN();
                    break;
                case 2:
                    registar();
                    break;
                case 5:
                    save();
                    break;
                case 0:
                    x = false;
                    System.out.println("Saiu com sucesso");
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
            System.out.println("User já em uso, por favor escolha outro");
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
        System.out.println("Insira o seu email");
        user = escolha.nextLine();

        System.out.println("Insira a sua password");
        password = escolha.nextLine();

        flagLog = essldt.login(user, password);
        while (flagLog != 1 && flagLog != 2 && flagLog != 3) {
            if (flagLog == 0) {
                System.out.println("Password não corresponde ao Email inserido");
            } else {
                System.out.println("O email inserido não se encontra registado");
            }
            System.out.println("Insira o seu Email");
            user = escolha.nextLine();

            System.out.println("Insira a sua password");
            password = escolha.nextLine();

            flagLog = essldt.login(user, password);
        }
        menuUtil();
    }
}
