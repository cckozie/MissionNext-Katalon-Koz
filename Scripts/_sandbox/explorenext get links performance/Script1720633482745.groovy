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

ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

sql = new Sql(ds)

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'facebook', 'instagram'
	, 'twitter', 'linkedin', 'browsers', '#', 'group']

sql.execute('DELETE FROM en_web_pages_bypass')

valueClause = 'values'

for (def bypass : bypassList) {
	valueClause = (((valueClause + '(\'') + bypass) + '\'),')
}

valueClause = valueClause.substring(0, valueClause.length() - 1)

sql.executeInsert('INSERT INTO en_web_pages_bypass (string)' + valueClause)

whereClause = "where url not like '%explorenext.org%'"

for(bp in bypassList) {
	whereClause += " or url like '%" + bp + "%'"
}

println(whereClause)

sql.execute('delete from en_web_pages_temp')

katalon = false

timeStart = new Date()
println(timeStart.getTime())

if(katalon) {
links = ['https://explorenext.org/#content','null','https://journey.explorenext.org/journey-home/login-here/','https://education.explorenext.org/education-home/login-here/','https://quickstart.explorenext.org/quickstart-home/login-here/','https://explorenext.org/homepage/about/','https://explorenext.org/category/impact-report/','https://explorenext.org/homepage/about/positions-with-missionnext/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/#','https://explorenext.org/homepage/goer/','https://quickstart.explorenext.org/','https://journey.explorenext.org/','https://education.explorenext.org/','https://explorenext.org/homepage/goer/positions/','https://explorenext.org/homepage/goer/resources-for-goers/','https://explorenext.org/homepage/sender/','https://journey.missionnext.org/journey-home/im-an-organization/','https://education.missionnext.org/education-home/im-a-school/','https://explorenext.org/homepage/sender/sender-churches/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/sender/resources-for-senders/','https://explorenext.org/homepage/supporter/','https://explorenext.org/homepage/supporter/mobilizer/','https://explorenext.org/homepage/supporter/support-donor/','https://explorenext.org/homepage/supporter/intercessor/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/supporter/volunteer/','https://explorenext.org/homepage/events/','https://explorenext.org/homepage/blog-vlog/','https://explorenext.org/homepage/blog-vlog/','https://explorenext.org/category/founders-blog-archive/','https://explorenext.org/category/ceos-vlog/','https://explorenext.org/homepage/donate-here/','https://explorenext.org/homepage/partners/','null','https://journey.explorenext.org/journey-home/login-here/','https://education.explorenext.org/education-home/login-here/','https://quickstart.explorenext.org/quickstart-home/login-here/','https://explorenext.org/homepage/about/','https://explorenext.org/category/impact-report/','https://explorenext.org/homepage/about/positions-with-missionnext/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/#','https://explorenext.org/homepage/goer/','https://quickstart.explorenext.org/','https://journey.explorenext.org/','https://education.explorenext.org/','https://explorenext.org/homepage/goer/positions/','https://explorenext.org/homepage/goer/resources-for-goers/','https://explorenext.org/homepage/sender/','https://journey.missionnext.org/journey-home/im-an-organization/','https://education.missionnext.org/education-home/im-a-school/','https://explorenext.org/homepage/sender/sender-churches/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/sender/resources-for-senders/','https://explorenext.org/homepage/supporter/','https://explorenext.org/homepage/supporter/mobilizer/','https://explorenext.org/homepage/supporter/support-donor/','https://explorenext.org/homepage/supporter/intercessor/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/supporter/volunteer/','https://explorenext.org/homepage/events/','https://explorenext.org/homepage/blog-vlog/','https://explorenext.org/homepage/blog-vlog/','https://explorenext.org/category/founders-blog-archive/','https://explorenext.org/category/ceos-vlog/','https://explorenext.org/homepage/donate-here/','https://explorenext.org/homepage/partners/','https://explorenext.org/sender/','https://explorenext.org/sender/','https://journey.explorenext.org/journey-home/im-an-organization/','https://education.explorenext.org/education-home/im-a-school/','https://explorenext.org/homepage/sender/sender-churches/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/sender/resources-for-senders/','https://explorenext.org/goer/','https://explorenext.org/goer/','https://quickstart.explorenext.org/','https://journey.explorenext.org/','https://education.explorenext.org/','https://explorenext.org/homepage/goer/positions/','https://explorenext.org/homepage/goer/resources-for-goers/','https://explorenext.org/supporter/','https://explorenext.org/supporter/','https://explorenext.org/homepage/supporter/mobilizer/','https://explorenext.org/homepage/supporter/support-donor/','https://explorenext.org/homepage/supporter/intercessor/','https://explorenext.org/homepage/about/missionnext-strategic-partners/','https://explorenext.org/homepage/supporter/volunteer/','https://journey.missionnext.org/journey-home/im-an-organization/','https://education.missionnext.org/education-home/im-a-school/','https://explorenext.org/goer/positions/','https://explorenext.org/missio-nexus-2024-mission-leaders-conference/','https://explorenext.org/missio-nexus-2024-mission-leaders-conference/','https://explorenext.org/missio-nexus-2024-mission-leaders-conference/','https://explorenext.org/perspectives-50th-anniversary-global-conference/','https://explorenext.org/perspectives-50th-anniversary-global-conference/','https://explorenext.org/perspectives-50th-anniversary-global-conference/','https://explorenext.org/icom/','https://explorenext.org/icom/','https://explorenext.org/icom/','https://explorenext.org/terms/','https://explorenext.org/terms/privacy/','https://explorenext.org/terms/legal/','https://explorenext.org/site-map/','https://explorenext.org/contact-us/','https://explorenext.org/#link-popup','https://www.facebook.com/MissionNextOrg/','https://twitter.com/MissionNext','https://instagram.com/missionnextglobal','https://www.linkedin.com/company/missionnext']

loop = 0
	
while(loop < 1) {
	myLinks = []
	
	for(link in links) {
		if(link != null && link.indexOf('#') < 0) {
			if(link.indexOf('missionnext.org') < 0 && link.indexOf('explorenext') >= 0) {
				myLinks.add(link)
			}
		}
	}
	loop ++
}

timeStop = new Date()
println(timeStop.getTime())

}

else {
println('#########################################')

valueClause = "values('https://explorenext.org/#content'),('null'),('https://journey.explorenext.org/journey-home/login-here/'),('https://education.explorenext.org/education-home/login-here/'),('https://quickstart.explorenext.org/quickstart-home/login-here/'),('https://explorenext.org/homepage/about/'),('https://explorenext.org/category/impact-report/'),('https://explorenext.org/homepage/about/positions-with-missionnext/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/#'),('https://explorenext.org/homepage/goer/'),('https://quickstart.explorenext.org/'),('https://journey.explorenext.org/'),('https://education.explorenext.org/'),('https://explorenext.org/homepage/goer/positions/'),('https://explorenext.org/homepage/goer/resources-for-goers/'),('https://explorenext.org/homepage/sender/'),('https://journey.missionnext.org/journey-home/im-an-organization/'),('https://education.missionnext.org/education-home/im-a-school/'),('https://explorenext.org/homepage/sender/sender-churches/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/sender/resources-for-senders/'),('https://explorenext.org/homepage/supporter/'),('https://explorenext.org/homepage/supporter/mobilizer/'),('https://explorenext.org/homepage/supporter/support-donor/'),('https://explorenext.org/homepage/supporter/intercessor/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/supporter/volunteer/'),('https://explorenext.org/homepage/events/'),('https://explorenext.org/homepage/blog-vlog/'),('https://explorenext.org/homepage/blog-vlog/'),('https://explorenext.org/category/founders-blog-archive/'),('https://explorenext.org/category/ceos-vlog/'),('https://explorenext.org/homepage/donate-here/'),('https://explorenext.org/homepage/partners/'),('null'),('https://journey.explorenext.org/journey-home/login-here/'),('https://education.explorenext.org/education-home/login-here/'),('https://quickstart.explorenext.org/quickstart-home/login-here/'),('https://explorenext.org/homepage/about/'),('https://explorenext.org/category/impact-report/'),('https://explorenext.org/homepage/about/positions-with-missionnext/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/#'),('https://explorenext.org/homepage/goer/'),('https://quickstart.explorenext.org/'),('https://journey.explorenext.org/'),('https://education.explorenext.org/'),('https://explorenext.org/homepage/goer/positions/'),('https://explorenext.org/homepage/goer/resources-for-goers/'),('https://explorenext.org/homepage/sender/'),('https://journey.missionnext.org/journey-home/im-an-organization/'),('https://education.missionnext.org/education-home/im-a-school/'),('https://explorenext.org/homepage/sender/sender-churches/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/sender/resources-for-senders/'),('https://explorenext.org/homepage/supporter/'),('https://explorenext.org/homepage/supporter/mobilizer/'),('https://explorenext.org/homepage/supporter/support-donor/'),('https://explorenext.org/homepage/supporter/intercessor/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/supporter/volunteer/'),('https://explorenext.org/homepage/events/'),('https://explorenext.org/homepage/blog-vlog/'),('https://explorenext.org/homepage/blog-vlog/'),('https://explorenext.org/category/founders-blog-archive/'),('https://explorenext.org/category/ceos-vlog/'),('https://explorenext.org/homepage/donate-here/'),('https://explorenext.org/homepage/partners/'),('https://explorenext.org/sender/'),('https://explorenext.org/sender/'),('https://journey.explorenext.org/journey-home/im-an-organization/'),('https://education.explorenext.org/education-home/im-a-school/'),('https://explorenext.org/homepage/sender/sender-churches/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/sender/resources-for-senders/'),('https://explorenext.org/goer/'),('https://explorenext.org/goer/'),('https://quickstart.explorenext.org/'),('https://journey.explorenext.org/'),('https://education.explorenext.org/'),('https://explorenext.org/homepage/goer/positions/'),('https://explorenext.org/homepage/goer/resources-for-goers/'),('https://explorenext.org/supporter/'),('https://explorenext.org/supporter/'),('https://explorenext.org/homepage/supporter/mobilizer/'),('https://explorenext.org/homepage/supporter/support-donor/'),('https://explorenext.org/homepage/supporter/intercessor/'),('https://explorenext.org/homepage/about/missionnext-strategic-partners/'),('https://explorenext.org/homepage/supporter/volunteer/'),('https://journey.missionnext.org/journey-home/im-an-organization/'),('https://education.missionnext.org/education-home/im-a-school/'),('https://explorenext.org/goer/positions/'),('https://explorenext.org/missio-nexus-2024-mission-leaders-conference/'),('https://explorenext.org/missio-nexus-2024-mission-leaders-conference/'),('https://explorenext.org/missio-nexus-2024-mission-leaders-conference/'),('https://explorenext.org/perspectives-50th-anniversary-global-conference/'),('https://explorenext.org/perspectives-50th-anniversary-global-conference/'),('https://explorenext.org/perspectives-50th-anniversary-global-conference/'),('https://explorenext.org/icom/'),('https://explorenext.org/icom/'),('https://explorenext.org/icom/'),('https://explorenext.org/terms/'),('https://explorenext.org/terms/privacy/'),('https://explorenext.org/terms/legal/'),('https://explorenext.org/site-map/'),('https://explorenext.org/contact-us/'),('https://explorenext.org/#link-popup'),('https://www.facebook.com/MissionNextOrg/'),('https://twitter.com/MissionNext'),('https://instagram.com/missionnextglobal'),('https://www.linkedin.com/company/missionnext')"

sql.executeInsert('insert into en_web_pages_temp (url)' + valueClause)

timeStart = new Date()

sql.execute("delete from en_web_pages_temp where url not like '%explorenext.org%'")

sql.execute('delete from en_web_pages_temp a where exists (select 1 from en_web_pages_bypass b where a.url like \'%\' || b.string || \'%\')')



//sql.execute("delete from en_web_pages_temp " + whereClause)


timeStop = new Date()


}

println((timeStop.getTime() - timeStart.getTime()))