#!/bin/bash

mvn clean package
/home/kaironst/.apache-tomcat-11/bin/shutdown.sh
rm -rf /home/kaironst/.apache-tomcat-11/webapps/*;
mv /home/kaironst/trabalhos/lpoo2/projeto-lpoo2-jpa/target/projeto-1.0-SNAPSHOT.war /home/kaironst/.apache-tomcat-11/webapps/projeto-1.0-SNAPSHOT.war;
/home/kaironst/.apache-tomcat-11/bin/startup.sh
