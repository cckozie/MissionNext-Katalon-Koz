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

//positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'), [('varFileName') : 'Education Candidate Forms/spouse service prefs.csv'
//        , ('varSelections') : 'Spouse Preferred Position(s)'], FailureHandling.STOP_ON_FAILURE)


String[] selections = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Education Candidate Forms/preferred positions.csv')
println(selections)
println(selections.size())
String[] groups = selections.findAll{element -> element.contains('<Group>')}
println(groups)
groupLocs = [:]
for (group in groups){
	groupLocs.put(group, selections.findIndexOf{ it == group })
}
println(groupLocs)
count = groupLocs.size()

myGroup = '<Group>positions' 
//myGroup = '<Group>regions' 
myGroup = myGroup + ','
loc = groupLocs.get(myGroup)
println(loc)

sequence = groups.findIndexOf{ it == myGroup}
println(sequence)

loc1 = groupLocs.get(groups[sequence]) + 1
println(loc1)

if(sequence + 1 < count) {
	loc2 = groupLocs.get(groups[sequence + 1]) -1
} else {
	loc2 = selections.size()-1
}
println(loc2)

myList = selections[loc1+1..loc2]
println(myList)
myList = myList.findAll{element -> element.contains(',y')}
println(myList)
group = []
myList.each {
	group.add(it.substring(1, it.length()-2).replace('�', ''))
}

group.each {
	println(it)
}
System.exit(1)
