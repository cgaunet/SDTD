sudo unzip apache-maven-3.5.2-bin.zip
sudo mv apache-maven-3.5.2/ /opt/maven
sudo ln -s /opt/maven/bin/mvn /usr/bin/mvn
sudo cp maven.sh /etc/profile.d/maven.sh
sudo chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
