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
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

KeywordLogger log = new KeywordLogger()

count = WebUI.callTestCase(findTestCase('_Functions/Find error messages'), [varFunction : 'count'], FailureHandling.STOP_ON_FAILURE)

outFile = GlobalVariable.outFile

if(count == 0) {
	outText = '??? Bypassing field error message test'
	
    outFile.append(outText + '\n')
	
} else {

	fieldList = varFieldList
	
	requiredFieldMsgs = varRequiredFieldMsgs
	
	outText = ('Verifying error messages for fields ' + fieldList)
	
	for (def field : fieldList) {
	    errorMsg = requiredFieldMsgs.get(field)
	
	    msg = WebUI.verifyTextPresent(errorMsg, false, FailureHandling.OPTIONAL)
	
	    println((field + ':') + msg)
	
	    if (!(msg)) {
	        outText = (((('--- The expected error message "' + errorMsg) + '" for field ') + field) + ' was not found.')
	
	        println(outText)
	
	        outFile.append(outText + '\n')
			log.logFailed(outText)
			
			GlobalVariable.testCaseErrorFlag = true
	    }
	}
	
	WebUI.delay(GlobalVariable.fieldTestDelay)
}
