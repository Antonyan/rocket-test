## MacOS:
## Prerequisites Software/Tools
 - Git [ http://git-scm.com/downloads ]

 - Java 8 [ http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html ]

 - TestNG [ http://testng.org/doc/ ]

 - Gradle [https://gradle.org/]

 - Selenide [http://selenide.org/]

 - some IDE [I prefer IntelliJ IDEA]
 
 - for mobile testing [brew install carthage]

## Getting Started

1. Install GIT:

2. Install Java: https://java.com/en/download/help/mac_install.xml

3. Install Gradle:

 - brew install gradle

## Then:

4. Open the terminal / console and Initialize the project using GIT; then clone the project using below command: 
git clone ${repository_url}
5. Once the project is cloned successfully, open the terminal and navigate to the project root directory.
6. And run the following command to start the tests:
 -  ./gradlew test

## Gradle: Use rocket-test as a dependency for other projects.
Currently, the rocket-test is not available on remote maven central repository.
Therefore, need to install the rocket-test in local .m2 repository in order to use the dependency with other project.
1. Open rocket-test on your machine in IDE (Intellij)
2. Navigate to Gradle (on right pane) -> Tasks -> other and execute install task.
3. Open your Gradle Project and reconfigure build.gradle file:
   - set compile group: 'rocket-test', name: 'rocket-test-core', version: '1.0' dependency in dependency section
   - set mavenLocal() in repositories section. It allows use the rocket-test dependency from your local maven .m2 repository.

## Windows:
To run mysql dump successfully install mysql client.

Add to PATH env variable path to mysql.exe client folder.

 -  ./gradlew test
