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
import java.io.File as File
import groovy.io.FileType

// THIS SCRIPT VALIDATES THAT ALL OF THE PROFILE TOOLTIP TEXT MAPS HAVE CORRESPONDING TOOLTIP MAPS (tooltip image test objects are keyed to tooltip maps)

profiles = ['Education Affiliate',
'Education Candidate Profile',
'Education Partner Profile',
'Journey Affiliate',
'Journey Candidate Profile',
'Journey Partner Profile',
'QuickStart Candidate Profile']

path = '/Users/cckozie/git/MissionNext-Katalon-Koz/Scripts/'

totalErrors = 0

for(profile in profiles) {
	dir = new File(path + profile)
	dir.eachFileRecurse(FileType.FILES) { file ->
		myFile = file.absolutePath
		if(myFile.contains('.groovy') && !myFile.contains('Copy') && !myFile.contains('Archive')) {
			println "Processing file: " + myFile
			File scriptFile = new File(myFile)
			String fileContent = scriptFile.text
			String fileContent2 = fileContent 
			tooltipsMapLoc = fileContent.indexOf('tooltips = [')
			if(tooltipsMapLoc > 0) {
				fileContent = fileContent.substring(tooltipsMapLoc + 10)
				tooltipsMapEnd = fileContent.indexOf(']')
				myMap = fileContent.substring(0,tooltipsMapEnd + 1)
				newMap = Eval.me(myMap)
				if(newMap.size() > 1) {
					tooltips = []
					newMap.each { 
						tooltips.add(it.key)
					}
					
					tooltipTextMapLoc = fileContent2.indexOf('tooltipText = [')
					fileContent2 = fileContent2.substring(tooltipTextMapLoc + 12)
					tooltipTextMapEnd = fileContent2.indexOf(']')
					myMap = fileContent2.substring(2,tooltipTextMapEnd + 1)
					newMap = Eval.me(myMap)
					tooltipText = []
					newMap.each { 
						tooltipText.add(it.key)
					}
					
					println('TOOLTIPS:' + tooltips)
					
					println('TOOLTIPTEXT:' + tooltipText)

					errors = 0
					for (tooltip in tooltipText) {
						if(!tooltip.contains('//')) {
							if(!tooltips.contains(tooltip)) {
								println(tooltip + ' is not in the tooltips element list.')
								errors++
								totalErrors++
//								System.exit(0)
							}
						}
					}
					println(errors + ' errors found.\n')
				}
			}
		}
	}
}
println(totalErrors + ' total errors found.')
