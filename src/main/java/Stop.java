import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class Stop extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Leek.stop();
        System.out.println("Stopped");
    }
}
