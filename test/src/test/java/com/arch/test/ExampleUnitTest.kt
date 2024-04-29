//package com.arch.test
//
//import com.arch.portdata.IRepositoryDAO
//import org.junit.Test
//
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.BeforeClass
//import org.junit.runners.Suite
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//class ExampleUnitTest {
//    private lateinit var repositoryLocalTest : RepositoryLocalTest
//
//        @Before
//        fun stepUp() {
//            repositoryLocalTest = RepositoryLocalTest()
//        }
//
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }
//
//    @Test
//    fun testMockData(){
//     assert(repositoryLocalTest.getMockDao().isNotEmpty())
//    }
//
//    @Test
//    fun testGson(){
//        val str = repositoryLocalTest.testGson()
//        assertEquals("{\"name\":\"Associated Press\",\"author\":\"ANTHONY IZAGUIRRE\",\"title\":\"Race to succeed Georg\",\"description\":\"An unusual special election in New York City’\",\"url\":\"xz\",\"url_to_image\":\"xz\",\"published_at\":\"2024-02-13T05:16:51Z\",\"id\":0}",str)
//    }
//}