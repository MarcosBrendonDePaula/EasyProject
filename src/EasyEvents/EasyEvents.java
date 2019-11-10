/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyEvents;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MarcosBrendon
 */
public class EasyEvents {
    private Map<Object, Event> Eventos=new HashMap<Object, Event>();
    private LinkedList<Object> Signal = new LinkedList<Object>();
    private int Delay = 100;
    private Runnable Executor = new Runnable() {
        @Override
        public void run() {
            while(true){
                while(Signal.isEmpty()){
                    try {
                        Thread.sleep(Delay);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EasyEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                new Thread(Eventos.get(Signal.getFirst()).Funcao,"Evento").start();
                Signal.removeFirst();
            }
        }
    };

    public EasyEvents() {
        new Thread(Executor,"Executor").start();
    }
    public EasyEvents(int Delay) {
        this.Delay=Delay;
        new Thread(Executor,"Executor").start();
    }
    public void addEvent(Object id,Event evento){
        Eventos.put(id,evento);
    }
    
    public Event getEvent(Object id){
        return Eventos.get(id);
    }
    
    public void setDelay(int delay){
        this.Delay=delay;
    }
    public void sendEvent(Object id){
        Signal.add(id);
    }
}
