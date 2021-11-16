/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package montador;

import java.util.ArrayList;
import java.util.Arrays;

public class Emulador {
    public void print(String word){
        System.out.println(word);
    }

    public enum paramTypes {
        OPD,
        REG,
        NULL
    }

    public Memory memory = new Memory();
    public boolean finished = false;

    public ArrayList<Short> inputStream = new ArrayList<Short>();
    private int inputStreamIndex = 0;
    public String outputStream;
    public String error;
    public short AX = 0;
    public short DX = 0;

    public short SP = 0;

    public short SI = 0;
    public short IP = 0;
    public short SR = 0;

    public short DS = 3000;
    public short CS = 1000;
    public short SS = 0;
    
    public ArrayList<Integer> mapIPLineIndex = new ArrayList<Integer>();

    public void reset(){
        finished = false;
        IP = 0;
        SI = 0;
        AX = 0;
        DX = 0;
        SR = 0;
        this.memory = new Memory();
        this.error = null;
        this.outputStream="";
    }
    
    public void run(){
        while(!this.finished){
            // String instruction = this.instructions[this.IP];
            // if(inputStreamIndex>=inputStream.size() && instruction.matches("read.*")) break;
            this.step();
        }
    }

    public paramTypes paramType(String param){
        if(param == null)
            return paramTypes.NULL;
        if(param.matches("AX|DX|SP|SI|IP|SR"))
            return paramTypes.REG;
        return paramTypes.OPD;
    }

    public boolean checkParams(String[] params, paramTypes type1, paramTypes type2){
        return paramType(params[0]) == type1 && paramType(params[1])==type2;
    }
    public boolean checkParams(String[] params, String type1, paramTypes type2){
        return params[0].matches(type1) && paramType(params[1])==type2;
    }
    public boolean checkParams(String[] params, String type1, String type2){
        return params[0].matches(type1) && params[1].matches(type2);
    }

    public void step(){
        int div;
        int mul;
        short opd = 0;
        if(finished) return;
        
        short instruction = memory.getPalavra(CS+IP++);
        switch((int)instruction){
            case 0x03c0:// add ax
                AX += AX;
            break;
            case 0x03c2:// add dx
                AX += DX;
            break;
            case 0x05: // add opd
                opd = memory.getPalavra(CS+IP++);
                AX += opd;
            break;
            case 0xf7f6:// div si
                div = AX / SI;
                AX = (short)(div & 256);
                DX = (short)(div >>> 8);
            break;
            case 0xf7c0:// div ax
                div = AX / AX;
                AX = (short)(div & 256);
                DX = (short)(div >>> 8);
            break;
            case 0x2bc0:// sub ax
                AX -= AX;
            break;
            case 0x2bc2:// sub dx
                AX -= DX;
            break;
            case 0x25:// sub opd
                memory.getPalavra(CS+IP++);
                AX -= opd;
            break;
            // case 0xf7f6:// mul si
                //todo
            // break;
            case 0xf7f0:// mul AX
                mul = AX * AX;
                AX = (short)(mul & 256);
                DX = (short)(mul >>> 8);
            break;
            case 0x3d:// cmp opd
                opd = memory.getPalavra(CS+IP++);
                setFlag("zf", AX == opd);
            break;
            case 0x3bc2://cmp DX
                setFlag("zf", AX == DX);
            break;
            case 0x23c0:// and AX
                setFlag("zf", AX == AX);
            break;
            case 0x23c2:// and DX
                AX &= AX;
            break;
            // case 0x25:// and opd
                //todo
                // CS+IP++;
            // break;
            case 0xf8c0:// not ax
                AX = (short)~AX;
            break;
            case 0x0bc0:// or ax
                AX|=AX;
            break;
            case 0x0bc2:// or dx
                AX|=DX;
            break;
            case 0x0d:// or opd
                opd = memory.getPalavra(CS+IP++);
                AX|=opd;
            break;
            case 0x33c0:// xor ax
                AX|=AX;
            break;
            case 0x33c2:// xor dx
                AX|=DX;
            break;
            case 0x35:// xor opd
                opd = memory.getPalavra(CS+IP++);
                AX^=opd;
            break;
            case 0xeb:// jmp
                opd = memory.getPalavra(CS+IP++);
                IP = opd;
            break;
            case 0x74:// jz
                opd = memory.getPalavra(CS+IP++);
                if(getFlag("zf")) IP = opd;
            break;
            case 0x75:// jnz
                opd = memory.getPalavra(CS+IP++);
                if(!getFlag("zf")) IP = opd;
            break;
            case 0x7a:// jp
                opd = memory.getPalavra(CS+IP++);
                if(!getFlag("SF")) IP = opd;
            break;
            case 0xe8:// call
                opd = memory.getPalavra(CS+IP++);
                memory.setPalavra(IP, SI++);
                IP = opd;
            break;
            case 0xef:// ret
                IP = memory.getPalavra(--SI);
            break;
            case 0x58c0:// pop ax
                AX = memory.getPalavra(--SI);
            break;
            case 0x58c2:// pop dx
                DX = memory.getPalavra(--SI);
            break;
            case 0x59:// pop opd
                opd = memory.getPalavra(IP++);
                memory.setPalavra(memory.getPalavra(--SI), DS+opd);
            break;
            case 0x9d:// popf
                SR = memory.getPalavra(--SI);
            break;
            case 0x50c0:// push ax
                memory.setPalavra(AX, DS+opd);
            break;
            case 0x50c2:// push dx
                memory.setPalavra(DX, DS+opd);
            break;
            case 0x9c://pushf
                memory.setPalavra(SR, SI++);
            break;
            case 0x07c0:// store ax
                //todo
            break;
            case 0x07c2:// store dx
                //todo
            break;
            case 0x12:// read opd
                opd = memory.getPalavra(IP++);
                outputStream = Util.convertIntegerToBinary(opd);
                if(inputStream.size()>inputStreamIndex){
                    memory.setPalavra(inputStream.get(inputStreamIndex++).shortValue(), opd);
                }else{
                    IP-=2;
                }
            break;
            case 0x08:// write opd
                opd = memory.getPalavra(CS+IP++);
                outputStream = Util.convertIntegerToBinary(opd);
            break;
            case 0xEE: // hlt
                this.finished = true;
            break;
        }
    }
    
    private short getRegister(String reg){
        switch(reg){
            case "AX":
            return AX;
            case "DX":
            return DX;
            case "SP":
            return SP;
            case "SI":
            return SI;
            case "IP":
            return IP;
            case "SR":
            return SR;
            default: 
            return 0;
        }
    }
    private short setRegister(String reg, short value){
        switch(reg){
            case "AX":
            return AX = value;
            case "DX":
            return DX = value;
            case "SP":
            return SP = value;
            case "SI":
            return SI = value;
            case "IP":
            return IP = value;
            case "SR":
            return SR = value;
            default: 
            return 0;
        }
    }
    
    public boolean getFlag(String flag){
        switch(flag){
            case "of":
                return Util.getBit(this.SR,12 );
            case "sf":
                return Util.getBit(this.SR,9 );
            case "zf":
                return Util.getBit(this.SR,8 );
            case "if":
                return Util.getBit(this.SR,7 );
            case "pf":
                return Util.getBit(this.SR,6 );
            case "cf":
                return Util.getBit(this.SR,0 );
            default:
                return false;
        }
    }

    public void setFlag(String flag, boolean set){
        switch(flag){
            case "of":
                 SR = Util.modifyBit(SR, 12, set);
                 break;
            case "sf":
                 SR = Util.modifyBit(SR, 9, set);
                 break;
            case "zf":
                 SR = Util.modifyBit(SR, 8, set);
                 break;
            case "if":
                 SR = Util.modifyBit(SR, 7, set);
                 break;
            case "pf":
                 SR = Util.modifyBit(SR, 6, set);
                 break;
            case "cf":
                 SR = Util.modifyBit(SR, 0, set);
                 break;
        }
    }

    short calculateOpd (String opd){
        if(opd.matches("[0-1]+b")){
            return Short.parseShort(opd.replace("b",""),2);
        }if(opd.matches("0x[0-9a-fA-F]+")){
            return Short.parseShort(opd.replace("0x",""),16);
        }if(opd.matches("[0-9]+")){
            return Short.parseShort(opd);
        }if(opd.matches("[A-Za-z][A-Za-z0-9]*")){
            // TODO retorna valor da variavel
        }
        // Integrar isso aqui, n entendi como funciona
        // varTable.checkVariable(params[0], this.IP);
        // varTable.checkVariable(params[1], this.IP);  
        return 1;
    }

    public void input (String input){
        this.inputStream.add(Short.parseShort(input));
        this.outputStream = Util.convertIntegerToBinary(Short.parseShort(input));
        Short instruction = memory.getPalavra(CS+IP);
        if(instruction==0x12) step();
    }
}
