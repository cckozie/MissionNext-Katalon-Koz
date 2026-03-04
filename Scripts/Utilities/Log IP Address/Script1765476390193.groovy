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
import java.io.File as File

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/IP Address.csv')

if (!(outFile.isFile())) {
    outFile.write('IP Address Log for Macbook Pro\nDate,IP Address\n')

}

WebUI.callTestCase(findTestCase('_Functions/Manage Avast VPN'), [('varDesiredState') : 'off'], FailureHandling.STOP_ON_FAILURE)

date = WebUI.callTestCase(findTestCase('_Functions/Get Timestamp'), [('varFormat') : 'date'], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

WebUI.openBrowser('https://www.whatismyip.com/')

myIP = WebUI.getText(findTestObject('Object Repository/Utilities/span_IP Address'))

outText = date + ',' + myIP

println(outText)

outFile.append('\n' + outText)

WebUI.closeBrowser()

