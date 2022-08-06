package eu.pm.idea.project.configuration;

import com.intellij.DynamicBundle;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
public class ErrorNotifier {
    private static ResourceBundle bundle = null;
    private static String title;

    public static void notifyError(@Nullable Project project,
                                   String content) {

        if (null == bundle) {
            bundle = PropertyResourceBundle.getBundle("messages.PomToTemplatesBundle", Locale.getDefault());
            title = bundle.getString("error.notification.title");
        }
        NotificationGroupManager.getInstance()
                .getNotificationGroup("ProjectModelPluginERR")
                .createNotification(content, NotificationType.ERROR)
                .setTitle(title)
                .notify(project);
    }
}
