import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.Messages;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Leek {

    public static Timer t = new Timer();

    public static void start() {
        String ans = Messages.showInputDialog("Enter stock symbol. (Will push notification every minute.)", "Enter Stock Symbol", Messages.getQuestionIcon());

        if (ans != null && (!ans.isBlank())) {
            t.scheduleAtFixedRate(
                    new TimerTask() {
                        public void run() {
                            query(ans);
                        }
                    },
                    1000,
                    60000);
            Notification notification = new Notification("StockNG", "Operation successful: ", ans + ". Will push share price every minute.", NotificationType.INFORMATION);
            Notifications.Bus.notify(notification);
        } else {
            Notification notification = new Notification("StockNG", "Operation failed. ", "No stock symbol found.", NotificationType.WARNING);
            Notifications.Bus.notify(notification);
        }
    }

    public static void stop() {
        t.cancel();
    }

    public static void query(String ans) {
        JSONObject json = new JSONObject();
        try {
            json = JSONReader.readJsonFromUrl("https://query1.finance.yahoo.com/v8/finance/chart/" + ans + "?interval=1d");
        } catch (Exception ex) {
            ex.printStackTrace();
            Notification notification = new Notification("StockNG", "Process failed", "Can't get response correctly.", NotificationType.WARNING);
            Notifications.Bus.notify(notification);
            stop();
        }
        JSONObject chart = json.getJSONObject("chart");
        JSONArray result = chart.getJSONArray("result");
        JSONObject meta = result.getJSONObject(0).getJSONObject("meta");
        int regularMarketPrice = meta.getInt("regularMarketPrice");
        String currency = meta.getString("currency");
        Notification notification = new Notification("StockNG", ans, regularMarketPrice + " " + currency, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }
}
