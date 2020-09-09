import org.junit.Assert.assertEquals
import org.junit.Test


class MainKtTest {

    @Test
    fun getMoneyTotalMonthMasterCard() {
        assertEquals(moneyTotalMonthMasterCard, 0)
    }

    @Test
    fun setMoneyTotalMonthMasterCard() {
        moneyTotalMonthMasterCard = 100
        assertEquals(moneyTotalMonthMasterCard, 100)
    }

    @Test
    fun getMoneyTotalMonthCard() {
        assertEquals(moneyTotalMonthCard, 0)
    }

    @Test
    fun setMoneyTotalMonthCard() {
        moneyTotalMonthCard = 100
        assertEquals(moneyTotalMonthCard, 100)
    }

    @Test
    fun getMoneyTotalMonthVkPay() {
        assertEquals(moneyTotalMonthVkPay, 0)
    }

    @Test
    fun setMoneyTotalMonthVkPay() {
        moneyTotalMonthVkPay = 100
        assertEquals(moneyTotalMonthVkPay, 100)
    }

    @Test
    fun isLimitPay_TypeElse() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 100_000_00
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, true)
    }
    @Test
    fun isLimitPay_MaxLimitMonthPayCard() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 600_000_01
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun isLimitPay_MaxLimitOnePayCard() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 150_000_01
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, false)
    }

    @Test
    fun isLimitPay_MaxLimitMonthPayVK() {
        val typeCard = TypePay.VKPAY
        val amount = 40_000_01
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun isLimitPay_MaxLimitOnePayVK() {
        val typeCard = TypePay.VKPAY
        val amount = 15_000_01
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun isLimitPay_NormalPayVK() {
        val typeCard = TypePay.VKPAY
        val amount = 15_000_00
        val result = isLimitPay(typeCard, amount)
        assertEquals(result, true)
    }

    @Test
    fun getComission_Mcard_NoLimit() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 50000_00
        val result = getComission(typeCard, amount)
        assertEquals(result, 0)
    }
    @Test
    fun getComission_Mcard_MaxLimit() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 75_001_00
        val result = getComission(typeCard, amount)
        assertEquals(result, 470_00)
    }
    @Test
    fun getComission_Visa_MinLimit() {
        val typeCard = TypePay.VISA_MIR
        val amount = 1_000_00
        val result = getComission(typeCard, amount)
        assertEquals(result, 35_00)
    }
    @Test
    fun getComission_Visa_Normal() {
        val typeCard = TypePay.VISA_MIR
        val amount = 110_000_00
        val result = getComission(typeCard, amount)
        assertEquals(result, 825_00)
    }
    @Test
    fun getComission_VkPay() {
        val typeCard = TypePay.VKPAY
        val amount = 100_000_00
        val result = getComission(typeCard, amount)
        assertEquals(result, 0)
    }

    @Test
    fun convertToRouble() {
        val summa = 100_000_00
        val result = convertToRouble(summa)
        assertEquals((100_000).toDouble(), result, 0.009)
    }

    @Test
    fun transfer_MasterCard_false() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 200_000_00
        val result = transfer(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun transfer_MasterCard_true() {
        val typeCard = TypePay.MASTERCARD_MAESTRO
        val amount = 1_00_00
        val result = transfer(typeCard, amount)
        assertEquals(result, true)
    }
    @Test
    fun transfer_Visa_false() {
        val typeCard = TypePay.VISA_MIR
        val amount = 600_001_00
        val result = transfer(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun transfer_Visa_true() {
        val typeCard = TypePay.VISA_MIR
        val amount = 15_000_00
        val result = transfer(typeCard, amount)
        assertEquals(result, true)
    }
    @Test
    fun transfer_VK_false() {
        val typeCard = TypePay.VKPAY
        val amount = 600_001_00
        val result = transfer(typeCard, amount)
        assertEquals(result, false)
    }
    @Test
    fun transfer_VK_true() {
        val typeCard = TypePay.VKPAY
        val amount = 1_000_00
        val result = transfer(typeCard, amount)
        assertEquals(result, true)
    }

//    @Test
//    fun transfer() {
//    }
//    @Test
//    fun transfer() {
//    }

    @Test
    fun getTypePay_Mcard() {
        val typeCard = 1
        val result = getTypePay(typeCard)
        assertEquals(result, TypePay.MASTERCARD_MAESTRO)
    }
    @Test
    fun getTypePay_Visa() {
        val typeCard = 2
        val result = getTypePay(typeCard)
        assertEquals(result, TypePay.VISA_MIR)
    }
    @Test
    fun getTypePay_VK() {
        val typeCard = 3
        val result = getTypePay(typeCard)
        assertEquals(result, TypePay.VKPAY)
    }
    @Test
    fun getTypePay_Other() {
        val typeCard = 40
        val result = getTypePay(typeCard)
        assertEquals(result, TypePay.VKPAY)
    }
}