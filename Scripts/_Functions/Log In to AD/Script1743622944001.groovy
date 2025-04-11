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

//Check to see if we're writing printed output also to a file
writeFile = false
if(GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile
	println(myFile)
	outFile = new java.io.File(myFile)
	writeFile = true
}

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.waitForPageLoad(15)

