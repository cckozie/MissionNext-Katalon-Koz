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
import java.time.*
import java.time.temporal.ChronoUnit
import java.io.File as File

// SET PROFILE TO DESIRED USER
username = GlobalVariable.username

// DELAY TIME BETWEEN TESTS
sleepMinutes = 1

outFile = new File(GlobalVariable.reportPath + 'Waiting for Access Granted.txt')

granted = WebUI.callTestCase(findTestCase('Admin/Test for Access Granted'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

LocalDateTime now = LocalDateTime.now()

myTime = now.toLocalTime()

outFile.write('Access Granted for user ' + username + ' at ' + myTime.truncatedTo(ChronoUnit.MINUTES) + ' is ' + granted + '.\n')

while (!(granted)) {
	WebUI.callTestCase(findTestCase('Utilities/Sleep Until'), [('varSleepMinutes') : sleepMinutes], FailureHandling.STOP_ON_FAILURE)
	
	granted = WebUI.callTestCase(findTestCase('Admin/Test for Access Granted'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
	
	now = LocalDateTime.now()

	myTime = now.toLocalTime()
	
	outFile.append('Access Granted for user ' + username + ' at ' + myTime.truncatedTo(ChronoUnit.MINUTES) + ' is ' + granted + '.\n')
}


