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
		//println(myFile)
		if(myFile.contains('.groovy') && !myFile.contains('Copy')) {
			println "Processing file: ${file.absolutePath}"
			File scriptFile = new File(myFile)
			String fileContent = scriptFile.text
			String fileContent2 = fileContent
			tooltipsMapLoc = fileContent.indexOf('tooltips = [')
			if(tooltipsMapLoc > 0) {
				fileContent = fileContent.substring(tooltipsMapLoc + 13)
				// println(fileContent)
				tooltipsMapEnd = fileContent.indexOf(']')
				myMap = fileContent.substring(0,tooltipsMapEnd)
				elements = myMap.split(',')
				if(elements.size() > 1) {
					//println('Tooltips:' + elements)
					
					tooltips = []
					for(element in elements) {
						//println('element:' + element)
						element = element.substring(3,element.indexOf("')" - 1))
						//println('element:' + element)
						tooltips.add(element)
					}
					
					tooltipTextMapLoc = fileContent2.indexOf('tooltipText = [')
					fileContent2 = fileContent2.substring(tooltipTextMapLoc + 15)
					//println(fileContent2)
					tooltipTextMapEnd = fileContent2.indexOf(']')
					myMap = fileContent2.substring(2,tooltipTextMapEnd)
					elements = myMap.split("',\n")
					//println('TooltipTexts:' + elements)
					
					tooltipText = []
					for(element in elements) {
						//println('element:' + element)
						end = element.indexOf("')")
						element = element.substring(0,end)
						element = element.replace("('", '')
						element = element.replace("'","")
						//println('element:' + element)
						tooltipText.add(element)
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
								System.exit(0)
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
