package eu.pm.idea.project.maven;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xmlb.annotations.Tag;

/**
 * maven pom root.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public interface ProjectModelRoot extends DomElement {

    @Tag("version")
    ProjectModelRootVersion getVersion();

    @Tag("parent")
    ProjectModelRootParent getParentPOM();

}
