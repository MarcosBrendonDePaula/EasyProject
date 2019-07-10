/**
 * Classe Criada Para Tornar mais Facil a Criaçao e execução de um socket
 * Criado Por Marcos Brendon De Paula
 * ----------------------------------------- UFMS-------------------------------------------
 */
package EasySocket;

/**
 *
 * @author MarcosB
 */
public class Contador {
    private int parametro=1;
    Thread contador;
    int tempo=0;
        Runnable Time = new Runnable() {
            public void run() {
                tempo=0;
                while(true){
                    try {
                        Thread.sleep(parametro);
                    } catch (InterruptedException ex) {
                        return;
                    }
                    tempo++;
                }
            }
        };
    public Contador(){
        contador=new Thread(Time);
        contador.setPriority(Thread.MAX_PRIORITY);
    }
    /**
     * Tempo de Espera de Incremento
     * @param tempo int -  Ultilize em milisegundos 
     */
    public void setTempoDeEspera(int tempo){
        parametro=tempo;
    }
    /**
     * Inicia a Contagem
     */
    public void IniciarContagem(){
        contador.start();
    }
    /**
     * Interrompe a contagem
     */
    public void FinalizarContagem(){
        contador.stop();
    }
    /**
     * Retorna O total contado Em milisegundos
     * @return int - Milisegundos
     */
    public int GetTempo(){
        return tempo;
    }
}
