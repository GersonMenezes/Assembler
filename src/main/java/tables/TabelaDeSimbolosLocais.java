/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tables;

/**
 *
 * @author Gerson Menezes
 */
public class TabelaDeSimbolosLocais {
    
    private boolean relocable; // Para constante false (valor absoluto) para outros simbolos true (relocável)
    private String value;
    private boolean definition;
    
    public TabelaDeSimbolosLocais(){
        
    }
    
    public TabelaDeSimbolosLocais(String value, boolean relocable, boolean definition) {
        this.relocable = relocable;
        this.value = value;
        this.definition = definition;
    }
   

    public void setValue(String value) {
        
            this.value = value;
       
    }

    public boolean isRelocable() {
        return relocable;
    }

    public String getValue() {
        return value;
    }  

    public boolean isDefinited() {
        return definition;
    }

    public void setDefinition(boolean definition) {
        this.definition = definition;
    }
    
    
}
