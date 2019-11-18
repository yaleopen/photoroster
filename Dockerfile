FROM amd64/tomcat:8-jre8

# workaround for apt-get breaking because of unreachable columbia mirror
RUN rm -f /etc/apt/sources.list.d/jessie-backports.list
RUN /bin/echo -e "deb http://ftp.us.debian.org/debian/ jessie main contrib non-free\ndeb http://security.debian.org/ jessie/updates main contrib non-free" > /etc/apt/sources.list

# copy additional database jar files
RUN cd /usr/local/tomcat/lib && \
    wget -nv https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.43.tar.gz && \
    tar xzf mysql-connector-java-5.1.43.tar.gz --strip-components 1 mysql-connector-java-5.1.43/mysql-connector-java-5.1.43-bin.jar && \
    wget -nv http://backpack.its.yale.internal/vendor/oracle/jdbc/12.1.0.1/ojdbc7.jar && \
    rm -f mysql-connector-java-5.1.43.tar.gz

# clean up default apps
RUN cd /usr/local/tomcat/webapps && \
    rm -rf /usr/local/tomcat/webapps/*

# install aws-cli
RUN apt-get update && apt-get install -y python-minimal && \
    wget -nv "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" && \
    unzip awscli-bundle.zip && \
    ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws

RUN rm /etc/java-8-openjdk/accessibility.properties

# copy config setup scripts
COPY docker/*_config.sh /root/

# copy config templates
#COPY docker/server.xml docker/context.xml.template docker/roster.properties.template docker/logging.properties /usr/local/tomcat/conf/
COPY docker/server.xml docker/context.xml docker/roster.properties docker/logging.properties /usr/local/tomcat/conf/

# set max heap size and other JVM options
ENV JAVA_OPTS '-Xmx256m -Djava.awt.headless=true -Doracle.jdbc.timezoneAsRegion=false -Djava.util.concurrent.ForkJoinPool.common.parallelism=16'

# deploy war
#COPY photoroster.war /usr/local/tomcat/webapps/
COPY target/photoroster.war /usr/local/tomcat/webapps/
# Go
#CMD /root/import_config.sh && catalina.sh run
CMD catalina.sh run