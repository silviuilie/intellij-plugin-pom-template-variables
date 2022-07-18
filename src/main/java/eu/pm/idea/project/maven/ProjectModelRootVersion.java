package eu.pm.idea.project.maven;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

/**
 *
 * version element.
 *
 * @see org.jetbrains.idea.maven.dom.model.MavenDomProjectModel
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public interface ProjectModelRootVersion extends DomElement {

    @NotNull
    @Required(value = false, nonEmpty = true)
    String getValue();
}
