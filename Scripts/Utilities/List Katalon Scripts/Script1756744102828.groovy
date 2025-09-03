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

filePath = '/Users/cckozie/Documents/MissionNext/Scripts/'

csvFile = new File(filePath + 'Katalon Scripts.csv')

csvFile.write('Script Name\n\n')

ignore = ['Archive', 'ARCHIVE','Misc Old', 'Recorded Scripts', '.DS', 'sandbox', 'Functions', 'Copy'] //,'groovy']
myDir = "/Users/cckozie/git/MissionNext-Katalon-Koz/Scripts"

files = new File(myDir)

dirs = []

files.eachFileRecurse {
	dirs.add(it.absolutePath)
}

dirs.sort()

dirs.each {
	bypass = false
//	path = (it.absolutePath)
	path = it
	for(item in ignore) {
		if(path.contains(item)) {
			bypass = true
		}
	}
	if(!bypass && path.contains('groovy')) {
		name = path.indexOf('Scripts/') + 8
		script = (path.substring(name,path.length()-27))
		println(script)
		csvFile.append(script + '\n')
	}
}
System.exit(0)
files.eachFileRecurse {
	bypass = false
	path = (it.absolutePath)
	for(item in ignore) {
		if(path.contains(item)) {
			bypass = true
		}
	}
	if(!bypass) {
		name = path.indexOf('Scripts/') + 8
		println(path.substring(name))
		csvFile.append(path + '\n')
	}
}
