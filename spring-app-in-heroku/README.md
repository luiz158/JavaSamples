javapp in heroku deploy 
=======================

**reference: **[Testing java web application deployed in heroku](https://devcenter.heroku.com/articles/getting-started-with-java#introduction)


Getting Started with Java on Heroku

Create an application
===================================================

run command in terminal
```java
$ mvn archetype:generate -DarchetypeArtifactId=maven-archetype-webapp
```

then output should be:
``` 
...
[INFO] Generating project in Interactive mode
Define value for property 'groupId': : br.com.myjapp
Define value for property 'artifactId': : myjapp
```

**Configure Maven to download Webapp Runner**

Edit pom.xml file and add the code block for plugins:
```xml
<plugins>
	<plugin>
		<artifactId>maven-eclipse-plugin</artifactId>
		<version>2.9</version>
		<configuration>
			<additionalProjectnatures>
				<projectnature>org.springframework.ide.eclipse.core.springnature</projectnature>
			</additionalProjectnatures>
			<additionalBuildcommands>
				<buildcommand>org.springframework.ide.eclipse.core.springbuilder</buildcommand>
			</additionalBuildcommands>
			<downloadSources>true</downloadSources>
			<downloadJavadocs>true</downloadJavadocs>
		</configuration>
	</plugin>
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-compiler-plugin</artifactId>
		<version>2.5.1</version>
		<configuration>
			<source>${java-version}</source>
			<target>${java-version}</target>
			<compilerArgument>-Xlint:all</compilerArgument>
			<showWarnings>true</showWarnings>
			<showDeprecation>true</showDeprecation>
		</configuration>
	</plugin>
	<plugin>
		<groupId>org.codehaus.mojo</groupId>
		<artifactId>exec-maven-plugin</artifactId>
		<version>1.2.1</version>
		<configuration>
			<mainClass>org.test.int1.Main</mainClass>
		</configuration>
	</plugin>


	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-dependency-plugin</artifactId>
		<version>2.3</version>
		<executions>
			<execution>
				<phase>package</phase>
				<goals>
					<goal>copy</goal>
				</goals>
				<configuration>
					<artifactItems>
						<artifactItem>
							<groupId>org.mortbay.jetty</groupId>
							<artifactId>jetty-runner</artifactId>
							<version>7.4.5.v20110725</version>
							<destFileName>jetty-runner.jar</destFileName>
						</artifactItem>
					</artifactItems>
				</configuration>
			</execution>
		</executions>
	</plugin>

	<plugin>
		<groupId>org.codehaus.mojo</groupId>
		<artifactId>appassembler-maven-plugin</artifactId>
		<version>1.1.1</version>
		<configuration>
			<assembleDirectory>target</assembleDirectory>
			<programs>
				<program>
					<mainClass>WorkerProcess</mainClass>
					<name>worker</name>
				</program>
				<program>
					<mainClass>OneOffProcess</mainClass>
					<name>oneoff</name>
				</program>
			</programs>
		</configuration>
		<executions>
			<execution>
				<phase>package</phase>
				<goals>
					<goal>assemble</goal>
				</goals>
			</execution>
		</executions>
	</plugin>
	
</plugins>
```

create file **system.properties** in root directory with the line:
```
java.runtime.version=1.7
```

Run your application for Test
=============================

First make build aplication
```shell
$ mvn package
```

And then run your app using the java command:
```shell
$ java -jar target/dependency/webapp-runner.jar target/*.war
```

> That’s it. Your application should start up on port 8080.
> Note: if you need your WAR file to be expanded before launching you can add the --expand-war option before target/*.war



Deploy your application to Heroku
=================================

**Prerequisites**
* Create your account in Heroku(if you don't already): https://dashboard.heroku.com/
* Create ssh-key in local machine and add in your heroku account: http://guides.beanstalkapp.com/version-control/git-on-windows.html
* Download and install heroku toolbelt: https://toolbelt.heroku.com/
* Create you account in github(if you don't already): https://github.com
* Add ssh-key in you accont github.

==================================

**Create a Procfile**

Create a Procfile file in root directory of application with a single line:
```
web:    java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
```

**Deploy to Heroku**

Commit your changes to Git:
```shell
$ git init
$ git add .
$ git commit -m "Ready to deploy"
```

Create the app:

```shell
$ heroku create
```

then output should be:
```
Creating high-lightning-129... done, stack is cedar
http://high-lightning-129.herokuapp.com/ | git@heroku.com:high-lightning-129.git
Git remote heroku added
```

Deploy your code:

```shell
$ git push heroku master
```

then output should be:
```
Counting objects: 227, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (117/117), done.
Writing objects: 100% (227/227), 101.06 KiB, done.
Total 227 (delta 99), reused 220 (delta 98)

-----> Heroku receiving push
-----> Java app detected
-----> Installing Maven 3.0.3..... done
-----> Installing settings.xml..... done
-----> executing .maven/bin/mvn -B -Duser.home=/tmp/build_1jems2so86ck4 -s .m2/settings.xml -DskipTests=true clean install
       [INFO] Scanning for projects...
       [INFO]
       [INFO] ------------------------------------------------------------------------
       [INFO] Building webappRunnerSample Maven Webapp 1.0-SNAPSHOT
       [INFO] ------------------------------------------------------------------------
       ...
       [INFO] ------------------------------------------------------------------------
       [INFO] BUILD SUCCESS
       [INFO] ------------------------------------------------------------------------
       [INFO] Total time: 36.612s
       [INFO] Finished at: Tue Aug 30 04:03:02 UTC 2011
       [INFO] Final Memory: 19M/287M
       [INFO] ------------------------------------------------------------------------
-----> Discovering process types
       Procfile declares types -> web
-----> Compiled slug size is 4.5MB
-----> Launching... done, v5
       http://pure-window-800.herokuapp.com deployed to Heroku
```

The application is now deployed. Ensure that at least one instance of the app is running:
```shell
heroku ps:scale web=1
```

Congratulations! 
Your web app should now be up and running on Heroku. 
For open it in your browser with:
```shell
$ heroku open
```       

Help - Deploy in Heroku 
=======================

https://devcenter.heroku.com/articles/java-webapp-runner#create-a-procfile


View run app in Heroku:
=======================

http://myjapp.herokuapp.com/

