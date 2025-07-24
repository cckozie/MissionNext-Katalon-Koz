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
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard

/*
matchFields = [

'Preferred School Positions',
'Formal Education Degree',
'Formal Teaching Credentials',
'Previous Experience',
'Classroom Experience',
'English Proficiency',
'Paid & Volunteer Positions',
'Time Commitment(s)',
'Preferred Region(s)',
'Travel Options',
'Bible Training',
'I/We can be Available']
*/

debug = true

matchFields = varMatchFields

if(debug) {
	matchFields.each {
		println(it)
	}
}
//System.exit(0)
userFields = [
	'First Name',
	'Last Name',
	'Marital status']

	
ignoreRows = [
	'Eligible for Journey Guide?',
	'Experience',
	'Resume:',
	'Education',
	'Additional Language ProficiencyGroup',
	'Situation',
	'Church',
	'Availability',
	'Preferences',
	'Options/Comment']

debug = true

bypass = false //Clipboard already holds table

if (!(bypass)) {
    
    Robot robot = new Robot()

    //Select All
    robot.keyPress(KeyEvent.VK_META)

    robot.keyPress(KeyEvent.VK_A)

    robot.keyRelease(KeyEvent.VK_A)

    robot.keyRelease(KeyEvent.VK_META)

    WebUI.delay(1)

    //Copy
    robot.keyPress(KeyEvent.VK_META)

    robot.keyPress(KeyEvent.VK_C)

    robot.keyRelease(KeyEvent.VK_C)

    robot.keyRelease(KeyEvent.VK_META)
}

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

tempFile = new File(filePath + 'education candidate profile.txt')

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

println(data)

tempFile.write(data)

BufferedReader reader

reader = new BufferedReader(new FileReader(tempFile))

fields = [:]

values = []

first = true

valueLine = false

line = ''

while(!line.contains('First Name:')) { //Ignore all until first name field
	line = getNextLine(reader)
}

while(line != null && !line.contains('Copyright')) {
	
	if(line.contains(':')) { // It's potentially a field
		
		if(!first) {
			
			if(debug) {println(field + ':' + values)}
			
			if(field in matchFields || field in userFields) {
			
				fields.put(field,values)
			}
			
			if(field == 'First Name') {
				firstName = values[0]
			}
			
			if(field == 'Last Name') {
				lastName = values[0]
			}
			
			if(field == 'Marital status') {
				maritalStatus = values[0]
			}
		}
		
		first = false
		
		values = []
		
		colon = line.indexOf(':')
		
		field = line.substring(0, colon)
		
		if(debug) {println('field is ' + field)}
		
		if(colon < line.length()) {
			
			value = line.substring(colon + 1).trim()
			
			if(value.length() > 1) {
			
				if(debug) {println('value is ' + value)}
				
				values.add(value)
			}
			
		} else {
			
			value = line.trim()
			
			if(debug) {println('value is ' + value)}
				
			values.add(value)
			
		}
		
	} else {
		
		valueLine = true
		
		value = line.trim()
			
		if(debug) {println('value is ' + value)}
			
		values.add(value)
		
		line = getNextLine(reader)
	}

	if(!valueLine) {
		line = getNextLine(reader)
	}
	
	valueLine = false
}

if(field in matchFields || field in userFields) {
	fields.put(field,values)
}

if(debug) {println(field + ':' + values)}
	
reader.close()

if(debug) {
	fields.each{
		println(it.key + ':' + it.value)
	}
}

return fields

def getNextLine(reader) {
	
	line = reader.readLine()
	
	if(debug) {println(line)}
	
	while(line == null || line in ignoreRows) {
		line = reader.readLine()
	}
	
	return line.trim()
}
