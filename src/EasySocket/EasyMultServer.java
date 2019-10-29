
/**
 * Classe Criada Para Tornar mais Facil a Criaçao e execução de um socket
 * Criado Por Marcos Brendon De Paula
 * ----------------------------------------- UFMS-------------------------------------------
 */
package EasySocket;

import java.awt.Label;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
/**
 *
 * @author MarcosB
 */
public class EasyMultServer {
    public static LinkedList<nsocket> Conecxoes=new LinkedList<>();
    public static LinkedList<Integer> OrdemDeChegada=new LinkedList<>();
    public static LinkedList<Integer> ConectadosID=new LinkedList<>();
    //private static LinkedList<Integer> DesconectadosID=new LinkedList<>();
    private ServerSocket server;
    public Thread T1;
    static boolean on=false;
    Label erro = new Label("Erro");
    /**
     * Funçao executada apos a ativaçao caso queira mudala faça uma sobrecarga de metodo
     * @param Aceitado - Conexao Aceitada 
     */
    public void funçaoExtra(nsocket Aceitado){
        
    }
    /**
     * A porta informada sera ultilizada para conexoes
     * @param porta int - Porta A ser utilizada para a Comunicaçao do servidor
     */
    public EasyMultServer(int porta){
        try {
            server=new ServerSocket(porta);
            System.out.println("server iniciado");
            on=true;
        } catch (IOException ex) {
            System.out.println("Erro: porta "+porta+" Em uso");
        }
    }
    /**
     * Funçao interna
     */
    private Runnable accept=new Runnable() {
        @Override
        public void run() {
            Socket acept=null;
            while(true){
                System.out.println("Aguardando conexoes "+getServer().getLocalPort());
                try {
                    acept = getServer().accept();
                    if(acept == null){
                        System.out.println("erro Ao aceitar Conexão");
                    }else{
                        nsocket cnovo = new nsocket(acept);
                        System.out.println("conexao:"+acept.getInetAddress()+" Porta:"+acept.getPort()+" Id:"+cnovo.getId());
                        Conecxoes.add(cnovo);
                        ConectadosID.add(cnovo.getId());
                        funçaoExtra(cnovo);
                    }
                } catch (IOException ex) {
                    System.out.println("erro Ao aceitar Conexão de modo geral");
                }
            }
        }
    };
    
    /**
     * Inicializa O servidor
     */
    public void start(){
       T1=new Thread(accept);
       T1.setPriority(Thread.MIN_PRIORITY);
       T1.start();
        System.out.println("Inicializado");
    }
    /**
     * Retorna O nsocket que tenha a ID especifica 
     * @param id int -  Id Do nsocket Registrado na lista de conexoes
     * @return nsocket
     */
    public nsocket getConID(int id){
        for(int i=0;i<Conecxoes.size();i++){
            if(Conecxoes.get(i).getId()==id){
                return Conecxoes.get(i);
            }
        }
        return null;
    }
    /**
     * Retorna A lista que contem todas as conexoes atuais.
     * @return LinkedList
     */
    public LinkedList<nsocket> getConecxoes(){
        return Conecxoes;
    }
     /**
     * Retorna A lista que contem todas as conexoes atuais.
     * @deprecated Nome errado :>
     * @return LinkedList
     */
    public LinkedList<nsocket> getConecçoes(){
        return Conecxoes;
    }
    /**
     * Retorna Uma LIsta contendo as IDS com Ordem de chegada de mensssagens.
     * Necessario Remoçao de elemento manualmente.
     * @return LinkedList
     */
    public LinkedList<Integer> getOrdem(){
        return OrdemDeChegada;
    }
    /**
     * Funçao interna da classe, Remove A determinada ID do servidor
     * @param id - Remove um nsocket especifico da lista de conexoes
     */
    static public void removerID(int id){
        for(int i=0;i<Conecxoes.size();i++){
            if(Conecxoes.get(i).getId()==id){
                Conecxoes.get(i).Disconnect();
                Conecxoes.remove(i);
            }
        }
    }
    
    /**
     * Para quem sabe manipular thread Ultilize esta função para definir parametros diretamente na thread de execusao
     * @return Thread
     */
    public Thread getThreadMain(){
        return T1;
    }
    /**
     * Finaliza a execulsao do servidor principal
     */
    public void StopServer(){
        on=false;
        T1.stop();
        for(int i=0;i<Conecxoes.size();i++){
            Conecxoes.get(i).Disconnect();
        }
    }

    /**
     * @return the server
     */
    public ServerSocket getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(ServerSocket server) {
        this.server = server;
    }
}
