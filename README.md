
# intellij-plugin-pom-template-variables



###### TODO fix for latest (> 2024.*)

IntelliJ IDEA plugin that exposes project build data (version/artifactId) as file template variables





###### sample :
 
    * ..
    * @since ${CURRENT_VERSION} for ${ARTIFACT_ID}
    * ..


###### other TODOs 
- update !
- multimodule 
- gradle 
    - load gradle properties (if any) **TODO** : remove
    - load gradle **TODO** : see 
      - https://github.com/JetBrains/gradle-intellij-plugin/blob/master/src/main/kotlin/org/jetbrains/intellij/Version.kt
      - intellijExtension at https://raw.githubusercontent.com/JetBrains/gradle-intellij-plugin/51ac90da2ede0c02bcadb42c03b156f61fdfe1b1/src/main/kotlin/org/jetbrains/intellij/IntelliJPlugin.kt
      - /ideaIC-2021.2/plugins/gradle-java/lib/gradle-java.jar!/META-INF/plugin.xml 



###### ref: 
- **[File Template Variables IDEA plugin](https://github.com/vkravets/FileTemplatesVariable) by Vladimir Kravets**
- [XML DOM API](https://plugins.jetbrains.com/docs/intellij/xml-dom-api.html?from=jetbrains.org)
- [Plugin Configuration File](https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html)
- [Overview of Custom Settings Implementation](https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#the-appsettingsstate-class)
- [ IDEs Support (IntelliJ Platform): How to I get all xml files in resource ](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360010497879-How-to-I-get-all-xml-files-in-resource)

###### issues 
 
1. load pom as MavenDomProjectModel instead of POMProject https://github.com/silviuilie/intellij-plugin-pom-template-variables/issues/1
<!--
2.  / how to load MavenDomProjectModel? 
-->


2. use org.jetbrains.idea.maven.dom.model.MavenDomProjectModel https://github.com/silviuilie/intellij-plugin-pom-template-variables/issues/2 
(see `eu.pm.idea.project.maven.ProjectModelDomFileDescription`) 



 

 

 
