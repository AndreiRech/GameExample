import java.awt.Color;

public class Placa implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Placa(Character simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }
    
    @Override
    public Character getSimbolo() {
        return simbolo;
    }

    @Override
    public Color getCor() {
        return cor;
    }

    @Override
    public boolean podeSerAtravessado() {
        return true;
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    @Override
    public boolean aposInteracao() {
        return false;
    }

    @Override
    public String interage() {
        return "Parece que há um tipo de árvore diferente. Tente usar seu machado!";
    }
}