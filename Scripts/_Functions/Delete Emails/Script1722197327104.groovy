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
//Message[] messages = []

emailFolder = store.getFolder("INBOX");
emailFolder.open(Folder.READ_WRITE);

println(emailFolder.getDeletedMessageCount() + ' deleted emails')

BufferedReader reader = new BufferedReader(new InputStreamReader(
	System.in));
 // retrieve the messages from the folder in an array and print it
 Message[] messages = emailFolder.getMessages();
 System.out.println("messages.length---" + messages.length);
 for (int i = 0; i < messages.length; i++) {
	Message message = messages[i];
	System.out.println("---------------------------------");
	System.out.println("Email Number " + (i + 1));
	System.out.println("Subject: " + message.getSubject());
	System.out.println("From: " + message.getFrom()[0]);

	String subject = message.getSubject();
//	System.out.print("Do you want to delete this message [y/n] ? ");
//	String ans = reader.readLine();
//	if ("Y".equals(ans) || "y".equals(ans)) {
   // set the DELETE flag to true
   message.setFlag(Flags.Flag.DELETED, true);
   System.out.println("Marked DELETE for message: " + subject);
//	} else if ("n".equals(ans)) {
//   break;
//	}
 }

/*
messages = emailFolder.getMessages()
println(messages.length + ' emails found.');
count = 0
Flags deleted = new Flags(Flags.Flag.DELETED);
emailFolder.setFlags(messages, deleted, true);
*/
/*
for(Message message in messages) {
	String from = message.getFrom()[0]
	println('Email is from ' + from)
	if(from.indexOf(fromKey) >= 0) {
		String subject = message.getSubject()
		message.setFlag(Flags.Flag.DELETED, true);
		println("----- Email from " + from + ", With subject of " + subject + ' was flagged for deletion.');
		count ++
	}
}
println('=====> ' + count + ' emails were deleted.')
*/
//close the folder forcing the flagged emails to be deleted
emailFolder.close(true);
store.close();


