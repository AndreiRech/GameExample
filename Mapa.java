import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapa {
    private List<String> mapa;
    private Map<Character, ElementoMapa> elementos;

    private int x = 50; 
    private int y = 50; 
    private final int TAMANHO_CELULA = 10; 
    private boolean[][] areaRevelada; 

    private final Color brickColor = new Color(153, 76, 0); 
    private final Color vegetationColor = new Color(34, 139, 34); 

    private final Color axeColor = new Color(80,80,80); // COR CINZA PARA O MACHADO
    private final Color cupimColor = new Color(255,0,0); // COR VERMELHA PARA O CUPIM
    private final Color amigoColor = new Color(0,0,255); // COR AZUL PARA O AMIGO
    private final Color placaColor = new Color(255,0,255); // COR BEGE PARA A PLACA
    private final Color arvoreCortColor = new Color(0,204,102); // COR VERDE CLARO PARA ÁRVORE CORTÁVEL
    private final Color portaColor = new Color(51,25,0); // COR MARROM PRA PORTA
    private final Color chaveColor = new Color(240,234,51); // COR AMARELO PARA CHAVE

    private final int RAIO_VISAO = 5; 
    private int contador;
    private boolean machado;

    public Mapa(String arquivoMapa) {
        mapa = new ArrayList<>();
        elementos = new HashMap<>();
        this.contador = 7;
        this.machado = false;
        registraElementos();
        carregaMapa(arquivoMapa);
        areaRevelada = new boolean[mapa.size()+1000][mapa.get(0).length()+1000];
        atualizaCelulasReveladas();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamanhoCelula() {
        return TAMANHO_CELULA;
    }

    public int getNumLinhas() {
        return mapa.size();
    }

    public int getNumColunas() {
        return mapa.get(0).length();
    }

    public ElementoMapa getElemento(int x, int y) {
        Character id = mapa.get(y).charAt(x);
        return elementos.get(id);
    }

    public boolean estaRevelado(int x, int y) {
        return areaRevelada[y][x];
    }

    public boolean move(Direcao direcao) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case CIMA:
                dy = -TAMANHO_CELULA;
                break;
            case BAIXO:
                dy = TAMANHO_CELULA;
                break;
            case ESQUERDA:
                dx = -TAMANHO_CELULA;
                break;
            case DIREITA:
                dx = TAMANHO_CELULA;
                break;
            default:
                return false;
        }

        if (!podeMover(x + dx, y + dy)) {
            System.out.println("Não pode mover");
            return false;
        }

        x += dx;
        y += dy;

        atualizaCelulasReveladas();
        return true;
    }

    private boolean podeMover(int nextX, int nextY) {
        int mapX = nextX / TAMANHO_CELULA;
        int mapY = nextY / TAMANHO_CELULA - 1;

        if (mapa == null)
            return false;

        if (mapX >= 0 && mapX < mapa.get(0).length() && mapY >= 1 && mapY <= mapa.size()) {
            char id;

            try {
               id = mapa.get(mapY).charAt(mapX);
            } catch (StringIndexOutOfBoundsException e) {
                return false;
            }

            if (id == ' ')
                return true;

            ElementoMapa elemento = elementos.get(id);
            if (elemento != null) {
                //System.out.println("Elemento: " + elemento.getSimbolo() + " " + elemento.getCor());
                return elemento.podeSerAtravessado();
            }
        }

        return false;
    }

    public String interage() {
        // A sequência utilizada para a interação é a mesma passada pelos for, ou seja, por linha, da esquerda para direita

        int raioInteracao = 1; // Raio de interação 3x3 centrado no personagem
    
        for (int i = y / TAMANHO_CELULA - 1 - raioInteracao; i <= y / TAMANHO_CELULA - 1 + raioInteracao; i++) {
            for (int j = x / TAMANHO_CELULA - raioInteracao; j <= x / TAMANHO_CELULA + raioInteracao; j++) {

                if (i >= 0 && i < mapa.size() && j >= 0 && j < mapa.get(i).length()) {
                    ElementoMapa elemento = getElemento(j, i);

                    if (elemento != null && elemento.podeInteragir()) {
                        return verificaInteracao(elemento, j, i);
                    }
                }
            }
        }
    
        return "Não há nenhum elemento para interagir";
    }

    private String verificaInteracao(ElementoMapa elemento, int j, int i) {
        String mensagemInteracao = elemento.interage();
        String nome = elemento.getClass().getName();

        if(nome.equals("Inimigo")) {
            diminuiContador();
        }

        if(nome.equals("Machado")) {
            setMachado(true);
        }

        if(nome.equals("Amigo")) {
            int x = 16;
            while (x<20) {
                removeElemento(x, 5);
                x++;
            }
        }

        if (elemento.aposInteracao()) {    
                             
            if(nome.equals("VegetacaoCortavel")) {
                if (getMachado()) {
                    removeElemento(j,i);
                }
                else {
                    return "Você não possui um machado";
                }
            }

            if(getContador() == 2) {
                removeElemento(44, 21);
            }
                            
            removeElemento(j, i); // Remove o elemento se necessário
        }

        return mensagemInteracao;
    }

    private void removeElemento(int x, int y) {
        if (y >= 0 && y < mapa.size() && x >= 0 && x < mapa.get(y).length()) {
            StringBuilder linha = new StringBuilder(mapa.get(y));
            linha.setCharAt(x, ' ');
            mapa.set(y, linha.toString());
        }
    }

    public boolean getMachado() {
        return machado;
    }

    public void setMachado(boolean status) {
        this.machado = status;
    }

    public int getContador() {
        return contador;
    }

    public void diminuiContador() {
        this.contador--;
    }

    public String ataca() {
        return "Acho que isso não faz nada...";
    }

    private void carregaMapa(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                mapa.add(line);
                // Se character 'P' está contido na linha atual, então define a posição inicial do personagem
                if (line.contains("P")) {
                    x = line.indexOf('P') * TAMANHO_CELULA;
                    y = mapa.size() * TAMANHO_CELULA;
                    // Remove o personagem da linha para evitar que seja desenhado
                    mapa.set(mapa.size() - 1, line.replace('P', ' '));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar as células reveladas
    private void atualizaCelulasReveladas() {
        if (mapa == null)
            return;
        for (int i = Math.max(0, y / TAMANHO_CELULA - RAIO_VISAO); i < Math.min(mapa.size(), y / TAMANHO_CELULA + RAIO_VISAO + 1); i++) {
            for (int j = Math.max(0, x / TAMANHO_CELULA - RAIO_VISAO); j < Math.min(mapa.get(i).length(), x / TAMANHO_CELULA + RAIO_VISAO + 1); j++) {
                areaRevelada[i][j] = true;
            }
        }
    }

    // Registra os elementos do mapa
    private void registraElementos() {
        // Parede
        elementos.put('#', new Parede('▣', brickColor));
        // Vegetação
        elementos.put('V', new Vegetacao('♣', vegetationColor));

        //Adicionais
        elementos.put('X', new VegetacaoCortavel('♣', arvoreCortColor));
        elementos.put('M', new Machado('¶', axeColor));
        elementos.put('C', new Inimigo('ϫ', cupimColor));
        elementos.put('A', new Amigo('ʘ', amigoColor));
        elementos.put('D', new Placa('Ƥ', placaColor));
        elementos.put('G', new Porta('Ħ', portaColor));
    }
}
