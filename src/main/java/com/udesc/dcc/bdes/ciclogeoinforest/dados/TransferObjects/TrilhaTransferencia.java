package com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author Leandro & George
 */
public class TrilhaTransferencia {
    
    private int desnivel;
    private float comprimento_km;
    private String nome;
    private ArrayList<String> geomPartes;

    public TrilhaTransferencia(BufferedReader br){
        
        String p1,p2,linha="",infoTrilha="",retorno="",trilha="",temps="";
        int temp,maior=-100,menor=100,tottr=0,num_e;
        String resto;
//        BufferedReader br;
        ArrayList<String> linhatrack = new ArrayList<String>();
        ArrayList<String> temptrack = new ArrayList<String>();
        ArrayList<String> partes = new ArrayList<String>();

        try{
//            br = new BufferedReader(arq);
            //Contar qtas trilhas tem no arquivo e pegar os seus dados:
            while((linha = br.readLine())!=null){ //Para cada linha do arquivo...
                if (linha.startsWith("Track") && !linha.startsWith("Trackpoint")){ //se for o início de uma nova trilha...
                    tottr++;
                    if (tottr>1){retorno="O arquivo txt deve possuir apenas uma trilha!";}
                    infoTrilha = linha.substring(6); //Guarda a linha atual
                }
                else if (linha.startsWith("Trackpoint")){ //Se for um ponto da trilha atual...
                    linha=linha.substring(linha.indexOf('\t')+1); //Remove da linha o "Trackpoint"
                    p1=linha.substring(0, 10).replace('S', '-');
                    resto = (""+((Float.parseFloat(p1.substring(4)))/60)); //7.833333333333333E-4 => 0.0007833333...
                    resto = resto.replace(".", ""); //7833333333333333E-4
                    p1=p1.substring(0, 3)+".";
                    if (resto.contains("E")){
                        num_e = Integer.parseInt(resto.substring(resto.indexOf("-")+1));
                        for (int o=0;o<num_e;o++){p1=p1+"0";}
                        p1=p1+resto.substring(0, resto.indexOf("E"));
                    }
                    else{ //mesmo que 'if (resto.startsWith("0")){'
                        p1=p1+resto.substring(1);
                    }

                    p2=linha.substring(11, linha.indexOf('\t')).replace('W', '-');
                    resto = (""+((Float.parseFloat(p2.substring(4)))/60));
                    resto = resto.replace(".", "");
                    p2=p2.substring(0, 3)+".";
                    if (resto.contains("E")){
                        num_e = Integer.parseInt(resto.substring(resto.indexOf("-")+1));
                        for (int o=0;o<num_e;o++){p2=p2+"0";}
                        p2=p2+resto.substring(0, resto.indexOf("E"));
                    }
                    else{ //mesmo que 'if (resto.startsWith("0")){'
                        p2=p2+resto.substring(1);
                    }

                    linhatrack.add(p2+" "+p1); //Guarda a coordenada do ponto
                    linha=linha.substring(linha.indexOf('\t')+1); //Remove da linha a coordenada do ponto da trilha
                    linha=linha.substring(linha.indexOf('\t')+1); //Remove da linha o data/hora que o ponto foi gravado
                    if (!linha.startsWith("\t")){ //Se o arquivo tiver a altura do ponto, pegá-lo para calcular o desnível
                        temp=Integer.parseInt(linha.substring(0, linha.indexOf(' ')));
                        if (temp>maior){maior = temp;}
                        if (temp<menor){menor = temp;}
                    }
                }
            }
            br.close();
            if (tottr==0){retorno="Não há trilhas nesse arquivo!";}
            
            //Se tudo correu bem:
            desnivel = maior-menor;
            nome = infoTrilha.substring(0, infoTrilha.indexOf('\t'));
            infoTrilha=infoTrilha.substring(infoTrilha.indexOf('\t')+1, infoTrilha.length());
            infoTrilha=infoTrilha.substring(infoTrilha.indexOf('\t')+1, infoTrilha.length()); //remove "Start Time"
            infoTrilha=infoTrilha.substring(infoTrilha.indexOf('\t')+1, infoTrilha.length()); //remove "Elapsed Time"
            if (infoTrilha.substring(0, infoTrilha.indexOf('\t')+1).contains("km")){
                comprimento_km = Float.parseFloat(infoTrilha.substring(0, infoTrilha.indexOf(' ')));
            }
            else{
                comprimento_km = Float.parseFloat(infoTrilha.substring(0, infoTrilha.indexOf(' ')))/1000;
            }

            //Se trilha tiver + de 163 pontos, dividí-la no menor número possível de partes
            //Parte 1: Remove pontos duplicados
            temps=linhatrack.get(0);
            temptrack.add(temps);
            for (int i=1;i<linhatrack.size();i++){
                if (!linhatrack.get(i).equals(temps)){ //Se o próximo ponto não for igual ao anterior
                    temps=linhatrack.get(i);
                    temptrack.add(temps);
                }
            }
            //Parte 2: Dividir a trilha em partes de 163 pontos
            int numPartes; //numero de partes em que a trilha será dividida
            if (temptrack.size()<=163){numPartes=1;}
            else{numPartes=(int)(temptrack.size()/163)+1;}
            for (int i=0;i<numPartes;i++){
                for (int j=0+((163*i)-i);j<163+((163*i)-i);j++){
                    try{
                        trilha=trilha+temptrack.get(j)+",\n";
                    }catch(Exception e){} //Só pro programa não fechar
                }
                trilha=trilha.substring(0, trilha.length()-2);
                partes.add(trilha);
                trilha="";
            }
            geomPartes = partes;
        }
        catch(Exception ex){retorno = ex.toString();}
    }            

    public float getComprimento_km() {
        return comprimento_km;
    }
    
    public void setComprimento_km(float comprimento_km) {
        this.comprimento_km = comprimento_km;
    }

    public int getDesnivel() {
        return desnivel;
    }
    
    public void setDesnivel(int desnivel) {
        this.desnivel = desnivel;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getGeomPartes() {
        return geomPartes;
    }
    
    public void setGeomPartes(ArrayList<String> geomPartes) {
        this.geomPartes = geomPartes;
    }
    
}