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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

///// NEED TO EXCLUDE HIDDEN MENUS i.e. LINKS IN li ELEMENTS WHOSE PARENT IS HIDDEN

// 1. RUN FIRST WITH FUNCTION SET TO 'get links', THEN REPLACE THE homePageLinks LIST WITH THE LIST PRINTED IN THE CONSOLE, REPLACING THE LAST COMMA WITH A ]
// 2. RUN WITH FUNCTION SET TO 'get titles', THEN REPLACE THE titleMap LIST WITH THE LIST PRINTED IN THE CONSOLE, REPLACING THE LAST COMMA WITH A ]
// 3. REPLACE BOTH LISTS IN THE 'Test All Menu Links' TEST CASE WITH THE UPDATED LISTS FROM THIS TEST CASE

//function = 'get links'
function = 'get titles'

pageLinks = []

homePageLinks = [    //Format is HOME PAGE URL | LINKED TO URL
"education.missionnext.org|http://missionnext.org/events",
"education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/",
"education.missionnext.org|https://education.missionnext.org/",
"education.missionnext.org|https://education.missionnext.org/education-home/im-a-school",
"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator",
"education.missionnext.org|https://education.missionnext.org/education-home/im-an-organization-rep-for-schools/",
"education.missionnext.org|https://education.missionnext.org/managerlogin/?action=logout&redirect_to=http://education.missionnext.org&_wpnonce=9af575b0a6",
"education.missionnext.org|https://education.missionnext.org/register/",
"education.missionnext.org|https://education.missionnext.org/site-map/",
"education.missionnext.org|https://info.missionnext.org/browsers.php?",
"education.missionnext.org|https://missionnext.org/",
"education.missionnext.org|https://missionnext.org/contact-us/",
"education.missionnext.org|https://missionnext.org/education-jobs",
"education.missionnext.org|https://missionnext.org/homepage/about/",
"education.missionnext.org|https://missionnext.org/homepage/blog-vlog/",
"education.missionnext.org|https://missionnext.org/homepage/donate-here/",
"education.missionnext.org|https://missionnext.org/homepage/events/",
"education.missionnext.org|https://missionnext.org/homepage/goer/",
"education.missionnext.org|https://missionnext.org/homepage/sender/",
"education.missionnext.org|https://missionnext.org/homepage/supporter/",
"education.missionnext.org|https://missionnext.org/terms/",
"education.missionnext.org|https://missionnext.org/terms/legal/",
"education.missionnext.org|https://missionnext.org/terms/privacy/",
"education.missionnext.org|https://missionworks.global/",
"jg.missionnext.org|https://info.missionnext.org/browsers.php?",
"jg.missionnext.org|https://jg.missionnext.org/",
"jg.missionnext.org|https://jg.missionnext.org/journey-guide-home/login-here/",
"jg.missionnext.org|https://jg.missionnext.org/managerlogin/?action=logout&redirect_to=http://jg.missionnext.org&_wpnonce=ba898adf4a",
"jg.missionnext.org|https://jg.missionnext.org/signup/candidate",
"jg.missionnext.org|https://jg.missionnext.org/signup/organization",
"jg.missionnext.org|https://jg.missionnext.org/site-map/",
"jg.missionnext.org|https://missionnext.org/",
"jg.missionnext.org|https://missionnext.org/contact-us/",
"jg.missionnext.org|https://missionnext.org/events",
"jg.missionnext.org|https://missionnext.org/goer/",
"jg.missionnext.org|https://missionnext.org/homepage/about/",
"jg.missionnext.org|https://missionnext.org/homepage/blog-vlog/",
"jg.missionnext.org|https://missionnext.org/homepage/donate-here/",
"jg.missionnext.org|https://missionnext.org/homepage/events/",
"jg.missionnext.org|https://missionnext.org/homepage/supporter/",
"jg.missionnext.org|https://missionnext.org/sender/",
"jg.missionnext.org|https://missionnext.org/terms/",
"jg.missionnext.org|https://missionnext.org/terms/legal/",
"jg.missionnext.org|https://missionnext.org/terms/privacy/",
"jg.missionnext.org|https://missionworks.global/",
"journey.missionnext.org|http://missionnext.org/events",
"journey.missionnext.org|https://info.missionnext.org/browsers.php?",
"journey.missionnext.org|https://journey.missionnext.org/",
"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization-rep/",
"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/",
"journey.missionnext.org|https://journey.missionnext.org/learn-more/im-an-individual/",
"journey.missionnext.org|https://journey.missionnext.org/managerlogin/?action=logout&redirect_to=https://journey.missionnext.org&_wpnonce=aed6e21c37",
"journey.missionnext.org|https://journey.missionnext.org/register/",
"journey.missionnext.org|https://journey.missionnext.org/site-map/",
"journey.missionnext.org|https://missionnext.org/",
"journey.missionnext.org|https://missionnext.org/about/",
"journey.missionnext.org|https://missionnext.org/blog-vlog/",
"journey.missionnext.org|https://missionnext.org/contact-us/",
"journey.missionnext.org|https://missionnext.org/donate-here/",
"journey.missionnext.org|https://missionnext.org/events/",
"journey.missionnext.org|https://missionnext.org/goer/",
"journey.missionnext.org|https://missionnext.org/goer/journey-partner-ministries/",
"journey.missionnext.org|https://missionnext.org/journey-jobs",
"journey.missionnext.org|https://missionnext.org/sender/",
"journey.missionnext.org|https://missionnext.org/supporter/",
"journey.missionnext.org|https://missionnext.org/terms/",
"journey.missionnext.org|https://missionnext.org/terms/legal/",
"journey.missionnext.org|https://missionnext.org/terms/privacy/",
"journey.missionnext.org|https://missionworks.global/",
"missionnext.org|https://education.missionnext.org/education-home/im-a-school/",
"missionnext.org|https://education.missionnext.org/education-home/im-an-educator/",
"missionnext.org|https://info.missionnext.org/browsers.php?",
"missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/",
"missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/",
"missionnext.org|https://journey.missionnext.org/site-map/",
"missionnext.org|https://missionnext.org/contact-us/",
"missionnext.org|https://missionnext.org/goer/",
"missionnext.org|https://missionnext.org/homepage/about/",
"missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/",
"missionnext.org|https://missionnext.org/homepage/blog-vlog/",
"missionnext.org|https://missionnext.org/homepage/donate-here/",
"missionnext.org|https://missionnext.org/homepage/events/",
"missionnext.org|https://missionnext.org/homepage/goer/",
"missionnext.org|https://missionnext.org/homepage/goer/positions/",
"missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/",
"missionnext.org|https://missionnext.org/homepage/sender/",
"missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/",
"missionnext.org|https://missionnext.org/homepage/sender/sender-churches/",
"missionnext.org|https://missionnext.org/homepage/supporter/",
"missionnext.org|https://missionnext.org/homepage/supporter/intercessor/",
"missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/",
"missionnext.org|https://missionnext.org/homepage/supporter/support-donor/",
"missionnext.org|https://missionnext.org/homepage/supporter/volunteer/",
"missionnext.org|https://missionnext.org/sender/",
"missionnext.org|https://missionnext.org/supporter/",
"missionnext.org|https://missionnext.org/terms/",
"missionnext.org|https://missionnext.org/terms/legal/",
"missionnext.org|https://missionnext.org/terms/privacy/",
"missionnext.org|https://missionworks.global/",
"missionnext.org|https://quickstart.missionnext.org/",
"quickstart.missionnext.org|https://info.missionnext.org/browsers.php?",
"quickstart.missionnext.org|https://missionnext.org/",
"quickstart.missionnext.org|https://missionnext.org/blog-vlog/",
"quickstart.missionnext.org|https://missionnext.org/contact-us/",
"quickstart.missionnext.org|https://missionnext.org/events",
"quickstart.missionnext.org|https://missionnext.org/homepage/about/",
"quickstart.missionnext.org|https://missionnext.org/homepage/donate-here/",
"quickstart.missionnext.org|https://missionnext.org/homepage/events/",
"quickstart.missionnext.org|https://missionnext.org/homepage/goer/",
"quickstart.missionnext.org|https://missionnext.org/homepage/sender/",
"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/",
"quickstart.missionnext.org|https://missionnext.org/terms/",
"quickstart.missionnext.org|https://missionnext.org/terms/legal/",
"quickstart.missionnext.org|https://missionnext.org/terms/privacy/",
"quickstart.missionnext.org|https://missionworks.global/",
"quickstart.missionnext.org|https://quickstart.missionnext.org/",
"quickstart.missionnext.org|https://quickstart.missionnext.org/managerlogin/?action=logout&redirect_to=http://quickstart.missionnext.org&_wpnonce=ffa470f58d",
"quickstart.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/",
"quickstart.missionnext.org|https://quickstart.missionnext.org/signup/candidate",
"quickstart.missionnext.org|https://quickstart.missionnext.org/site-map/"]
	

	
titleMap = [	//Format is HOME PAGE URL | LINKED TO URL : LINKED PAGE TITLE
"education.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
"education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/" : "Education Partner Schools - MissionNext.org",
"education.missionnext.org|https://education.missionnext.org/" : "Education Home - Education",
"education.missionnext.org|https://education.missionnext.org/education-home/im-a-school" : "I'm a School - Education",
"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator" : "I'm an Educator - Education",
"education.missionnext.org|https://education.missionnext.org/education-home/im-an-organization-rep-for-schools/" : "I'm an Organization Affiliate for Schools - Education",
"education.missionnext.org|https://education.missionnext.org/managerlogin/?action=logout&redirect_to=http://education.missionnext.org&_wpnonce=9af575b0a6" : "You are attempting to log out of Education",
"education.missionnext.org|https://education.missionnext.org/register/" : "Register - Education",
"education.missionnext.org|https://education.missionnext.org/site-map/" : "Site Map - Education",
"education.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
"education.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
"education.missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
"education.missionnext.org|https://missionnext.org/education-jobs" : "Education Job Summary - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/blog-vlog/" : "blog/vlog - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/goer/" : "Goer - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/sender/" : "Sender - MissionNext.org",
"education.missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
"education.missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
"education.missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
"education.missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
"education.missionnext.org|https://missionworks.global/" : "MissionWorks",
"jg.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
"jg.missionnext.org|https://jg.missionnext.org/" : "Journey Guide Home - Journey Guide Home",
"jg.missionnext.org|https://jg.missionnext.org/journey-guide-home/login-here/" : "Login - Journey Guide Home",
"jg.missionnext.org|https://jg.missionnext.org/managerlogin/?action=logout&redirect_to=http://jg.missionnext.org&_wpnonce=ba898adf4a" : "You are attempting to log out of Journey Guide Home",
"jg.missionnext.org|https://jg.missionnext.org/signup/candidate" : "Journey Guide Home",
"jg.missionnext.org|https://jg.missionnext.org/signup/organization" : "Journey Guide Home",
"jg.missionnext.org|https://jg.missionnext.org/site-map/" : "Site Map - Journey Guide Home",
"jg.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/events" : "Events - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/goer/" : "Goer - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/homepage/blog-vlog/" : "blog/vlog - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/sender/" : "Sender - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
"jg.missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
"jg.missionnext.org|https://missionworks.global/" : "MissionWorks",
"journey.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
"journey.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
"journey.missionnext.org|https://journey.missionnext.org/" : "Journey HOME - Journey",
"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization-rep/" : "I'm an Organization Affiliate - Journey",
"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
"journey.missionnext.org|https://journey.missionnext.org/learn-more/im-an-individual/" : "I'm an Individual - Journey",
"journey.missionnext.org|https://journey.missionnext.org/managerlogin/?action=logout&redirect_to=https://journey.missionnext.org&_wpnonce=aed6e21c37" : "You are attempting to log out of Journey",
"journey.missionnext.org|https://journey.missionnext.org/register/" : "Register - Journey",
"journey.missionnext.org|https://journey.missionnext.org/site-map/" : "Site Map - Journey",
"journey.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/about/" : "About - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/blog-vlog/" : "blog/vlog - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/donate-here/" : "Donate - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/events/" : "Events - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/goer/" : "Goer - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/goer/journey-partner-ministries/" : "Journey Partner Ministries - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/journey-jobs" : "Journey Missionary Job Summary - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/sender/" : "Sender - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/supporter/" : "Supporter - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
"journey.missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
"journey.missionnext.org|https://missionworks.global/" : "MissionWorks",
"missionnext.org|https://education.missionnext.org/education-home/im-a-school/" : "I'm a School - Education",
"missionnext.org|https://education.missionnext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
"missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
"missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
"missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
"missionnext.org|https://journey.missionnext.org/site-map/" : "Site Map - Journey",
"missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
"missionnext.org|https://missionnext.org/goer/" : "Goer - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/blog-vlog/" : "blog/vlog - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/goer/" : "Goer - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/goer/positions/" : "Positions - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/" : "Resources for Goers - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/sender/" : "Sender - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/" : "Resources for Senders - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/sender/sender-churches/" : "Churches - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/supporter/intercessor/" : "Intercessor - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/" : "Mobilizer - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/supporter/support-donor/" : "Donor - MissionNext.org",
"missionnext.org|https://missionnext.org/homepage/supporter/volunteer/" : "Volunteer - MissionNext.org",
"missionnext.org|https://missionnext.org/sender/" : "Sender - MissionNext.org",
"missionnext.org|https://missionnext.org/supporter/" : "Supporter - MissionNext.org",
"missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
"missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
"missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
"missionnext.org|https://missionworks.global/" : "MissionWorks",
"missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
"quickstart.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
"quickstart.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/blog-vlog/" : "blog/vlog - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/events" : "Events - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/goer/" : "Goer - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/sender/" : "Sender - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
"quickstart.missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
"quickstart.missionnext.org|https://missionworks.global/" : "MissionWorks",
"quickstart.missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
"quickstart.missionnext.org|https://quickstart.missionnext.org/managerlogin/?action=logout&redirect_to=http://quickstart.missionnext.org&_wpnonce=ffa470f58d" : "You are attempting to log out of QuickStart",
"quickstart.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/" : "Login here - QuickStart",
"quickstart.missionnext.org|https://quickstart.missionnext.org/signup/candidate" : "QuickStart",
"quickstart.missionnext.org|https://quickstart.missionnext.org/site-map/" : "Site Map - QuickStart"]
	
titleMap = [:]

WebUI.openBrowser('')

WebUI.maximizeWindow()

// Find all of the links on missionnext, journey, education, and quickstart home pages
startPages = ['missionnext.org', 'journey.missionnext.org', 'education.missionnext.org', 'quickstart.missionnext.org', 'jg.missionnext.org']

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'wp_cron', 'twitter', 'facebook', 'linkedin', 'instagram', '#']

WebDriver driver = DriverFactory.getWebDriver()

if(function == 'get links') {
	for(page in startPages) {
		
		WebUI.navigateToUrl(page)
		
		WebUI.waitForPageLoad(30)
		
		elements = driver.findElements(By.tagName("a"));
		
		elements.each {
			
			href = it.getAttribute('href')
			
			parent = it.findElement(By.xpath("./.."))
			
			if(parent.isDisplayed()) {
			
				skip = false
				
				if(href != null) {
			
					for(item in bypassList) {
						
						pos = href.indexOf(item)
						
						if(pos >= 1) {
						
							skip = true
							
							break
						}
					}
					
					if(!skip) {
						
						element = page + '|' + href
						
						pageLinks.add(element)
					}
				}
			}
		}
	}
	pageLinks = pageLinks.sort()
	
	myLinks = pageLinks.unique()
	
	//Print the home page list so it can be pasted into homePageLinks
	println('\n\nPage links:')
	pageLinks.each {
		println('"' + it + '",')
	}
	
	System.exit(0)
}

if(function == 'get titles') {

	// Capture the title of the pages linked to
	homePageLinks.each {
		
		delim = it.indexOf('|')
		
		homePage = it.substring(0,delim)
		
		link = it.substring(delim + 1)
		
		WebUI.navigateToUrl(link)
		
		WebUI.waitForPageLoad(30)
		
		title = WebUI.getWindowTitle()	//Get the page's title
		
		titleMap.put(it, title)
		
	}
	
	WebUI.closeBrowser()
	
	//Print the map so it can be pasted into the titleMap
	println('\n\ntitleMap:')
	titleMap.each {
		println('"' + it.key + '" : "' + it.value + '",')
	}
}
	
