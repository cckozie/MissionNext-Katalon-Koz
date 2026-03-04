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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils
import javax.swing.*;

String[] usernames = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/input temp/unassigned candidates.txt')

println(usernames)

System.exit(0)

//println(selections)
//println(selections.size())
String[] groups = selections.findAll{element -> element.contains('<Group>')}
//println(groups)
groupLocs = [:]
for (group in groups){
	groupLocs.put(group, selections.findIndexOf{ it == group })
}
//println(groupLocs)
count = groupLocs.size()

myGroup = varSelections
myGroup = '<Group>' + myGroup + ','
loc = groupLocs.get(myGroup)
//println(loc)

sequence = groups.findIndexOf{ it == myGroup}
//println(sequence)

loc1 = groupLocs.get(groups[sequence]) + 1
//println(loc1)

if(sequence + 1 < count) {
	loc2 = groupLocs.get(groups[sequence + 1]) -1
} else {
	loc2 = selections.size()-1
}
//println(loc2)

myList = selections[loc1+1..loc2]
//println(myList)
myList = myList.findAll{element -> element.contains(',y')}
//println(myList)

myList.each {
	checked.add(it.substring(1, it.length()-2).replace('�', ''))
}

checked.each {
	println(it)
}

if(!GlobalVariable.testSuiteRunning) {
	frame.dispose()
}

return checked

