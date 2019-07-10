/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyThread;

/**
 *
 * @author MarcosB
 */
public class EasyThread {
    private Runnable Codigo=null;
    private Thread Thread;
    /**
     * 
     * @param Codigo - runable a ser executado 
     */
    public EasyThread(Runnable Codigo) {
        this.Codigo = Codigo;
        Thread=new Thread(this.Codigo);
    }
    /**
     * Inicia o processamento da Thread
     */
    public void Iniciar(){
        if(Codigo!=null)
            this.Thread.start();
        else
            System.out.println("Informe o trecho a ser processado no construtor");
    }
    /**
     * Termina o Processamento da Thread
     */
    public void Parar(){
        this.Thread.stop();
    }
    
}
