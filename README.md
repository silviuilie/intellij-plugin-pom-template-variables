# intellij-plugin-pom-template-variables

IntelliJ IDEA plugin that exposes project build data (version/artifactId) as file template variables





###### sample java doc class header usage :
 
    * ..
    * @since ${CURRENT_VERSION}
    * ..


###### TODO

- rename classes
- UI, change the variable name
- multimodule support, find version from the pom of the current module
- gradle
- find the cause for error #1


###### issues
 
1. load pom as MavenDomProjectModel instead of POMProject /how to load MavenDomProjectModel?



---
      java.lang.ClassCastException: class org.jetbrains.idea.maven.dom.model.MavenDomProjectModel$$EnhancerByJetBrainsMainCglib$$8ae7e121 cannot be cast to class eu.pm.idea.project.maven.POMProject (org.jetbrains.idea.maven.dom.model.MavenDomProjectModel$$EnhancerByJetBrainsMainCglib$$8ae7e121 is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @6572421; eu.pm.idea.project.maven.POMProject is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @2d2b379d)
      at eu.pm.idea.project.filetemplate.POMPropertiesProvider.fillProperties(POMPropertiesProvider.java:52)
      at com.intellij.ide.fileTemplates.FileTemplateUtil.fillDefaultProperties(FileTemplateUtil.java:362)
      at com.intellij.ide.fileTemplates.FileTemplateUtil.createFromTemplate(FileTemplateUtil.java:299)

