package eu.pm.idea.project.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since unknown on exposePOMtoFileTemplates
 **/
@State(
        name = "eu.pm.idea.project.configuration.TemplateVariables",
        storages = @Storage("pom-template-variables.xml")
)
public class ProjectModelTemplateVariablesData implements PersistentStateComponent<ProjectModelTemplateVariablesData> {

    private static final String DEFAULT_VERSION_VAR_NAME = "CURRENT_VERSION";
    private static final String DEFAULT_ARTIFACT_VAR_NAME = "ARTIFACT_ID";

    private static final String DEFAULT_VALUE = "unknown";

    private String version = DEFAULT_VERSION_VAR_NAME;
    private String versionDefaultValue = DEFAULT_VALUE;
    private String name = DEFAULT_ARTIFACT_VAR_NAME;
    private String artifactNameDefaultValue = DEFAULT_VALUE;

    public static String getDefaultVersionValue() {
        return DEFAULT_VALUE;
    }
    public static String getDefaultArtifactName() {
        return DEFAULT_VALUE;
    }

    public ProjectModelTemplateVariablesData() {
        //
    }

    public static ProjectModelTemplateVariablesData getInstance() {
        return ApplicationManager.getApplication().getService(ProjectModelTemplateVariablesData.class);
    }

    @Override
    public @Nullable ProjectModelTemplateVariablesData getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ProjectModelTemplateVariablesData state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

      void setVersion(String version) {
        this.version = version;
    }

      void setName(String name) {
        this.name = name;
    }

    public String getVersionDefaultValue() {
        return versionDefaultValue;
    }

    public void setVersionDefaultValue(String versionDefaultValue) {
        this.versionDefaultValue = versionDefaultValue;
    }

    public String getArtifactNameDefaultValue() {
        return artifactNameDefaultValue;
    }

    public void setArtifactNameDefaultValue(String artifactNameDefaultValue) {
        this.artifactNameDefaultValue = artifactNameDefaultValue;
    }
}
