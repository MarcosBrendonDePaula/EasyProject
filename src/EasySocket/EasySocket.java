/**
 * Classe Criada Para Tornar mais Facil a Criaçao e execução de um socket
 * Criado Por Marcos Brendon De Paula
 * ----------------------------------------- UFMS-------------------------------------------
 */
package EasySocket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author MarcosB
 */
public class EasySocket {
    private Socket Cliente;
    private ServerSocket Server;
    private final String ip;
    private final int porta;
    private final int CV;
    private String Entrada="";
    private String Saida;
    private PrintStream saida;
    private int refresh=1000;
    private int status=0;
    private boolean novo=false;
    private Thread Proc;
    private Scanner entrada;
    private LinkedList<String> Buffer=new LinkedList();
    /**
     * No parametro tipo voce deve informar:
     *        0 Para servidor
     *        1 Para Cliente
     * @param ip String - Ip da Maquina Onde sera Conectado
     * @param porta int - Porta Do servidor A ser conectado ou criado
     * @param Tipo int - Tipo Do EasySocket
     */
    public EasySocket(String ip, int porta, int Tipo) {
        this.ip = ip;
        this.porta = porta;
        this.CV = Tipo;
    }
    /**
     * No parametro tipo voce deve informar: "servidor" ou "cliente"
     * @param ip String - Ip da Maquina Onde sera Conectado
     * @param porta int - Porta Do servidor A ser conectado ou criado
     * @param tipo String - Tipo Do EasySocket
     */
    public EasySocket(String ip, int porta, String tipo) {
        this.ip = ip;
        this.porta = porta;
        if(tipo.equalsIgnoreCase("cliente"))
            CV=1;
        else
            if(tipo.equalsIgnoreCase("servidor"))
                CV=0;
            else{
                System.out.println("Verifique O construtor");
                CV=0;
            }
    }
    
    
    /**
     * Faz As funçoes do Start mas tambem inicializa a thread de Verificaçao;
     * Mais seguro A ser ultilizado Para servidor.
     * @return boolean
     */
    public boolean ServerStart(){
        Proc=new Thread(Verificar);
        ClientStart();
        AceitarConecxao();
        Proc.start();
        return true;
    }
    
    /**
     * Inicializa A thread De Verificaçao de menssagens
     */
    public void startVerificador(){
        Proc=new Thread(Verificar);
        Proc.setPriority(Thread.NORM_PRIORITY);
        Proc.start();
    }
    
    
    /**
     * Inicializa O servidor || Cliente;
     * Caso Seja iniciado com sucesso retorna True;
     * Necessario ultilizar a funçao startVerificador() caso tenha iniciado um cliente.
     * @return Boolean 
     */
    public boolean ClientStart() {
        if(CV==0){
            System.out.println("Inicializando Servidor com A porta:"+porta);
            try {
                Server=new ServerSocket(porta);
                if(Proc.isInterrupted()){
                    System.out.println("Inicialize O verificador");
                }
            } catch (IOException ex) {
                return false;
            }
            return true;
        }else
            try {
                Cliente=new Socket(ip,porta);
                entrada=new Scanner(Cliente.getInputStream());
                saida=new PrintStream(Cliente.getOutputStream());
                return true;
            } catch (IOException ex) {
                return false;
        }
    }
    
    
    /**
     * Funçao de verificação para recebimento de menssagens.
     */
    private final Runnable Verificar=new Runnable() {
        @Override
        public void run() {
            System.out.println("Verificador Inicializado");
            try {
                entrada= new Scanner(Cliente.getInputStream());
            } catch (IOException ex) {
                System.out.println("Erro Ao Criar A porta de Escuta");
                return;
            }
            String et="";// buffer Local de entrada
            while(status!=1){
                try{
                    et=entrada.nextLine();
                    if(Entrada.equals("")&&Buffer.isEmpty()){
                        Entrada=et;
                        novo=true;
                    }else{
                        Buffer.add(et);
                    }
                }catch(Exception e){
                    status=0;
                    System.out.println("Desconectado");
                    entrada.close();
                    saida.close();
                    Proc.stop();
                }
            }
        }
    };
    /**
     * Retorna O buffer de Entradas
     * @return LinkedList
     */
    public LinkedList<String> getBufferEntradas(){
        return Buffer;
    }
    /**
     * Pega a Entrada atual sem a aremover
     * @return String
     */
    public String getET(){
        return Entrada;
    }
    /**
     * Espera por uma conexao interna ou externa Modo servidor
     * Retorna (True Para sucesso, False para Falha) de conexao.
     * @return Boolean
     */
    public boolean AceitarConecxao(){
        try {
            Cliente=Server.accept();
            System.out.println("Cliente aceito:"+Cliente.getInetAddress()+" porta:"+Cliente.getPort());
            saida= new PrintStream(Cliente.getOutputStream());
            return true;
        } catch (IOException ex) {
            System.out.println("Erro ao aceitar a connecxao");
            return false;
        }
    }
    
    
    /**
     * Setar Um tempo de espera de uma verificacao de recebimento de informaçao a outra.
     * Quanto Maior O tempo de espera Menos Ultilização do processador.
     * Parametro informado em MILISEGUNDOS.
     * @param Range int - Numero Em milisegundos
     */
    public void setRefreshRate(int Range){
        refresh=Range;
    }
    
    
    /**
     * Para Ativar ou desativar A verificaçao de menssagem;
     * 0 Para Ativado, 1 Para Desativado;
     * @param Status int - Ligado OU desligado 0 ou 1  
     */
    public void setStatus(int Status){
        status=Status;
    }
    
    
    /**
     * Seta A menssagem No buffer de entrada Do Cliente;
     * Seta A menssagem No Buffer de saida Do Servidor;
     * @param Menssagem String - Menssagem A ser Gravada no buffer
     * @deprecated 
     */
    public void setSaida(String Menssagem){
        Saida=Menssagem;
    }
    
    /**
     * Enviar uma menssagem para o servidor ou para o cliente;
     * Entrada somente em String, retorna True caso Envie;
     * @param Menssagem String - Menssagem A ser Enviada
     */
    public void Enviar(String Menssagem){
        saida.println(Menssagem);
    }
    /**
     * Seta Um nivel de Prioridade Na Thread De verificaçao.
     * @deprecated 
     */
    public void SetMinPriority(){
        Proc.setPriority(Thread.MIN_PRIORITY);
    }
    /**
     * Seta Um nivel de Prioridade Na Thread De verificaçao.
     * @deprecated 
     */
    public void SetNorPriority(){
        Proc.setPriority(Thread.NORM_PRIORITY);
    }
    /**
     * Seta Um nivel de Prioridade Na Thread De verificaçao.
     * @deprecated 
     */
    public void SetMaxPriority(){
        Proc.setPriority(Thread.MAX_PRIORITY);
    }    
    /**
     * Ao pegar uma entrada ela é automaticamente removida do buffer para Que uma nova menssagem chegue
     * @return String
     */
    public String getEntrada(){
        if(!Buffer.isEmpty()&&Entrada.equals("")){
            Entrada=Buffer.get(0);
            Buffer.remove(0);            
            System.out.println("Quantidade em Fila:"+Buffer.size());          
            novo=true;
        }
        String ET=Entrada;
        Entrada="";
        novo=false;
        return ET;
    }
    /**
     * Encerrar a comunicação no modo cliente
     */
    public void Disconnect(){
        try {
            Cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(EasySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * retorna false caso não tenha chegado menssagens novas
     * retorna true caso tenha menssagens novas
     * @return boolean
     */
    public boolean getAlterado(){
        return novo;
    }
    public Socket getSocket(){
        return Cliente;
    }
}
