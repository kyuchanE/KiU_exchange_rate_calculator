package team.devloopy.kiu_exchange_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonObject
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import team.devloopy.kiu_exchange_rate.data.ExchangeRateRepository
import team.devloopy.kiu_exchange_rate.ui.MainViewModel

class MainViewModelTest: AbstractKoinTest() {
    private val mainViewModel: MainViewModel by inject()

    @Before
    fun setUp() {
        // set test data

    }

    @Test
    fun `Koin Unit Test`() {
        runBlocking {
            val jsonData = JsonObject().apply {
                addProperty("success", true)
                addProperty("timestamp", 1654219804)

                val quotesData = JsonObject().apply {
                    addProperty("USDKRW", 1243.055032)
                }
                add("quotes", quotesData)
            }

//            repository = declareMock {
//                given(this.getExchangeRate()).willReturn(
//                    Flowable
//                        .just(jsonData)
//                )
//            }

//            `when`(repository.getExchangeRate()).thenReturn(
//                Flowable
//                    .just(jsonData)
//            )

//            mainViewModel = MainViewModel(repository)
//            assertThat(mainViewModel.getTestExchangeRateRepoData()).isEqualTo("doOnNext : SUCCESS")
//            assertThat(mainViewModel.testGetTimeData(jsonData)).isEqualTo("2022-06-03 10:30")
//            assertThat(mainViewModel.testGetKrwData(jsonData)).isEqualTo("1,243.06")
        }



//        `when`(repository.testGetTime()).thenReturn("2022-06-03 10:31")
//        `when`(repository.testGetKrw()).thenReturn("1,243.07")



//        assertThat(repository.testSetTimeData().value).isEqualTo(1654219804)
//        assertThat(repository.testSetKrwData().value).isEqualTo(1243.055032)

//        val timeResult = repository.testGetTime()
//        assertThat(timeResult).isEqualTo("2022-06-03 10:30")
//
//        val krwResult = repository.testGetKrw()
//        assertThat(krwResult).isEqualTo("1,243.06")


    }
}