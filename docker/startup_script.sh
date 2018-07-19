# a simple shell script to provision the automation environment and start the execution 

# Start the selenium server in the background 
nohup /opt/bin/entry_point.sh &
# create a temp test directory 
sudo mkdir test_run

cd test_run
# Clone the repo from github 
sudo git clone https://github.com/bharathkumar-gopalan/selenium-demo

# switch to the base maven project directory 
cd selenium-demo/ui-automation

# start the browser automation in firefox 
sudo mvn clean test -Dbrowser=chrome
