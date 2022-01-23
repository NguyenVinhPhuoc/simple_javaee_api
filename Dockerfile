FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh root root --silent
COPY ./target/bookstore-back.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]