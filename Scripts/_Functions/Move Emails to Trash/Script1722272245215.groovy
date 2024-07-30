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

Folder inbox = store.getFolder("INBOX");
//Folder inbox = store.getFolder("[Gmail]/Trash")
inbox.open(Folder.READ_WRITE);
int numOfMessages = inbox.getMessageCount();
for (int i = 1; i<=numOfMessages; i++){
	Message message = inbox.getMessage(i);
	message.setFlag(Flags.Flag.DELETED, true);
	System.out.println(message.getSubject());
}
inbox.close(true);
store.close();
System.exit(0)
//create the folder object and open it

//Folder emailFolder = store.getFolder("INBOX");
//emailFolder.open(Folder.READ_ONLY);
Folder trashFolder = store.getFolder("OLD");
//trashFolder.open(Folder.READ_WRITE);

Message[] messages = []
Folder emailFolder = store.getFolder("INBOX");
emailFolder.open(Folder.READ_ONLY);
messages = emailFolder.getMessages()

//Message[] messages = {message};
//inbox.copyMessages(messages, trashFolder);
emailFolder.copyMessages(messages, trashFolder);
trashFolder.close(true);

//emailFolder.expunge(messages)

//close the folder forcing the flagged emails to be deleted
emailFolder.close(true);
store.close();


