package eu.pm.idea.project.maven;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xmlb.annotations.Tag;

/**
 * parent version element.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public interface ProjectModelRootParent extends DomElement {

    @Tag("version")
    ProjectModelRootVersion getValue();
}
