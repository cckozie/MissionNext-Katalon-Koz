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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import javax.swing.*;

outFile = GlobalVariable.outFile

debug = false	//Print debug info

results = [:] //[element:[label,status]

WebDriver driver = DriverFactory.getWebDriver()
println(varList)

// For the group of elements (checkboxes or radio buttons) in varXpath, get the checked/clicked status of the elements whose text/label 
// corresponds to one that is in the input parameter (varList) for that group.


if(debug) {println('Desired label is "' + varList + '"')}

elements = driver.findElements(By.xpath(varXpath))

element_count = elements.size()

//	for(element in elements) {
elements.each {
	element = it
	
	myValue = element.getAttribute("value")
	
	if(debug) {println('Element label is "' + myValue + '"')}
	
	//This processing is necessary because '(!) ' is getting prepended to the value attribute
	//	if it starts with 'No'
	if(myValue.length() >= 4) {
		
		if(myValue.substring(0,4) == '(!) ') {
			
			myValue = myValue.substring(4)
			
			if(debug) {println('Modified element label is "' + myValue + '"')}
			
		}
	}
	
//		myType = element.getAttribute("type")	//We need to know if it's checkbox or radio button
	
	if(varList.size() == 0 || myValue in varList) {	//Is the element's label in the list to be selected, or the list is empty
	
		myStatus = element.isSelected()
		if(myStatus) {
			checked = true
		} else {
			checked = false
		}
		if(varCheckedOnly && checked || checked) {
			pair = [myValue, checked]
			results.put(element,pair)
			if(debug) {
				println('Label ' + myValue + ' checked status is ' + myStatus)
			}
		}
	}
}

return results

/*
def scrollToObject(def element) {
	loc = element.getLocation()
	
	if(debug) {
		println('Location is ' + loc)
	}

	y = loc.getY()

	println('Y location is ' + y)

	top = WebUI.getViewportTopPosition()

	println('Viewport top is ' + top)

	bottom = (top + 600)

	if (((y - top) < 150) || ((bottom - y) < 10)) {
		WebUI.scrollToPosition(0, y - 150)

		WebUI.delay(1)
	}
}

def printError(msg) {
	println(msg)
	lastIndex = 0
	colons = []
	colons = msg.findAll(~/:/) { match ->
		lastIndex = msg.indexOf(match, lastIndex+1)
	}
	error = msg.substring(colons[0]+1, colons[1])
//	println(error)
	
	typeIndex = msg.indexOf('type=')
	typeEnd = msg.indexOf('" ',typeIndex+1)
	type = msg.substring(typeIndex+5, typeEnd).replace('"', '')
//	println(type)
	
	valueIndex = msg.indexOf('value=')
	valueEnd = msg.indexOf('"',valueIndex+7)
	value = msg.substring(valueIndex+7, valueEnd).replace('"', '')
//	println(value)
	
	errorMsg = '##### ERROR: ' + error + ' on ' + type + ' ' + value
	
	println(errorMsg)
	
	outFile.append(errorMsg + '\n')
}

def click(def elem) {
	scrollToObject(elem)

	try {
		elem.click();
	} catch (Exception e) {
//		println("Exception Occured:" + e);
		printError("Exception Occured - " + e)
//		System.exit(0)
	}
//	elem.click()
}

*/