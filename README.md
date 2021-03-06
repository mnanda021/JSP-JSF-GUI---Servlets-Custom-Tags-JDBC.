# JSP-JSF-GUI---Servlets-Custom-Tags-JDBC.

Part I:  
1. Setting up the Apache Tomcat Server  Configure the TOMCAT web server (or any other web server that supports the JSP) for testing the JSPs and Custom tags.  

2. Deploy and test the JSP  Deploy and test one simple JSP example and custom tag example from the TOMCAT web server page.  (Please read the lecture notes for setting up the JSP tag library.) 

--------------------------------------------------------------------------------------------  

Part II:  
1. Develop JSP  Design and develop Java JSPs to process the Book client requests. 
    
    1. Develop a dbConnection JavaBean class that will be used to connect to the Book database.   You may call the dbConnection from a JSP using &lt;java:useBean ...>  (Use a Http session in the JSP to store the database login, password, and database connection.) 
    
    2. Develop a dbQuery JSP to process the data query request from the client.  You should try to create the similar GUI as in the previous assignments.   The JSP should use a custom tag to process the data query from the database to hide the SQL operation details.  The custom tag should use the same database connection object stored in the Http session. 
    
    3. Develop a dbUpdate JSP to process the data update request from the client. You should try to create the similar GUI as in the previous assignments.  The JSP should use a custom tag to process the data update from the database to hide the SQL operation details.    - The custom tag should use the same database connection object stored in the Http session. 
    

2. Deploy and Test the Three-Tier JSP Client-Servlet/Custom Tags Server-Database System  Combine the above into a three-tier client/application server/database application.  You should try to reuse the java code developed in assignment 1 and 2.  - Deploy your JSP project in a war file and test it in a TOMCAT web server and your Book database.
