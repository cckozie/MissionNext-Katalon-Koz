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
import groovy.console.ui.SystemOutputInterceptor as SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import javax.swing.*;
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

//
// Random missing, but with password. Then fix missing and remove password.

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

println(username)

if(username[-3..-1] != '5jc' && username[-3..-1] != '4ec') {
    println('The Execution Profile must be set to "Journey or Education Candidate"')

    System.exit(0)
}

if(username.contains('ec')) {
	site = 'education'
} else {
	site = 'journey'
}

domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://' + site + '.' + domain) + '/signup/candidate')

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)


if(GlobalVariable.testSuiteRunning) {
	myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
	myTestCase = testName
}

outFile = new File(GlobalVariable.reportPath + myTestCase + '_' + site +  '_on ' + domain + '.txt')

//GlobalVariable.outFile = outFile //Comment to prevent the called test case from writing to the outfile

outFile.write(('Testing ' + myTestCase + ' on ' + domain) + '.\n')

//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Create the Journey candidate ==================================

first = true

randomInt = new Random()

count = 0

//while(count < 50) {
while(1 == 1) {
	count++
	
	str = '11111111'
	
	myNum = new StringBuilder(str)
	
	myNum1 = new StringBuilder(str)
	
	index = randomInt.nextInt(7)
	
	myNum.setCharAt(index,'0' as char)
	println myNum
	myNum1.setCharAt(index,'0' as char)
	println myNum1
	
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
	
	if(GlobalVariable.mobileScreen) {
		
		WebUI.setViewPortSize(600, 1200)
		
		WebUI.delay(1)
	}
	
	WebUI.navigateToUrl(url)
	
	WebUI.waitForPageLoad(10)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	Actions action = new Actions(driver)
	
	
	for(loop = 1; loop <= 2; loop++) {
	
		if(loop == 1 || index == 6) {
			if(myNum.substring(6,7) == '1') {
				value = GlobalVariable.username
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/input_Username'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(loop == 1 || index == 5) {
			if(myNum.substring(5,6) == '1') {
				value = GlobalVariable.email
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/input_Email'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(myNum.substring(7) == '1') {
			value = GlobalVariable.password
		} else {
			value = ''
		}
		object = 'Journey Candidate Profile/Register/input_Password'
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
			('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		
		
		if(loop == 1 || index == 4) {
			if(myNum.substring(4,5) == '1') {
				value = GlobalVariable.first_name
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/input_First Name'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(loop == 1 || index == 3) {
			if(myNum.substring(3,4) == '1') {
				value = GlobalVariable.last_name
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/input_Last Name'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(loop == 1 || index == 2) {
			if(myNum.substring(2,3) == '1') {
				value = GlobalVariable.country
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/select_Country'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(loop == 1 || index == 1) {
			if(myNum.substring(1,2) == '1') {
				value = GlobalVariable.phone_number
			} else {
				value = ''
			}
			object = 'Journey Candidate Profile/Register/input_Best Phone Number'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
				('varObject') : object, ('varParm1') : value], FailureHandling.STOP_ON_FAILURE)
		}
		
		if(loop == 1 || index == 0) {
			object = 'Object Repository/Journey Candidate Profile/Register/checkbox_Terms and Conditions'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			if(myNum.substring(0,1) == '1') {
				WebUI.check(findTestObject(object))
			} else {
				WebUI.uncheck(findTestObject(object))
			}	
			object = 'Object Repository/Journey Candidate Profile/Register/button_Sign up'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
		}
		
		myNum.setCharAt(index,'1' as char)
		myNum.setCharAt(7,'0' as char)
		println myNum
		
		WebUI.delay(2)
	}
	
//	if(first) {
		WebUI.executeJavaScript('window.open();', [])
		first = false
//	}
	
	WebUI.switchToWindowIndex(1)
	
	found = WebUI.callTestCase(findTestCase('Admin/Test for User in AD'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
	
	outText = 'For ' + myNum1 + ', new user found is ' + found + ' on test ' + count
	
	println(outText)

	outFile.append(outText + '\n')
	
	if(found) {
		System.exit(0)
	}
	
//	WebUI.switchToWindowIndex(0)
	WebUI.closeBrowser()
	
}


//WebUI.closeBrowser()

