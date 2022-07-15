package eu.pm.idea.project.configuration;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton okButton;

    public ConfigurationComponent(Project project) {
        System.out.println("ConfigurationComponent(project) : " + project);
    }

    public JComponent getMain() {
        return mainpanel;
    }

    public boolean isModified() {
        return true;
    }

    public void commit() {
        //persist variable names
        System.out.println(" = todo = ");
    }
}
