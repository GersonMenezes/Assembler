/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tables;

/**
 *
 * @author Gerson Menezes
 */
public class SimbolosLocais {
    
    private boolean relocable; // Para constante false (valor absoluto) para outros simbolos true (relocável)
    private String value;
    private boolean definition;
    private int position;
    
    public SimbolosLocais(){
        
    }
    
    public SimbolosLocais(String value, boolean relocable, boolean definition) {
        this.relocable = relocable;
        this.value = value;
        this.definition = definition;
        this.position = 0;
    }
    public SimbolosLocais(boolean relocable, boolean definition) {
        this.relocable = relocable;
        this.value = "00";
        this.definition = definition;
        this.position = 0;
    }
    
    public SimbolosLocais(String value, int position, boolean relocable, boolean definition) {
        this.relocable = relocable;
        this.value = value;
        this.definition = definition;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    
}
