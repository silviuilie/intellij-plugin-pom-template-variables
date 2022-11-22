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
    private JTextField CURRENT_VERSION_DEFAULT_VAL;
    private JTextField ARTIFACT_ID_DEFAULT_VAL;

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


    public String getArtifactDefaultValue() {
        return ARTIFACT_ID_DEFAULT_VAL.getText();
    }

    public void setArtifactDefaultValue(String defaultValue) {
        this.ARTIFACT_ID_DEFAULT_VAL.setText(defaultValue);
    }


    public String getVersionDefaultValue() {
        return CURRENT_VERSION_DEFAULT_VAL.getText();
    }

    public void setVersionDefaultValue(String defaultValue) {
        this.CURRENT_VERSION_DEFAULT_VAL.setText(defaultValue);
    }

    public ConfigurationComponent(Project project) {
        //
    }

    public JComponent getMain() {
        return mainpanel;
    }

}
