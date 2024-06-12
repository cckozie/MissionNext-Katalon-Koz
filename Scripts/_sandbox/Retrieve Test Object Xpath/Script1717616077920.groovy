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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.SelectorMethod
import groovy.json.JsonOutput

//TestObject tObj = findTestObject('Journey Profile/Contact Info tab/input_First Name')

myObject = 'Journey Profile/Contact Info tab/input_Gender_Male'

//TestObject tObj = findTestObject('Journey Profile/Contact Info tab/input_Gender_Male')
TestObject tObj = findTestObject(myObject)

/*
String json = JsonOutput.toJson(tObj)
println json

println JsonOutput.prettyPrint(json)
WebUI.comment(tObj.getSelectorCollection().toString())
// /html/body/div[1]/div/div[1]/div/div/div/div[2]/div[2]/form/div[2]/div[1]/div[3]/div[2]/div[1]/input
// //*[@id="group-1444754265.711"]/div[3]/div[2]/div[1]/input

//xpath = tObj.getSelectorCollection().toString()

//start = xpath.indexOf('XPATH:') + 6

TestObject testObjectAttribute = findTestObject('Journey Profile/Contact Info tab/input_Gender_Female')
//String selectedLocatorAttribute = testObjectAttribute.getSelectorCollection()[SelectorMethod.BASIC]
String selectedLocatorAttribute = testObjectAttribute.getSelectorCollection().get(SelectorMethod.BASIC)
WebUI.comment("selectedLocatorAttribute='${selectedLocatorAttribute}'")

String myreturn = testObjectAttribute.getSelectorMethod().toString()
WebUI.comment ('Returned value 1: ' + myreturn)



TestObject testObjectCSS = findTestObject("Page_CURA Healthcare Service/a_Make Appointment_CSS")
String selectedLocatorCSS = testObjectCSS.getSelectorCollection()[SelectorMethod.CSS]
String selectedLocatorCSS = testObjectCSS.getSelectorCollection()[SelectorMethod.CSS]
WebUI.comment("selectedLocatorCSS='${selectedLocatorCSS}'")

TestObject testObjectXPATH = findTestObject("Page_CURA Healthcare Service/a_Make Appointment_XPATH")
String selectedLocatorXPATH = testObjectXPATH.getSelectorCollection()[SelectorMethod.XPATH]
WebUI.comment("selectedLocatorXPATH='${selectedLocatorXPATH}'")
*/

String myreturn = tObj.getSelectorMethod().toString()
WebUI.comment ('Returned value 1: ' + myreturn)

myreturn = tObj.getSelectorCollection().toString()
WebUI.comment ('Returned value 2: ' + myreturn)

myreturn = tObj.getSelectorCollection().get(SelectorMethod.XPATH)
WebUI.comment ('Returned value 3: ' + myreturn)



//println(xpath)

