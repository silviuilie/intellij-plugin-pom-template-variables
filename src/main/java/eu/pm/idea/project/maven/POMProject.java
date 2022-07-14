package eu.pm.idea.project.maven;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xmlb.annotations.Tag;

/**
 * maven pom root.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public interface POMProject extends DomElement {

    @Tag("version")
    POMProjectVersion getVersion();

    @Tag("parent")
    POMProjectParent getParentPOM();

}
