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

debug = true	//Print debug info

toggle = true	//Toggle selected checkboxes if already checked

if(!GlobalVariable.testSuiteRunning) {
	frame = new JFrame("");
	JPanel p = new JPanel();
	JLabel l = new JLabel('Making user selections...', SwingConstants.CENTER);
	frame.add(l);
	frame.setSize(300, 100);
	frame.setLocation(600, 0);
	frame.setAlwaysOnTop (true)
	frame.show();
}

WebDriver driver = DriverFactory.getWebDriver()
println(varParms)
println(varXpaths)

// For each group of elements (checkboxes or radio buttons), click each of the elements whose text/label
// corresponds to one that is in the input parameter (varParms) for that group.
//
// We have to do this by group in case of identicle labels in multiple groups
for(i = 0; i < varXpaths.size(); i++) {
	
	if(debug) {println('Desired label is "' + varParms[i] + '"')}
	
	println(varParms.size())
//	if(varParms[i].indexOf('Europe') >= 0) {
//		println('#############   Europe : ' + varParms[i])
//	}
	
	elements = driver.findElements(By.xpath(varXpaths[i]))
	
	element_count = elements.size()
	
	if(debug) {println('element_count is ' + elements.size())}
	
	for(it in elements) {
//	elements.each {
		element = it
		
		myValue = element.getAttribute("value")
		
		if(debug) {println('Element label is "' + myValue + '"')}
		
		//This processing is necessary because '(!) ' is getting prepended to the value attribute to designate wildcard response

		if(it != null && myValue.length() >= 4) {
			
			if(myValue.substring(0,4) == '(!) ') {
				
				myValue = myValue.substring(4)
				
				if(debug) {println('Modified element label is "' + myValue + '"')}
				
			}
		}
		
		myType = element.getAttribute("type")	//We need to know if it's checkbox or radio button
		
		println(varParms[i])
		if(myValue in varParms[i]) {	//Is the element's label in the list to be selected?
			inList = true
		} else {
			inList = false
		}
		
		//If it's a checkbox, then either clear it or check it depending on the input parm
		//If toggle is true and checking an already checked box, toggle it off first
		if(myType == 'checkbox') {
			myStatus = element.isSelected()
			if(debug) {
				println('Label ' + myValue + ' checked status is ' + myStatus + ' and in list status is '
					+ inList)
				}
			if(toggle && myStatus && inList) {
				println('Toggling off ' + myValue)
				click(element)
//				WebUI.delay(1)
				myStatus = false
			}
			if(!myStatus && inList || myStatus && !inList) {
				if(debug) {println('Clicking ' + myValue)}
					println('Clicking ' + myValue)
					click(element)
			}
			
		//If it's a radio button, then just click it if it's the one
		} else { 		//Must be radio button
			if(inList) {
				click(element)
			}
		}
	}
}

if(!GlobalVariable.testSuiteRunning) {
	frame.dispose()
}

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

