package server.view.console;

import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.console;
import javafx.application.Platform;
import javafx.scene.control.TextArea;




public class CustomOutputStream extends OutputStream
{

    private final TextArea textArea;

    /**
     *
     * @param textArea
     */
    public CustomOutputStream(TextArea textArea)
    {
        this.textArea = textArea;
    }

    /**
     *
     * @param a
     */
    public void appendText(String a)
    {
        Platform.runLater(() -> textArea.appendText(a));
    }

    @Override
    public void write(final int i) throws IOException
    {
        Platform.runLater(new Runnable()
        {
            public void run()
            {
                textArea.appendText(String.valueOf((char) i));
            }
        });
    }

}