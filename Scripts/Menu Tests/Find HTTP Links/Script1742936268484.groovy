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
import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import javax.swing.JFrame
import java.awt.Frame
import javax.swing.JOptionPane
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File as File


myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf('/')+1)
myTestCase = myTestCase.substring(0,myTestCase.length()-3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write('Running ' + myTestCase + '\n')


printIt = false

separator = '----------------------------------------------------------------------------------------------------------------------------------------------------------'

httpLinks = []	
	
WebUI.openBrowser('')

WebUI.maximizeWindow()

// Home page URL's
startPages = ['missionnext.org', 'journey.missionnext.org', 'education.missionnext.org', 'quickstart.missionnext.org', 'jg.missionnext.org']

// Ignore all URL's that contain any of these items
bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'wp_cron', 'twitter', 'facebook', 'linkedin', 'instagram', '#']

WebDriver driver = DriverFactory.getWebDriver()

// Find all of the links on each of the home pages
for(page in startPages) { //List of home pages
	
	WebUI.navigateToUrl(page)
	
	WebUI.waitForPageLoad(30)	
	
	elements = driver.findElements(By.tagName("a")) 	//Find all of the links on the home page being processed
	
	elements.each {		//Process each link
		
		href = it.getAttribute('href')	//Get the URL of the link
		
		skip = false
		
		if(href != null) {				//Bypass any in the ignore list
	
			for(item in bypassList) {
				
				pos = href.indexOf(item)
				
				if(pos >= 1) {
				
					skip = true
					
					break
				}
			}
			
			if(!skip) {
				
				if(href.contains('http:'))
				
				httpLinks.add(page + '|' + href)
			}
		}
	}
}

httpLinks = httpLinks.sort()	//Sort the list

//myLinks = pageLinks.unique()	//Only key the links when the home page and link is unique

httpLinks.each { 
	delim = it.indexOf('|')
	
	homePage = it.substring(0,delim)

	link = it.substring(delim + 1)

	outText = ('Link from "' + homePage + '" to "' + link)
	
	println(outText)
	
	outFile.append(outText + '\n')
}
