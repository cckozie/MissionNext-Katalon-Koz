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
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import java.awt.Robot
import java.awt.event.KeyEvent
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;




// ======== DEFINES ==========
ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
sql = new Sql(ds)

sql = new Sql(ds)

host = GlobalVariable.host

profiles = [
	'Education Candidate 14' : ['My Profile', 'View Job Matches', 'Search School Jobs', 'View School Matches', 'Search Schools', 'Job Inquiry List', 'My Favorites', 'Logout'],
	'Education Partner 16' : ['My Profile', 'My Presentation', 'Educator Matches', 'Search Educators', 'My Favorites', 'Job Matches', 'Job Inquiry List', 'Manage Folders', 'Affiliates','Logout'],
	'Journey Candidate 15' : ['My Profile', 'View Job Matches', 'Search Agency Jobs', 'View Agency Matches', 'Search Agencies', 'Job Inquiry List', 'My Favorites', 'Logout'],
	'Journey Partner 17' : ['My Profile', 'My Presentation', 'Candidate Matches', 'QuickStart Profiles', 'Search Candidates', 'My Favorites', 'Job Matches', 'Job Inquiry List', 'Manage Folders', 'Affiliates', 'Logout'],
	'QuickStart Candidate 02' : ['My Profile', 'My Opportunities', 'Logout']
	]

menus = [
	'About' : ['About', 'Join Us', 'Strategic and Mobilization Partners', 'Subscribe to Newsletter'],
	'Goer' : ['Goer', 'QuickStart', 'Start your Journey', 'Serve in Education', 'Jobs', 'Resources'],
	'Sender' : ['Sender', 'Organizations', 'Schools', 'Churches', 'Strategic and Mobilization Partners', 'Resources'],
	'Supporter' : ['Supporter', 'Mobilizer', 'Donor', 'Intercessor', 'Strategic and Mobilization Partners', 'Volunteer'],
	'Blog' : ['MissionNext Blog' , "Founders Blog"]
	]
	
goerPages = ['QuickStart' : 'button_Register Now', 'Start your Journey' : 'button_Start your Journey',  'Serve in Education' : 'button_Start Now']

senderPages = [
	'Organizations' : ['button_Apply Now for Journey', 'button_Apply Now', 'button_Sign up'],
	'Schools' : ['button_Sign Up Now', 'button_Submit']
	]
	
blogPages = ['MissionNext Blog' : 'a_Seasons', 'Founders Blog' : 'a_Appointed']

user = 'none'

action = 'Navigate to site'

from = 'none'

to = from

WebUI.openBrowser('')

WebUI.maximizeWindow()

timeStart = new Date()

WebUI.navigateToUrl('missionnext.org')

// Call Insert/Update Load Times
updateDB()

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Test Candidate and Partner Functions >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
for(profile in profiles) {
	
	println(profile.key)
	
	println(profile.value)
	
	user = profile.key
	
	timeStart = new Date()
	
	login(profile.key,1)
	
	action = 'Navigate to Login Page'

	WebUI.waitForPageLoad(60)
	
	updateDB()
	
	timeStart = new Date()
	
	login(profile.key,2)
	
	action = 'Log in'
	
	WebUI.waitForPageLoad(60)
		
	updateDB()
	
	for(link in profile.value) {
		
		WebDriver driver = DriverFactory.getWebDriver()
		
		println(link)
		
		WebUI.delay(1)
	
		timeStart = new Date()
		
		action = 'Click ' + link
		
		WebUI.click(findTestObject('Timings/a_Dashboard Link Parm', [('link') : link]))
		
		allTabs = driver.getWindowHandles()
		
		if(allTabs.size() > 1) {
			
			WebUI.switchToWindowIndex(1)
		}	
		
		WebUI.waitForPageLoad(60)
		
		updateDB()
		
		driver = DriverFactory.getWebDriver()
		
		if(link == 'Search School Jobs' || link == 'Search Agency Jobs') {
			
			if(link.contains('School')) {
			
				WebUI.switchToWindowIndex(1)
				
				WebUI.delay(1)
				
//				driver.findElement(By.xpath("//input[@type='checkbox' and @value='Elementary School Teacher']")).click();
				driver.findElement(By.xpath("//input[@type='checkbox' and @value='Volunteer/self-supported position']")).click();
				
				action = "Execute Search School Jobs"
			
			} else { 
				
				WebUI.delay(1)
				
				driver.findElement(By.xpath("//input[@type='checkbox' and @value='(!) No Preference']")).click();
				
				action = "Execute Search Agency Jobs"
			}
				
			Robot robot = new Robot()
			
			robot.keyPress(KeyEvent.VK_ENTER) // Simulates pressing Enter
			
			robot.keyRelease(KeyEvent.VK_ENTER)
			
			timeStart = new Date()
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
		} else if(link == 'Search Schools' || link == 'Search Agencies') {
			
			WebUI.delay(1)
			
			WebUI.setText(findTestObject('Object Repository/Timings/input_Organization'), 'in')
			
			action = "Execute Search Schools"
			
			Robot robot = new Robot()
			
			robot.keyPress(KeyEvent.VK_ENTER) // Simulates pressing Enter
			
			robot.keyRelease(KeyEvent.VK_ENTER)
			
			timeStart = new Date()
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
		}  else if(link == 'Search Educators') {
			
			WebUI.delay(1)
			
			WebUI.selectOptionByValue(findTestObject('Object Repository/Timings/select_Your Country of Citizenship'), 'United States', false)
			
			action = "Execute Search Educators"
			
			Robot robot = new Robot()
			
			robot.keyPress(KeyEvent.VK_ENTER) // Simulates pressing Enter
			
			robot.keyRelease(KeyEvent.VK_ENTER)
			
			timeStart = new Date()
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
		} else if(link == 'Job Matches') {
			
			WebUI.switchToWindowIndex(1)
			
			WebUI.delay(1)
			
			action = "Execute Job Matches Search"
			
			timeStart = new Date()
			
			WebUI.click(findTestObject('Object Repository/Timings/button_Matches'))
						
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
		}

		if(link != 'Logout') {
		
			returnToDashboard()
		}
	}
	
	timeStart = new Date()
	
	action = "Click Really want to log out"
	
	WebUI.click(findTestObject('Object Repository/Timings/a_Really Want to Log Out'))
	
	WebUI.waitForPageLoad(60)
	
	updateDB()
	
	WebUI.navigateToUrl('missionnext.org')
	
}

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Test Menu Functions >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

user = ''

from = 'missionnext.org'

baseObjectFolder = 'Timings/Menus/'

for(menu in menus) {
	
	objectFolder = baseObjectFolder + menu.key + '/'
	
	downArrow = objectFolder + 'span_' + menu.key + '_sub-arrow'
	
	for(menuOption in menu.value) {
		
		action = 'Click ' + menuOption + ' option on ' + menu.key + ' menu.'	
		
		WebUI.click(findTestObject(downArrow))
		
		option = objectFolder + 'a_' + menuOption
		
		WebUI.mouseOver(findTestObject(option))
		
		timeStart = new Date()
		
		WebUI.click(findTestObject(option))
		
		WebUI.waitForPageLoad(60)
		
		updateDB()
		
		WebUI.delay(1)
		
		if(menuOption in goerPages.keySet()) {
			
			button = goerPages.get(menuOption)
			
			object = objectFolder + button
			
			println(button)
			
			action = 'Click ' + button.substring(button.indexOf("_") + 1) + ' button on ' + menuOption + ' page.'
			
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
			WebUI.delay(1)
	
		}
		
		if(menuOption in senderPages.keySet()) {
			
			buttons = senderPages.get(menuOption)
			
			for(button in buttons) {
			
				object = objectFolder + button
				
				println(object)
				
				driver = DriverFactory.getWebDriver()
				
				allTabs = driver.getWindowHandles()
				
				if(allTabs.size() > 1 && WebUI.getWindowIndex() == 0) {
					
					WebUI.switchToWindowIndex(1)
					
					WebUI.delay(1)
				}
					
				action = 'Click ' + button.substring(button.indexOf("_") + 1) + ' button on ' + menuOption + ' page.'
				
				WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
				
				WebUI.waitForPageLoad(60)
				
				updateDB()
				
				WebUI.delay(1)
			}
			
			if(WebUI.getWindowIndex() == 1) {
				
				WebUI.closeWindowIndex(1)
				
				WebUI.switchToWindowIndex(0)
			}
		}
		
		if(menuOption in blogPages.keySet()) {
			
			button = blogPages.get(menuOption)
			
			object = objectFolder + button
			
			println(button)
			
			action = 'Click ' + button.substring(button.indexOf("_") + 1) + ' link on ' + menuOption + ' page.'
			
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			
			WebUI.switchToWindowIndex(1)
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
			
			WebUI.delay(1)
	
		}
		
		if(WebUI.getWindowIndex() == 1) {
			
			WebUI.closeWindowIndex(1)
			
			WebUI.switchToWindowIndex(0)
		}

		WebUI.navigateToUrl('missionnext.org')
	}
	
	for(option in ['Events', 'Donate']) {
		
		action = 'Click ' + option + ' in Header'
		
		timeStart = new Date()
		
		WebUI.click(findTestObject(baseObjectFolder + '/a_' + option))
		
		WebUI.waitForPageLoad(60)
		
		updateDB()
		
		WebUI.delay(1)
		
		WebUI.navigateToUrl('missionnext.org')
	}
}

WebUI.closeBrowser()

def login(profile, phase) {
	if(phase == 1) {
	
		new ExecutionProfilesLoader().loadProfile(profile)
		
		site = profile[0..profile.indexOf(' ') - 1]
		
		println(site)
		
		username = GlobalVariable.username
		
		password = GlobalVariable.password
				
		if(site == 'Journey Guide') {
			url = 'https://jg.'  + GlobalVariable.domain + '/journey-guide-home/login-here/'
			repository = 'Object Repository/Journey Guide/'
		} else {
			url = 'https://' + site + '.' + GlobalVariable.domain + '/' + site + '-home/login-here/'
			repository = 'Object Repository/' + site + ' Candidate Profile/Login/'
		}
		
		//WebUI.navigateToUrl('https://' + site + '.' + GlobalVariable.domain + '/' + site + '-home/login-here/')
		WebUI.navigateToUrl(url)
		
	} else if(phase == 2) {
	
		WebUI.setText(findTestObject(repository + 'input_Username'), username)
		
		WebUI.setEncryptedText(findTestObject(repository + '/input_Password'), password)
		
		WebUI.click(findTestObject(repository + '/button_Log In'))
	}
}

def calcDuration () {

	timeStop = new Date()
	
	float duration = (timeStop.getTime() - timeStart.getTime()) / 1000
	
	println(duration)
	
	return duration
}

//def updateDB(def fromUrl, def myUrl, def where, def duration) {
def updateDB() {
	
	duration = calcDuration()
	
	from = to
	
	to = WebUI.getUrl()
	
	if(to.contains("?")) {
		to = to.takeBefore("?")
	}
	
//	String where = 'WHERE from_url = \'' + from + '\' AND to_url = \'' + to + '\''
	String where = 'WHERE server = \'' + host + '\' AND from_url = \'' + from + '\' AND to_url = \'' + to + '\''
	
	println(from + ' : ' + to + ' : ' + where + ' : ' + duration)
		
	now = new Date().format('yyyy-MM-dd HH:mm:ss')
	
	query = 'SELECT COUNT(*) from server_mn_page_load_times ' + where
	
	println(query)
	
	sql.query(query, { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})
	println(rows)
	
	if (rows == 0) {
		sql.executeInsert('INSERT INTO server_mn_page_load_times VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', [host, from, to, duration, duration
				, duration, 1, now, now, now, from, action, user])
		
	} else {
		query = 'SELECT * from server_mn_page_load_times ' + where
		println(query)
		sql.query(query, { def resultSet ->
				while (resultSet.next()) {
//SELECT server, max_time_from_url, to_url, minimum_time, maximum_time, average_time, test_count, minimum_timestamp, maximum_timestamp, last_timestamp, from_url, last_action, user_profile
					
					
					lastFrom = resultSet.getString(2)
					
					min = resultSet.getFloat(4)
	
					max = resultSet.getFloat(5)
	
					avg = resultSet.getFloat(6)
	
					count = resultSet.getInt(7)
	
					minTimeStamp = resultSet.getString(8)
	
					maxTimeStamp = resultSet.getString(9)
	
					lastTimeStamp = resultSet.getString(10)
					
					lastAction = resultSet.getString(12)
					
					lastUser = resultSet.getString(13)
					
				}
			})
	
//		maxTimeStamp = ''
	
//		minTimeStamp = ''
	
		lastTimeStamp = now
	
		if (duration > max) {
			max = duration
	
			maxTimeStamp = now
			
			maxUrl = from
			
		} else {
			
			maxUrl = lastFrom
		}
			
		if (duration < min) {
			min = duration

			minTimeStamp = now
			
		}
		
		
		float newAvg = (((avg * count) + duration) / (count + 1)).round(3)
	
		count++
		

		sql.executeUpdate("UPDATE server_mn_page_load_times SET server = $host, max_time_from_url = $maxUrl, minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count, minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp, last_action = $action, user_profile = $user " + where)

	}
}		
	
def returnToDashboard() {
	
	driver = DriverFactory.getWebDriver()
	
	allTabs = driver.getWindowHandles()
	
	if(allTabs.size() > 1) {
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
		WebUI.delay(1)
		
	} else {
	
		exists = WebUI.verifyElementPresent(findTestObject('Object Repository/Timings/a_My Dashboard'), 1, FailureHandling.OPTIONAL)
		
		if(!exists) {
			
			timeStart = new Date()
			
			action = "Navigate to Dashboard"
			
			WebUI.navigateToUrl(site + '.missionnext.org/dashboard')
			
			WebUI.waitForPageLoad(60)
			
			updateDB()
		}
	}
}
