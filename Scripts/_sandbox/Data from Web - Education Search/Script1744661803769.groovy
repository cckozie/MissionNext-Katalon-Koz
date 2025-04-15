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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
//import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


matchFields = [
	'Affiliated with a church?',
	'Preferred School Positions',
	'I/We can be Available',
	'Time Commitment(s)',
	'Process Stage',
	'Relocation Option(s)',
	'Previous Experience',
	'Formal Education Degree',
	'Classroom Experience',
	'Paid & Volunteer Positions',
	'Bible Training',
	'Cross-Cultural Experience']
	
debug = true

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

site = 'Education'

radioType = 'Org'

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : radioType],
	FailureHandling.STOP_ON_FAILURE)

schoolMatchFields = []

matchValues.each {
	schoolMatchFields.add(it.value[0])
}
filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

tempFile = filePath + 'education partner profile.txt'

BufferedReader reader
reader = new BufferedReader(new FileReader(tempFile))

fields = [:]
values = []
line = getNextLine(reader)
while(line != 'end') { // && !line.contains(':')) {
	line = getNextLine(reader)
	println(line)
//	while(line != null && line != 'end') {
	while(line != 'end') {
		if(line.contains(':')) { // It's potentially a field
			if(line.length() > 2) {
				values = []
				colon = line.indexOf(':')
				if(colon >= 0) {
					field = line.substring(0,colon).trim()
					println('Field is ' + field)
					value = line.substring(colon + 1).trim()
					println('Value is ' + value)
					if(value.length() >= 2) {
						values.add(value)
					}
//					System.exit(0)
					line = getNextLine(reader)
					while (!line.contains(':')) { // not field 
						line = line.trim()
						values.add(line)
						line = getNextLine(reader)
						if(line == 'end') {
							break
						}
					}
					if(field in schoolMatchFields) {
						fields.put(field,values)
					}
				}
			} else {
				line = getNextLine(reader)
				if(line == 'end') {
					break
				}
			}
		} else {
			line = getNextLine(reader)
			if(line == 'end') {
				break
			}
		}
	}
	line = getNextLine(reader)
	if(line == 'end') {
		break
	}
}

reader.close()
fields.each{
	println(it.key + ':' + it.value)
}

def getNextLine(reader) {
	line = reader.readLine()
//	println(line + ':' + line.length())
	while(line != null && line.length() <= 2) {
		println(line + ':' + line.length())
		line = reader.readLine()
		println(line)
	}
//	println(line)
	if(line != null && !line.contains('Logout')) {
		return line
	} else {
		return 'end'
	}
	
}
