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

GlobalVariable.testSuiteCollectionRunning = true

collectionName = GlobalVariable.testSuiteCollectionName

running = collectionName.indexOf('Running')

timestamp = WebUI.callTestCase(findTestCase('_Functions/Get Timestamp'), [('varFormat'): 'timestamp'], FailureHandling.STOP_ON_FAILURE)

//fileNameBase = collectionName.substring(0, running - 1)
fileNameBase = 'Sequenced Profiles Test Suite Collection'
fileName = fileNameBase + '_' + timestamp

println(fileName)

fileName = fileName.replace(':', '.')

myFile = '/Users/cckozie/Documents/MissionNext/Test Reports/_Test Suites/' + fileName + '.txt'

GlobalVariable.testSuiteCollectionsFile = myFile

outFile = new File(myFile)
 
outFile.write(fileNameBase  + ' Report Files with Errors.\n\n')
