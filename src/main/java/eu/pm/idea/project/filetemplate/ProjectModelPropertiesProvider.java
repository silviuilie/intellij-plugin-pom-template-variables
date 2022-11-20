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
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.util.Collection;
import java.util.Properties;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * property provider exposing project object model.
 *
 * @author silviu ilie
 * @since 1.0-SNAPSHOT on exposePOMtoFileTemplateVars
 **/
public class ProjectModelPropertiesProvider implements DefaultTemplatePropertiesProvider {

    private static final String UNKNOWN_VALUE = "unknown";


    ProjectModelTemplateVariablesData defaultTemplateVariables = null;

    class SimpleProjectDescriptor {
        private String candidateVersion = UNKNOWN_VALUE;
        private String candidateModuleName = UNKNOWN_VALUE;

        public SimpleProjectDescriptor() {
            //
        }

        public SimpleProjectDescriptor(String candidateVersion, String candidateModuleName) {
            this.candidateVersion = candidateVersion;
            this.candidateModuleName = candidateModuleName;
        }

          void setCandidateVersion(String candidateVersion) {
            this.candidateVersion = candidateVersion;
        }

          void setCandidateModuleName(String candidateModuleName) {
            this.candidateModuleName = candidateModuleName;
        }

          String getCandidateVersion() {
            return candidateVersion;
        }

          String getCandidateModuleName() {
            return candidateModuleName;
        }
    }
    SimpleProjectDescriptor simpleProjectDescriptor = new SimpleProjectDescriptor();

    @Override
    public void fillProperties(@NotNull PsiDirectory psiDirectory, @NotNull Properties properties) {

        defaultTemplateVariables = ProjectModelTemplateVariablesData.getInstance();

        final Project project = psiDirectory.getProject();
        DomManager domManager = DomManager.getDomManager(project);

        setVersion(psiDirectory, properties, project, domManager);

    }

    /**
     * produce version/artifact name from pom/gradle
     *
     */
    private void setVersion(PsiDirectory psiDirectory, @NotNull Properties properties, Project project, DomManager domManager) {

        ProjectScopeImpl scope = new ProjectScopeImpl(project, FileIndexFacade.getInstance(domManager.getProject()));
        String currentFileRoot = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(psiDirectory.getVirtualFile()).getPath();

        Collection<VirtualFile> pomVirtualFiles = FilenameIndex.getVirtualFilesByName("pom.xml", scope);
        Collection<VirtualFile> gradlePropertiesVFs = FilenameIndex.getVirtualFilesByName("gradle.properties", scope);
        boolean errNotified = false;


        if (!isEmpty(pomVirtualFiles)) {
            errNotified = loadMavenVersion(project, domManager, currentFileRoot, pomVirtualFiles);
        } else {
            if (!isEmpty(gradlePropertiesVFs)) {
                errNotified = loadGradle(project, domManager, currentFileRoot, gradlePropertiesVFs);
            }
        }

        if (!errNotified && isNotEmpty(pomVirtualFiles) && simpleProjectDescriptor.getCandidateVersion().equals(UNKNOWN_VALUE)) {
            ErrorNotifier.notifyError(project, "version not identified.");
        }

        properties.put(defaultTemplateVariables.getVersion(), simpleProjectDescriptor.getCandidateVersion());
        properties.put(defaultTemplateVariables.getName(), simpleProjectDescriptor.getCandidateModuleName());
    }

    private boolean loadGradle(Project project, DomManager domManager, String currentFileRoot, Collection<VirtualFile> gradlePropertiesVFs) {
        boolean errNotified = false;

        // check for properties and common naming
        ProjectScopeImpl scope = new ProjectScopeImpl(project, FileIndexFacade.getInstance(domManager.getProject()));
        for (VirtualFile vfile : gradlePropertiesVFs) {
            try {
                var psiFile = PsiManager.getInstance(project).findFile(vfile);
                String pomModuleRoot = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(vfile).getPath();

                if (currentFileRoot.equals(pomModuleRoot)) {
                    Properties gradleProps = new Properties();
                    gradleProps.load(new FileReader(vfile.getPath()));
                    simpleProjectDescriptor.setCandidateVersion(defaultString(
                            gradleProps.getProperty("snapshotVersion"),
                            gradleProps.getProperty("version")
                    ));
                }
            } catch (Throwable throwable) {
                logger.error("failed to get version with : " + throwable.getMessage(), throwable);
                ErrorNotifier.notifyError(project, throwable.getMessage());
                errNotified = true;
            }
        }

        // Load org.jetbrains.intellij.Version.tk from IntelliJPluginExtension
        // see https://raw.githubusercontent.com/JetBrains/gradle-intellij-plugin/51ac90da2ede0c02bcadb42c03b156f61fdfe1b1/src/main/kotlin/org/jetbrains/intellij/IntelliJPlugin.kt



        return errNotified;
    }

    private boolean loadMavenVersion(Project project, DomManager domManager, String currentFileRoot, Collection<VirtualFile> pomVirtualFiles) {
        ProjectScopeImpl scope = new ProjectScopeImpl(project, FileIndexFacade.getInstance(domManager.getProject()));
        boolean errNotified = false;
        for (VirtualFile vfile : pomVirtualFiles) {
            try {
                var psiFile = PsiManager.getInstance(project).findFile(vfile);
                String pomModuleRoot = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(vfile).getPath();

                if (currentFileRoot.equals(pomModuleRoot)) {

                    XmlFile asXMLFile = (XmlFile) psiFile;
                    DomFileElement domFileElement = domManager.getFileElement(asXMLFile, ProjectModelRoot.class);
                    //   DomFileElement domFileElement = domManager.getFileElement(asXMLFile);

                    if (domFileElement != null) {
                        ProjectModelRoot projectModelRoot = (ProjectModelRoot) domFileElement.getRootElement();
                        simpleProjectDescriptor.setCandidateVersion(projectModelRoot.getVersion() != null ? projectModelRoot.getVersion().getValue() : UNKNOWN_VALUE);
                        simpleProjectDescriptor.setCandidateModuleName(ProjectFileIndex.SERVICE.getInstance(project).getModuleForFile(vfile).getName());

                        if (!simpleProjectDescriptor.getCandidateVersion().equals(UNKNOWN_VALUE)) {
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
        return errNotified;
    }

    private static final Logger logger = Logger.getInstance("#eu.pm.idea.project.filetemplate.POMPropertiesProvider");

}
