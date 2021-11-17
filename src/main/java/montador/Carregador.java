/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package montador;

/**
 *
 * @author Gerson Menezes
 */
public class Carregador {
    
    
    public Carregador(String caminho){
        loadMemory();
    }
    
    public void loadMemory(){
        
        // Dados inicializados
        Tela2.memory.setPalavra(1, 3000);
        Tela2.memory.setPalavra(9, 3001);
        
        // Instruções inicializadas
        // move AX, um
        Tela2.memory.setPalavra(161, 1000);
        Tela2.memory.setPalavra(1, 1001);
        Tela2.memory.setPalavra(0, 1002);
        // move DX, AX
        Tela2.memory.setPalavra(139, 1003);
        Tela2.memory.setPalavra(208, 1004);
        // move AX, nove
        Tela2.memory.setPalavra(161, 1005);
        Tela2.memory.setPalavra(9, 1006);
        Tela2.memory.setPalavra(0, 1007);
        // add AX, DX
        Tela2.memory.setPalavra(139, 1008);
        Tela2.memory.setPalavra(194, 1009);
        // move result, AX
        Tela2.memory.setPalavra(163, 1010);
        Tela2.memory.setPalavra(3002, 1011);
        Tela2.memory.setPalavra(0, 1012);

    }
}
