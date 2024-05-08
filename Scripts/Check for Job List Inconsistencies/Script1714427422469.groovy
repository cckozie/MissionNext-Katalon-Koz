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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

Boolean printAll = false

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebUI.focus(findTestObject('Page_Serve in Missions - MissionNext.org/a_Goer'))

WebUI.click(findTestObject('Page_Serve in Missions - MissionNext.org/Goer_arrow'))

WebUI.click(findTestObject('Page_Serve in Missions - MissionNext.org/a_Jobs'))

WebUI.click(findTestObject('Page_Positions - MissionNext.org/span_Click Here'))

// BUILD ARRAYS OF JOBS ON THE JOB LIST PAGE

List_Category = []

List_Category_Map = [:]

List_Description = []

List_Region = []

WebUI.callTestCase(findTestCase('_Functions/Navigate to Job Summary Page'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowTitle('Journey Missionary Job Summary - MissionNext.org')

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath("//table/tbody"))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

Boolean first = true

category_count = 0

old_category = ""

int row

cat_descriptions = []

for(row = 1; row < row_count; row++) {
		
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	String category = Columns.get(0).getText()
	
	if(first) {
		old_category = category
		first = false
	}
	
	List_Category.add(category)
	
	String region = Columns.get(2).getText()
	
	String description = Columns.get(1).getText() + ' (' + region + ')'
	
	List_Description.add(description)
	
	List_Region.add(region)
	
	if(category == old_category) {
	
		cat_descriptions.add(description)
		
		category_count ++
		
	} else {
		
		List_Category_Map.put(old_category, cat_descriptions)
		
		category_count = 0
		
		old_category = category
		
		cat_descriptions = []
				
	}

}

List_Category_Map.put(old_category,cat_descriptions)

if(printAll) {

	for(int e = 0; e < row - 1; e ++) {
		
		println(List_Category[e] + " : " + List_Description[e] + " : " + List_Region[e])
		
	}
	
	List_Category_Map.each { key, val ->
	    println("Category $key count is $val")
	}

}

jsonString = new JsonBuilder(List_Category_Map).toString()

new FileWriter('/Users/cckozie/Documents/MissionNext/List_Category_Map.txt').with {
	write(jsonString)
	flush()
}




// BUILD MAPS OF JOB SUMMARY AND THE DETAIL LIST
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Build lists of summary and details at the same time
Summary_Category_Map = [:]

Summary_Category_Link = [:]

Detail_Category_Description = [:]

WebUI.click(findTestObject('Object Repository/Page_Journey Missionary Job Summary - Missi_d1fec4/span_View Job Details by Category'))

int count = 0

Boolean done = false

while (!(done)) {
	Table = driver.findElement(By.xpath('//table/tbody'))

	List<WebElement> Elements = Table.findElements(By.tagName('td'))

	int element_count = Elements.size()

	if (count < element_count) {
		element = (Elements[count])

		String celltext = Elements.get(count).getText()

		pos = celltext.indexOf('(')

		if (pos >= 0) {
			String category = celltext.substring(0, pos - 1)

			String jobs_text = celltext.substring(pos + 1, celltext.length() - 1)

			//        Summary_Category_Link.put(category, Elements[count])
			if (printAll) {
				println(((('>>>>>>>>>> ' + category) + ' has ') + jobs_text) + ' jobs.')
			}
			
			int job_count = jobs_text.toInteger()

			Summary_Category_Map.put(category, job_count)

			// BUILD MAP OF JOBS IN THE CATEGORY
			String separator = ' — '

			jobs = []

			if (printAll) {
				println(('Navigating to ' + category) + ' jobs list.')
			}
			
//            element.click()
			WebUI.delay(5)
//			WebUI.click(waitForElementClickable('Page_Journey missionary-jobs-detail - MissionNext.org/a_Category_Parm', [('category') : category]),10)
			WebUI.click(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/a_Category_Parm', [('category') : category]))
//			WebUI.click(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/a_Category_Parm', [('category') : "BUSINESS AS MISSION"]))
//			WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))
			
			WebUI.waitForElementPresent(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/a_Back to Category List'),
				60, FailureHandling.STOP_ON_FAILURE)

			category_jobs = []

			String job_list = WebUI.getText(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/div_Current Category Jobs'))

			if (printAll) {
				println('Job list element is')

				println(job_list)
			}
			
			int added_jobs_count = 0
			
			int add_jobs_pos = job_list.indexOf("job listing")
			
			if(add_jobs_pos >= 0) {
				
				int there_pos = job_list.indexOf("There",add_jobs_pos - 20)
				
				if(there_pos >=0) {
					
					int start = job_list.indexOf(" ", there_pos + 6) + 1
					
					int end = job_list.indexOf(" ", start + 1)
					
					String added_jobs = job_list.substring(start,end)
					
					added_jobs_count = added_jobs.toInteger()
					
					if(printAll) {
						println("Added jobs:" + added_jobs + ":")
					}
					
				}
			}
			
			jobs = job_list.tokenize('[+]')

			for (def job : jobs) {
				if (job.contains('Job ID:')) {
					if (printAll) {
						println('job is ' + job)
					}
					
					int lf = job.indexOf('\n')

					if (lf >= 0) {
						job = job.substring(0, lf //						println("Job substring is " + job)
							)
					}
					
					int dash = job.indexOf(separator)

					if (dash >= 0) {
						job = job.substring(dash + 3)

						//						println("New job substring is " + job)
						category_jobs.add(job)
					}
					
				}
					
			}
			
			for(int i = 0; i < added_jobs_count; i ++ ) {
				
				category_jobs.add("Added job " + (i + 1))
			}
			
			Detail_Category_Description.put(category, category_jobs)

			if (printAll) {
				println((('The job list for ' + category) + ' is ') + category_jobs)
			}
			
			WebUI.delay(5)

			WebUI.click(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/a_Back to Category List'))

			WebUI.waitForElementClickable(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/a_View Job Summary List'),
				30)
		}
		
		count++
	} else {
		done = true
	}
}

jsonString = new JsonBuilder(Summary_Category_Map).toString()

new FileWriter('/Users/cckozie/Documents/MissionNext/Summary_Category_Map.txt').with {
	write(jsonString)
	flush()
}

if (printAll) {
	println('Printing Summary_Category_Map')

	Summary_Category_Map.each({ def key, def val ->
			println("Category $key count is $val")
		})
}

if (printAll) {
	Detail_Category_Description.each({ def key, def val ->
			println((key + ' : ') + val)
		})
}

jsonString = new JsonBuilder(Detail_Category_Description).toString()

new FileWriter('/Users/cckozie/Documents/MissionNext/Detail_Category_Description.txt').with {
	write(jsonString)
	flush()
}


// COMPARE NUMBER OF JOBS ON SUMMARY PAGE TO THE NUMBER IN THE DETAILS LIST (CLICKED TO FROM SUMMARY)
Summary_Category_Map.each({ def key, def val ->
//        println(key)

		detail_jobs_count = Detail_Category_Description.get(key).size()

		list_jobs_count = List_Category_Map.get(key).size()

		println('For category ' + key + '(' + val + ') the job list count is ' + detail_jobs_count + ' and the list jobs count is ' + list_jobs_count)
	})

