package eu.pm.idea.project.configuration;

/**
 * TODO : remove or make it work with POMTemplateVariablesData
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/

public class TemplateVariables {

    private String version;
    private String name;

    public TemplateVariables(String version, String name) {
        this.version = version;
        this.name = name;
    }

    public String getVersion() {
        return version;
    }


    public String getName() {
        return name;
    }

}
