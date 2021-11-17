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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;



public class Montador {
    
    public  LinkedHashMap<String, SimbolosGlobais> tabelaDeSimbolosGlobais = new LinkedHashMap<String, SimbolosGlobais>();    //Linked NÃO perde a ordem que foi acrescentado    
    public  LinkedHashMap<String, SimbolosLocais> tabelaDeSimbolosLocais = new LinkedHashMap<String, SimbolosLocais>();
    public  List<SimbolosUsados> tabelaDeSimbolosUsados = new ArrayList<SimbolosUsados>();
    public List<String> instrucoes = new ArrayList<String>();
    public List<Integer> data = new ArrayList<Integer>();
    private Memory memory = new Memory();
    private int programCounter = 0; // Marca a posição da instrução corrente
    private int dataCounter = 0; // Marca tamanho do segmento de dados, cada variavel e const 2 bytes
    
    
    public Montador(){
        
    }
    
    public  void primeira_passagem() throws IOException {
        
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToFirstPass();
        //simbolos.put("valor", new Simbolo("valor","type", true));
    }

    public  void segunda_passagem() throws IOException {
        
        instrucoes = Arrays.asList(Tela2.ArquivoCarregado.split("\n"));
        loadInstructionToSecondPass();
        //simbolos.put("nome", new Simbolo("valor","type", true));
    }
    
    public  String getSymbolValue(String opd) { 
        
        String value;
        value = tabelaDeSimbolosLocais.get(opd).getValue();
            
        return value;
    }
    
    public  void loadInstructionToFirstPass() throws IOException{  // Primeira passada (Falta resolver código dos simbolos)
        
        System.out.println("loadIstruction ");
        String codigoIntermediario = new String();
        int controlSymbolTable = 0;
        
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);
            if(instrucao.contains(":")){
                String label = instrucao.split(":")[0];
                instrucao = instrucao.split(":")[1];
                instrucao = instrucao.trim();
                tabelaDeSimbolosLocais.put(label, new SimbolosLocais(Integer.toString(programCounter), true, true));
            }
            
            if(instrucao.matches(".*add AX,AX")){
               
                codigoIntermediario += "0x03C0" + "\n";
                //String label = instrucao.split("add AX,AX")[0];
                /*if(label.contains("")){
                    
                }else if(label.contains(":")){
                    label = label.split(":")[0];
                    tabelaDeSimbolosLocais.put(label, new SimbolosLocais(Integer.toString(programCounter), true, true));
                }
                else{
                    System.out.println("Erro, label deve possuir ':' depois da declaracao");
                }*/
                programCounter += 2; 
            }
            
            else if(instrucao.matches(".*add AX,DX")){
                codigoIntermediario += "0x03C2" + "\n";
                programCounter += 2; 
                //updateMemoria(0x03C2, controle_mem++);
   
            }else if(instrucao.matches("add AX,.*")){
                codigoIntermediario += "0x05";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                System.out.println("Testando var 1. Nome: " + opd + " PC: " + programCounter);
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));    
                }
                
                programCounter = programCounter + 3;  
                
            }else if(instrucao.matches("div SI")){
                codigoIntermediario += "0xf7f6" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("div AX")){
                codigoIntermediario += "0xf7c0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("sub AX,AX")){
                codigoIntermediario += "0x2bc0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("sub AX,DX")){
                codigoIntermediario += "0x2bc2" + "\n";
                programCounter += 2; 
  
            }else if(instrucao.matches("sub AX,.*")){
                codigoIntermediario += "0x2d";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter = programCounter + 3; 
            
            }else if(instrucao.matches("mul SI")){
                codigoIntermediario += "0xf6f7" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("mul AX")){
                codigoIntermediario += "0xf7f0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("cmp AX,DX")){
                codigoIntermediario += "0x3BC2" + "\n";
                programCounter += 2; 
                
            
            }else if(instrucao.matches("cmp AX,.*")){
                codigoIntermediario += "0x3d";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter = programCounter + 3;  

            }else if(instrucao.matches("and AX,DX")){
                codigoIntermediario += "0xf7C2" + "\n";
                programCounter += 2; 
  
            }else if(instrucao.matches("and AX,")){
                codigoIntermediario += "0x25";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));    
                }
                programCounter = programCounter + 3; 
                
            }else if(instrucao.matches("not AX")){
                codigoIntermediario += "0xF8C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("xor AX,AX")){
                codigoIntermediario += "0x33C0" + "\n";
                programCounter += 2; 

            }else if(instrucao.matches("xor AX,DX")){
                codigoIntermediario += "0x33C2" + "\n";
                programCounter += 2; 
 
            }else if(instrucao.matches(".*xor AX,.*")){
                codigoIntermediario += "0x35";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                
                //String label = instrucao.split("AX,")[0];
                /*if(label.contains("")){
                    
                }else if(label.contains(":")){
                    label = label.split(":")[0];
                    tabelaDeSimbolosLocais.put(label, new SimbolosLocais(Integer.toString(programCounter), true, true));
                }
                else{
                    System.out.println("Erro, label deve possuir ':' depois da declaracao");
                }*/
                
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter = programCounter + 3; 

            }else if(instrucao.matches("or AX,AX")){
                codigoIntermediario += "0x0BC0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("or AX,DX")){
                codigoIntermediario += "0x0BC2" + "\n";
                programCounter += 2; 

            }else if(instrucao.matches("or AX,.*")){
                codigoIntermediario += "0x0DC2";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter = programCounter + 3;  

            }else if(instrucao.matches("jmp")){
                codigoIntermediario += "0xEB";
                String opd = instrucao.split("jmp")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3;    
            
            }else if(instrucao.matches("jz")){
                codigoIntermediario += "0x74";
                String opd = instrucao.split("jz")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));    
                }
                programCounter += 3; 
                
            }else if(instrucao.matches("jnz")){
                codigoIntermediario += "0x75";
                String opd = instrucao.split("jnz")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3; 
            
            }else if(instrucao.matches("jp")){
                codigoIntermediario += "0x7A";
                String opd = instrucao.split("jp")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3; 
            
            }else if(instrucao.matches("call")){
                codigoIntermediario += "0xE8";
                String opd = instrucao.split("call")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));    
                }
                programCounter += 3; 
            
            }else if(instrucao.matches("ret")){
                codigoIntermediario += "0xEF" + "\n";
                programCounter += 1; 
            
            }else if(instrucao.matches("hlt")){
                codigoIntermediario += "0xEE" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.matches("pop AX")){
                codigoIntermediario += "0x58C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("pop DX")){
                codigoIntermediario += "0x58C2" + "\n";
                programCounter += 2; 
            
            }else if(instrucao.matches("popf")){
                codigoIntermediario += "0x9C" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.matches("pop ")){
                codigoIntermediario += "0x58";
                String opd = instrucao.split("pop")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3; 
                
            }else if(instrucao.matches("push AX")){
                codigoIntermediario += "0x50C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("push DX")){
                codigoIntermediario += "0x50C2" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("pushf")){
                codigoIntermediario += "0x9C" + "\n";
                programCounter += 1; 
                
            }else if(instrucao.matches("store AX")){
                codigoIntermediario += "0x07C0" + "\n";
                programCounter += 2; 
                
            }else if(instrucao.matches("store DX")){
                codigoIntermediario += "0x07C2" + "\n";
                programCounter += 2; 
            
            }else if(instrucao.matches("read .*")){
                codigoIntermediario += "0x12";
                String opd = instrucao.split("read ")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3;     
            
            }else if(instrucao.matches("move AX,DX")){
                codigoIntermediario += "0x8BC2" + "\n";
                programCounter += 3; 
            
            }else if(instrucao.matches("move DX,AX")){
                codigoIntermediario += "0x8BD0" + "\n";
                programCounter += 3; 
            
            }else if(instrucao.matches("move AX,.*")){
                codigoIntermediario += "0xA1";
                String opd = instrucao.split("read ")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3;     
            
            }else if(instrucao.matches("move .*,AX")){
                codigoIntermediario += "0xA3";
                String opd = instrucao.split("AX,")[1];
                opd = opd.trim();
                if(!(tabelaDeSimbolosLocais.get(opd).isRelocable())){  // Verifica se é uma constante
                    codigoIntermediario += tabelaDeSimbolosLocais.get(opd).getValue() + "\n";
                }else{
                    codigoIntermediario += "0000" + "\n";
                    
                    tabelaDeSimbolosUsados.add(new SimbolosUsados(opd, programCounter + 1));     
                }
                programCounter += 3; 
                
            }else if(instrucao.matches(".*DW.*")){ // Falta implementar, dataCounter soma-se ao tamanho do vetor
                String opd = instrucao.split("DW")[0];
                opd = opd.trim();
                String value = instrucao.split("DW ")[1];
                opd = opd.trim();
                tabelaDeSimbolosLocais.put(opd, new SimbolosLocais(value, true, true)); // Valor, posição, recolavel, definicao
                data.add((Integer.decode(value))); // preparar dw para receber qualquer base numerica
                dataCounter += 1; 
            }
            else if(instrucao.matches(".*EQU.*")){  // Falta implementar, EQU pode receber expressões
                String opd = instrucao.split("EQU")[0];
                opd = opd.trim();
                String value = instrucao.split("EQU ")[1];
                opd = opd.trim();
                tabelaDeSimbolosLocais.put(opd, new SimbolosLocais(value, false, true));
                
            }else if(instrucao.matches(".*PROC.*")){
                String label = instrucao.split("PROC")[0];
                label = label.trim();
                
                tabelaDeSimbolosLocais.put(label, new SimbolosLocais(label, true, true));
                
            }
            else if(instrucao.matches("")){
                
            }
            else{

                System.out.println("Instrucao nao reconhecida = " + instrucao);
            }      

        //updateRegistrador(instrucoes.size(),"DS");    CASO SETAR DINAMICAMENTE OS SEGMENTOS   PILHA-INSTRUCOES-DADOS
        }
        writeFirstPassInFile(codigoIntermediario);
        print_simbolosLocais(); // Printa na interface na tabela de símbolos
        System.out.println("Contadores values: PC = " + programCounter + " DC = "+ dataCounter);
        print_simbolosUsados();
        print_data();
        
    }
    
    public  void loadInstructionToSecondPass() throws IOException{ // Segunda passada (Falta passar endereço das variaveis)
        
        String finalCode = new String();
        List<String> codeAux = new ArrayList<String>();
        
        for (int i = 0; i< instrucoes.size(); i++){
            String instrucao = instrucoes.get(i);
            instrucao = instrucao.split("0x")[1];
            
            int stringLength = instrucao.length();
            
            if(stringLength == 2){
                codeAux.add(instrucao.substring(0, 2));
            }
            else if(stringLength == 4){
                codeAux.add(instrucao.substring(0, 2));
                codeAux.add(instrucao.substring(2, 4));
            }
            else if(stringLength == 6){
                codeAux.add(instrucao.substring(0, 2));
                codeAux.add(instrucao.substring(2, 4));
                codeAux.add(instrucao.substring(4, 6));
            }
            else if(stringLength == 8){
                codeAux.add(instrucao.substring(0, 2));
                codeAux.add(instrucao.substring(2, 4));
                codeAux.add(instrucao.substring(4, 6));
                codeAux.add(instrucao.substring(6, 8));
            }else{
                System.out.println("Tamanho não suportado: " + instrucao);
            }  
        }
        
        try{
            int i = 1000;
            for(String list: codeAux){ // Carrega intruções na memoria
            list = "0x"+list;
            int inst = Integer.decode(list);
            memory.setPalavra(inst, i);
            i++;
            }
            i = 3000;
            for(Integer datas: data){ // Carrega dados na memoria
            
            memory.setPalavra(datas, i);
            i++;
            }
            
            for(Iterator list = codeAux.iterator(); list.hasNext(); i++){ // Coloca 2 bytes em cada linha
            
            finalCode += list.next();
            finalCode += list.next() + "\n";
            }
        }catch(NoSuchElementException e){
                System.out.println("Error: " + e.getMessage());
         }
        print_memory();
        
        for(SimbolosUsados usados : tabelaDeSimbolosUsados){ // Carrega dados na memoria
            
            int position = usados.getOcorrencia() + programCounter;
            String label = usados.getName();
            String value = tabelaDeSimbolosLocais.get(label).getValue();
            if(value.contains("0x")){
                memory.setPalavra(Integer.decode(value), position);
            }else{
               memory.setPalavra(Integer.parseInt(value), position); 
            }
            
            
         }
        
        /*String finalCode = new String();
        for (int i = 0; i< instrucoes.size(); i++){
            
            String instrucao = instrucoes.get(i);
            System.out.println("Instrução por instrução: " + instrucao);

            /*if(instrucao.matches("0x05")){
                finalCode += "0x05";
                String opd = instrucao.split("0x05")[0];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
                
                
            }else if(instrucao.matches("0x2d")){
                finalCode += "0x2d";
                String opd = instrucao.split("0x2d")[0];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
            
            }else if(instrucao.matches("0x3d")){
                finalCode += "0x3d";
                String opd = instrucao.split("0x3d")[0];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.matches("0x25")){
                finalCode += "0x25";
                String opd = instrucao.split("0x25")[1];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";
                
            }else if(instrucao.matches("0x0DC2")){
                finalCode += "0x0DC2";
                String opd = instrucao.split("0x0DC2")[1];
                opd = opd.trim();
                System.out.println("Testando here" + opd);
                //System.out.println("Testando here" + tabelaDeSimbolosLocais.get(opd).getValue());
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.matches("0x35")){
                finalCode += "0x35";
                String opd = instrucao.split("0x35")[1];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";

            }else if(instrucao.matches("0xEB")){
                finalCode += "0xEB";
                finalCode += "66" + "\n";   
            
            }else if(instrucao.matches("0x74")){
                finalCode += "0x74";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.matches("0x75")){
                finalCode += "0x75";
                String opd = instrucao.split("0x75")[1];
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.matches("0x7A")){
                finalCode += "0x7A";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.matches("0xE8")){
                finalCode += "0xE8";
                finalCode += "66" + "\n"; 
            
            }else if(instrucao.matches("0x58")){
                finalCode += "0x58";
                String opd = instrucao.split("0x58")[1];
                opd = opd.trim();
                finalCode += "66" + "\n";
                
            }else if(instrucao.matches("0x12")){
                finalCode += "0x12";
                String opd = instrucao.split("0x12")[1];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n";  
            
            }else if(instrucao.matches("0x08")){
                finalCode += "0x08";
                String opd = instrucao.split("0x08")[1];
                opd = opd.trim();
                opd = tabelaDeSimbolosLocais.get(opd).getValue();
                finalCode += opd + "\n"; 
 
            }
            else{  
                finalCode += instrucao + "\n";
            }
        }*/
        writeSecondPassInFile(finalCode);
        
        
    }

    public  void writeFirstPassInFile(String string) throws IOException{ // Escreve primeira passada em um arquivo .txt e exibe na interface

        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/firstPass.txt")));
        fw.write(string);
        fw.close();
        
        /*String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listMemoryModel.addElement(vetor[i]);
        }*/
    }
    
    public  void writeSecondPassInFile(String string) throws IOException{ // Escreve segunda passada em um arquivo .txt e exibe na interface

        FileWriter fw = new FileWriter(new File(new String(System.getProperty("user.dir")+"/src/main/java/montador/saida.txt")));
        fw.write(string);
        fw.close();
        
        String[] vetor = string.split("\n");
        for (int i = 0; i < vetor.length; i++){     // Para adiciona texto intermediario na interface
            Tela2.listMemoryModel.addElement(vetor[i]);
        }
    }
    
    public  void print_simbolosLocais() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        for (String keys : tabelaDeSimbolosLocais.keySet()){
 
            System.out.println(keys + tabelaDeSimbolosLocais.get(keys).getValue());
            Tela2.symbolTableModel.addElement(keys + " | " + tabelaDeSimbolosLocais.get(keys).getValue() +  " | " +  tabelaDeSimbolosLocais.get(keys).isRelocable() +  " | "  + tabelaDeSimbolosLocais.get(keys).isDefinited());

        }               
    }
    
    public  void print_simbolosUsados() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        System.out.println("Tabela de simbolos usados: \n" + "Tamanho da Tabela: " + tabelaDeSimbolosUsados.size());
        for (SimbolosUsados simbolos : tabelaDeSimbolosUsados){
 
            System.out.println(simbolos.getName() + " = " + simbolos.getOcorrencia());
            //Tela2.symbolTableModel.addElement(keys + " | " + tabelaDeSimbolosLocais.get(keys).getValue() +  " | " +  tabelaDeSimbolosLocais.get(keys).isRelocable() +  " | "  + tabelaDeSimbolosLocais.get(keys).isDefinited());

        }               
    }
    
    public  void print_data() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        System.out.println("Tabela de simbolos usados: \n" + "Tamanho da Tabela: " + tabelaDeSimbolosUsados.size());
        System.out.println("Vetor de dados: ");
        for (Integer datas : data){
 
            System.out.println(datas);
            //Tela2.symbolTableModel.addElement(keys + " | " + tabelaDeSimbolosLocais.get(keys).getValue() +  " | " +  tabelaDeSimbolosLocais.get(keys).isRelocable() +  " | "  + tabelaDeSimbolosLocais.get(keys).isDefinited());

        }               
    }
    public  void print_memory() {  // Printa simbolos (variaveis e constantes) na tabela de simbolos com seus valores
     
        System.out.println("Memory here");
        for (int i = 1000; i < (1000 + programCounter); i++){
            
            System.out.println(memory.getPalavra(i));
            //Tela2.symbolTableModel.addElement(keys + " | " + tabelaDeSimbolosLocais.get(keys).getValue() +  " | " +  tabelaDeSimbolosLocais.get(keys).isRelocable() +  " | "  + tabelaDeSimbolosLocais.get(keys).isDefinited());

        }               
    }
    
    
            
}
