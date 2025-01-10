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


titleMap = [
	"education.missionnext.org|http://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/" : "Education Partner Schools - MissionNext.org"]

titleMap.each {
	println(it.key)
	println(it.value + '\n')
}
key = 'education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/'
title = titleMap.get(key)
println(title)
/*
map = [:]

String key = "key1"

list = ["A","B","C"]

map.put(key, list)

key = "key2"

list = ["D","E","F"]

map.put(key, list)

println(map)

new File("/Users/cckozie/Documents/MissionNext/map.txt").withWriter { out ->
	map.each {
	  out.println it
	}
  }
*/

checkedSkills = [1:'TECHNICAL', 2:'IT ENGINEERING/ANALYST', 7:'DATABASE', 11:'Developer, Applications', 12:'Developer, Architect',
	13:'Developer, Front End', 21:'Developer, Python', 23:'Developer, Software', 26:'Engineer, Software', 30:'Engineer, Quality Assurance',
	31:'IT Analyst, Business', 56:'Software Development Manager', 57:'Software Systems Integrator', 69:'Manager, Project',
	71:'Manager, Quality Assurance', 74:'Manager, Software Development']

List skills = new ArrayList(checkedSkills.values());

println(skills)