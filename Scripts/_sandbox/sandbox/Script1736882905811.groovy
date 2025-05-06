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
import org.sikuli.script.*
import java.awt.Desktop as Desktop
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.SikulixForJython as SikulixForJython

BufferedReader reader

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFile = (filePath + 'jobprofile.txt')

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

matchFields = [:]

categories = []

formatProfile(jobFile, categories, matchFields)


def formatProfile(def file, def categories, def matchFields) {
	reader = new BufferedReader(new FileReader(file))

	selections = [:]

	fieldNumbers = []

	values = []

	married = false

	spouseServing = false
	
	line = ' '
	
	line = reader.readLine()
	
	isField = false
	
	while(line != null) {
	
		while (!isField && line != null) { // Read to the first field
			
			line = reader.readLine()
			
			if(line == null) {
				break
			}
			
			println('1>' + line)
			
			if(line != null && line.contains(':')) {
			
				colon = line.indexOf(':')
				
				println(colon)
	
				isField = true
				
				while(isField) {
				
					values = []
					
					field = line.substring(0, colon).trim()
					
					println('field is ' + field)
					
					if(line.length() - colon > 2) {
						
						value = line.substring(colon + 2).trim()
						
						values.add(value)
					}
					
					line = reader.readLine()
					
					println('2>' + line)
					
					while(!line.contains(':') && line != null) {
						
						values.add(line)
						
						line = reader.readLine()
						
						if(line == null) {
							break
						}
					
						println('3>' + line)
					
					}
					
					selections.putAt(field, values)
					
					println(field + ':' + values)
					
					if(line == null) {
						break
					}
					
					if(!line.contains(':')) {
						
						isField = false
						
					} else {
						
						colon = line.indexOf(':')
					}
					
				}	
			}
		}
	}
}

selections.each {
	println(it)
}
	
