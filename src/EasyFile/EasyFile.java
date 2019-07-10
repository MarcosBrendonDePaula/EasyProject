/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author MarcosB
 */
public class EasyFile {
    private final String NomeDoArquivo;
    private File Arquivo;
    private FileReader ArquivoLeitor;
    private FileWriter ArquivoGravador;
    private BufferedReader BufferDeLeitura;
    private BufferedWriter BufferDeGravaçao;
    /**
     * Metodo Construtor para Inicializar o Arquivo
     * @param NomeDoArquivo - Caminho da pasta + Nome do Arquivo a ser Monitorado EX:(teste/Textos.txt) ou (Textos.txt)
     */
    public EasyFile(String NomeDoArquivo) {
        this.NomeDoArquivo = NomeDoArquivo;
    }
    /**
     * Tenta Abrir Um determinado Arquivo Caso Não exista Ele O cria
     * @return boolean - true caso tenha dado tudo certo; False Caso Tenha dado Algum erro 
     */
    public boolean AbrirArquivo(){
        Arquivo=new File(NomeDoArquivo);
        if(!Arquivo.exists()){
            System.out.println("O Arquivo Foi Criado");
            try {
                Arquivo.createNewFile();
            } catch (IOException ex) {
                System.out.println("Erro Ao Criar O arquivo");
                return false;
            }
        }
        return true;
    }
    /**
     * Cria O diretorio que o arquivo necessita
     * @deprecated - Com problemas
     */
    public void CriarDiretorio(){
        String[] Entradas= NomeDoArquivo.split("//");
        String diretorio="";
        for(int i=0;i<(Entradas.length-1);i++){
            diretorio+=Entradas[i]+"//";
        }
        new File(diretorio).mkdir();
    }
    /**
     * Grava Apenas Uma linha no Arquivo
     * @param DADO String - Dado A ser Armazenado No Arquivo
     * @throws IOException - Pode dar erros Existencia de Arquivo
     */
    public void GravarEmArquivo(String DADO) throws IOException{
        ArquivoGravador=new FileWriter(Arquivo);
        BufferDeGravaçao =new BufferedWriter(ArquivoGravador);
        BufferDeGravaçao.newLine();
        BufferDeGravaçao.write(DADO);
        BufferDeGravaçao.close();
        ArquivoGravador.close();
    }
     /**
     * Grava Varias Linhas de dados em Um determinado Arquivo
     * @param Dados LinkedList - Dados A serem Armazenado No Arquivo
     * @throws IOException - Pode dar erros Existencia de Arquivo
     */
    public void GravarEmArquivo(LinkedList<String> Dados) throws IOException{
        ArquivoGravador=new FileWriter(Arquivo);
        BufferDeGravaçao =new BufferedWriter(ArquivoGravador);
        for(int i=0;i<Dados.size();i++){
            BufferDeGravaçao.write(Dados.get(i));
            BufferDeGravaçao.newLine();
        }
        BufferDeGravaçao.close();
        ArquivoGravador.close();
    }
    /**
     * Grava O dado Na ultima Linha Do Arquivo Atual
     * @param Dado - Dado A ser guardado
     * @throws IOException - pode dar erro de disponibilidade
     */
    public void GravarNoFinal(String Dado) throws IOException{
        LinkedList<String> aux=LerArquivo();
        aux.add(Dado);
        GravarEmArquivo(aux);
    }
    /**
     * Grava um determinado Dado em um posiçao escolhida
     * @param Dado - Dado A ser Armazenado
     * @param Posiçao - Posiçao onde o dado sera Alocado
     * @throws IOException - pode dar erro de disponibilidade
     */
    public void GravarEmPosiçao(int Posiçao,String Dado) throws IOException{
        LinkedList<String> aux=LerArquivo();
        try{
        aux.add(Posiçao,Dado);
        }catch(Exception e){
            System.out.println("Posiçao não encontrada");
        }
        GravarEmArquivo(aux);
    }
    /**
     * Le Todas as linhas presentes no Arquivo
     * @return LinkedListe String - Retorna Uma Lista de String Contendo em cada Posiçao A linha referente
     * @throws FileNotFoundException - Pode dar Alguns Problemas de existencia de arquivo
     * @throws IOException - Pode dar Alguns Problemas de disponibilidade de arquivo
     */
    public LinkedList<String> LerArquivo() throws FileNotFoundException, IOException{
        LinkedList<String> Linhas=new LinkedList<>();
        ArquivoLeitor=new FileReader(Arquivo);
        BufferDeLeitura=new BufferedReader(ArquivoLeitor);
        String linha="";
        try {
            linha = BufferDeLeitura.readLine();
        } catch (IOException ex) {
            System.out.println("Erro de leitura O arquivo pode estar sendo usado por outro programa");
        }
        while(linha!=null){
            Linhas.add(linha);
            linha=BufferDeLeitura.readLine();
        }
        BufferDeLeitura.close();
        ArquivoLeitor.close();
        return Linhas;
    }
    /**
     * Cria O arquivo.
     * @throws IOException - pode Gerar um erro de disponibilidade 
     */
    public void CriarArquivo() throws IOException{
        Arquivo.createNewFile();
    }
    /**
     * Recria O arquivo Sem nada dentro
     * @throws IOException - Pode dar Erro de disponibilidade de Arquivo
     */
    public void RecriarArquivo() throws IOException{
        DeletarArquivo();
        CriarArquivo();
    }
    /**
     * Deleta O arquivo Criado
     */
    public void DeletarArquivo(){
        Arquivo.delete();
    }
    /**
     * Retorna a quantidade de linhas que estao em uso no arquivo
     * @return int - Retorna A quantidade De linhas Presente No Arquivo
     * @throws FileNotFoundException - Pode dar Erros de existencia de arquivo
     * @throws IOException - Pode dar Erro de disponibilidade
     */
    public int getQuantidadeDeLinhas() throws FileNotFoundException, IOException{
        ArquivoLeitor=new FileReader(Arquivo);
        int qt=0;
        BufferDeLeitura=new BufferedReader(ArquivoLeitor);
        String linha=BufferDeLeitura.readLine();
        while(linha!=null){
            qt++;
            linha=BufferDeLeitura.readLine();
        }
        return qt;
    }
}
