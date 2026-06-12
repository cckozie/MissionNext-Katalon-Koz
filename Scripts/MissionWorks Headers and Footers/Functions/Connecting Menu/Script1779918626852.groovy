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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.File as File

site = varSite

url = varUrl

menuOptions = ['MissionConnexion', 'MissionNext','MissionGuide']

pageTitles = ['Homepage - MissionConnexion', 'Serve in Missions - MissionNext.org', 'Short Term Mission Trips | International and Domestic']

//extensions = ['MissionNext' : '', 'Journey' : '', 'Education' : 'Education/', 'QuickStart' : '', 'Journey Guide' : '', 'MissionLinked' : 'MissionLinked/',
extensions = ['MissionNext' : '', 'Journey' : '', 'Education' : '', 'QuickStart' : '', 'Journey Guide' : '', 'MissionLinked' : 'MissionLinked/',
	'Coaching' : '']

folderBase = 'MissionWorks Headers and Footers/Functions/Connecting/'

extension = extensions.get(site)

objectFolder = folderBase + extension

outFile = GlobalVariable.outFile

if(outFile == '') {
	outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Testing Connecting Menu Options.txt')
	outFile.write('     Testing Connecting Menu Options\n')
}

WebUI.openBrowser('')

WebUI.maximizeWindow()

outFile.append('\n_________________________________________________________________________________________________\n')

outFile.append('\n<<< TESTING THE CONNECTING MENU ON ' + site + ' >>>\n')

errorFlag = false

index = 0

for(option in menuOptions) {
	
	WebUI.navigateToUrl(url)
	
	if(site == 'MissionLinked') {
		WebUI.mouseOver(findTestObject('Object Repository/MissionWorks Headers and Footers/img_MissionWorks logo'))
		WebUI.delay(2)
	}
	
	outFile.append('\nTesting ' + option + ' option on the Connecting menu on ' + site + '.\n')
	
	println('>>> clicking sub-arrow')
	
	arrowClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'span_Connecting sub-arrow'), 1)
	
	if(arrowClickable) {
		
		println('>>> clicking menu option ' + option)
	
		WebUI.click(findTestObject(objectFolder + 'span_Connecting sub-arrow'))
		
		WebUI.delay(1)
	
		optionClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'a_' + option), 1)
		
		if(optionClickable) {
			
			linkText = WebUI.getText(findTestObject(objectFolder + 'a_' + option))
			
			if(linkText != option) {
				
				outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
				errorFlag = true
			}

			WebUI.click(findTestObject(objectFolder + 'a_' + option))
			
			println('<<< getting page title')
			
			for(attemps = 1; attemps <= 2; attemps++) {
				
					title = WebUI.getWindowTitle()
					
					println(title)
					
					if(title != pageTitles[index]) {
						if(attemps == 1) {
							outFile.append('----- RETRYING option "' + option+ '"\n')
							WebUI.delay(2)
							WebUI.click(findTestObject(objectFolder + 'a_' + option))
							WebUI.delay(2)
						} else {
							outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles[index] + '"\n')
							errorFlag = true
						}
					}
				}
		
/*			
			title = WebUI.getWindowTitle()
			
			println(title)
			
			if(title != pageTitles[index]) {
				outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles[index] + '"\n')
				errorFlag = true
			}
*/		
		} else { 
		
			outFile.append('#### ERROR: Unable to click on ' + option + ' option.\n')
			errorFlag = true
		}
	} else {
		outFile.append('#### ERROR: Unable to click on down arrow.\n')
		errorFlag = true
	}
	
	WebUI.delay(2)
	
	index++
}

WebUI.navigateToUrl(url)
	
return errorFlag

