# intellij-plugin-pom-template-variables

IntelliJ IDEA plugin that exposes project build data (version/artifactId) as file template variables





###### sample java doc class/interface header template usage :
 
    * ..
    * @since ${CURRENT_VERSION}
    * ..


###### TODOs

- rename POM* classes
- ~~UI, change the variable name~~
- multimodule support, find version from the pom of the current module
- gradle
- find the cause for error #1

###### references: 
- **[File Template Variables IDEA plugin](https://github.com/vkravets/FileTemplatesVariable) by Vladimir Kravets**
- [XML DOM API](https://plugins.jetbrains.com/docs/intellij/xml-dom-api.html?from=jetbrains.org)
- [Plugin Configuration File](https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html)
- [Overview of Custom Settings Implementation](https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#the-appsettingsstate-class)

###### issues
 
1. load pom as MavenDomProjectModel instead of POMProject / how to load MavenDomProjectModel?
   module exports ? 



---
      java.lang.ClassCastException: class org.jetbrains.idea.maven.dom.model.MavenDomProjectModel$$EnhancerByJetBrainsMainCglib$$8ae7e121 cannot be cast to class eu.pm.idea.project.maven.POMProject (org.jetbrains.idea.maven.dom.model.MavenDomProjectModel$$EnhancerByJetBrainsMainCglib$$8ae7e121 is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @6572421; eu.pm.idea.project.maven.POMProject is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @2d2b379d)
      at eu.pm.idea.project.filetemplate.POMPropertiesProvider.fillProperties(POMPropertiesProvider.java:52)
      at com.intellij.ide.fileTemplates.FileTemplateUtil.fillDefaultProperties(FileTemplateUtil.java:362)
      at com.intellij.ide.fileTemplates.FileTemplateUtil.createFromTemplate(FileTemplateUtil.java:299)

