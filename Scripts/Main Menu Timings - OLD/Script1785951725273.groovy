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


profiles = [//'Education Candidate 14' : ['My Profile', 'View Job Matches', 'Search School Jobs', 'View School Matches', 'Search Schools', 'Job Inquiry List', 'Logout'],
	'Education Partner 16' : ['My Profile', 'Educator Matches', 'Search Educators', 'My Favorites', 'Job Matches', 'Job Inquiry List']]
//	'Journey Candidate 15','Journey Partner 17','QuickStart Candidate 02']
//profiles = ['Education Candidate 14' : ['Search Schools']]

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

for(profile in profiles) {
	
	println(profile.key)
	
	println(profile.value)
	
	user = profile.key
	
	timeStart = new Date()
	
	login(profile.key,1)
	
	action = 'Navigate to Login Page'

	WebUI.waitForPageLoad(30)
	
	updateDB()
	
	timeStart = new Date()
	
	login(profile.key,2)
	
	action = 'Log in'
	
	WebUI.waitForPageLoad(30)
		
	updateDB()
	
	for(link in profile.value) {
		
		println(link)
	
		timeStart = new Date()
		
		action = 'Click ' + link
		
		WebUI.click(findTestObject('Timings/a_Dashboard Link Parm', [('link') : link]))
		
		WebUI.waitForPageLoad(30)
		
//		System.exit(0)
		
		updateDB()
		
		if(link.contains('View')) {
			
			timeStart = new Date()
			
			action = "Click Back"
			
			WebUI.back()
			
			WebUI.waitForPageLoad(30)
			
			updateDB()
			
		} else if(link == 'Search School Jobs') {
			
			WebUI.switchToWindowIndex(1)
			
			WebUI.delay(1)
			
			WebDriver driver = DriverFactory.getWebDriver()
			
			driver.findElement(By.xpath("//input[@type='checkbox' and @value='Elementary School Teacher']")).click();
			
			action = "Execute Search School Jobs"
			
			Robot robot = new Robot()
			
			robot.keyPress(KeyEvent.VK_ENTER) // Simulates pressing Enter
			
			robot.keyRelease(KeyEvent.VK_ENTER)
			
			timeStart = new Date()
			
			WebUI.waitForPageLoad(30)
			
			updateDB()
			
			WebUI.closeWindowIndex(1)
			
			WebUI.switchToWindowIndex(0)
			
			WebUI.delay(1)
			
		} else if(link == 'Search Schools') {
			
			WebUI.delay(1)
			
			WebUI.setText(findTestObject('Object Repository/Timings/input_Organization'), 'in')
			
			action = "Execute Search Schools"
			
			Robot robot = new Robot()
			
			robot.keyPress(KeyEvent.VK_ENTER) // Simulates pressing Enter
			
			robot.keyRelease(KeyEvent.VK_ENTER)
			
			timeStart = new Date()
			
			WebUI.waitForPageLoad(30)
			
			updateDB()
			
			timeStart = new Date()
			
			action = "Click Back"
			
			WebUI.back()
			
			WebUI.waitForPageLoad(30)
			
			WebUI.back()
			
			WebUI.waitForPageLoad(30)

		}
	}
	
	timeStart = new Date()
	
	action = "Click Really want to log out"
	
	WebUI.click(findTestObject('Object Repository/Timings/a_Really Want to Log Out'))
	
	WebUI.waitForPageLoad(30)
	
	updateDB()
	
//	System.exit(0)
	
//	WebUI.navigateToUrl('missionnext.org')
	
}

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
	
	String where = ((('WHERE from_url = \'' + from) + '\' AND to_url = \'') + to) + '\''
	
	println(from + ' : ' + to + ' : ' + where + ' : ' + duration)
		
	now = new Date().format('yyyy-MM-dd HH:mm:ss')
	
	query = 'SELECT COUNT(*) from godaddy_mn_page_load_times ' + where
	
	println(query)
	
	sql.query(query, { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})
	println(rows)
	
	if (rows == 0) {
		sql.executeInsert('INSERT INTO godaddy_mn_page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', [from, to, duration, duration
				, duration, 1, now, now, now, from, action, user])
		
	} else {
		query = 'SELECT * from godaddy_mn_page_load_times ' + where
		println(query)
		sql.query(query, { def resultSet ->
				while (resultSet.next()) {
					lastFrom = resultSet.getString(1)
					
					min = resultSet.getFloat(3)
	
					max = resultSet.getFloat(4)
	
					avg = resultSet.getFloat(5)
	
					count = resultSet.getInt(6)
	
					minTimeStamp = resultSet.getString(7)
	
					maxTimeStamp = resultSet.getString(8)
	
					lastTimeStamp = resultSet.getString(8)
					
					lastAction = resultSet.getString(9)
					
					lastUser = resultSet.getString(10)
					
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
		
		sql.executeUpdate("UPDATE godaddy_mn_page_load_times SET max_time_from_url = $maxUrl, minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count, minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp, last_action = $action, user_profile = $user " + where)


	}
}		
	

