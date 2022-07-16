package eu.pm.idea.project.filetemplate;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.ProjectScopeImpl;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import eu.pm.idea.project.configuration.POMTemplateVariablesData;
import eu.pm.idea.project.maven.POMProject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Properties;

/**
 * property provider exposing project object model.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public class POMPropertiesProvider implements DefaultTemplatePropertiesProvider {

    private static final String UNKNOWN_VALUE = "unknown";


    POMTemplateVariablesData defaultTemplateVariables = null;


    @Override
    public void fillProperties(@NotNull PsiDirectory psiDirectory, @NotNull Properties properties) {

        defaultTemplateVariables = POMTemplateVariablesData.getInstance();//psiDirectory.getProject().getService(POMTemplateVariablesData.class);

        final Project project = psiDirectory.getProject();
        DomManager domManager = DomManager.getDomManager(project);

        setVersion(properties, project, domManager);

    }

    private void setVersion(@NotNull Properties properties, Project project, DomManager domManager) {
        String candidateVersion = UNKNOWN_VALUE;
        ProjectScopeImpl scope = new ProjectScopeImpl(project, FileIndexFacade.getInstance(domManager.getProject()));
        Collection<VirtualFile> virtualFiles = FilenameIndex.getVirtualFilesByName("pom.xml", scope);

        for (VirtualFile vfile : virtualFiles) {
            //OpenFileDescriptor ofd = new OpenFileDescriptor(project, vfile);
            try {
                var psiFile = PsiManager.getInstance(project).findFile(vfile);
                XmlFile asXMLFile = (XmlFile) psiFile;

                    //   DomFileElement domFileElement = domManager.getFileElement(asXMLFile, POMProject.class);
                   DomFileElement domFileElement = domManager.getFileElement(asXMLFile);

                POMProject pomProject = (POMProject) domFileElement.getRootElement();
                candidateVersion = pomProject.getVersion() != null ? pomProject.getVersion().getValue() : "unknown";
            } catch (Exception e) {
                logger.error("failed to get version with : " + e.getMessage(), e);
            }
        }
        properties.put(defaultTemplateVariables.getVersion(), candidateVersion);
        properties.put(defaultTemplateVariables.getName(), project.getName() + "(todo!)");
    }

    private static final Logger logger = Logger.getInstance("#eu.pm.idea.project.filetemplate.POMPropertiesProvider");

}
