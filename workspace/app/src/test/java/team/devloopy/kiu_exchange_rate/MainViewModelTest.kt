package team.devloopy.kiu_exchange_rate

import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonObject
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mockito
import team.devloopy.kiu_exchange_rate.ui.MainViewModel

class MainViewModelTest: AbstractKoinTest() {
    val mainViewModel: MainViewModel by inject()

    @Before
    fun setUp() {

    }

    @Test
    fun testMainViewModel() {
        val result = mainViewModel.getExchangeRate()

        assertThat(result).isEqualTo(JsonObject())
    }
}