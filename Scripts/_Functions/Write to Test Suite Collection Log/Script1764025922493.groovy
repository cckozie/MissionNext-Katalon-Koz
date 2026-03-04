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

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)
/*
	outFile.append('Running "Write to Test Suite Collection Log" from "After_Test_Case" listener \n')
	outFile.append('Test case error flag is ' + GlobalVariable.testCaseErrorFlag + '\n')
	outFile.append('Test Suite Running flag is ' + GlobalVariable.testSuiteRunning + '\n')
*/
	if (GlobalVariable.testSuiteRunning && GlobalVariable.testCaseErrorFlag) {
		
		outFile.append('Writing to Test Suite Collection Log \n')
		
		logFile = WebUI.callTestCase(findTestCase('_Functions/Get Test Suite Collection Log File Name'), [:], FailureHandling.STOP_ON_FAILURE)
		
		outFile.append('logFile is ' + logFile + '\n')
	
		collectionsFile = new File(logFile)
	
		collectionsFile.append(GlobalVariable.testCaseName + '\n')
	}
}