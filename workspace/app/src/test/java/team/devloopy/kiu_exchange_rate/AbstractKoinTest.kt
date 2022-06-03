package team.devloopy.kiu_exchange_rate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import team.devloopy.kiu_exchange_rate.di.activityModule
import team.devloopy.kiu_exchange_rate.di.apiModule
import team.devloopy.kiu_exchange_rate.di.repositoryModule

abstract class AbstractKoinTest: KoinTest {
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(
            listOf(
                activityModule,
                apiModule,
                repositoryModule
            )
        )
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> Mockito.mock(clazz.java) }
}