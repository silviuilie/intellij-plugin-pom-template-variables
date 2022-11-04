package eu.pm.idea.project.configuration;

import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ex.ApplicationManagerEx;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.apache.commons.text.StringSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.event.HyperlinkEvent;
import java.util.*;

/**
 *
 * plugin exec error notification
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
public class ErrorNotifier {
    private static ResourceBundle bundle = null;
    private static String title;

    private static final String disableDescription = "disable";
    private static final String reportDescription = "report";

    private static final String configureDescription = "configure";

    private static final String reportURL = "https://github.com/silviuilie/intellij-plugin-pom-template-variables/issues/new";

    private static String template = "${content}<br/>" +
            "<a href='disable'>Disable plugin and restart.. </a>&nbsp;<a href='configure'>Configure plugin</a>&nbsp;&nbsp;&nbsp;<a href='report'> Report Error</a>";

    public static void notifyError(@Nullable Project project,
                                   String content) {

        if (null == bundle) {
            bundle = PropertyResourceBundle.getBundle("messages.PomToTemplatesBundle", Locale.getDefault());
            title = bundle.getString("error.notification.title");
        }
        NotificationGroupManager.getInstance()
                .getNotificationGroup("ProjectModelPluginERR")
                .createNotification(mergeTemplate(content), NotificationType.ERROR)
                .setListener(new NotificationListener.Adapter() {
                    @Override
                    protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent e) {
                        if (e.getDescription().equalsIgnoreCase(disableDescription)) {
                            PluginManagerCore.disablePlugin(PluginId.getId("eu.pm.idea.project.filetemplate"));
                            ApplicationManagerEx.getApplicationEx().restart(true);
                        } else if (e.getDescription().equalsIgnoreCase(configureDescription)) {
                            ShowSettingsUtil.getInstance().showSettingsDialog(project,ProjectModelTemplateVariablesConfigurable.class);
                        }else if (e.getDescription().equalsIgnoreCase(reportDescription)) {
                            BrowserLauncher.getInstance().open(reportURL);
                        }
                    }
                })
                .setTitle(title)//.addAction(new BrowseNotificationAction("test",))
                .notify(project);
    }

    private static String mergeTemplate(String content) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("content", content);
        valuesMap.put("report", disableDescription);
        valuesMap.put("configure", configureDescription);
        valuesMap.put("disable", disableDescription);

        StringSubstitutor sub = new StringSubstitutor(valuesMap);

        return sub.replace(template);
    }
}
