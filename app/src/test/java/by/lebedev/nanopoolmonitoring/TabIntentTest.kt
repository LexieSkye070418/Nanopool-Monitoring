package by.lebedev.nanopoolmonitoring

import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock


class TabIntentTest {
    @Mock
    lateinit var tabIntent: TabIntent

    @Test
    fun testFullName() {
        tabIntent= TabIntent.instance
        val coin = "eth"
        val fullName = "Ethereum"
        Assert.assertEquals(tabIntent.fullName(coin),fullName)
    }

    @Test
    fun testGetHashType() {
        tabIntent= TabIntent.instance
        val coin = "grin29"
        val hashType = "Kgp/s"
        Assert.assertEquals(tabIntent.getHashType(coin),hashType)
    }
}