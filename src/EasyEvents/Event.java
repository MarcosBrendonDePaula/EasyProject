/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyEvents;

/**
 *
 * @author MarcosBrendon
 */
public class Event {
    public Object Parametro = null;
    public Runnable Funcao = null;
    
    public Event(Runnable func) {
        this.Funcao=func;
    }
    
    public Event(Runnable func,Object args) {
        this.Parametro=args;
        this.Funcao=func;
    }
    
    public Object getParametro() {
        return Parametro;
    }

    public void setParametro(Object Parametro) {
        this.Parametro = Parametro;
    }

    public Runnable getFuncao() {
        return Funcao;
    }

    public void setFuncao(Runnable Funcao) {
        this.Funcao = Funcao;
    }
    
}
