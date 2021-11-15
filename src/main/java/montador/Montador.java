/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package montador;

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
    public static LinkedHashMap<String, Simbolo> simbolos = new LinkedHashMap<String, Simbolo>();    //Linked NÃO perde a ordem que foi acrescentado    
    static public List<String> instrucoes = new ArrayList<String>();
    
    
    public static void primeira_passagem() throws IOException {
        
        System.out.println("primeira_passagem ");
        System.out.println("hauhauahua ");
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToFirstPass();
        //simbolos.put("valor", new Simbolo("valor","type", true));
    }

    public static void segunda_passagem() throws IOException {
        
        System.out.println("segunda_passagem ");
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToSecondPass();
        //simbolos.put("nome", new Simbolo("valor","type", true));
    }
    
    
    public static void print_simbolos() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        for (String keys : simbolos.keySet()){
 
            System.out.println(keys + "simbolos.get(key).getValue()");
            Tela2.symbolTableModel.addElement(keys + " = " + simbolos.get(keys).getValue());

        }               
    }
    
    public static String getSymbolValue(String opd) { 
        
        String value;
        value = simbolos.get(opd).getValue();
            
        return value;
    }
    
    public static void loadInstructionToFirstPass() throws IOException{  // Primeira passada (Falta resolver código dos simbolos)
        
        System.out.println("loadIstruction ");
        String codigoIntermediario = new String();
        int controlSymbolTable = 0;
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);
            if(instrucao.contains("add AX,AX")){
               
                codigoIntermediario += "0x03C0" + "\n"; 
            }
            
            else if(instrucao.contains("add AX,DX")){
                codigoIntermediario += "0x03C2" + "\n";
                //updateMemoria(0x03C2, controle_mem++);
   
            }else if(instrucao.matches("add AX,")){
                codigoIntermediario += "0x05";
                
                //updateMemoria(0x05, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                
                //updateMemoria(calculateOpd(opd),controle_mem++);
                
            }else if(instrucao.contains("div SI")){
                codigoIntermediario += "0xf7f6" + "\n";
                //updateMemoria(0xf7f6, controle_mem++);
                
            }else if(instrucao.contains("div AX")){
                codigoIntermediario += "0xf7c0" + "\n";
                //updateMemoria(0xf7c0, controle_mem++);
                
            }else if(instrucao.contains("sub AX,AX")){
                codigoIntermediario += "0x2bc0" + "\n";
                //updateMemoria(0x2bc0, controle_mem++);
                
            }else if(instrucao.contains("sub AX,DX")){
                codigoIntermediario += "0x2bc2" + "\n";
  
            }else if(instrucao.contains("sub AX,")){
                codigoIntermediario += "0x2d";
                //updateMemoria(0x2d, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
                
            
            }else if(instrucao.contains("mul SI")){
                codigoIntermediario += "0xf6f7" + "\n";
                //updateMemoria(0xf6f7, controle_mem++);
                
            }else if(instrucao.contains("mul AX")){
                codigoIntermediario += "0xf7f0" + "\n";
                //updateMemoria(0xf7f0, controle_mem++);
                
            }else if(instrucao.contains("cmp AX,DX")){
                codigoIntermediario += "0x3BC2" + "\n";
                //updateMemoria(0x3BC2, controle_mem++);
                
            
            }else if(instrucao.contains("cmp AX,")){
                codigoIntermediario += "0x3d";
                //updateMemoria(0x3d, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);

            }else if(instrucao.contains("and AX,DX")){
                codigoIntermediario += "0xf7C2" + "\n";
                //updateMemoria(0xf7C2, controle_mem++);
  
            }else if(instrucao.contains("and AX,")){
                codigoIntermediario += "0x25";
                //updateMemoria(0x25, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
                
            }else if(instrucao.contains("not AX")){
                codigoIntermediario += "0xF8C0" + "\n";
                //updateMemoria(0xF8C0, controle_mem++);
                
            }else if(instrucao.matches("xor AX,AX")){
                codigoIntermediario += "0x33C0" + "\n";
                //updateMemoria(0x33C0, controle_mem++);

            }else if(instrucao.matches("xor AX,DX")){
                codigoIntermediario += "0x33C2" + "\n";
                //updateMemoria(0x33C2, controle_mem++);
 
            
            }else if(instrucao.contains("xor AX,")){
                codigoIntermediario += "0x35";
                //updateMemoria(0x35, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);

            }else if(instrucao.matches("or AX,AX")){
                codigoIntermediario += "0x0BC0" + "\n";
                //updateMemoria(0x0BC0, controle_mem++);
                
            }else if(instrucao.matches("or AX,DX")){
                codigoIntermediario += "0x0BC2" + "\n";
                //updateMemoria(0x0BC2, controle_mem++);

            }else if(instrucao.contains("or AX,")){
                codigoIntermediario += "0x0DC2";
                //updateMemoria(0x0D, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);

            }else if(instrucao.contains("jmp")){
                codigoIntermediario += "0xEB";
                //updateMemoria(0xEB, controle_mem++);
                String opd = instrucao.split("jmp")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);    
            
            }else if(instrucao.contains("jz")){
                codigoIntermediario += "0x74";
                //updateMemoria(0x74, controle_mem++);
                String opd = instrucao.split("jz")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
                
            
            }else if(instrucao.contains("jnz")){
                codigoIntermediario += "0x75";
                //updateMemoria(0x75, controle_mem++);
                String opd = instrucao.split("jnz")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
            
            }else if(instrucao.contains("jp")){
                codigoIntermediario += "0x7A";
                //updateMemoria(0x7A, controle_mem++);
                String opd = instrucao.split("jp")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
            
            }else if(instrucao.contains("call")){
                codigoIntermediario += "0xE8";
                //updateMemoria(0xE8, controle_mem++);
                String opd = instrucao.split("call")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
            
            }else if(instrucao.contains("ret")){
                codigoIntermediario += "0xEF" + "\n";
                //updateMemoria(0xEF, controle_mem++);  
            
            }else if(instrucao.contains("hlt")){
                codigoIntermediario += "0xEE" + "\n";
                //updateMemoria(0xEE, controle_mem++);
                
            }else if(instrucao.contains("pop AX")){
                codigoIntermediario += "0x58C0" + "\n";
                //updateMemoria(0x58C0, controle_mem++);
                
            }else if(instrucao.contains("pop DX")){
                codigoIntermediario += "0x58C2" + "\n";
                //updateMemoria(0x58C2, controle_mem++);
            
            }else if(instrucao.contains("popf")){
                codigoIntermediario += "0x9C" + "\n";
                //updateMemoria(0x9C, controle_mem++);
                
            }else if(instrucao.contains("pop ")){
                codigoIntermediario += "0x58";
                //updateMemoria(0x58, controle_mem++);
                String opd = instrucao.split("pop")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);
                
            }else if(instrucao.contains("push AX")){
                codigoIntermediario += "0x50C0" + "\n";
                //updateMemoria(0x50C0, controle_mem++);
                
            }else if(instrucao.contains("push DX")){
                codigoIntermediario += "0x50C2" + "\n";
                //updateMemoria(0x50C2, controle_mem++);
                
            }else if(instrucao.contains("pushf")){
                codigoIntermediario += "0x9C" + "\n";
                //updateMemoria(0x9C, controle_mem++);
                
            }else if(instrucao.contains("store AX")){
                codigoIntermediario += "0x07C0" + "\n";
                //updateMemoria(0x07C0, controle_mem++);
                
            }else if(instrucao.contains("store DX")){
                codigoIntermediario += "0x07C2" + "\n";
                //updateMemoria(0x07C2, controle_mem++); 
            
            }else if(instrucao.contains("read ")){
                codigoIntermediario += "0x12";
                //updateMemoria(0x12, controle_mem++);
                String opd = instrucao.split("read ")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                //updateMemoria(calculateOpd(opd),controle_mem++);     
            
            }else if(instrucao.contains("write ")){
                codigoIntermediario += "0x08";
                //updateMemoria(0x08, controle_mem++);
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                codigoIntermediario += opd + "\n";
                
                //updateMemoria(calculateOpd(opd),controle_mem++);
                //controle_mem++;
            }else if(instrucao.contains("DW")){
                String opd = instrucao.split("DW")[0];
                opd = opd.trim();
                String value = instrucao.split("DW ")[1];
                opd = opd.trim();
                simbolos.put(opd, new Simbolo(value,"DW", true));
            }
            else if(instrucao.contains("EQU")){
                String opd = instrucao.split("EQU")[0];
                opd = opd.trim();
                String value = instrucao.split("EQU ")[1];
                opd = opd.trim();
                simbolos.put(opd, new Simbolo(value,"EQU", true));
                
            }else if(instrucao.contains("")){
                
            }
            else{
                System.out.println("Instrucao nao reconhecida = " + instrucao);
            }      

        //updateRegistrador(instrucoes.size(),"DS");    CASO SETAR DINAMICAMENTE OS SEGMENTOS   PILHA-INSTRUCOES-DADOS
        }
        writeFirstPassInFile(codigoIntermediario);
        print_simbolos(); // Printa na interface na tabela de símbolos
    }
    
    public static void loadInstructionToSecondPass() throws IOException{ // Segunda passada (Falta passar endereço das variaveis)
        
        System.out.println("loadFinalIstruction ");
        String finalCode = new String();
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);

            if(instrucao.contains("0x05")){
                finalCode += "0x05";
                String opd = instrucao.split("0x05")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";
                
                
            }else if(instrucao.contains("0x2d")){
                finalCode += "0x2d";
                String opd = instrucao.split("0x2d")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";
            
            }else if(instrucao.contains("0x3d")){
                finalCode += "0x3d";
                String opd = instrucao.split("0x3d")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0x25")){
                finalCode += "0x25";
                String opd = instrucao.split("0x25")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";
                
            }else if(instrucao.contains("0x0DC2")){
                finalCode += "0x0DC2";
                String opd = instrucao.split("0x0DC2")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0x35")){
                finalCode += "0x35";
                String opd = instrucao.split("0x35")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.contains("0xEB")){
                finalCode += "0xEB";
                //String opd = instrucao.split("0xEB")[1];
                //opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n";   
            
            }else if(instrucao.contains("0x74")){
                finalCode += "0x74";
                //String opd = instrucao.split("0x74")[1];
                //opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x75")){
                finalCode += "0x75";
                String opd = instrucao.split("0x75")[1];
                //opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x7A")){
                finalCode += "0x7A";
                //String opd = instrucao.split("0x7A")[1];
                //opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0xE8")){
                finalCode += "0xE8";
                //String opd = instrucao.split("0xE8")[1];
                //opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.contains("0x58")){
                finalCode += "0x58";
                String opd = instrucao.split("0x58")[1];
                opd = opd.trim();
                //opd = simbolos.get(opd).getValue();
                finalCode += "66" + "\n";
                
            }else if(instrucao.contains("0x12")){
                finalCode += "0x12";
                String opd = instrucao.split("0x12")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n";  
            
            }else if(instrucao.contains("0x08")){
                finalCode += "0x08";
                String opd = instrucao.split("0x08")[1];
                opd = opd.trim();
                opd = simbolos.get(opd).getValue();
                finalCode += opd + "\n"; 
 
            }
            else{  
                finalCode += instrucao + "\n";
            }
        }
        writeSecondPassInFile(finalCode);
        
        
    }

    public static void writeFirstPassInFile(String string) throws IOException{ // Escreve primeira passada em um arquivo .txt e exibe na interface

        System.out.println("writeFirstPassInFile ");
        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/firstPass.txt")));
        fw.write(string);
        fw.close();
        
        String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listIntermediarioModel.addElement(vetor[i]);
        }
    }
    
    public static void writeSecondPassInFile(String string) throws IOException{ // Escreve segunda passada em um arquivo .txt e exibe na interface

        System.out.println("writeFirstPassInFile ");
        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/saida.txt")));
        fw.write(string);
        fw.close();
        
        String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listSaidaModel.addElement(vetor[i]);
        }
    }
            
}
