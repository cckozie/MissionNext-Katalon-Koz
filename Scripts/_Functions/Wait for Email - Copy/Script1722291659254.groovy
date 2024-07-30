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
scriptInstant = scriptStart.toInstant()
println('========= scriptStart is ' + scriptStart)
println('========= toInstant is ' + scriptInstant)

// Initialize the return code
GlobalVariable.returnCode = ''

if (binding.hasVariable('varUsername')) {
	user = varUsername
} else {
	user = 'cktest06ep'
}

//define the email subject search string
//subjectKey = '[Journey] Email Changed'
if (binding.hasVariable('subjectKey')) {	
	subjectKey = varSubjectKey
} else {
	subjectKey = 'Approval request'
}

println('Email subjectKey is ' + subjectKey + ', and username is ' + user)

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
Folder emailFolder
Message[] messages = []

loopsMax = 24	//We'll wait 2 minutes for the email before bailing
loops = 0
while(messages.length == 0 && loops <= loopsMax) {
	emailFolder = store.getFolder("INBOX");
	emailFolder.open(Folder.READ_ONLY);
	messages = emailFolder.getMessages()
	if(messages.length == 0) {
		emailFolder.close(false);
		WebUI.delay(5)
		loops ++
	}
}

if(messages.length > 0) {
	
	println("messages.length---" + messages.length);
	
	msgFound = false
	for(Message message in messages) {
	//	 Message message = messages[i];
		 String from = message.getFrom()[0]
		 String subject = message.getSubject()
		 println("----- Found email from " + from + ", With subject of " + subject);
		 
		 // Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND		 
		 Object content = message.getContent();
		 if(content instanceof String) {
			println('It is a string')
			body = message.getContent();
		 } else if(content instanceof Multipart) {
			println('It is multipart')		 
			mp = (Multipart)message.getContent();
			Object p = mp.getBodyPart(0).getContent();
			body = p.toString(); //object has the body content
		 }
		 subjectMatch = subject.indexOf(subjectKey)
		 fromMatch = from.indexOf(fromKey)
		 if(subjectMatch > 0 && fromMatch > 0) {
			 println("     ----- Body:" + body)
			 sentPos = body.indexOf('Sent:')
			 toPos = body.indexOf('To:')
			 msgBody = body
			 msgTime = body.substring(sentPos + 6, toPos)
			 if(msgBody.indexOf(user) >= 0) {
				 msgFound = true
				 println('>>>>> Username ' + user + ' was found in the email.')
			 }
		 }
		 
	}
	
	if(msgFound) {
		 msgInstant = convertTime(msgTime)
		 long diffInMinutes = java.time.Duration.between(msgInstant, scriptInstant).toMinutes();
		 println('>>>>> The ' + subjectKey + ' message was sent ' + diffInMinutes + ' minutes after the initiating event <<<<<')
		 println(msgBody)
  		 //update the return code
		 GlobalVariable.returnCode = 'found'
	}

	//close the folder objects
	emailFolder.close(false);
	
}	

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
	println('======== dt is ' + dt)
	dtI = dt.toInstant()
	println('======== dtI is ' + dtI)
	return dtI
	
}

/*
________________________________
From: MissionNext Staff <do_not_reply@missionnext.org>
Sent: Monday, July 22, 2024 5:31 PM
To: Chris Kosieracki <Chris.Kosieracki@missionnext.org>
Subject: Approval request

The user just registered and is waiting for approval.
LOGIN INFORMATION

*/


/*
Sent: Friday, June 14, 2024 9:38 PM
To: Chris Kosieracki <Chris.Kosieracki@missionnext.org>
Subject: [Journey] Email Changed

Hi cktest01, This notice confirms that your email address on Journey was changed to Cktest01@gmail.com<mailto:Cktest01@gmail.com>. If you did not change your email, please contact the Site Administrator at Brad.benson@missionnext.org<mailto:Brad.benson@missionnext.org> This email has been sent to Cktest01@missionnext.org<mailto:Cktest01@missionnext.org> Regards, All at Journey https://journey.missionnext.org<https://journey.missionnext.org/wp-json/easy-wp-smtp/v1/e/ZGF0YSU1QmVtYWlsX2xvZ19pZCU1RD0xMTMmZGF0YSU1QmV2ZW50X3R5cGUlNUQ9Y2xpY2stbGluayZkYXRhJTVCb2JqZWN0X2lkJTVEPTE1NyZkYXRhJTVCdXJsJTVEPWh0dHBzJTI1M0ElMjUyRiUyNTJGam91cm5leS5taXNzaW9ubmV4dC5vcmcmaGFzaD04MzZjMGYxMDQ3ZmE2MGIxYTI2ZGM5MDIyZTIyMThkNGNmNjU0ZmYzNDJkNGYxMDRmNDJiNmE2MjQxOGU1YzM5>[https://journey.missionnext.org/wp-json/easy-wp-smtp/v1/e/ZGF0YSU1QmVtYWlsX2xvZ19pZCU1RD0xMTMmZGF0YSU1QmV2ZW50X3R5cGUlNUQ9b3Blbi1lbWFpbCZoYXNoPTNhMTFlMmQ5YjY3N2YwODU0M2Q2MzY0M2EwZmM5ZjhjZGJlYzVlNjk0MWRiMTU0NzgwM2E1NDk3YWJkODEwOWM=]
*/