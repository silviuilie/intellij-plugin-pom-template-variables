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

###### references: 
- **[File Template Variables IDEA plugin](https://github.com/vkravets/FileTemplatesVariable) by Vladimir Kravets**
- [XML DOM API](https://plugins.jetbrains.com/docs/intellij/xml-dom-api.html?from=jetbrains.org)
- [Plugin Configuration File](https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html)
- [Overview of Custom Settings Implementation](https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#the-appsettingsstate-class)

###### issues
 
1. load pom as MavenDomProjectModel instead of POMProject / how to load MavenDomProjectModel?
   module exports ? 
   
   https://github.com/silviuilie/intellij-plugin-pom-template-variables/issues/1

 
