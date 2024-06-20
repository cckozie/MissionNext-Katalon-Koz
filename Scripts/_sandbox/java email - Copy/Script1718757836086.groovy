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

/*
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
*/

scriptStart = new Date()
println(scriptStart)
//Tue Jun 18 19:36:13 CDT 2024

String host = "pop.gmail.com"
String mailStoreType = "pop3"
String username = "cktest01mn@gmail.com"
String password = "sfesezftmpqxbbbd"
try {

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
		 System.out.println("---------------------------------");
		 System.out.println("Email Number " + (i + 1));
		 System.out.println("Subject: " + message.getSubject());
		 System.out.println("From: " + message.getFrom()[0]);
		 System.out.println("Text: " + message.getContent().toString());

// Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND
/*
		 mp = (Multipart)message.getContent();
		 Object p = mp.getBodyPart(i).getContent();
				 String q = p.toString();//object has the body content
				 println(q);//prints the body
*/
	  	 i++
	  }

	  //close the store and folder objects
	  emailFolder.close(false);
	  store.close();

	  } catch (NoSuchProviderException e) {
		 e.printStackTrace();
	  } catch (MessagingException e) {
		 e.printStackTrace();
	  } catch (Exception e) {
		 e.printStackTrace();
	  }


