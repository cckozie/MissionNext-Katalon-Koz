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

adminMode = true

if(adminMode) {
	
		WebUI.callTestCase(findTestCase('The Mission App/Admin Login'), [:], FailureHandling.STOP_ON_FAILURE)
	
	} else {
	
		WebUI.openBrowser('')
	
		WebUI.navigateToUrl('https://www.themissionapp.com/')
	
	}
	
WebUI.maximizeWindow()

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/span_TAKE TEST'), 5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/span_TAKE TEST'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/a_Take the missionary profile test'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/a_Take the missionary profile test'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_32.3'), 5)

WebUI.setText(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_32.3'), 'Chris TEST')

WebUI.setText(findTestObject('Object Repository/The Mission App/The Mission App Test/input_First_input_32.6'), 'Kosieracki')

WebUI.setText(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_31'), 'cktest01@missionnext.org')

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I truly love everyone_input_1'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_2'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I sometimes share the gospel with the_aabfbd'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I feel like I am being called to miss_cf7ad8'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I love to learn new things_input_5'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I dont like to learn at all_gform_nex_416961'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Everyday_input_7'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Everyday_input_7'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Very important I will do whatever it _b85ac6'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_8'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Everyone (Pastor, parents, elders etc_595b39'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Very flexible_input_10'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I love to give out scriptures to anyo_bdc3b3'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I have never given out any scriptures_b85fe8'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I respect ALL cultures  enjoy the dif_f5e683'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I respect ALL cultures  enjoy the dif_f5e683'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_14'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_I am very comfortable discipling othe_af7546'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_16'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_17'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Lies come easily to help me stay out _fe88f5'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Most of my conversations are Biblical_1a62f2'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Most of my conversations are Biblical_1a62f2'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_20'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_21'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_22'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_23'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_Most of my thoughts are about me and _9a2be7'))

WebUI.waitForElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_25'),5)

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_25'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_26'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_27'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_28'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input__input_29'))

WebUI.click(findTestObject('Object Repository/The Mission App/The Mission App Test/input_This field is for validation purposes_14da4e'))

i = 0

found = false

while(!found && i < 15) {

	found = WebUI.verifyElementVisible(findTestObject('Object Repository/The Mission App/The Mission App Test/p_You scored as almost ready'), FailureHandling.OPTIONAL)

	WebUI.delay(1)
	
	i ++
	
}

if(found) {
	println('+++++ SUCCESS +++++')
} else {
	println('----- FAILED -----')
}
//You scored as “almost ready”
