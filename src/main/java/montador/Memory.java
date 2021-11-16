package montador;

public class Memory{
    private short[] palavras = new short[8192];

    public Memory(){
        for(int i = 0; i < 4096; i++){
            this.palavras[i] = (short)0;
        }  
    }
    public void setPalavra(int position, short data){
        this.palavras[position] = data;
    }

    public short getPalavra(int position){
        return this.palavras[position];
    }
}