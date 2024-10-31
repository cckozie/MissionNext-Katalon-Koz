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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

debug = false

WebDriver driver = DriverFactory.getWebDriver()

// For each group of elements (checkboxes or radio buttons), click each of the elements whose text/label 
// corresponds to one that is in the input parameter (varParms) for that group.
//
// We have to do this by group in case of identicle labels in multiple groups
for(i = 0; i < varXpaths.size(); i++) {
	
	if(debug) {println('Desired label is "' + varParms[i] + '"')}
	
	elements = driver.findElements(By.xpath(varXpaths[i]))
	
	element_count = elements.size()
	
//	println('count is ' + element_count)
	
	for(element in elements) {
		
		myValue = element.getAttribute("value")
		
		//This processing is necessary because '(!) ' is getting prepended to the value attribute
		//	if it starts with 'No'
		if(myValue.length() >= 4) {
			
			if(myValue.substring(0,4) == '(!) ') {
				
				myValue = myValue.substring(4)
				
			}
		}
		
		if(debug) {println('Element label is "' + myValue + '"')}
		
		myObj = WebUI.convertWebElementToTestObject(element) //Convert the element to a test object
		
		myType = element.getAttribute("type")	//We need to know if it's checkbox or radio button
		
		if(myValue in varParms[i]) {	//Is the element's label in the list to be selected?
			inList = true
		} else {
			inList = false
		}
		
		//If it's a checkbox, then either clear it or check it depending on the input parm
		if(myType == 'checkbox') {
			myStatus = element.isSelected()
			if(!myStatus && inList || myStatus && !inList) {
					WebUI.click(myObj)
			}
			
		//If it's a radio button, then just click it if it's the one
		} else { 		//Must be radio button
			if(inList) {
				WebUI.click(myObj)
			}
		}
	}
}

/*
for(i = 0; i < varXpaths.size(); i++) {
	
	elements = driver.findElements(By.xpath(varXpaths[i])) //Find all of the elements in the group
	
	element_count = elements.size()
	
	println('count is ' + element_count)
	
	for(element in elements) {
		
		myValue = element.getAttribute("value") //Get the element's label (value attribute)
		
		//This processing is necessary because '(!) ' is getting prepended to the value attribute
		//	if it starts with 'No'
		if(myValue.length() >= 4) {
			
			if(myValue.substring(0,4) == '(!) ') {
				
				myValue = myValue.substring(4)
				
			}
		}
		if(myValue in varParms[i]) {	//Click on the element if it's label matches the input parameter
			
			myObj = WebUI.convertWebElementToTestObject(element) //convert element to test object
			
			WebUI.click(myObj, FailureHandling.OPTIONAL)
		
		}
	}
}
*/