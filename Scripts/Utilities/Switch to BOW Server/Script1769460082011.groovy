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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.File as File
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.awt.Desktop as Desktop
import org.sikuli.script.*

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Switch to BOW Server.txt')

outFile.write('Switching to the BOW Server\n')

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Misc/'

File file = new File('/System/Applications/Utilities/Terminal.app')

Desktop.getDesktop().open(file)

WebUI.delay(2)

Screen s = new Screen()

s.paste('bow')

s.type(Key.ENTER)

s.paste('Cck0322?')

s.type(Key.ENTER)

WebUI.openBrowser('')

WebUI.navigateToUrl('https://journey.missionnext.org/login')

WebUI.setText(findTestObject('Object Repository/BOW/input_Username_log'), 'cktest15jc')

WebUI.setEncryptedText(findTestObject('Object Repository/BOW/input_Password_pwd'), 'gnYzEwUXvDp+5uGLsmRWLQ==')

WebUI.click(findTestObject('Object Repository/BOW/button_Log In'))

WebUI.click(findTestObject('Object Repository/BOW/a_My Profile'))

city = WebUI.getText(findTestObject('Object Repository/Journey Candidate Profile/Tabs/Contact Info/input_City'))

outFile.append('City is ' + city)

phone = WebUI.getText(findTestObject('Object Repository/Journey Candidate Profile/Tabs/Contact Info/input_Best Phone Number'))\

outFile.append('Phone is ' + phone)

if(phone == '123-456-7890') {
	server = '##### ERROR: GoDaddy'
} else {
	server = 'BOW'
}

msg = server + ' data reflected.'

outFile.append(msg + '/n')

WebUI.delay(5)

WebUI.closeBrowser()

