package eu.pm.idea.project.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
public class ProjectModelTemplateVariablesConfigurable implements Configurable.NoScroll, Configurable {

    private final Project project;
    private ConfigurationComponent uiConfiguration;

    public ProjectModelTemplateVariablesConfigurable(Project project) {
        this.project = project;
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Project Model Template Variables";
    }

    @Override
    public @Nullable JComponent createComponent() {
        uiConfiguration = new ConfigurationComponent(project);
        return uiConfiguration.getMain();
    }

    @Override
    public boolean isModified() {
        ProjectModelTemplateVariablesData persistedConfiguration = ProjectModelTemplateVariablesData.getInstance();
        return !(
                uiConfiguration.getVersion().equals(persistedConfiguration.getState().getVersion())
                        &&
                uiConfiguration.getName().equals(persistedConfiguration.getState().getName())
        );
    }

    @Override
    public void reset() {
        ProjectModelTemplateVariablesData persistedConfiguration = ProjectModelTemplateVariablesData.getInstance();
        uiConfiguration.setVersion(persistedConfiguration.getVersion());
        uiConfiguration.setName(persistedConfiguration.getName());
    }

    @Override
    public void apply() throws ConfigurationException {
        ProjectModelTemplateVariablesData persistedConfiguration = ProjectModelTemplateVariablesData.getInstance();

        persistedConfiguration.setVersion(uiConfiguration.getVersion());
        persistedConfiguration.setName(uiConfiguration.getName());
    }


}
