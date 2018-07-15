# selenium-demo
A simple automation framework based on selenium (Works with firefox and Chrome at the moment) 

### Prerequisites
This project needs maven to be installed and needs selenium server to be running in standalone or grid mode 

### Invocation 
The tests can be directly invoked from commandline by calling mvn clean test (Assuming that the selenium server is running in localhost:4444) 

### Parameters
The project accepts three commandline parameters 
1. browser - The browser to run the automation framework. The allowed options are chrome and firefox and if no parameter is give the default is taken as chrome 
2. env - The environment to run the code on (currently the only one available and default environment option is prod , This can be ignored) 
3. server - The selenium server url . The default server is http://localhost:4444/wd/hub

For example the command **mvn clean test -Dbrowser=firefox** would run the suite in firefox browser 


### Test flow automated
1. Navigate to the home page and search for a given product (for example Calvin Klein) 
2. In the auto suggest values , select a given product and navigate to the product page 
3. Select a give SKU of the product and click add to cart 
4. Verify if the SKU selected is present in the cart and the cart quantity is incremented by 1 
5. Clear the cart and navigate to the home page and repeat the test for different data 

The test case is data driven 

### Issues noted 
Around 30% of the time the product auto suggest values are not displayed when a text is entered in the search box(possibly the operation is timed out) , The results in the test suite failing .


