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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.*
/*
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
*/
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;



@GrabConfig(systemClassLoader=true)
@Grapes(
	@Grab(group='javax.mail', module='mail', version='1.4.4')
)
// setup connection
Properties props = new Properties();
def host = "gmail.com";
username = "cktest01mn@gmail.com"
password = "sfesezftmpqxbbbd"
def provider = "pop3";

// Connect to the POP3 server
Session session = Session.getDefaultInstance props, null
Store store = session.getStore provider
store.connect host, username, password

// Open the folder
//Folder inbox = store.getFolder 'INBOX'
Folder inbox = store.getFolder 'INBOX'
if (!inbox) {
	println 'No INBOX'
	System.exit 1
}

inbox.open(Folder.READ_ONLY)

// Get the messages from the server
Message[] messages = inbox.getMessages()
messages.eachWithIndex { m, i ->
	println "------------ Message ${i+1} ------------"
	m.writeTo(System.out)
}
// Close the connection
// but don't remove the messages from the server
inbox.close false
store.close()


