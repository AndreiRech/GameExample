import java.awt.Color;
public class Amigo implements ElementoMapa {
    private Color cor;
    private Character simbolo;

    public Amigo (Character simbolo, Color cor) {
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
        return false;
    }

    @Override
    public String interage() {
        return "Me ajude! Os cupins estão vindo destruir minha casa! Procure pelo meu machado e pare eles!";
    }
}
