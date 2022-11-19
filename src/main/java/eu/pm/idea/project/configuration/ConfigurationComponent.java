package eu.pm.idea.project.configuration;

import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
public class ConfigurationComponent {
    private JPanel mainpanel;
    private JTextField CURRENT_VERSIONTextField;
    private JTextField ARTIFACT_IDTextField;

    public void setVersion(String version) {
        CURRENT_VERSIONTextField.setText(version);
    }

    public String getVersion() {
        return CURRENT_VERSIONTextField.getText();
    }
    public String getName() {
        return ARTIFACT_IDTextField.getText();
    }

    public void setName(String name) {
        ARTIFACT_IDTextField.setText(name);
    }

    public ConfigurationComponent(Project project) {
        //
    }

    public JComponent getMain() {
        return mainpanel;
    }

}
