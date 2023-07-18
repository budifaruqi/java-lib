# java-lib
parent dependency 
```
<dependency>
  <groupId>com.solusinegeri</groupId>
  <artifactId>java-lib</artifactId>
  <version>0.0.1</version>
</dependency> 
```
dependecy change
````
<dependency>
  <groupId>com.abinarystar.spring</groupId>
  <artifactId>abinarystar-spring-parent</artifactId>
  <version>0.5.13</version>
</dependency>
````
-- into --
````
<dependency>
  <groupId>com.solusinegeri</groupId>
  <artifactId>java-lib</artifactId>
  <version>0.0.1</version>
</dependency> 
````

see package to see complete artifact list
>https://github.com/solusinegeri/java-lib/packages

in dockerfile change
>RUN mvn -f /sources/pom.xml clean install --> RUN mvn -f /sources/pom.xml clean install -s /sources/settings.xml

add settings.xml file to project root
>https://github.com/solusinegeri/backend-authentication/blob/main/settings.xml

## add in pom.xml
````
<repository>
      <id>solusinegeri-java-lib</id>
      <url>https://maven.pkg.github.com/solusinegeri/java-lib</url>
      <releases>
        <checksumPolicy>ignore</checksumPolicy>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
````


## for run in local
>add settings.xml into home/{user}/.m2/

