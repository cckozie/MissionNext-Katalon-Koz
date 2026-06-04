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

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.lang.String;


////
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

//loopsMax = waitTime * 60 / 5	// Number of loops = minutes to wait * 60 seconds/min / 5 (wait time each loop)
emailFolder = store.getFolder("INBOX");
emailFolder.open(Folder.READ_ONLY);
messages = emailFolder.getMessages()
println(messages.size())


////
/*
def props = new Properties()
props.put("mail.smtp.host", "://example.com")
props.put("mail.smtp.port", "587")
props.put("mail.smtp.auth", "true")
props.put("mail.smtp.starttls.enable", "true")

def session = Session.getInstance(prop, new Authenticator() {
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("your-email@example.com", "your-password")
	}
})

try {
	def message = new MimeMessage(session)
	message.setFrom(new InternetAddress("your-email@example.com"))
	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient@example.com"))
	message.setSubject("Groovy Email Test")
	message.setText("This is a test email sent from Groovy!")

	Transport.send(message)
	println "Email sent successfully!"
} catch (MessagingException e) {
	e.printStackTrace()
}


*/

/*
def session = Session.getInstance(properties, new Authenticator() {
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password)
	}
})
*/

try {
	def message = new MimeMessage(emailSession)
	message.setFrom(new InternetAddress("cckozie@gamil.com"))
	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("cckozie@hotmail.com"))
	message.setSubject("Groovy Email Test")
	message.setText("This is a test email sent from Groovy!")

	Transport.send(message)
	println "Email sent successfully!"
} catch (MessagingException e) {
	e.printStackTrace()
}

