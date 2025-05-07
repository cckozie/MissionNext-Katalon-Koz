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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

//#### REVISE TO MOVE LOGIN TO Test School Matches
bypass = false

username = varUsername
println(username)

profileFile = varFile
println(profileFile)

emailAddress = varEmail
println(emailAddress)

searchType = varSearchType
println(searchType)

/*
try {
	WebUI.getWindowIndex()
	
} catch (BrowserNotOpenedException) {
	
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
	
	WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [('varUsername') : ''], FailureHandling.STOP_ON_FAILURE)
}
*/

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

WebUI.waitForPageLoad(10)

if(searchType == 'username') {
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)

	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))

} else {
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Email Address'), emailAddress)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Email Address'))

}

WebUI.waitForPageLoad(10)

//println('On AD search results window index is ' + WebUI.getWindowIndex())

userFound = WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/Ad User Viewer Utility/a_Top Line Username'), 
	3, FailureHandling.OPTIONAL)

if(userFound) {
	WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/a_Top Line Username'))

	WebUI.delay(1)

	Robot robot = new Robot()
	
	//Select All
	robot.keyPress(KeyEvent.VK_META)
	
	robot.keyPress(KeyEvent.VK_A)
	
	robot.keyRelease(KeyEvent.VK_A)
	
	robot.keyRelease(KeyEvent.VK_META)
	
	WebUI.delay(1)
	
	//Copy
	robot.keyPress(KeyEvent.VK_META)
	
	robot.keyPress(KeyEvent.VK_C)
	
	robot.keyRelease(KeyEvent.VK_C)
	
	robot.keyRelease(KeyEvent.VK_META)
	
	tempFile = profileFile //'/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/my test page tmp.csv'
	
	outFile = new File(tempFile)
	
	Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()
	
	String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()
	
	println(data)
	
	outFile.write(data)
	
	WebUI.delay(2)

} else {
	return null
}
return userFound
