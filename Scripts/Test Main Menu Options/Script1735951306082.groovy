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

printIt = false

separator = '----------------------------------------------------------------------------------------------------------------------------------------------------------'

pageLinks = []

homePageLinks = [    //Format is HOME PAGE URL | LINKED TO URL
	"education.missionnext.org|http://missionnext.org/contact-us/",
	"education.missionnext.org|http://missionnext.org/events",
	"education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/",
	"education.missionnext.org|http://missionnext.org/terms/",
	"education.missionnext.org|http://missionnext.org/terms/legal/",
	"education.missionnext.org|http://missionnext.org/terms/privacy/",
	"education.missionnext.org|https://education.missionnext.org/",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-a-school",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-a-school/",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-organization-rep-for-schools/",
	"education.missionnext.org|https://education.missionnext.org/education-home/login-here/",
	"education.missionnext.org|https://education.missionnext.org/managerlogin/?action=logout&redirect_to=https://education.missionnext.org&_wpnonce=ffa470f58d",
	"education.missionnext.org|https://education.missionnext.org/register/",
	"education.missionnext.org|https://education.missionnext.org/site-map/",
	"education.missionnext.org|https://info.missionnext.org/browsers.php?",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/login-here/",
	"education.missionnext.org|https://missionnext.org/",
	"education.missionnext.org|https://missionnext.org/category/founders-blog-archive/",
	"education.missionnext.org|https://missionnext.org/category/impact-report/",
	"education.missionnext.org|https://missionnext.org/education-jobs",
	"education.missionnext.org|https://missionnext.org/homepage/about/",
	"education.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/",
	"education.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/",
	"education.missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/",
	"education.missionnext.org|https://missionnext.org/homepage/blog-vlog/",
	"education.missionnext.org|https://missionnext.org/homepage/donate-here/",
	"education.missionnext.org|https://missionnext.org/homepage/events/",
	"education.missionnext.org|https://missionnext.org/homepage/goer/",
	"education.missionnext.org|https://missionnext.org/homepage/goer/positions/",
	"education.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/",
	"education.missionnext.org|https://missionnext.org/homepage/sender/",
	"education.missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/",
	"education.missionnext.org|https://missionnext.org/homepage/sender/sender-churches/",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/",
	"education.missionnext.org|https://missionnext.org/homepage/supporters/volunteer/",
	"education.missionnext.org|https://missionworks.global/",
	"education.missionnext.org|https://quickstart.missionnext.org/",
	"education.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/",
	"journey.missionnext.org|http://missionnext.org/contact-us/",
	"journey.missionnext.org|http://missionnext.org/events",
	"journey.missionnext.org|http://missionnext.org/terms/",
	"journey.missionnext.org|http://missionnext.org/terms/legal/",
	"journey.missionnext.org|http://missionnext.org/terms/privacy/",
	"journey.missionnext.org|https://education.missionnext.org/education-home/im-a-school/",
	"journey.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/",
	"journey.missionnext.org|https://education.missionnext.org/education-home/login-here/",
	"journey.missionnext.org|https://info.missionnext.org/browsers.php?",
	"journey.missionnext.org|https://journey.missionnext.org/",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization-rep/",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/",
	"journey.missionnext.org|https://journey.missionnext.org/learn-more/im-an-individual/",
	"journey.missionnext.org|https://journey.missionnext.org/login-here",
	"journey.missionnext.org|https://journey.missionnext.org/managerlogin/?action=logout&redirect_to=http://journey.missionnext.org&_wpnonce=ffa470f58d",
	"journey.missionnext.org|https://journey.missionnext.org/register/",
	"journey.missionnext.org|https://journey.missionnext.org/site-map/",
	"journey.missionnext.org|https://missionnext.org/",
	"journey.missionnext.org|https://missionnext.org/about/",
	"journey.missionnext.org|https://missionnext.org/about/missionnext-strategic-partners/",
	"journey.missionnext.org|https://missionnext.org/about/positions-with-missionnext/",
	"journey.missionnext.org|https://missionnext.org/blog-vlog/",
	"journey.missionnext.org|https://missionnext.org/category/founders-blog-archive/",
	"journey.missionnext.org|https://missionnext.org/category/impact-report/",
	"journey.missionnext.org|https://missionnext.org/donate-here/",
	"journey.missionnext.org|https://missionnext.org/events/",
	"journey.missionnext.org|https://missionnext.org/goer/",
	"journey.missionnext.org|https://missionnext.org/goer/journey-partner-ministries/",
	"journey.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/",
	"journey.missionnext.org|https://missionnext.org/homepage/goer/positions/",
	"journey.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/",
	"journey.missionnext.org|https://missionnext.org/journey-jobs",
	"journey.missionnext.org|https://missionnext.org/sender/",
	"journey.missionnext.org|https://missionnext.org/sender/resources-for-senders/",
	"journey.missionnext.org|https://missionnext.org/sender/sender-churches/",
	"journey.missionnext.org|https://missionnext.org/supporter/",
	"journey.missionnext.org|https://missionworks.global/",
	"journey.missionnext.org|https://quickstart.missionnext.org/",
	"journey.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/",
	"missionnext.org|https://education.missionnext.org/education-home/im-a-school/",
	"missionnext.org|https://education.missionnext.org/education-home/im-an-educator/",
	"missionnext.org|https://education.missionnext.org/education-home/login-here/",
	"missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/",
	"missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/",
	"missionnext.org|https://journey.missionnext.org/journey-home/login-here/",
	"missionnext.org|https://missionnext.org/category/founders-blog-archive/",
	"missionnext.org|https://missionnext.org/category/impact-report/",
	"missionnext.org|https://missionnext.org/contact-us/",
	"missionnext.org|https://missionnext.org/goer/",
	"missionnext.org|https://missionnext.org/goer/positions/",
	"missionnext.org|https://missionnext.org/homepage/about/",
	"missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/",
	"missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/",
	"missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/",
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
	"missionnext.org|https://missionnext.org/missionconnexion-northwest/",
	"missionnext.org|https://missionnext.org/sender/",
	"missionnext.org|https://missionnext.org/site-map/",
	"missionnext.org|https://missionnext.org/supporter/",
	"missionnext.org|https://missionnext.org/terms/",
	"missionnext.org|https://missionnext.org/terms/legal/",
	"missionnext.org|https://missionnext.org/terms/privacy/",
	"missionnext.org|https://missionworks.global/",
	"missionnext.org|https://quickstart.missionnext.org/",
	"missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/",
	"quickstart.missionnext.org|http://info.missionnext.org/browsers.php?",
	"quickstart.missionnext.org|http://missionnext.org/contact-us/",
	"quickstart.missionnext.org|http://missionnext.org/events",
	"quickstart.missionnext.org|http://missionnext.org/terms/",
	"quickstart.missionnext.org|http://missionnext.org/terms/legal/",
	"quickstart.missionnext.org|http://missionnext.org/terms/privacy/",
	"quickstart.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/",
	"quickstart.missionnext.org|https://education.missionnext.org/education-home/login-here/",
	"quickstart.missionnext.org|https://journey.missionnext.org/education-home/login-here/",
	"quickstart.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/",
	"quickstart.missionnext.org|https://missionnext.org/",
	"quickstart.missionnext.org|https://missionnext.org/blog-vlog/",
	"quickstart.missionnext.org|https://missionnext.org/category/founders-blog-archive/",
	"quickstart.missionnext.org|https://missionnext.org/category/impact-report/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/blog-vlog/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/donate-here/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/events/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/positions/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/agencies/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/sender-churches/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/sender-schools/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/",
	"quickstart.missionnext.org|https://missionworks.global/",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/managerlogin/?action=logout&redirect_to=https://quickstart.missionnext.org&_wpnonce=691b8c6ae4",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/signup/candidate",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/site-map/"]
	
titleMap = [	//Format is HOME PAGE URL | LINKED TO URL : PAGE TITLE
	"education.missionnext.org|http://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/homepage/goer/education-partner-schools/" : "Education Partner Schools - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
	"education.missionnext.org|http://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
	"education.missionnext.org|https://education.missionnext.org/" : "Education Home - Education",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-a-school/" : "I'm a School - Education",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator" : "I'm an Educator - Education",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
	"education.missionnext.org|https://education.missionnext.org/education-home/im-an-organization-rep-for-schools/" : "I'm an Organization Affiliate for Schools - Education",
	"education.missionnext.org|https://education.missionnext.org/education-home/login-here/" : "Login - Education",
	"education.missionnext.org|https://education.missionnext.org/managerlogin/?action=logout&redirect_to=https://education.missionnext.org&_wpnonce=ffa470f58d" : "You are attempting to log out of Education",
	"education.missionnext.org|https://education.missionnext.org/register/" : "Register - Education",
	"education.missionnext.org|https://education.missionnext.org/site-map/" : "Site Map - Education",
	"education.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
	"education.missionnext.org|https://journey.missionnext.org/journey-home/login-here/" : "Login - Journey",
	"education.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/category/founders-blog-archive/" : "Founder's Blog Archives - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/category/impact-report/" : "Impact Report Archives - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/education-jobs" : "Education Job Summary - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/" : "Mobilization Partners - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/" : "Positions with MissionNext - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/blog-vlog/" : "blog/vlog - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/goer/" : "Goer - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/goer/positions/" : "Positions - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/" : "Resources for Goers - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/sender/" : "Sender - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/" : "Resources for Senders - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/sender/sender-churches/" : "Churches - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/" : "Intercessor - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/" : "Mobilizer - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/" : "Donor - MissionNext.org",
	"education.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/" : "Volunteer - MissionNext.org",
	"education.missionnext.org|https://missionworks.global/" : "MissionWorks",
	"education.missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
	"education.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/" : "Login here - QuickStart",
	"journey.missionnext.org|http://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
	"journey.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
	"journey.missionnext.org|http://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
	"journey.missionnext.org|http://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
	"journey.missionnext.org|http://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
	"journey.missionnext.org|https://education.missionnext.org/education-home/im-a-school/" : "I'm a School - Education",
	"journey.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
	"journey.missionnext.org|https://education.missionnext.org/education-home/login-here/" : "Login - Education",
	"journey.missionnext.org|https://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
	"journey.missionnext.org|https://journey.missionnext.org/" : "Journey HOME - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization-rep/" : "I'm an Organization Affiliate - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/learn-more/im-an-individual/" : "I'm an Individual - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/login-here" : "Login - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/managerlogin/?action=logout&redirect_to=http://journey.missionnext.org&_wpnonce=ffa470f58d" : "You are attempting to log out of Journey",
	"journey.missionnext.org|https://journey.missionnext.org/register/" : "Register - Journey",
	"journey.missionnext.org|https://journey.missionnext.org/site-map/" : "Site Map - Journey",
	"journey.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/about/" : "About - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/about/positions-with-missionnext/" : "Positions with MissionNext - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/blog-vlog/" : "blog/vlog - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/category/founders-blog-archive/" : "Founder's Blog Archives - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/category/impact-report/" : "Impact Report Archives - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/donate-here/" : "Donate - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/events/" : "Events - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/goer/" : "Goer - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/goer/journey-partner-ministries/" : "Journey Partner Ministries - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/" : "Mobilization Partners - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/goer/positions/" : "Positions - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/" : "Resources for Goers - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/" : "Intercessor - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/" : "Mobilizer - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/" : "Donor - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/" : "Volunteer - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/journey-jobs" : "Journey Missionary Job Summary - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/sender/" : "Sender - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/sender/resources-for-senders/" : "Resources for Senders - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/sender/sender-churches/" : "Churches - MissionNext.org",
	"journey.missionnext.org|https://missionnext.org/supporter/" : "Supporter - MissionNext.org",
	"journey.missionnext.org|https://missionworks.global/" : "MissionWorks",
	"journey.missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
	"journey.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/" : "Login here - QuickStart",
	"missionnext.org|https://education.missionnext.org/education-home/im-a-school/" : "I'm a School - Education",
	"missionnext.org|https://education.missionnext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
	"missionnext.org|https://education.missionnext.org/education-home/login-here/" : "Login - Education",
	"missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
	"missionnext.org|https://journey.missionnext.org/journey-home/im-an-organization/" : "I'm an Organization - Journey",
	"missionnext.org|https://journey.missionnext.org/journey-home/login-here/" : "Login - Journey",
	"missionnext.org|https://missionnext.org/category/founders-blog-archive/" : "Founder's Blog Archives - MissionNext.org",
	"missionnext.org|https://missionnext.org/category/impact-report/" : "Impact Report Archives - MissionNext.org",
	"missionnext.org|https://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
	"missionnext.org|https://missionnext.org/goer/" : "Goer - MissionNext.org",
	"missionnext.org|https://missionnext.org/goer/positions/" : "Positions - MissionNext.org",
	"missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
	"missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - MissionNext.org",
	"missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/" : "Mobilization Partners - MissionNext.org",
	"missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/" : "Positions with MissionNext - MissionNext.org",
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
	"missionnext.org|https://missionnext.org/missionconnexion-northwest/" : "MissionConnexion Northwest - MissionNext.org",
	"missionnext.org|https://missionnext.org/sender/" : "Sender - MissionNext.org",
	"missionnext.org|https://missionnext.org/site-map/" : "Site Map - MissionNext.org",
	"missionnext.org|https://missionnext.org/supporter/" : "Supporter - MissionNext.org",
	"missionnext.org|https://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
	"missionnext.org|https://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
	"missionnext.org|https://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
	"missionnext.org|https://missionworks.global/" : "MissionWorks",
	"missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
	"missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/" : "Login here - QuickStart",
	"quickstart.missionnext.org|http://info.missionnext.org/browsers.php?" : "MissionNext: Information Section",
	"quickstart.missionnext.org|http://missionnext.org/contact-us/" : "Contact Us - MissionNext.org",
	"quickstart.missionnext.org|http://missionnext.org/events" : "Events - MissionNext.org",
	"quickstart.missionnext.org|http://missionnext.org/terms/" : "Terms of Use - MissionNext.org",
	"quickstart.missionnext.org|http://missionnext.org/terms/legal/" : "Legal Statement - MissionNext.org",
	"quickstart.missionnext.org|http://missionnext.org/terms/privacy/" : "Privacy Statement - MissionNext.org",
	"quickstart.missionnext.org|https://education.missionnext.org/education-home/im-an-educator/" : "I'm an Educator - Education",
	"quickstart.missionnext.org|https://education.missionnext.org/education-home/login-here/" : "Login - Education",
	"quickstart.missionnext.org|https://journey.missionnext.org/education-home/login-here/" : "Login - Journey",
	"quickstart.missionnext.org|https://journey.missionnext.org/journey-home/im-an-individual/" : "I'm an Individual - Journey",
	"quickstart.missionnext.org|https://missionnext.org/" : "Serve in Missions - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/blog-vlog/" : "blog/vlog - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/category/founders-blog-archive/" : "Founder's Blog Archives - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/category/impact-report/" : "Impact Report Archives - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/" : "About - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners" : "MissionNext Strategic Partners - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/missionnext-strategic-partners/" : "MissionNext Strategic Partners - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/mobilization-partners/" : "Mobilization Partners - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/about/positions-with-missionnext/" : "Positions with MissionNext - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/blog-vlog/" : "blog/vlog - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/donate-here/" : "Donate - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/events/" : "Events - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/" : "Goer - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/positions/" : "Positions - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/goer/resources-for-goers/" : "Resources for Goers - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/" : "Sender - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/agencies/" : "Page not found - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/resources-for-senders/" : "Resources for Senders - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/sender-churches/" : "Churches - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/sender/sender-schools/" : "Page not found - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/" : "Supporter - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/intercessor/" : "Intercessor - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/mobilizer/" : "Mobilizer - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/support-donor/" : "Donor - MissionNext.org",
	"quickstart.missionnext.org|https://missionnext.org/homepage/supporter/volunteer/" : "Volunteer - MissionNext.org",
	"quickstart.missionnext.org|https://missionworks.global/" : "MissionWorks",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/" : "QuickStart Home - QuickStart",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/managerlogin/?action=logout&redirect_to=https://quickstart.missionnext.org&_wpnonce=691b8c6ae4" : "You are attempting to log out of QuickStart",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/quickstart-home/login-here/" : "Login here - QuickStart",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/signup/candidate" : "QuickStart",
	"quickstart.missionnext.org|https://quickstart.missionnext.org/site-map/" : "Site Map - QuickStart"]
	
	
WebUI.openBrowser('')

WebUI.maximizeWindow()

// Home page URL's
startPages = ['missionnext.org', 'journey.missionnext.org', 'education.missionnext.org', 'quickstart.missionnext.org']

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

//Display all of the error messages
JOptionPane.showMessageDialog(new Frame('Test result'), outMsg)

WebUI.closeBrowser()

def msgPrint(msg) {	//Function to build the output message text
	println(msg)
	outMsg = outMsg + msg + '\n'
}
