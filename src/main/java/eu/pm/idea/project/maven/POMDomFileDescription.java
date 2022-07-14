package eu.pm.idea.project.maven;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * maven project object model descriptor.
 * 
 * 
 * TODO : how to use org.jetbrains.idea.maven.dom.model.MavenDomProjectModel ?
 * 
 * @see {@link com.intellij.util.xml.DomFileDescription}
 * 
 * 
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public class POMDomFileDescription<T extends DomElement> extends DomFileDescription<T> {

    public POMDomFileDescription() {
        this(POMProject.class, "project","project");
    }

    public POMDomFileDescription(Class rootElementClass,
                                 @NonNls String rootTagName,
                                 @NonNls String @NotNull ... allPossibleRootTagNamespaces) {
        super(rootElementClass, rootTagName, allPossibleRootTagNamespaces);
    }

}
