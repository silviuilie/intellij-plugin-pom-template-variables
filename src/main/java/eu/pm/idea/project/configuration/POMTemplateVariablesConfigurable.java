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
public class POMTemplateVariablesConfigurable implements Configurable.NoScroll, Configurable {

    private final Project project;
    private ConfigurationComponent configuration;

    public POMTemplateVariablesConfigurable(Project project) {
        this.project = project;
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "project template variables";
    }

    @Override
    public @Nullable JComponent createComponent() {
        configuration = new ConfigurationComponent(project);
        return configuration.getMain();
    }

    @Override
    public boolean isModified() {
        if (null == configuration) { return false;}
        return configuration.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        configuration.commit();
    }
}
