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

filePath = '/Users/cckozie/Documents/MissionNext/Katalon Debug/'

users = ['joshua.r@jesusfilm.org','office']

for(user in users) {

	//user = 'joshua.r@jesusfilm.org'// "office" //'joshua.r@jesusfilm.org'
	
	myFile = new File(filePath + user + '.txt')
	
	outFile = new File(filePath + user + '-Strip.txt')
	
	outFile.write('user')
	
	lines = myFile.readLines()
	println(lines)
	
	start = false
	
	//for(line in lines){
	lines.each {
	
	//	println('------- ' + line) 
		if(it.contains('doMatching')) {
			start = true
			println('######### START')
		} 
		if(start && it.length() > 20) {
			delimiter = it.indexOf('testcase.')
			if(delimiter > 0) {
				it = it.substring(delimiter + 43)
			}
			println(it)
			outFile.append(it + '\n')
		}
	}
}

