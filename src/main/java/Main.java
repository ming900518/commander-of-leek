import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class Main extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Leek.start();
        System.out.println("Started");
    }
}