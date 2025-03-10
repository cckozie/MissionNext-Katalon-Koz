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
import java.io.*
import java.util.Properties as Properties
import javax.mail.*
import javax.mail.internet.*
import java.lang.String as String
import groovy.time.*
import java.time.Duration as Duration
import java.time.Instant as Instant

//************* Time to wait in minutes ****************
waitTime = 4 //I've seen it take 10+ minutes

// Set the calling parameter values or defaults
if (binding.hasVariable('varSearchKey')) {
    searchKey = varSearchKey
} else {
    searchKey = 'NA'
}

if (binding.hasVariable('varSubjectKey')) {
    subjectKey = varSubjectKey
} else {
    subjectKey = 'Approval request'
}

if (binding.hasVariable('varFromKey')) {
    fromKey = varFromKey
} else {
    fromKey = 'Chris.Kosieracki@missionnext.org'
}

msgCount = 2

//mail server and credentials (Password is App password)
host = 'pop.gmail.com'

mailStoreType = 'pop3'

username = 'cktest01mn@gmail.com'

password = 'sfesezftmpqxbbbd'

//create properties field
Properties properties = new Properties()

properties.put('mail.pop3.host', host)

properties.put('mail.pop3.port', '995')

properties.put('mail.pop3.starttls.enable', 'true')

Session emailSession = Session.getDefaultInstance(properties)

//create the POP3 store object and connect with the pop server
Store store = emailSession.getStore('pop3s')

store.connect(host, username, password)

//create the folder object and open it
Folder emailFolder

Message[] messages = []

loopsMax = ((waitTime * 60) / 5) // Number of loops = minutes to wait * 60 seconds/min / 5 (wait time each loop)

loops = 0

emailFolder = store.getFolder('INBOX')

emailFolder.open(Folder.READ_ONLY)

messages = emailFolder.getMessages()

if (messages.length == 0) {
    emailFolder.close(false)
}

while ((messages.length < msgCount) && (loops <= loopsMax)) {
	println(messages.length)
	
    WebUI.delay(5)

    emailFolder.open(Folder.READ_ONLY)

    messages = emailFolder.getMessages()

    if (messages.length < msgCount) {
        emailFolder.close(false)
    }
    
    loops++
}

if (messages.length > 0) {
    println('messages.length---' + messages.length)

    msgFound = false

    for (Message message : messages) {
        String from = message.getFrom()[0]

        String subject = message.getSubject()

        // Get the full message text - THIS WILL CAUSE THE EMAIL TO NO LONGER BE FOUND		 
        Object content = message.getContent()

        if (content instanceof String) {
            println('It is a string')

            body = message.getContent() //object has the body content
        } else if (content instanceof Multipart) {
            println('It is multipart')

            mp = ((message.getContent()) as Multipart)

            Object p = mp.getBodyPart(0).getContent()

            body = p.toString()
        }
        
        println('     ----- Body:' + body)

        sentPos = body.indexOf('Sent:')

        toPos = body.indexOf('To:')

        msgBody = body

    }
}
//close the folder objects
emailFolder.close(false)

store.close()

