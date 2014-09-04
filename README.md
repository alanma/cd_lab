Continuous Delivery Lab
=======================

Before you start make sure all prerequisites are met.
 
* Java 
* Git
* Jenkins - 8080
* Nexus - 8081


The purpose of this lab is to build a simple Continuous Delivery Pipeline. All heavy lifting 
is done by Gradle tasks. We will use Jenkins to orchestrate the build. The last step of the build 
pipeline, deploy to test, will require a manual trigger.

When the pipeline is complete it will consist of the following build steps:

1. `todo_initial` - compile and run unit tests 
2. `todo_ineg_test` - run integration test
3. `todo_code_quality` - assert metrics based code quality
4. `todo_distribution` - upload artifact to Nexus
5. `todo_deploy_test` - deploy webapp to test server

Resources
=========

* [Gradle User Guide](http://www.gradle.org/docs/current/userguide/userguide_single.html)
* [Jenkins Docs](https://wiki.jenkins-ci.org/display/JENKINS/Use+Jenkins)
* [Repository Management with Nexus](http://books.sonatype.com/nexus-book/reference/)
* [Building a Continuous Delivery Pipeline with Gradle and Jenkins](http://www.infoq.com/presentations/cd-gradle-jenkins)

Lab
===

1. Start Jenkins -> java -jar /usr/local/opt/jenkins/libexec/jenkins.war
2. Start Nexus   -> nexus start  
3. Clone Github Project -> [Github](https://github.com/kallestenflo/cd_lab) 

1. Compile and Run Unit Tests
-----------------------------

You will need the following Jenkins plugins: 

* Git plugin
* Gradle plugin
* Build Name Setter Plugin
* Clone Workspace SCM Plug-in

1. Create a new free-style build in Jenkins named `todo_initial`.
2. Get project from your local Git repository (file://.../cd_lab)
3. Compile code and run unit tests using Gradle tasks `clean test`
4. The name of the build should be todo#<build number> (see Build Environment)
5. Unit test results should be published (available in **/build/test-results/unit/*.xml)
6. Archive all files in the workspace for cloning (all files should be included)


2. Run Integration Tests
------------------------

You will need the following Jenkins plugins: 

* Parameterized Trigger plugin

1. Create a new build in Jenkins named `todo_integ_test`. 
2. This build step should be triggered by the previous build step with the predefined parameter SOURCE_BUILD_NUMBER (the build number used in the initial step)
3. Set the build name to name created in the previous step (todo#${ENV,var="SOURCE_BUILD_NUMBER"})
4. Clone the workspace archived by the previous step (Most Recent Successful Build)
5. Run integration tests using Gradle task `integrationTest`
6. Archive all files in the workspace for cloning (all files should be included)


3. Verify Code Quality
----------------------
You will need the following Jenkins plugins: 

* JaCoCo plugin

1. Create a new build in Jenkins named `todo_code_quality`.
2. This build step should be triggered by the previous build step with the predefined parameter SOURCE_BUILD_NUMBER (the build number used in the initial step)
3. Apply the JaCoCo plugin to the output of the previous steps


4. Upload artifact to Nexus
----------------------------

1. Create a new build in Jenkins named `todo_distribution`
2. This build step should be triggered by the previous build step with the predefined parameter SOURCE_BUILD_NUMBER
3. Publish artifact to Nexus using gradle (publish task)


5. Configure Build Pipeline
---------------------------
You will need the following Jenkins plugins: 

* Build Pipeline Plugin

1. Create a new pipeline named `TODO Pipeline` with `todo_initial` as its initial job.


6. Deploy to test environment
-----------------------------

1. [Download](http://tomcat.apache.org/download-70.cgi) and install Tomcat 7 
2. Configure server port in $TOMCAT_HOME/conf/server.xml

  ```
  <Service name="Catalina">
  <Connector port="9292" protocol="HTTP/1.1" />
  ```
3. Configure tomcat users in $TOMCAT_HOME/conf/tomcat-users.xml 

  ```
  <tomcat-users>
    <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <user username="manager" password="manager" roles="manager-gui,manager-script"/>
  </tomcat-users>
  ```
4. Start Tomcat $TOMCAT_HOME/bin/startup.sh 
5. Create a new build in Jenkins named `todo_deploy_test`
6. Publish to test server using gradle task `cargoRedeployRemote` using switch `-Penv=test` 
7. This build step should be manually triggered as a `post-build action` by the previous build step


7. Functional tests
-------------------

1. [Download](https://code.google.com/p/selenium/wiki/ChromeDriver) Chrome driver
2. Enable driver in `gradle.properties` with the correct path for your machine
3. Add a new build step, `todo_functional_tests` between the `todo_code_quality` and `todo_distribution` step
4. Configure Gradle to run task `localFunctionalTest`

 


