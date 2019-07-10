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
    private static int contador=0;
    private int id=contador;
    private Socket Cliente;
    private String Entrada     ="";
    //private String Saida;
    private PrintStream saida;
    public static int refresh  =1000;
    private int status=0;
    private Thread Proc;
    private Scanner entrada;
    private LinkedList<String> Buffer=new LinkedList();
    //private LinkedList<Object> objetos=new LinkedList<>();
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
            } catch (IOException ex) {
                System.out.println("Erro Ao Criar stream de Entrada");
                return;
            }            
            String et="";
            while(status!=1){
                try{
                    et=entrada.nextLine();
                    if(Entrada.equalsIgnoreCase("")){
                        Entrada=et;
                        EasyMultServer.OrdemDeChegada.add(id);
                    }else{
                        Buffer.add(et);
                        EasyMultServer.OrdemDeChegada.add(id);
                    }
                }catch(Exception e){
                    System.out.println("Ocorreu uma perda de conexao ID:"+id);
                    Cliente=null;
                    EasyMultServer.removerID(id);
                    status=1;
                    Proc.stop();
//                    EasyMultServer.ConectadosID.add(id);
                    try {
                        this.finalize();
                    } catch (Throwable ex) {
                        System.out.println("Erro Ao tentar Finalizar a Execulsao id: "+id);
                        entrada.close();
                        saida.close();
                    }
                }
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
        status=1;
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
     * Setar Um tempo de espera de uma verificacao de recebimento de informaçao a outra.
     * Quanto Maior O tempo de espera Menor a Ultilização do processador.
     * Parametro informado em MILISEGUNDOS.
     * @param Range int - Parametro informado em MILISEGUNDOS
     */
    public void setRefreshRate(int Range){
        refresh=Range;
    }
    /**
     * Para Ativar ou desativar A verificaçao de menssagem;
     * 0 Para Ativado, 1 Para Desativado;
     * @param Status int - Ligado ou Desligado 0 ou 1
     */
    public void setStatus(int Status){
        status=Status;
    }
//    /**
//     * Seta A menssagem No buffer de entrada Do Cliente;
//     * Seta A menssagem No Buffer de saida Do Servidor;
//     * @param Menssagem String - Menssagem A ser Enviada
//     * @deprecated 
//     */
//    public void setSaida(String Menssagem){
//        Saida=Menssagem;
//        saida.println(Saida);
//    }
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
     * Ao pegar uma entrada ela é automaticamente removida do buffer para Que uma nova menssagem chgue
     * @return String
     */
    public String getEntrada(){
        if(!Buffer.isEmpty()&&Entrada.equals("")){
            System.out.println("Quantidade em Fila:"+(Buffer.size()-1));
            Entrada=Buffer.getFirst();
            Buffer.removeFirst();
        }
        String ET=Entrada;
        Entrada="";
        return ET;
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
     * é funcional mas recomendamos que ultilize o copyEntrada
     * @deprecated
     * @return String
     */
    public String ET(){
        return Entrada;
    }
     /**
     * retorna uma copia da entrada atual sem a remover
     * @return String
     */
    public String copyEntrada(){
        return Entrada;
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
