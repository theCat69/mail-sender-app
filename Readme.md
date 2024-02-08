# Mail sender app

## Purpose

This is a simple mail sender app for my vps server.  
It takes REST Http requests to send an email to a decicated address.  
I use it for the contact section on my portfolio website : https://thecatmaincave.com  
I also used good practices of DDD and Spring testing environment as an example project.

## Prerequist

If you want to use this project for yourself this is free of any license 
(I will probably use the opensource gnu license)  

Technically for this to run properly you will need :
- Java 21 or newer
- Maven 3.6.x or newer
- SMTP server 

## Setup for yourself

Setup an SMTP server you can reach (I personally use postfix with sasl authentification).  
Create an application-prod.yml inside src/main/resources/ folder.  
Copy the content from application-dev.yml and fill it up with your own value.  
For DKIM verification to work the "from" value should use your domain name.

Build the project : 
```shell
mvn -B -DskipTests clean package 
```

Run unit test :
```shell
mvn test -P ut
```

Run integration test :
```shell
mvn test -P it
```

Build for production :
```shell
mvn -B -DskipTests clean package -P prod
```

## Deployment

This application is deployed on Ubuntu.  
You can check example on how to configure the systemd service in the app-scripts folder.  
I use jenkins as my CI and it is deploying the app using shell scripts found in jenkins-scripts folder.  
