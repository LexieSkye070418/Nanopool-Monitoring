package by.lebedev.nanopoolmonitoring

import io.reactivex.Single
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun  testVehicleTestState() {
        val list = listOf<Vehicle>()
        val testVehicleSingle = Single.just(list)


        //обратить внимание!!!!!!!!!!!!!!!!!11
        Mockito.`when`(vehicleUseCase.get()).thenReturn(testVehicleSingle)


        val viewModel = VehicleListViewModel(vehicleUseCase)

        val vehicle = Vehicle(5, 1.5, 1.8,FleetType.TAXI,5.5)
        viewModel.vechicleClick(vehicle)

        Assert.assertEquals(vehicle, viewModel.vehicleClicked.value)
    }
}
