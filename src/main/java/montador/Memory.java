package montador;

public class Memory{
    private int[] palavras = new int[4096];

    public Memory(){
        for(int i = 0; i < 4096; i++){
            this.palavras[i] = (int)0;
        }  
    }
    public void setPalavra(int data, int position){
        this.palavras[position] = data;
    }

    public int getPalavra(int position){
        return this.palavras[position];
    }
}