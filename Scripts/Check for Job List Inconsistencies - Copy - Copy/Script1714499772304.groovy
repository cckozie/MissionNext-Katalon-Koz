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

//WebUI.callTestCase(findTestCase('_Functions/Navigate to Job Summary Page'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.switchToWindowTitle('Journey Missionary Job Summary - MissionNext.org')

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table/tbody'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

Boolean first = true

category_count = 0

old_category = ''

int row

// BUILD MAPS OF JOB SUMMARY AND THE DETAIL LIST
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Build lists of summary and details at the same time 

Summary_Category_Map = [:]

Summary_Category_Link = [:]

WebUI.click(findTestObject('Object Repository/Page_Journey Missionary Job Summary - Missi_d1fec4/span_View Job Details by Category'))

Table = driver.findElement(By.xpath('//table/tbody'))

List<WebElement> Elements = Table.findElements(By.tagName('td'))

if(printAll || 1 == 1) {
	println("Table Elements")
	println(Elements)
}

int element_count = Elements.size()

for (int count = 0; count < element_count; count++) {
    String celltext = Elements.get(count).getText()

    pos = celltext.indexOf('(')

    if (pos >= 0) {
        String category = celltext.substring(0, pos - 1)

        String jobs_text = celltext.substring(pos + 1, celltext.length() - 1)

        Summary_Category_Link.put(category, Elements[count])

		if(printAll) {
			println('>>>>>>>>>> ' + category + ' has ' + jobs_text + ' jobs.')
		}
		
        int jobs = jobs_text.toInteger()

        Summary_Category_Map.put(category, jobs)
    }
}

if(printAll) {
	println('Printing Summary_Category_Map')
	
	Summary_Category_Map.each({ def key, def val ->
	        println("Category $key count is $val")
	    })
}

new File("/Users/cckozie/Documents/MissionNext/Summary_Category_Map.txt").withWriter { out ->
	Summary_Category_Map.each {
	  out.println it
	}
}

new File("/Users/cckozie/Documents/MissionNext/Summary_Category_Link.txt").withWriter { out ->
	Summary_Category_Link.each {
	  out.println it
	}
}

// BUILD MAP OF JOB SUMMARY
Detail_Category_Description = [:]

String separator = ' — '

jobs = []

Summary_Category_Link.each({ def key, def val ->
	
		println("Navigating to " + key + " jobs list.")
	
        val.click()

		jobs_text = []
		
        String job_list = WebUI.getText(findTestObject('Page_Journey missionary-jobs-detail - MissionNext.org/div_Current Category Jobs'))
		
		if(printAll) {
			println(job_list)
		}
		
        jobs = job_list.tokenize('[+]')

        for (def job : jobs) {
            if (job.contains('Job ID:')) {
                //				println(job)
                int lf = job.indexOf('\n')

                if (lf >= 0) {
                    job = job.substring(0, lf) //					println("Job substring is " + job)
                }
                
                int dash = job.indexOf(separator)

                if (dash >= 0) {
                    job = job.substring(dash + 3)

                    jobs_text.add(job) //					println("New job substring is " + job)
                }
                
                Detail_Category_Description.put(key, jobs_text)
            }
        }
		
		WebUI.back()
        
    })

if(printAll) {
	Detail_Category_Description.each({ def key, def val ->
		println(key + " : " + val)
	})
}

new File("/Users/cckozie/Documents/MissionNext/Detail_Category_Description.txt").withWriter { out ->
	Detail_Category_Description.each {
	  out.println it
	}
}

// COMPARE NUMBER OF JOBS ON SUMMARY PAGE TO THE NUMBER IN THE DETAILS LIST (CLICKED TO FROM SUMMARY)
  
Summary_Category_Map.each({ def key, def val ->
	
	println(key)
	
	detail_jobs_count = Detail_Category_Description.get(key)
	
	println("For category " + key + "(" + val + ") the job list count is " + detail_jobs_count)
	
})
