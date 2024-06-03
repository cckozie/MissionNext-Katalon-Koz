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
//import org.sikuli.script.Key
//import org.sikuli.script.Screen
//import org.sikuli.script.FindFailed
//import org.sikuli.script.ImagePath

import org.sikuli.script.*
//import org.sikuli.api.Screen
//import org.sikuli.api.Pattern
//import org.sikuli.core.search.Match as Match

/*
 * Screen s = new Screen()
s.find("Windows.png")
s.click("Windows.png")
 */

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebUI.delay(3)

Screen s = new Screen()

myImage = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/x.png'

//Pattern icons = new Pattern(myImage).similar(0.50)

//found = s.exists(icons)

found = s.find(myImage)

println(found)

WebUI.delay(1)

