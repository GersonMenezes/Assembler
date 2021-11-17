/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package montador;

import tables.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Arrays;
import java.util.List;



public class Montador {
    
    public static LinkedHashMap<String, TabelaDeSimbolosGlobais> simbolosGlobais = new LinkedHashMap<String, TabelaDeSimbolosGlobais>();    //Linked NÃO perde a ordem que foi acrescentado    
    public static LinkedHashMap<String, TabelaDeSimbolosLocais> simbolosLocais = new LinkedHashMap<String, TabelaDeSimbolosLocais>();
    public static LinkedHashMap<String, TabelaDeUso> simbolosUsados = new LinkedHashMap<String, TabelaDeUso>();
    static public List<String> instrucoes = new ArrayList<String>();
    
    
    public Montador(){
        
    }
    
    public static void primeira_passagem() throws IOException {
        
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToFirstPass();
        //simbolos.put("valor", new Simbolo("valor","type", true));
    }

    public static void segunda_passagem() throws IOException {
        
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToSecondPass();
        //simbolos.put("nome", new Simbolo("valor","type", true));
    }
    
    
    public static void print_simbolos() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        for (String keys : simbolosLocais.keySet()){
 
            System.out.println(keys + simbolosLocais.get(keys).getValue());
            Tela2.symbolTableModel.addElement(keys + " | " + simbolosLocais.get(keys).getValue() +  " | " +  simbolosLocais.get(keys).isRelocable() +  " | "  + simbolosLocais.get(keys).isDefinited());

        }               
    }
    
    public static String getSymbolValue(String opd) { 
        
        String value;
        value = simbolosLocais.get(opd).getValue();
            
        return value;
    }
    
    public static void loadInstructionToFirstPass() throws IOException{  // Primeira passada (Falta resolver código dos simbolos)
        
        System.out.println("loadIstruction ");
        String codigoIntermediario = new String();
        int controlSymbolTable = 0;
        int programCounter = 0; // Marca a posição da instrução corrente
        int dataCounter = 0; // Marca tamanho do segmento de dados, cada variavel e const 2 bytes
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);
            if(instrucao.contains("add AX,AX")){
               
                codigoIntermediario += "0x03C0" + "\n"; 
                programCounter += 2; 
            }
            
            else if(instrucao.contains("add AX,DX")){
                codigoIntermediario += "0x03C2" + "\n";
                programCounter += 2; 
                //updateMemoria(0x03C2, controle_mem++);
   
            }else if(instrucao.matches("add AX,")){
                codigoIntermediario += "0x05";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                
                programCounter += 3; 
                
            }else if(instrucao.contains("div SI")){
                codigoIntermediario += "0xf7f6" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("div AX")){
                codigoIntermediario += "0xf7c0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("sub AX,AX")){
                codigoIntermediario += "0x2bc0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("sub AX,DX")){
                codigoIntermediario += "0x2bc2" + "\n";
                programCounter += 2; 
  
            }else if(instrucao.contains("sub AX,")){
                codigoIntermediario += "0x2d";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
            
            }else if(instrucao.contains("mul SI")){
                codigoIntermediario += "0xf6f7" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("mul AX")){
                codigoIntermediario += "0xf7f0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("cmp AX,DX")){
                codigoIntermediario += "0x3BC2" + "\n";
                programCounter += 2; 
                
            
            }else if(instrucao.contains("cmp AX,")){
                codigoIntermediario += "0x3d";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 

            }else if(instrucao.contains("and AX,DX")){
                codigoIntermediario += "0xf7C2" + "\n";
                programCounter += 2; 
  
            }else if(instrucao.contains("and AX,")){
                codigoIntermediario += "0x25";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
                
            }else if(instrucao.contains("not AX")){
                codigoIntermediario += "0xF8C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("xor AX,AX")){
                codigoIntermediario += "0x33C0" + "\n";
                programCounter += 2; 

            }else if(instrucao.matches("xor AX,DX")){
                codigoIntermediario += "0x33C2" + "\n";
                programCounter += 2; 
 
            }else if(instrucao.contains("xor AX,")){
                codigoIntermediario += "0x35";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 

            }else if(instrucao.matches("or AX,AX")){
                codigoIntermediario += "0x0BC0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("or AX,DX")){
                codigoIntermediario += "0x0BC2" + "\n";
                programCounter += 2; 

            }else if(instrucao.contains("or AX,")){
                codigoIntermediario += "0x0DC2";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 

            }else if(instrucao.contains("jmp")){
                codigoIntermediario += "0xEB";
                String opd = instrucao.split("jmp")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3;    
            
            }else if(instrucao.contains("jz")){
                codigoIntermediario += "0x74";
                String opd = instrucao.split("jz")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
                
            }else if(instrucao.contains("jnz")){
                codigoIntermediario += "0x75";
                String opd = instrucao.split("jnz")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
            
            }else if(instrucao.contains("jp")){
                codigoIntermediario += "0x7A";
                String opd = instrucao.split("jp")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
            
            }else if(instrucao.contains("call")){
                codigoIntermediario += "0xE8";
                String opd = instrucao.split("call")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
            
            }else if(instrucao.contains("ret")){
                codigoIntermediario += "0xEF" + "\n";
                programCounter += 1; 
            
            }else if(instrucao.contains("hlt")){
                codigoIntermediario += "0xEE" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.contains("pop AX")){
                codigoIntermediario += "0x58C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("pop DX")){
                codigoIntermediario += "0x58C2" + "\n";
                programCounter += 2; 
            
            }else if(instrucao.contains("popf")){
                codigoIntermediario += "0x9C" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.contains("pop ")){
                codigoIntermediario += "0x58";
                String opd = instrucao.split("pop")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
                
            }else if(instrucao.contains("push AX")){
                codigoIntermediario += "0x50C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("push DX")){
                codigoIntermediario += "0x50C2" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("pushf")){
                codigoIntermediario += "0x9C" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.contains("store AX")){
                codigoIntermediario += "0x07C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.contains("store DX")){
                codigoIntermediario += "0x07C2" + "\n";
                programCounter += 2; 
            
            }else if(instrucao.contains("read ")){
                codigoIntermediario += "0x12";
                String opd = instrucao.split("read ")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3;     
            
            }else if(instrucao.contains("write ")){
                codigoIntermediario += "0x08";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(simbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += simbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "00" + "\n";
                    simbolosUsados.put(opd, new TabelaDeUso(programCounter + 1));    
                }
                programCounter += 3; 
                
            }else if(instrucao.contains("DW")){ // Falta implementar, dataCounter soma-se ao tamanho do vetor
                String opd = instrucao.split("DW")[0];
                opd = opd.trim();
                String value = instrucao.split("DW ")[1];
                opd = opd.trim();
                simbolosLocais.put(opd, new TabelaDeSimbolosLocais(value, true, true));
                dataCounter += 2; 
            }
            else if(instrucao.contains("EQU")){  // Falta implementar, EQU pode receber expressões
                String opd = instrucao.split("EQU")[0];
                opd = opd.trim();
                String value = instrucao.split("EQU ")[1];
                opd = opd.trim();
                simbolosLocais.put(opd, new TabelaDeSimbolosLocais(value, false, true));
                
            }else if(instrucao.contains("PROC")){
                String label = instrucao.split("PROC")[0];
                label = label.trim();
                
                simbolosLocais.put(label, new TabelaDeSimbolosLocais(label, true, true));
                
            }
            else if(instrucao.contains("")){
                
            }
            else{
                System.out.println("Instrucao nao reconhecida = " + instrucao);
            }      

        //updateRegistrador(instrucoes.size(),"DS");    CASO SETAR DINAMICAMENTE OS SEGMENTOS   PILHA-INSTRUCOES-DADOS
        }
        writeFirstPassInFile(codigoIntermediario);
        print_simbolos(); // Printa na interface na tabela de símbolos
        System.out.println("Contadores values: PC = " + programCounter + " DC = "+ dataCounter);
        
    }
    
    public static void loadInstructionToSecondPass() throws IOException{ // Segunda passada (Falta passar endereço das variaveis)
        
        
        /*String finalCode = new String();
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);
            System.out.println("Instrução por instrução: " + instrucao);

            /*if(instrucao.contains("0x05")){
                finalCode += "0x05";
                String opd = instrucao.split("0x05")[0];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
                
                
            }else if(instrucao.contains("0x2d")){
                finalCode += "0x2d";
                String opd = instrucao.split("0x2d")[0];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
            
            }else if(instrucao.contains("0x3d")){
                finalCode += "0x3d";
                String opd = instrucao.split("0x3d")[0];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0x25")){
                finalCode += "0x25";
                String opd = instrucao.split("0x25")[1];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
                
            }else if(instrucao.contains("0x0DC2")){
                finalCode += "0x0DC2";
                String opd = instrucao.split("0x0DC2")[1];
                opd = opd.trim();
                System.out.println("Testando here" + opd);
                //System.out.println("Testando here" + simbolosLocais.get(opd).getValue());
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0x35")){
                finalCode += "0x35";
                String opd = instrucao.split("0x35")[1];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0xEB")){
                finalCode += "0xEB";
                finalCode += "66" + "\n";   
            
            }else if(instrucao.contains("0x74")){
                finalCode += "0x74";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x75")){
                finalCode += "0x75";
                String opd = instrucao.split("0x75")[1];
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x7A")){
                finalCode += "0x7A";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0xE8")){
                finalCode += "0xE8";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x58")){
                finalCode += "0x58";
                String opd = instrucao.split("0x58")[1];
                opd = opd.trim();
                finalCode += "66" + "\n";
                
            }else if(instrucao.contains("0x12")){
                finalCode += "0x12";
                String opd = instrucao.split("0x12")[1];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";  
            
            }else if(instrucao.contains("0x08")){
                finalCode += "0x08";
                String opd = instrucao.split("0x08")[1];
                opd = opd.trim();
                opd = simbolosLocais.get(opd).getValue();
                finalCode += opd + "\n"; 
 
            }
            else{  
                finalCode += instrucao + "\n";
            }
        }*/
        //writeSecondPassInFile(finalCode);
        
        
    }

    public static void writeFirstPassInFile(String string) throws IOException{ // Escreve primeira passada em um arquivo .txt e exibe na interface

        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/firstPass.txt")));
        fw.write(string);
        fw.close();
        
        String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listMemoryModel.addElement(vetor[i]);
        }
    }
    
    public static void writeSecondPassInFile(String string) throws IOException{ // Escreve segunda passada em um arquivo .txt e exibe na interface

        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/saida.txt")));
        fw.write(string);
        fw.close();
        
        String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listMemoryModel.addElement(vetor[i]);
        }
    }
            
}
