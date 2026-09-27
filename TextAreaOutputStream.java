import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// cajitaepica de texto de la ventana

public class TextAreaOutputStream extends OutputStream {
    private JTextArea areaTexto;
    private StringBuilder bufferLinea = new StringBuilder();

    public TextAreaOutputStream(JTextArea areaTexto) {
        this.areaTexto = areaTexto;
    }

    @Override
    public void write(int b) {
        char c = (char) b;
        bufferLinea.append(c);

        // esperamos hasta el salto de linea para no repintar letra por letra
        if (c == '\n') {
            String texto = bufferLinea.toString();
            bufferLinea.setLength(0);
            SwingUtilities.invokeLater(() -> {
                areaTexto.append(texto);
                areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
            });
        }
    }
}
