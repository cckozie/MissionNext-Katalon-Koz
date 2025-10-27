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
import groovy.io.FileType

// Delete the Test Suite Collection Log File if no test case failed records were written to it

fileList = []

filesPath = '/Users/cckozie/Documents/MissionNext/Test Reports/_Test Suites'

dir = new File(filesPath)

dir.eachFile (FileType.FILES) { file ->
	
  fileList << file
}

timestamps = []

fileList.each {
	
	fileName = it.getName()
	
	location = fileName.indexOf('Test Suite Collection_') 
	
	if(location > 0) {
		
		timestamp = fileName.substring(fileName.length() - 23, fileName.length()-4)
		
		timestamps.add(timestamp)
	}
}

timestamps.sort()

myTimestamp = timestamps[timestamps.size() - 1]

fileList.each {
	
	if(it.getName().contains(myTimestamp)) {
		
		myFile = filesPath + '/' + it.getName()
		
		file = it
		
	}
}

println('My file is ' + myFile)

lines = myFile.readLines()

println(lines.size())

if(lines.size() == 1) {
	println('renaming file')
	file.renameTo(file + '.renamed')
//	file.delete()
//	println('deleted')
}
