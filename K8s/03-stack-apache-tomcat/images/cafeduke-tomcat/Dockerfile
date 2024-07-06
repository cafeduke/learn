FROM tomcat:9.0.90-jdk21
WORKDIR /usr/local/tomcat
RUN mkdir cert
COPY cert/ cert/
COPY conf/ conf/
COPY duke.war webapps/

