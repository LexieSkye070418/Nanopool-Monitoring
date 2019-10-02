package by.lebedev.nanopoolmonitoringnoads

import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock


class CoinWalletTempDataTest {
    @Mock
    lateinit var coinWalletTempData: CoinWalletTempData

    @Test
    fun testFullName() {
        coinWalletTempData= CoinWalletTempData.INSTANCE
        val coin = "eth"
        val fullName = "Ethereum"
        Assert.assertEquals(coinWalletTempData.fullName(coin),fullName)
    }

    @Test
    fun testGetHashType() {
        coinWalletTempData= CoinWalletTempData.INSTANCE
        val coin = "grin29"
        val hashType = "Kgp/s"
        Assert.assertEquals(coinWalletTempData.getHashType(coin),hashType)
    }
}