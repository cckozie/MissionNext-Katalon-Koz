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

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

wildcards = WebUI.callTestCase(findTestCase('_sandbox/wildcards - all types'), [('varSite'):'Education'], FailureHandling.STOP_ON_FAILURE)

/*
wcCandidates = wildcards[0]

wcPartners = wildcards[1]

println('  Candidate Wildcards')
wcCandidates.each {
	println(it.key + ':' + it.value)
}

println('  Partner Wildcards')
wcPartners.each {
	println(it.key + ':' + it.value)
}

wcPS = convertMap(wcPartners)
println('WCPartners = ' + wcPS)
outFile = new File(filePath + 'Partner Wildcards.txt')
outFile.write(wcPS)

wcCS = convertMap(wcCandidates)
println('WCCandidates = ' + wcCS)
outFile = new File(filePath + 'Candidate Wildcards.txt')
outFile.write(wcCS)

def convertMap(myMap) {
	s = myMap.toString()
	s = s.replace("[", "['")
	s = s.replace("]", "']")
	s = s.replace(":", "':")
	s = s.replace(", ", ", '")
	s = s.replace("']']", "']]")
	s = s.replace("'],", "'],\n")
	s = s.replace(", '", "', '")
	return s
}
*/
