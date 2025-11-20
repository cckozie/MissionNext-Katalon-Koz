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
import java.io.File as File

LocalDateTime now = LocalDateTime.now()

println(now)

//startTime = now.toLocalTime().toString().substring(0,8)

//outText = 'The time now is ' + startTime

//println(outText)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Internet Speedtest.txt')

outFile.write('Testing Internet Upload and Download Bandwidth\n')

outFile.append('   TIME   \tUPLOAD\tDOWNLOAD\n\n')

WebUI.openBrowser('speedtest.net')

WebUI.maximizeWindow()

WebUI.waitForPageLoad(120)

while(1 == 1) {

	WebUI.click(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Go'))
	
	download = WebUI.getText(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Download Speed'))
	
	println(download)
	
//	WebUI.delay(1)
	
	while(!download.contains('.')) {
		
//		WebUI.delay(1)
		
		WebUI.verifyElementVisible(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Download Speed'), FailureHandling.OPTIONAL)
		
//		WebUI.delay(1)
		
//		download = WebUI.getText(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Download Speed'))
		download = WebUI.getAttribute(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Download Speed'), 'innerHTML')
		
		println(download)
		
		println('try again')
	}
	
	println('done')
	
	println(download)
	
	WebUI.delay(1)
	
	upload = WebUI.getText(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Upload Speed'))
	
	while(upload.length() < 3) {
		WebUI.delay(2)
		
		WebUI.verifyElementVisible(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Upload Speed'), FailureHandling.OPTIONAL)
		
		upload = WebUI.getText(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/span_Upload Speed'))
	}
	
	println(upload)
	
	now = LocalDateTime.now()
		
	myTime = now.toLocalTime().toString().substring(0,8)

	outFile.append(myTime + '\t' + download + '\t' + upload + '\n')
	
	WebUI.delay(10)
	
	popup = WebUI.verifyElementVisible(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/div_Try Speedtest for Mac'), FailureHandling.OPTIONAL)
	
	if(popup) {
		WebUI.click(findTestObject('Object Repository/Misc Non-MissionNext/Internet Speedtest/a_Back to test results'))
	}
	
	WebUI.delay(300)

}
