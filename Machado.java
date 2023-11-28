import java.awt.Color;

public class Machado implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Machado(Character simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }
    
    public Character getSimbolo() {
        return simbolo;
    }

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
        return true;
    }

    @Override
    public String interage() {
        return "Você achou um machado!";
    }
}
