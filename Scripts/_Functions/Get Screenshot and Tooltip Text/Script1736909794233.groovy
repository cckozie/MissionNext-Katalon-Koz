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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword as WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.io.File as File
import javax.swing.*;

/*
============================================================================================================
	Moved capture of tooltip text into test case 'Get Tooltip Text'
============================================================================================================
*/

//Maximize the length of the viewport to include everything down to and including the page footer
//Build the screenshot file name 
//Execute the screenshot and save to local machine
//Call Get Toolipt Text to get the text for each field and save to CSV file on local machine

WebDriver driver = DriverFactory.getWebDriver()

frame = new JFrame("");
JPanel p = new JPanel();
JLabel l = new JLabel("LOADING ...", SwingConstants.CENTER);
frame.add(l);
frame.setSize(300, 100);
frame.setLocation(600, 0);
frame.setAlwaysOnTop (true)
frame.show();

if (WebUI.verifyTextPresent('Partner Level', false, FailureHandling.OPTIONAL)) {
    user = 'Partner'
} else if (WebUI.verifyTextPresent('Partnership', false, FailureHandling.OPTIONAL)) {
    user = 'Partner'
} else {
    user = 'Candidate'
}

path = '/Users/cckozie/Documents/MissionNext/Screenshots and Tooltip Text/'

// Set the calling parameter values or defaults
if (binding.hasVariable('varExtension')) {
    extension = ('_' + varExtension)

    println('Extension is ' + extension)
} else {
    extension = ''
}

title = (WebUI.getWindowTitle() + extension)

title = title.replace('/', '_')

fileBase = (((path + user) + '_') + title)

fileImage = (fileBase + '_Screenshot.png')

WebUI.waitForPageLoad(30)

//Wait for the spinner to disappear (this test is not 100% effective. Moves on after 2 seconds if it can't determine the spinner stopped
spinner = driver.findElement(By.xpath('/html/body/div[1]/div/div[2]'))

JavascriptExecutor executor = (JavascriptExecutor) driver;

elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;', 
    spinner)

attrs = elementAttributes.toString()

waits = 0

while ((attrs.indexOf('display: none') < 0) && (waits < 2)) {
    WebUI.delay(1)

    attrs = elementAttributes.toString()

    waits++
}

// Get the tooltip fields and text
tooltipMap = WebUI.callTestCase(findTestCase('_Functions/Get Tooltip Text'), [:], FailureHandling.STOP_ON_FAILURE)

// If there were tooltips on the page, write the fields and associated tooltips to a CSV file
if (tooltipMap.size() > 0) {
	
	file = (fileBase + '_Tooltips.csv')
	
	outText = 'FIELD LABEL' + ',' + 'TOOLTIP TEXT' + '\n\n' 
	
	for(it in tooltipMap) {
		
		name = it.key
		
		it.value = it.value.replace('\n','') //Because some tooltips have lots of carriage returns

//		it.value = it.value.replace(",", "','") //Encapsulate commas in quotes for CSV
		
		it.value = '"' + it.value + '"'
		outText = outText + name + ',' + it.value
		outText = outText + '\n'
		println(outText)
	}

	outFile = new File(file)
	
	println(outText)

	outFile.write(outText)
}

frame.hide();

// Take and save the screenshot
WebUI.takeFullPageScreenshot(fileImage)

return tooltipMap

