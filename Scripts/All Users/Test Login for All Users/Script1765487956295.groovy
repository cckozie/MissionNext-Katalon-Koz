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
import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import groovy.io.FileType as FileType
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

GlobalVariable.testCaseErrorFlag = false

domain = GlobalVariable.domain

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get('current_testcase').toString().substring(RunConfiguration.getExecutionProperties().get(
        'current_testcase').toString().lastIndexOf('/') + 1)

if (1 == 2) {
    //GlobalVariable.testSuiteRunning) {
    myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
    myTestCase = testName
}

outFile = new File((((GlobalVariable.reportPath + myTestCase) + ' on ') + domain) + '.txt')

GlobalVariable.outFile = outFile

outFile.write(((('Testing ' + myTestCase) + ' on ') + domain) + '.\n')

profiles = []

filePart = 'Test Reports/Test Candidate Expiration Dates_All'

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Profiles'

dir = new File(filePath)

dir.eachFile(FileType.FILES, { def file ->
        fileName = file.toString()

        if ((fileName.contains('Education') || fileName.contains('Journey')) || fileName.contains('QuickStart')) {
            profiles.add(fileName.substring(filePath.length() + 1, fileName.length() - 5))
        }
    })

profiles.sort()

adOpen = false

profiles.each { 
	loginOK = true
	
	userText = ''
	
	value = ''

    WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : it], FailureHandling.STOP_ON_FAILURE)

    url = WebUI.getUrl()

    if (!url.contains('dashboard') && !url.contains('approval')) {
		
		loginOK = false
		
		username = GlobalVariable.username
		
		WebUI.executeJavaScript('window.open();', [])
		
		WebUI.switchToWindowIndex(1)
		
		WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)
		
		adOpen = true
	
		WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))
		
		WebUI.waitForPageLoad(10)
		
		WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)
		
		WebUI.delay(1)
		
		WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))
		
		WebUI.waitForPageLoad(10)
		
		userFound1 = WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/Ad User Viewer Utility/a_Top Line Username'),
			3, FailureHandling.OPTIONAL)
		
		userFound2 = WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/Ad User Viewer Utility/td_Role'), FailureHandling.OPTIONAL)
		
		WebUI.delay(2)
		
		if(userFound1) {
			if(!userFound2) {
				userText = 'User was found in AD, but does NOT have a role.'
			} else {
				userText = 'User was found in AD, and does have a role.'
			}
		} else {
			userText = 'User was NOT found in AD.'
		}
		
		value = 'NOT '		
    }
    
    outFile.append('Log in to ' + it + ' was ' + value + 'successful. ' + userText + ' URL is ' + url + '\n')

    WebUI.closeBrowser()
}

