from ubuntu:14.04
MAINTAINER kjansen
run echo "create DataManager container"
run ping -c 1 www.google.com
run echo "Network is oke"
run sudo apt-get update
run apt-get -y install openjdk-7-jdk
run apt-get -y install openssh-server
run apt-get -y install librxtx-java
run ln -s /usr/share/java/RXTXcomm.jar /usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/RXTXcomm.jar
run service ssh start
run useradd datamanager -m -d /home/datamanager -N -p "datamanager"
run echo 'export JAVA_HOME=/usr/lib/jvm/jre-1.7.0-openjdk.x86_64' > /etc/profile.d/java.sh
add DataManager-0.1-shadow.jar /tmp/DataManager-0.1-shadow.jar

CMD ["java", "-jar", "/tmp/DataManager-0.1-shadow.jar"]

EXPOSE 22