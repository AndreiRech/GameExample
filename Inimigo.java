import java.awt.Color;
public class Inimigo implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Inimigo (Character simbolo, Color cor) {
        this.cor = cor;
        this.simbolo = simbolo;
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
        return false;
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
        return "Você atingiu o cupim!";
    }
}
