/**
 * Classe Criada Para Tornar mais Facil a Criaçao e execução de um socket
 * Criado Por Marcos Brendon De Paula
 * ----------------------------------------- UFMS-------------------------------------------
 */
package EasySocket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author MarcosB
 */
public class nsocket {
    private static int contador = 0;
    private int id=contador;
    private Socket Cliente;
    private PrintStream saida;
    private Thread Proc;
    private Scanner entrada;
    private LinkedList<String> Buffer=new LinkedList();
    
    /**
     * Informe o socket(c) para que o cliente com o socket (c) seja criado
     * @param c - Cliente Nsocket Conectado
     * @throws IOException - Pode gerar um erro Ao criar a thread
     */
    public nsocket(Socket c) throws IOException{
        Cliente=c;
        entrada=new Scanner(Cliente.getInputStream());
        saida=new PrintStream(Cliente.getOutputStream());
        contador++;
        Proc=new Thread(Verificar);
        Proc.setPriority(Thread.NORM_PRIORITY);
        Proc.start();
        
    }
    /**
     * Runnable de Verificaçao
     */
    private Runnable Verificar=new Runnable() {
        @Override
        public void run() {
            try {
                entrada= new Scanner(Cliente.getInputStream());
                while(true){
                    Buffer.add(entrada.nextLine());
                    EasyMultServer.OrdemDeChegada.add(id);
                }
            } catch (Exception ex) {
                System.out.println("Ocorreu uma perda de conexao ID:"+id);
                Cliente=null;
                EasyMultServer.removerID(id);
                return;
            }            
        }
    };
    /**
     * Desconecta Entradas e saidas do cliente
     */
    public void Disconnect(){
        entrada.close();
        saida.close();
        Proc.stop();
    }
    
     /**
     * Para quem sabe manipular thread Ultilize esta função para definir parametros diretamente na thread de execusao
     * @return Thread
     */
    public Thread getThreadUpdate(){
        return Proc;
    }
    /**
     * Retorna O buffer de Entradas
     * @return LinkedList
     */
    public LinkedList<String> getBufferEntradas(){
        return Buffer;
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
     */
    public void SetMinPriority(){
        Proc.setPriority(Thread.MIN_PRIORITY);
    }
    /**
     * Seta Um nivel de Prioridade Na Thread De verificaçao.
     */
    public void SetNorPriority(){
        Proc.setPriority(Thread.NORM_PRIORITY);
    }
    /**
     * Seta Um nivel de Prioridade Na Thread De verificaçao.
     */
    public void SetMaxPriority(){
        Proc.setPriority(Thread.MAX_PRIORITY);
    }    
    /**
     * Ao pegar uma entrada ela é automaticamente removida do buffer para Que uma nova menssagem chgue
     * @return String
     */
    public String getEntrada(){
        if(!Buffer.isEmpty()){
            String x = Buffer.getFirst();
            Buffer.removeFirst();
            return x;
        }else
            return "?";
    }
    /**
     * Retorna a Id Da conexao Atual
     * @return int
     */
    public int getId(){
        return id;
    }
    
     /**
     * retorna uma copia da entrada atual sem a remover
     * @return String
     */
    public String copyEntrada(){
        return Buffer.getFirst();
    }
    /**
     * Para quem saber manipular Thread essa função retorna a Thread de verificaçao do Socket
     * @return Thread
     */
    public Thread getVerificador(){
        return Proc;
    }
    public Socket getSocket(){
        return Cliente;
    }
}
