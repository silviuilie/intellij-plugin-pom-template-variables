package eu.pm.idea.project;

import com.intellij.openapi.extensions.PluginId;

/**
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
public interface Plugin {
    public static final String PLUGIN_ID = "eu.pm.idea.project.filetemplate";

    public static PluginId id() {
        return PluginId.getId(Plugin.PLUGIN_ID);
    }

}
