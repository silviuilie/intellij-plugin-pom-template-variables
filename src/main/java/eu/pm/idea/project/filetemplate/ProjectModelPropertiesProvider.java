package eu.pm.idea.project.filetemplate;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.ProjectScopeImpl;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import eu.pm.idea.project.configuration.ErrorNotifier;
import eu.pm.idea.project.configuration.ProjectModelTemplateVariablesData;
import eu.pm.idea.project.maven.ProjectModelRoot;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Properties;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * property provider exposing project object model.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public class ProjectModelPropertiesProvider implements DefaultTemplatePropertiesProvider {

    private static final String UNKNOWN_VALUE = "unknown";


    ProjectModelTemplateVariablesData defaultTemplateVariables = null;


    @Override
    public void fillProperties(@NotNull PsiDirectory psiDirectory, @NotNull Properties properties) {


        defaultTemplateVariables = ProjectModelTemplateVariablesData.getInstance();//psiDirectory.getProject().getService(POMTemplateVariablesData.class);

        //psiDirectory.getParentDirectory()
        final Project project = psiDirectory.getProject();
        DomManager domManager = DomManager.getDomManager(project);

        setVersion(psiDirectory, properties, project, domManager);

    }

    /**
     * todo :
     * <p>
     * is vFile in psiDirectory ?
     * <p>
     * PsiDirectory
     */
    private void setVersion(PsiDirectory psiDirectory, @NotNull Properties properties, Project project, DomManager domManager) {
        String candidateVersion = UNKNOWN_VALUE;
        String candidateModuleName = UNKNOWN_VALUE;
        ProjectScopeImpl scope = new ProjectScopeImpl(project, FileIndexFacade.getInstance(domManager.getProject()));
        Collection<VirtualFile> virtualFiles = FilenameIndex.getVirtualFilesByName("pom.xml", scope);

        boolean errNotified = false;

        String currentFileRoot = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(psiDirectory.getVirtualFile()).getPath();

        for (VirtualFile vfile : virtualFiles) {
            try {
                var psiFile = PsiManager.getInstance(project).findFile(vfile);
                String pomModuleRoot = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(vfile).getPath();

                if (currentFileRoot.equals(pomModuleRoot)) {

                    XmlFile asXMLFile = (XmlFile) psiFile;
                    DomFileElement domFileElement = domManager.getFileElement(asXMLFile, ProjectModelRoot.class);
//                   DomFileElement domFileElement = domManager.getFileElement(asXMLFile);

                    if (domFileElement != null) {
                        ProjectModelRoot projectModelRoot = (ProjectModelRoot) domFileElement.getRootElement();
                        candidateVersion = projectModelRoot.getVersion() != null ? projectModelRoot.getVersion().getValue() : "unknown";
                        candidateModuleName = ProjectFileIndex.SERVICE.getInstance(project).getModuleForFile(vfile).getName();

                        if (!candidateVersion.equals(UNKNOWN_VALUE)) {
                            break;
                        }

                    }
                }
            } catch (Exception e) {
                logger.error("failed to get version with : " + e.getMessage(), e);
                ErrorNotifier.notifyError(project, e.getMessage());
                errNotified = true;
            }
        }

        ErrorNotifier.notifyError(project, "version not identified.");
        if (!errNotified && isNotEmpty(virtualFiles) && candidateVersion.equals(UNKNOWN_VALUE)) {
            ErrorNotifier.notifyError(project, "version not identified.");
        }

        properties.put(defaultTemplateVariables.getVersion(), candidateVersion);
        properties.put(defaultTemplateVariables.getName(), candidateModuleName);
    }

    private static final Logger logger = Logger.getInstance("#eu.pm.idea.project.filetemplate.POMPropertiesProvider");

}
