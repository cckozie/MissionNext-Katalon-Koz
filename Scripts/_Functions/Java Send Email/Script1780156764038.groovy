
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
import javax.mail.*
import javax.mail.internet.*

def sendEmail(subject, text) {
	String host = "smtp.gmail.com"
	String username = "cktest01mn@gmail.com"
	String password = "sfesezftmpqxbbbd" // Generate from Google Account Security

	Properties props = new Properties()
	props.put("mail.smtp.auth", "true")
	props.put("mail.smtp.starttls.enable", "true")
	props.put("mail.smtp.host", host)
	props.put("mail.smtp.port", "587")

	Session session = Session.getInstance(props, new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password)
		}
	})

	try {
		Message message = new MimeMessage(session)
		message.setFrom(new InternetAddress(username))
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("chris.kosieracki@missionnext.org"))
		message.setSubject(subject)
		message.setText(text)

		Transport.send(message)
		println "Email sent successfully!"
	} catch (Exception e) {
		e.printStackTrace()
	}
}
sendEmail(varSubject, varText)
