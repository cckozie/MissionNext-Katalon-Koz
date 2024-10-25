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

/////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	Modified to allow fromKey and subjectKey to be lists
/////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//************* Time to wait in minutes ****************
waitTime = 4 //I've seen it take 10+ minutes

scriptStart = new Date()

scriptInstant = scriptStart.toInstant()

println(scriptStart)

println(scriptInstant)

// Initialize the return code
GlobalVariable.returnCode = ''

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

isList = false
if(fromKey instanceof List) {
	isList = true
} else {
	fromKey = [fromKey]
	subjectKey = [subjectKey]
	searchKey = [searchKey]
	isList = false
}

msgCount = fromKey.size()

println(msgCount)
println(fromKey)
println(subjectKey)
println(searchKey)

//Check to see if we're writing printed output also to a file
writeFile = false

if (GlobalVariable.outFile != '') {
    String myFile = GlobalVariable.outFile

    println(myFile)

    outFile = new File(myFile)

    writeFile = true
}

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

emailFolder = store.getFolder('INBOX')

emailFolder.open(Folder.READ_ONLY)

messages = emailFolder.getMessages()

if (messages.length == 0) {
    emailFolder.close(false)
}

msgFound = false

msgFoundCount = 0

loops = 0

while ((messages.length < msgCount) && (loops <= loopsMax)) {
	
    WebUI.delay(5)

    emailFolder.open(Folder.READ_ONLY)

    messages = emailFolder.getMessages()

    if (messages.length < msgCount) {
		
        emailFolder.close(false)
		
    } 
    
    loops++
}

outText = 'messages.length---' + messages.length

println(outText)

if (writeFile) {
	outFile.append(outText + '\n')
}

emailNumber = 0

if (messages.length >= msgCount) { 
			
    for (Message message : messages) {
		
		emailNumber ++
		
		println('\n********* Processing email number ' + emailNumber)
		
        String from = message.getFrom()[0]

        String subject = message.getSubject()

        outText = (((('Found an email from ' + from) + ' with the subject of ') + subject) + '.')
       
        println(outText)

        if (writeFile) {
            outFile.append(outText + '\n')
        }
        
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
        
		println('----- Subject is:' + subject)
		
		for(i = 0; i < msgCount; i++) {
			
			println('********* Processing search number ' + i)
				
			println('==== subjectKey is:' + subjectKey[i])
			
	        subjectMatch = subject.indexOf(subjectKey[i])
	
	        println(subjectMatch)
			
			if(subjectMatch >= 0) {

				println('----- From is:' + from)
				
				println('==== fromKey is:' + fromKey[i])
				
		        fromMatch = from.indexOf(fromKey[i])
		
		        println(fromMatch)
		
		        if ((subjectMatch >= 0) && (fromMatch >= 0)) {
		            println('     ----- Body:' + body)
		
		            sentPos = body.indexOf('Sent:')
		
		            toPos = body.indexOf('To:')
		
		            msgBody = body
		
		            if (searchKey[i] != 'NA') {
		                //			 msgTime = body.substring(sentPos + 6, toPos)
		                if (msgBody.indexOf(searchKey[i]) >= 0) {
							
		                    msgFound = true
		
		                    outText = (('>>>>> SearchKey ' + searchKey[i]) + ' was found in the email.')
		
		                    println(outText)
		
		                    if (writeFile) {
		                        outFile.append(outText + '\n')
		                    }
							
							break
		                }
		            } else {
						
		                msgFound = true
						
						break
						
		            }
		        }
			}
        }
    
	    if (msgFound) {
			
			msgFoundCount ++
			
	    } else {
			
			if (searchKey[i] != 'NA') {
				
				outText = (((((('Failed to receive email from ' + fromKey[i]) + ' with the subject of ') + subjectKey[i]) + ' and a search string of ') +
				searchKey[i]) + '.')
			
			} else {
				
				outText = ('Failed to receive email from ' + fromKey[i] + ' with the subject of ' + subjectKey[i] + '.')
				
			}
	
			println(outText)
		
			if (writeFile) {
				outFile.append(outText + '\n')
			}
	    }
	}
}
//close the folder objects
emailFolder.close(false)
			
if(msgFoundCount == fromKey.size()) {
	
	GlobalVariable.returnCode = 'found'
	
} else {
	
	if(msgFoundCount == 0) {

		outText = '#### ' + msgFoundCount + ' emails were received.'
		
		println(outText)
	
		if (writeFile) {
			outFile.append(outText + '\n')
		}
	}
	
}
	
store.close()

