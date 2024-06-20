import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.lang.String;

import groovy.time.*

import java.time.Duration;
import java.time.Instant;

scriptStart = new Date()
println(scriptStart)
dt2 = scriptStart.toInstant()
myDate = 'Tue Jun 18 22:29:31 CDT 2024'
emailDate = 'Monday, June 18, 2024 9:01 PM'

arr = emailDate.split(',')
println(arr)
day = arr[0].substring(0,3)
println(day)
arr[1] = arr[1].trim()
month = arr[1].substring(0,3)
println(month)
d = Date.parse('MMM', month)
numS = d.format('MM')
if(numS.length() < 2) {
	numS = '0' + numS
}
println(numS)
space = arr[1].indexOf(' ')
date = arr[1].substring(space + 1)
println(date)
arr[2] = arr[2].trim()
println(arr[2])
arr2 = arr[2].split(' ')
println(arr2)
year = arr2[0]
println(year)
time = arr2[1].trim()
println(time)
colon = time.indexOf(':')
hour = time.substring(0,colon)
println(hour)
min = time.substring(colon+1)
println(min)
if(arr[2].indexOf('PM') > 0) {
	hour = hour.toInteger() + 12
	println(hour)
}
df = "dd.MM.yyyy HH:mm:ss"
dateTime1 = new Date().parse(df, date + '.' + numS + '.' + year + ' ' + hour + ':' + min + ':00')
println(dateTime1)
dt1 = dateTime1.toInstant()
//dt2 = scriptStart.toString()
//dateTime2 = new Date().parse(df, dt2)

//println(dateTime2)

et = scriptStart - dateTime1
println(et)
println(et/60)
println(et/60/60)

println(TimeCategory.minus(scriptStart, dateTime1).minutes)
println(TimeCategory.minus(scriptStart, dateTime1).hours)
println(TimeCategory.minus(scriptStart, dateTime1).days)

long diffInMinutes = java.time.Duration.between(dt1, dt2).toMinutes();

println(dt1)
println(dateTime1)
println(scriptStart)
println(dt2)
println(diffInMinutes)

//println(et)

//[Journey] Email Changed (subject)

/*

host = "pop.gmail.com"
mailStoreType = "pop3"
username = "cktest01mn@gmail.com"
password = "sfesezftmpqxbbbd"


//create properties field
Properties properties = new Properties();

properties.put("mail.pop3.host", host);
properties.put("mail.pop3.port", "995");
properties.put("mail.pop3.starttls.enable", "true");
Session emailSession = Session.getDefaultInstance(properties);
  
//create the POP3 store object and connect with the pop server
Store store = emailSession.getStore("pop3s");

store.connect(host, username, password);

//create the folder object and open it
Folder emailFolder = store.getFolder("INBOX");
emailFolder.open(Folder.READ_ONLY);

// retrieve the messages from the folder in an array and print it
Message[] messages = emailFolder.getMessages();
System.out.println("messages.length---" + messages.length);

i = 0
while (i <  messages.length) {
	 Message message = messages[i];
	 println("---------------------------------");
	 println("Email Number " + (i + 1));
	 println("Subject: " + message.getSubject());
	 println("From: " + message.getFrom()[0]);
	 println("Text: " + message.getContent().toString());

// Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND
	 mp = (Multipart)message.getContent();
	 Object p = mp.getBodyPart(i).getContent();
	 q = p.toString();//object has the body content
	 println(q);//prints the body

  	 i++
}

//close the store and folder objects
emailFolder.close(false);
store.close();

*/