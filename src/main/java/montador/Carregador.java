/* Conteúdo do código objeto. Cada linha é um byte
// Cabeçalho: tamanho do arquivo, tamanho do cabeçalho, tamanho dos dados, tamanho das instruções
// Segmento de Dados
// Segmento de Intruções

*/
package montador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerson Menezes
 */
public class Carregador {
    
    
    public List<String> CodigoObjeto = new ArrayList<String>();

    
    public Carregador(){
          
    }
    
    public void loadCodigo_Obj() {
                                                            
        try {
            String CaminhoDoArquivo = new String(System.getProperty("user.dir")+"/src/main/java/arquivos_txt/codigo_objeto.txt");
            CodigoObjeto = Files.readAllLines(Paths.get(CaminhoDoArquivo));
               
            //System.out.println("PRINTANDO" + CodigoObjeto.toString());
        } 
        catch (IOException ex) {
            Logger.getLogger(Carregador.class.getName()).log(Level.SEVERE, null, ex);
        }
       loadMemory();         
    }

     public void loadMemory(){
         
         int qtd_dados = Integer.parseInt(CodigoObjeto.get(2),16);
         int qtd_instrucoes = Integer.parseInt(CodigoObjeto.get(3),16);
         System.out.println("Qtds: " + qtd_dados +" and "+ qtd_instrucoes);

         int posDados = 3000;
         int posInstrucoes = 1000;
         int posicao = 4;

          
         for (int i =0; i<qtd_dados; i++){
             int valor = Integer.parseInt(CodigoObjeto.get(posicao).trim(),16);
             
             Tela2.memory.setPalavra(valor, (posDados++));  
       
             posicao++;
         }
         
         for (int i =0; i<qtd_instrucoes; i++){
             int valor = Integer.parseInt(CodigoObjeto.get(posicao).trim(),16);
             Tela2.memory.setPalavra(valor, (posInstrucoes++));
             posicao++;
         }

     }

} 
    /*
    public void loadMemory(){
        
        /*
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
*/

