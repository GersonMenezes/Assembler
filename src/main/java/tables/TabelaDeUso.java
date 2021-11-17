/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tables;

/**
 *
 * @author Gerson Menezes
 */
public class TabelaDeUso {
    private int signal; // 0 para + e 1 para -
    private int ocorrencia;
    
    public TabelaDeUso(int ocorrencia){
        this.ocorrencia = ocorrencia;
    }
    public TabelaDeUso(int ocorrencia, int signal){
        this.ocorrencia = ocorrencia;
        this.signal = signal;
    }
    public int getSignal(){
        return signal;
    }
    public int getOcorrencia(){
        return signal;
    }
}
