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
import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import javax.swing.JFrame
import java.awt.Frame
import javax.swing.JOptionPane
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File as File

myHost = GlobalVariable.host

myDomain = GlobalVariable.domain

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf('/')+1)
myTestCase = myTestCase.substring(0,myTestCase.length()-3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '-' + myDomain + '-on_' + myHost + '.txt')

outFile.write('Running ' + myTestCase + '\n')


printIt = false

separator = '----------------------------------------------------------------------------------------------------------------------------------------------------------'

pageLinks = []

homePageLinks = [    //Format is HOME PAGE URL | LINKED TO URL
"education.explorenext.org|http://explorenext.org/events",
"education.explorenext.org|http://explorenext.org/homepage/goer/education-partner-schools/",
"education.explorenext.org|https://education.explorenext.org/",
"education.explorenext.org|https://education.explorenext.org/education-home/im-a-school",
"education.explorenext.org|https://education.explorenext.org/education-home/im-an-educator",
"education.explorenext.org|https://education.explorenext.org/education-home/im-an-organization-rep-for-schools/",
"education.explorenext.org|https://education.explorenext.org/managerlogin/?action=logout&redirect_to=http://education.explorenext.org&_wpnonce=9af575b0a6",
"education.explorenext.org|https://education.explorenext.org/register/",
"education.explorenext.org|https://education.explorenext.org/site-map/",
"education.explorenext.org|https://info.explorenext.org/browsers.php?",
"education.explorenext.org|https://explorenext.org/",
"education.explorenext.org|https://explorenext.org/contact-us/",
"education.explorenext.org|https://explorenext.org/education-jobs",
"education.explorenext.org|https://explorenext.org/homepage/about/",
"education.explorenext.org|https://explorenext.org/homepage/blog-vlog/",
"education.explorenext.org|https://explorenext.org/homepage/donate-here/",
"education.explorenext.org|https://explorenext.org/homepage/events/",
"education.explorenext.org|https://explorenext.org/homepage/goer/",
"education.explorenext.org|https://explorenext.org/homepage/sender/",
"education.explorenext.org|https://explorenext.org/homepage/supporter/",
"education.explorenext.org|https://explorenext.org/terms/",
"education.explorenext.org|https://explorenext.org/terms/legal/",
"education.explorenext.org|https://explorenext.org/terms/privacy/",
"education.explorenext.org|https://missionworks.global/",
"jg.explorenext.org|https://info.explorenext.org/browsers.php?",
"jg.explorenext.org|https://jg.explorenext.org/",
"jg.explorenext.org|https://jg.explorenext.org/journey-guide-home/login-here/",
"jg.explorenext.org|https://jg.explorenext.org/managerlogin/?action=logout&redirect_to=http://jg.explorenext.org&_wpnonce=ba898adf4a",
"jg.explorenext.org|https://jg.explorenext.org/signup/candidate",
"jg.explorenext.org|https://jg.explorenext.org/signup/organization",
"jg.explorenext.org|https://jg.explorenext.org/site-map/",
"jg.explorenext.org|https://explorenext.org/",
"jg.explorenext.org|https://explorenext.org/contact-us/",
"jg.explorenext.org|https://explorenext.org/events",
"jg.explorenext.org|https://explorenext.org/goer/",
"jg.explorenext.org|https://explorenext.org/homepage/about/",
"jg.explorenext.org|https://explorenext.org/homepage/blog-vlog/",
"jg.explorenext.org|https://explorenext.org/homepage/donate-here/",
"jg.explorenext.org|https://explorenext.org/homepage/events/",
"jg.explorenext.org|https://explorenext.org/homepage/supporter/",
"jg.explorenext.org|https://explorenext.org/sender/",
"jg.explorenext.org|https://explorenext.org/terms/",
"jg.explorenext.org|https://explorenext.org/terms/legal/",
"jg.explorenext.org|https://explorenext.org/terms/privacy/",
"jg.explorenext.org|https://missionworks.global/",
"journey.explorenext.org|http://explorenext.org/events",
"journey.explorenext.org|https://info.explorenext.org/browsers.php?",
"journey.explorenext.org|https://journey.explorenext.org/",
"journey.explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization-rep/",
"journey.explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization/",
"journey.explorenext.org|https://journey.explorenext.org/learn-more/im-an-individual/",
"journey.explorenext.org|https://journey.explorenext.org/managerlogin/?action=logout&redirect_to=https://journey.explorenext.org&_wpnonce=aed6e21c37",
"journey.explorenext.org|https://journey.explorenext.org/register/",
"journey.explorenext.org|https://journey.explorenext.org/site-map/",
"journey.explorenext.org|https://explorenext.org/",
"journey.explorenext.org|https://explorenext.org/about/",
"journey.explorenext.org|https://explorenext.org/blog-vlog/",
"journey.explorenext.org|https://explorenext.org/contact-us/",
"journey.explorenext.org|https://explorenext.org/donate-here/",
"journey.explorenext.org|https://explorenext.org/events/",
"journey.explorenext.org|https://explorenext.org/goer/",
"journey.explorenext.org|https://explorenext.org/goer/journey-partner-ministries/",
"journey.explorenext.org|https://explorenext.org/journey-jobs",
"journey.explorenext.org|https://explorenext.org/sender/",
"journey.explorenext.org|https://explorenext.org/supporter/",
"journey.explorenext.org|https://explorenext.org/terms/",
"journey.explorenext.org|https://explorenext.org/terms/legal/",
"journey.explorenext.org|https://explorenext.org/terms/privacy/",
"journey.explorenext.org|https://missionworks.global/",
"explorenext.org|https://education.explorenext.org/education-home/im-a-school/",
"explorenext.org|https://education.explorenext.org/education-home/im-an-educator/",
"explorenext.org|https://info.explorenext.org/browsers.php?",
"explorenext.org|https://journey.explorenext.org/journey-home/im-an-individual/",
"explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization/",
"explorenext.org|https://journey.explorenext.org/site-map/",
"explorenext.org|https://explorenext.org/contact-us/",
"explorenext.org|https://explorenext.org/goer/",
"explorenext.org|https://explorenext.org/homepage/about/",
"explorenext.org|https://explorenext.org/homepage/about/missionnext-strategic-partners/",
"explorenext.org|https://explorenext.org/homepage/blog-vlog/",
"explorenext.org|https://explorenext.org/homepage/donate-here/",
"explorenext.org|https://explorenext.org/homepage/events/",
"explorenext.org|https://explorenext.org/homepage/goer/",
"explorenext.org|https://explorenext.org/homepage/goer/positions/",
"explorenext.org|https://explorenext.org/homepage/goer/resources-for-goers/",
"explorenext.org|https://explorenext.org/homepage/sender/",
"explorenext.org|https://explorenext.org/homepage/sender/resources-for-senders/",
"explorenext.org|https://explorenext.org/homepage/sender/sender-churches/",
"explorenext.org|https://explorenext.org/homepage/supporter/",
"explorenext.org|https://explorenext.org/homepage/supporter/intercessor/",
"explorenext.org|https://explorenext.org/homepage/supporter/mobilizer/",
"explorenext.org|https://explorenext.org/homepage/supporter/support-donor/",
"explorenext.org|https://explorenext.org/homepage/supporter/volunteer/",
"explorenext.org|https://explorenext.org/sender/",
"explorenext.org|https://explorenext.org/supporter/",
"explorenext.org|https://explorenext.org/terms/",
"explorenext.org|https://explorenext.org/terms/legal/",
"explorenext.org|https://explorenext.org/terms/privacy/",
"explorenext.org|https://missionworks.global/",
"explorenext.org|https://quickstart.explorenext.org/",
"quickstart.explorenext.org|https://info.explorenext.org/browsers.php?",
"quickstart.explorenext.org|https://explorenext.org/",
"quickstart.explorenext.org|https://explorenext.org/blog-vlog/",
"quickstart.explorenext.org|https://explorenext.org/contact-us/",
"quickstart.explorenext.org|https://explorenext.org/events",
"quickstart.explorenext.org|https://explorenext.org/homepage/about/",
"quickstart.explorenext.org|https://explorenext.org/homepage/donate-here/",
"quickstart.explorenext.org|https://explorenext.org/homepage/events/",
"quickstart.explorenext.org|https://explorenext.org/homepage/goer/",
"quickstart.explorenext.org|https://explorenext.org/homepage/sender/",
"quickstart.explorenext.org|https://explorenext.org/homepage/supporter/",
"quickstart.explorenext.org|https://explorenext.org/terms/",
"quickstart.explorenext.org|https://explorenext.org/terms/legal/",
"quickstart.explorenext.org|https://explorenext.org/terms/privacy/",
"quickstart.explorenext.org|https://missionworks.global/",
"quickstart.explorenext.org|https://quickstart.explorenext.org/",
"quickstart.explorenext.org|https://quickstart.explorenext.org/managerlogin/?action=logout&redirect_to=http://quickstart.explorenext.org&_wpnonce=ffa470f58d",
"quickstart.explorenext.org|https://quickstart.explorenext.org/quickstart-home/login-here/",
"quickstart.explorenext.org|https://quickstart.explorenext.org/signup/candidate",
"quickstart.explorenext.org|https://quickstart.explorenext.org/site-map/"]
	
titleMap = [	//Format is HOME PAGE URL | LINKED TO URL : PAGE TITLE
"education.explorenext.org|http://explorenext.org/events" : "Events - explorenext.org",
"education.explorenext.org|http://explorenext.org/homepage/goer/education-partner-schools/" : "Education Partner Schools - explorenext.org",
"education.explorenext.org|https://education.explorenext.org/" : "Education Home - Education",
"education.explorenext.org|https://education.explorenext.org/education-home/im-a-school" : "I'm a School - Education",
"education.explorenext.org|https://education.explorenext.org/education-home/im-an-educator" : "I'm an Educator - Education",
"education.explorenext.org|https://education.explorenext.org/education-home/im-an-organization-rep-for-schools/" : "I'm an Organization Affiliate for Schools - Education",
"education.explorenext.org|https://education.explorenext.org/managerlogin/?action=logout&redirect_to=http://education.explorenext.org&_wpnonce=9af575b0a6" : "You are attempting to log out of Education",
"education.explorenext.org|https://education.explorenext.org/register/" : "Register - Education",
"education.explorenext.org|https://education.explorenext.org/site-map/" : "Site Map - Education",
"education.explorenext.org|https://info.explorenext.org/browsers.php?" : "MissionNext: Information Section",
"education.explorenext.org|https://explorenext.org/" : "Serve in Missions - explorenext.org",
"education.explorenext.org|https://explorenext.org/contact-us/" : "Contact Us - explorenext.org",
"education.explorenext.org|https://explorenext.org/education-jobs" : "Education Job Summary - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/about/" : "About - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/blog-vlog/" : "blog/vlog - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/donate-here/" : "Donate - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/events/" : "Events - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/goer/" : "Goer - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/sender/" : "Sender - explorenext.org",
"education.explorenext.org|https://explorenext.org/homepage/supporter/" : "Supporter - explorenext.org",
"education.explorenext.org|https://explorenext.org/terms/" : "Terms of Use - explorenext.org",
"education.explorenext.org|https://explorenext.org/terms/legal/" : "Legal Statement - explorenext.org",
"education.explorenext.org|https://explorenext.org/terms/privacy/" : "Privacy Statement - explorenext.org",
"education.explorenext.org|https://missionworks.global/" : "MissionWorks",
"jg.explorenext.org|https://info.explorenext.org/browsers.php?" : "MissionNext: Information Section",
"jg.explorenext.org|https://jg.explorenext.org/" : "Journey Guide Home - Journey Guide Home",
"jg.explorenext.org|https://jg.explorenext.org/journey-guide-home/login-here/" : "Login - Journey Guide Home",
"jg.explorenext.org|https://jg.explorenext.org/managerlogin/?action=logout&redirect_to=http://jg.explorenext.org&_wpnonce=ba898adf4a" : "You are attempting to log out of Journey Guide Home",
"jg.explorenext.org|https://jg.explorenext.org/signup/candidate" : "Journey Guide Home",
"jg.explorenext.org|https://jg.explorenext.org/signup/organization" : "Journey Guide Home",
"jg.explorenext.org|https://jg.explorenext.org/site-map/" : "Site Map - Journey Guide Home",
"jg.explorenext.org|https://explorenext.org/" : "Serve in Missions - explorenext.org",
"jg.explorenext.org|https://explorenext.org/contact-us/" : "Contact Us - explorenext.org",
"jg.explorenext.org|https://explorenext.org/events" : "Events - explorenext.org",
"jg.explorenext.org|https://explorenext.org/goer/" : "Goer - explorenext.org",
"jg.explorenext.org|https://explorenext.org/homepage/about/" : "About - explorenext.org",
"jg.explorenext.org|https://explorenext.org/homepage/blog-vlog/" : "blog/vlog - explorenext.org",
"jg.explorenext.org|https://explorenext.org/homepage/donate-here/" : "Donate - explorenext.org",
"jg.explorenext.org|https://explorenext.org/homepage/events/" : "Events - explorenext.org",
"jg.explorenext.org|https://explorenext.org/homepage/supporter/" : "Supporter - explorenext.org",
"jg.explorenext.org|https://explorenext.org/sender/" : "Sender - explorenext.org",
"jg.explorenext.org|https://explorenext.org/terms/" : "Terms of Use - explorenext.org",
"jg.explorenext.org|https://explorenext.org/terms/legal/" : "Legal Statement - explorenext.org",
"jg.explorenext.org|https://explorenext.org/terms/privacy/" : "Privacy Statement - explorenext.org",
"jg.explorenext.org|https://missionworks.global/" : "MissionWorks",
"journey.explorenext.org|http://explorenext.org/events" : "Events - explorenext.org",
"journey.explorenext.org|https://info.explorenext.org/browsers.php?" : "MissionNext: Information Section",
"journey.explorenext.org|https://journey.explorenext.org/" : "Journey HOME - Journey",
"journey.explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization-rep/" : "I'm an Organization Affiliate - Journey",
"journey.explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
"journey.explorenext.org|https://journey.explorenext.org/learn-more/im-an-individual/" : "I'm an Individual - Journey",
"journey.explorenext.org|https://journey.explorenext.org/managerlogin/?action=logout&redirect_to=https://journey.explorenext.org&_wpnonce=aed6e21c37" : "You are attempting to log out of Journey",
"journey.explorenext.org|https://journey.explorenext.org/register/" : "Register - Journey",
"journey.explorenext.org|https://journey.explorenext.org/site-map/" : "Site Map - Journey",
"journey.explorenext.org|https://explorenext.org/" : "Serve in Missions - explorenext.org",
"journey.explorenext.org|https://explorenext.org/about/" : "About - explorenext.org",
"journey.explorenext.org|https://explorenext.org/blog-vlog/" : "blog/vlog - explorenext.org",
"journey.explorenext.org|https://explorenext.org/contact-us/" : "Contact Us - explorenext.org",
"journey.explorenext.org|https://explorenext.org/donate-here/" : "Donate - explorenext.org",
"journey.explorenext.org|https://explorenext.org/events/" : "Events - explorenext.org",
"journey.explorenext.org|https://explorenext.org/goer/" : "Goer - explorenext.org",
"journey.explorenext.org|https://explorenext.org/goer/journey-partner-ministries/" : "Journey Partner Ministries - explorenext.org",
"journey.explorenext.org|https://explorenext.org/journey-jobs" : "Journey Missionary Job Summary - explorenext.org",
"journey.explorenext.org|https://explorenext.org/sender/" : "Sender - explorenext.org",
"journey.explorenext.org|https://explorenext.org/supporter/" : "Supporter - explorenext.org",
"journey.explorenext.org|https://explorenext.org/terms/" : "Terms of Use - explorenext.org",
"journey.explorenext.org|https://explorenext.org/terms/legal/" : "Legal Statement - explorenext.org",
"journey.explorenext.org|https://explorenext.org/terms/privacy/" : "Privacy Statement - explorenext.org",
"journey.explorenext.org|https://missionworks.global/" : "MissionWorks",
"explorenext.org|https://education.explorenext.org/education-home/im-a-school/" : "I'm a School - Education",
"explorenext.org|https://education.explorenext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
"explorenext.org|https://info.explorenext.org/browsers.php?" : "MissionNext: Information Section",
"explorenext.org|https://journey.explorenext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
"explorenext.org|https://journey.explorenext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
"explorenext.org|https://journey.explorenext.org/site-map/" : "Site Map - Journey",
"explorenext.org|https://explorenext.org/contact-us/" : "Contact Us - explorenext.org",
"explorenext.org|https://explorenext.org/goer/" : "Goer - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/about/" : "About - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/blog-vlog/" : "blog/vlog - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/donate-here/" : "Donate - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/events/" : "Events - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/goer/" : "Goer - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/goer/positions/" : "Positions - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/goer/resources-for-goers/" : "Resources for Goers - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/sender/" : "Sender - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/sender/resources-for-senders/" : "Resources for Senders - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/sender/sender-churches/" : "Churches - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/supporter/" : "Supporter - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/supporter/intercessor/" : "Intercessor - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/supporter/mobilizer/" : "Mobilizer - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/supporter/support-donor/" : "Donor - explorenext.org",
"explorenext.org|https://explorenext.org/homepage/supporter/volunteer/" : "Volunteer - explorenext.org",
"explorenext.org|https://explorenext.org/sender/" : "Sender - explorenext.org",
"explorenext.org|https://explorenext.org/supporter/" : "Supporter - explorenext.org",
"explorenext.org|https://explorenext.org/terms/" : "Terms of Use - explorenext.org",
"explorenext.org|https://explorenext.org/terms/legal/" : "Legal Statement - explorenext.org",
"explorenext.org|https://explorenext.org/terms/privacy/" : "Privacy Statement - explorenext.org",
"explorenext.org|https://missionworks.global/" : "MissionWorks",
"explorenext.org|https://quickstart.explorenext.org/" : "QuickStart Home - QuickStart",
"quickstart.explorenext.org|https://info.explorenext.org/browsers.php?" : "MissionNext: Information Section",
"quickstart.explorenext.org|https://explorenext.org/" : "Serve in Missions - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/blog-vlog/" : "blog/vlog - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/contact-us/" : "Contact Us - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/events" : "Events - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/about/" : "About - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/donate-here/" : "Donate - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/events/" : "Events - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/goer/" : "Goer - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/sender/" : "Sender - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/homepage/supporter/" : "Supporter - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/terms/" : "Terms of Use - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/terms/legal/" : "Legal Statement - explorenext.org",
"quickstart.explorenext.org|https://explorenext.org/terms/privacy/" : "Privacy Statement - explorenext.org",
"quickstart.explorenext.org|https://missionworks.global/" : "MissionWorks",
"quickstart.explorenext.org|https://quickstart.explorenext.org/" : "QuickStart Home - QuickStart",
"quickstart.explorenext.org|https://quickstart.explorenext.org/managerlogin/?action=logout&redirect_to=http://quickstart.explorenext.org&_wpnonce=ffa470f58d" : "You are attempting to log out of QuickStart",
"quickstart.explorenext.org|https://quickstart.explorenext.org/quickstart-home/login-here/" : "Login here - QuickStart",
"quickstart.explorenext.org|https://quickstart.explorenext.org/signup/candidate" : "QuickStart",
"quickstart.explorenext.org|https://quickstart.explorenext.org/site-map/" : "Site Map - QuickStart"]
	

WebUI.openBrowser('')

WebUI.maximizeWindow()

// Home page URL's
startPages = ['explorenext.org', 'journey.explorenext.org', 'education.explorenext.org', 'quickstart.explorenext.org', 'jg.explorenext.org']

// Ignore all URL's that contain any of these items
bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'wp_cron', 'twitter', 'facebook', 'linkedin', 'instagram', '#']

WebDriver driver = DriverFactory.getWebDriver()

// Find all of the links on each of the home pages
for(page in startPages) { //List of home pages
	
	WebUI.navigateToUrl(page)
	
	WebUI.waitForPageLoad(30)	
	
	elements = driver.findElements(By.tagName("a")) 	//Find all of the links on the home page being processed
	
	elements.each {		//Process each link
		
		href = it.getAttribute('href')	//Get the URL of the link
		
		parent = it.findElement(By.xpath("./.."))
		
		if(parent.isDisplayed()) {
			
			skip = false
			
			if(href != null) {				//Bypass any in the ignore list
		
				for(item in bypassList) {
					
					pos = href.indexOf(item)
					
					if(pos >= 1) {
					
						skip = true
						
						break
					}
				}
				
				if(!skip) {
					
					element = page + '|' + href
										
					pageLinks.add(element)		//Build the list of links
				}
			}
		}
	}
}
pageLinks = pageLinks.sort()	//Sort the list

myLinks = pageLinks.unique()	//Only key the links when the home page and link is unique

//Print the home page links found
if(printIt) {
	println('\n\nPage links:')
	pageLinks.each {
		println(it)
	}
}

unexpectedLinks = []

pageLinks.each { 	//Build a list of messages related to any links we were not expecting to find
	
	if(!homePageLinks.contains(it)) {
		
		delim = it.indexOf('|')
			
		homePage = it.substring(0,delim)
		
		link = it.substring(delim + 1)
	
		unexpectedLinks.add('The link from home page "' + homePage + '" to "' + link + '" was not expected.')
		
	}
}

unexpectedLinks.each {
	println(it)
}

unfoundLinks = []

homePageLinks.each {	//Build a list of messages related to any links we were expecting to find but did not find
	
	if(!pageLinks.contains(it)) {
		
		delim = it.indexOf('|')
			
		homePage = it.substring(0,delim)
		
		link = it.substring(delim + 1)
	
		unfoundLinks.add('The expected page link from home page "' + homePage + '" to "' + link + '" was not found.')
		
	}
}

unfoundLinks.each{
	println(it)
}

wrongPages = []		

notFound = []

unexpectedCount = 0

// Navigate to each link and verify that the page title is what is expected and that it does not cause a 404 error
pageLinks.each {
	
	delim = it.indexOf('|')
	
	homePage = it.substring(0,delim)
	
	link = it.substring(delim + 1)
	
	WebUI.navigateToUrl(link)
	
	WebUI.waitForPageLoad(30)
	
	title = WebUI.getWindowTitle()	//Get the page's title
	
	expectedTitle = titleMap.get(it)
	
	if(title != expectedTitle || title.length() != expectedTitle.length()) {
		
		msg = 'From the home page "' + homePage + '" with link to "' + link + '", the expected page title is "' + expectedTitle + '", but the page title found is "' + title + '".'

		println(msg)
		
		wrongPages.add(msg)	//Build a list of the wrong pages found
	}
	
	if(!WebUI.verifyTextNotPresent('Oops', false, FailureHandling.OPTIONAL)) {
		
		msg = 'From the home page "' + homePage + '" the link to "' + link + '" generated a 404 page not found error.'
		notFound.add(msg)	//Build a list of 404 errors
	}
}

outMsg = ''

//Print the messages related to unexpected links
first = true
unexpectedLinkCount = unexpectedLinks.size()
if(unexpectedLinkCount == 0) {
	msg = 'No unexpected links were encountered.\n\n'
	msgPrint(msg)
	
} else {
	count = 0
	unexpectedLinks.each {
		if(first) {
			msg = 'Unexpected links:'
			msgPrint(msg)
			msg = unexpectedLinkCount + ' unexpected links were encountered.'
			msgPrint(msg)
			msgPrint(separator)
			first = false
		}
		
		msg = it
		msgPrint(msg)
		count ++
		
		if(count == unexpectedLinkCount) {
			msgPrint(separator)
			msgPrint('\n\n')
		}
	}
}

//Print the messages related to expected links not found
first = true
unfoundLinksCount = unfoundLinks.size()
if(unfoundLinksCount == 0) {
	msg = 'All expected links were found.\n\n'
	msgPrint(msg)
	
} else {
	count = 0
	unfoundLinks.each {
		if(first) {
			msg = 'Expected but unfound links:'
			msgPrint(msg)
			msg = unfoundLinksCount + ' expected links cound not be found.'
			msgPrint(msg)
			msgPrint(separator)
			first = false
		}
		
		msg = it
		msgPrint(msg)
		count ++
		
		if(count == unfoundLinksCount) {
			msgPrint(separator)
			msgPrint('\n\n')
		}
	}
}

//Print the messages related to pages whose title did not match what was expected
first = true
wrongPageCount = wrongPages.size()
if(wrongPageCount == 0) {
	msg = 'No links to the wrong page were encountered.\n\n'
	msgPrint(msg)
	
} else {
	count = 0
	wrongPages.each {
		if(first) {
			msg = 'Links to wrong page:'
			msgPrint(msg)
			msg = wrongPageCount + ' links to the wrong page were encountered. (The page title was not what was expected.)'
			msgPrint(msg)
			msgPrint(separator)
			first = false
		}
		
		msg = it
		msgPrint(msg)
		count ++
		
		if(count == wrongPageCount) {
			msgPrint(separator)
			msgPrint('\n\n')
		}
	}
}

//Print the messages related to 404 errors
first = true
notFoundCount = notFound.size()
if(notFoundCount == 0) {
	msg = 'No page not found errors were encountered.\n\n'
	msgPrint(msg)
} else {
	count = 0
	notFound.each {
		if(first) {
			msg = '404 Errors:'
			msgPrint(msg)
			msg = notFoundCount + ' page not found errors were encountered.'
			msgPrint(msg)
			msgPrint(separator)
			first = false
		}
		
		msg = it
		msgPrint(msg)
		count ++
		
		if(count == notFoundCount) {
			msgPrint(separator)
			msgPrint('\n\n')
		}
	}
}

outFile.append(outMsg)

//Display all of the error messages
//JOptionPane.showMessageDialog(new Frame('Test result'), outMsg)

WebUI.closeBrowser()

def msgPrint(msg) {	//Function to build the output message text
	println(msg)
	outMsg = outMsg + msg + '\n'
}
