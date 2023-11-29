import java.awt.Color;

public class Porta implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Porta(Character simbolo, Color cor) {
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
        return false;
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
        return "Uma porta impede sua passagem! Aho que uma chave seria uma boa...";
    }
}