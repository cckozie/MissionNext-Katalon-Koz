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
import org.sikuli.script.*
import java.awt.Desktop as Desktop
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import javax.swing.*;
import com.kms.katalon.core.testobject.TestObjectXpath

fieldTooltips = ['Check all that generally apply. Open will match all candidates.':'515',
'Check all that generally apply for your assignments.':'793',
'Does your organization accommodate mission Awareness Trips?':'890',
'Does your organization offer Vision Trips?':'1045',
'Does your organization accommodate Vision Trips?':'1151',
'The Open selection will match all short-term candidates':'1236',
'The Open selection will match all candidates':'1294',
'The candidates make a single choice.':'1369',
'Some explanation of the short-term program is helpful. ':'1559',
'Check all regions where your agency has activity. If there are needs at the home office, select the region where your organization has its headquarters.':'1591',
'Check all that are needed and/or helpful. If selecting "Other", complete the field below.':'1720']


fieldLabels = ['Time Commitments':'516',
'Available Start Options':'613',
'Travel Options':'710',
'Time/Hours Needed':'794',
'Awareness Trip':'891',
'Awareness Trip Description':'922',
'Vision Trip':'1046',
'Vision Trip Description':'1077',
'Need Candidates for Short-Term Assignments':'1183',
'Short-Term Trip Lengths':'1236',
'Short-Term Availability':'1294',
'Short-Term Objective':'1369',
'Short-Term Statement':'1426',
'Preferred Region(s)':'1592',
'Languages':'1721',
'Other Languages':'1815']

labelsY = []
fieldLabels.each{
	labelsY.add(it.value)
}
labelsCount = labelsY.size()

results = [:]
i = 0
//fieldTooltips.each{
for(it in fieldTooltips) {
	kY = it.key
	tY = it.value
	tYi = tY.toInteger() + 4
	println('tYi is ' + tYi)
	lY = labelsY[i+1]
	lYi = lY.toInteger()
	println('lYi is ' + lYi)
	while(tYi > lYi) {
		i = i + 1
		lY = labelsY[i+1]
		lYi = lY.toInteger()
		println('lYi is ' + lYi)
	}
	lY = labelsY[i]
	myKey = fieldLabels.find{ it.value == lY }?.key		
	println('tooltip ' + kY + ' is at ' + tY + ' and label ' + myKey + ' is at ' + lYi)
	results.put(myKey,kY)

}




/*
i = 0
//fieldTooltips.each {
for(it in fieldTooltips) {
	println('next pair is ' + it.key + ':' + it.value)
	found = false
	tKey = it.key
	tY = it.value.toInteger()
	println('tY is ' + tY)
	while(!found && i < labelsCount -2) {
		println('i is '+ i)
		lY = labelsY[i]
		lYi = lY.toInteger()
		println('lYi is ' + lYi + ' and lY + 1 is ' + labelsY[i+1].toInteger())
		if((tY == lYi || tY == lYi-1) && tY < labelsY[i+1].toInteger()) {
			myKey = fieldLabels.find{ it.value == lY }?.key
			println(myKey + ':' + tKey)
			results.put(myKey, tKey)
			found = true
			i = i + 1
			break
		} else {
			i = i + 1
		}
	}
//	i = i + 2

}

*/
results.each{
	println(it.key + ':' + it.value)
}