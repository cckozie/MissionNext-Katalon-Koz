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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

action = varAction

testObject = varObject

println(testObject)

parm1 = varParm1

println(parm1)

println(('Converting ' + testObject) + ' to web element')

element = WebUiCommonHelper.findWebElement(findTestObject(testObject), 1)

loc = element.getLocation()

y = loc.getY()

println('Y location is ' + y)

top = WebUI.getViewportTopPosition()

println('Viewport top is ' + top)

bottom = (top + 600)

if (((y - top) < 150) || ((bottom - y) < 10)) {
    WebUI.scrollToPosition(0, y - 150)

    WebUI.delay(1)
}

if (action == 'click') {
    println('clicking')
    WebUI.click(findTestObject(testObject))
} else if (action == 'selectOptionByValue') {
//    println('Selecting ' + parm1)
    WebUI.selectOptionByValue(findTestObject(testObject), parm1, false)
} else if (action == 'setText') {
    WebUI.setText(findTestObject(testObject), parm1)
} else if (action == 'setEncrytedText') {
    WebUI.setEncryptedText(findTestObject(testObject), parm1)
} else if (action == 'getText') {
    return WebUI.getText(findTestObject(testObject))
} else if (action == 'verifyElementVisible') {
    return WebUI.verifyElementVisible(findTestObject(testObject), FailureHandling.OPTIONAL)
}



