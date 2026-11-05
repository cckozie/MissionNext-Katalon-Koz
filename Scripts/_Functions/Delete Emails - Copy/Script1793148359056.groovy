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

// Just reading any exitsting emails will cause them to go to trash because of a gmail setting

//Check to see if we're writing printed output also to a file
writeFile = false
if(GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile
	println(myFile)
	outFile = new java.io.File(myFile)
	writeFile = true
}

outText = 'Deleting all emails in the inbox'

println(outText)

if(writeFile) {
	outFile.append(outText + '\n')
}

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

emailFolder = store.getFolder("INBOX");
emailFolder.open(Folder.READ_ONLY);
messages = emailFolder.getMessages()

if(messages.length > 0) {

	outText = messages.length + ' emails were found in the inbox'
	
	println(outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}

	count = 0
	for(Message message in messages) {
		 String from = message.getFrom()[0]
		 String subject = message.getSubject()
		 
		 outText = "Found email from " + from + ", With subject of " + subject
		 
		 println(outText)
		
		 if(writeFile) {
			  outFile.append(outText + '\n')
		 }
		 
		 // Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND
		 Object content = message.getContent()

		 if(content instanceof String) {
			println('It is a string')
			body = message.getContent();
		 } else if(content instanceof Multipart) {
			println('It is multipart')
			mp = (Multipart)message.getContent();
			Object p = mp.getBodyPart(0).getContent();
			body = p.toString(); //object has the body content
		 }
		 
		 count++

	}
	
	outText = count + ' emails were read and deleted'
	
	println(outText)
   
	if(writeFile) {
		 outFile.append(outText + '\n')
	}
	
} else {
	
	outText = 'No emails were found in the inbox'
	
	println(outText)
   
	if(writeFile) {
		 outFile.append(outText + '\n')
	}

}

//close the folder objects
emailFolder.close(false);
store.close();


