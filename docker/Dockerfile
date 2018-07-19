# Simple docker file for the flaconi demo project 
# use the selenium standalone chrome version as the base OS image
# please note that this is a very simple docker file that pulls the needed dependenies from git 
# I have tested this in mac and the execution results show up in commandline 
# For now this does not do the task of copying reports etc  
FROM selenium/standalone-chrome:3.13.0-argon
RUN sudo apt-get update 
# Install JDK version 8 
RUN sudo apt-get install default-jdk -y

# install git command line interface 
RUN sudo apt-get install git-core -y

# install maven 
RUN sudo apt-get install maven -y

# the main entry point for the application 
ADD startup_script.sh .
ENTRYPOINT ["bin/sh","startup_script.sh"]


