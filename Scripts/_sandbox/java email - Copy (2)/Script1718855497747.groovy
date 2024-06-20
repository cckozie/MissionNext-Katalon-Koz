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
ssI = scriptStart.toInstant()

//define the email subject search string
subjectKey = '[Journey] Email Changed'

//define the email from search string
fromKey = 'Chris.Kosieracki@missionnext.org'

//mail server and credentials (Password is App password)
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
	 String from = message.getFrom()[0]
	 String subject = message.getSubject()
	 println("----- Found email from " + from + ", With subject of " + subject);
	 // Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND
	 mp = (Multipart)message.getContent();
	 Object p = mp.getBodyPart(i).getContent();
	 body = p.toString(); //object has the body content
	 subjectMatch = subject.indexOf(subjectKey)
	 fromMatch = from.indexOf(fromKey)
	 if(subjectMatch > 0 && fromMatch > 0) {
		 println("     ----- Body:" + body)
	 }
  	 i++
}

//close the store and folder objects
emailFolder.close(false);
store.close();


// Convert the email timestamp string top a time instant
def convertTime(dateTime) {
	arr = dateTime.split(',')
	day = arr[0].substring(0,3)
	arr[1] = arr[1].trim()
	month = arr[1].substring(0,3)
	d = Date.parse('MMM', month)
	numS = d.format('MM')
	if(numS.length() < 2) {
		numS = '0' + numS
	}
	space = arr[1].indexOf(' ')
	date = arr[1].substring(space + 1)
	arr[2] = arr[2].trim()
	arr2 = arr[2].split(' ')
	year = arr2[0]
	time = arr2[1].trim()
	colon = time.indexOf(':')
	hour = time.substring(0,colon)
	min = time.substring(colon+1)
	if(arr[2].indexOf('PM') > 0) {
		hour = hour.toInteger() + 12
	}
	df = "dd.MM.yyyy HH:mm:ss"
	dt = new Date().parse(df, date + '.' + numS + '.' + year + ' ' + hour + ':' + min + ':00')
	dtI = dateTime1.toInstant()
	return dtI
	
	long diffInMinutes = java.time.Duration.between(dt1, dt2).toMinutes();
	
	println(dt1)
	println(dateTime1)
	println(scriptStart)
	println(dt2)
	println(diffInMinutes)
	
}