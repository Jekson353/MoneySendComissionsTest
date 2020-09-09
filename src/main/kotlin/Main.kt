import java.util.*

const val comissMinRubleVIsa = 35_00 //минимальная комиссия по Visa
const val comissFixRubleMasterCad = 20_00 //Фиксированная комиссия по MS
const val comissPercentVisaMir = 75 //0.75% комиссия по Visa
const val comissPercentMastercardMaestro = 60 //0,6% комиссия по MS
const val limitLgotMasterCard = 75_000_00 //Лимит по льготным переводам MasterCard/Maestro
const val limitPayCardMonth = 600_000_00 //имит по картам в месяц
const val limitPayCardOne = 150_000_00 // лимит по картам за платеж
const val limitPayVKMonth = 40_000_00 //лимит в ВК в месяц
const val limitPayVKOne = 15_000_00 // лимит в ВК за платеж
var moneyTotalMonthMasterCard = 0 //итого переводов по картам MasterCard/Maestro
var moneyTotalMonthCard = 0 //итого переводов по картам
var moneyTotalMonthVkPay = 0 // итого переводов в ВК

fun main() {

    while (true){
        println("Введите сумму (0 - для завершения)")
        val scan = Scanner(System.`in`)
        val summa = scan.nextDouble()
        val amount: Int = (summa * 100).toInt()
        if (amount==0) break

        println("Укажите тип карты/счета:")
        println("1 - MasterCard, 2 - Visa, 3 - VkPay (по умолчанию)")
        val typeCard = scan.nextInt()

        transfer(getTypePay(typeCard), amount)

        println(" ")
    }
    println("Спасибо за использование наших платежных систем и переводов.")
}

fun isLimitPay(typeCard: TypePay, amount: Int): Boolean {
    when (typeCard) {
        in TypePay.MASTERCARD_MAESTRO..TypePay.VISA_MIR -> {
            if ((moneyTotalMonthCard + amount) > limitPayCardMonth) { //сначала проверка на месячный лимит
                println("Превышен месячный лимит переводов по картам")
                return false
            } else if (amount > limitPayCardOne) {
                println("Превышен разовый лимит переводов по картам (${convertToRouble(limitPayCardOne)} руб). Уменьшите сумму и повторите платеж.")
                return false
            } else {
                return true
            }
        }
        TypePay.VKPAY -> {
            if ((moneyTotalMonthVkPay + amount) > limitPayVKMonth) {
                println("Превышен месячный лимит переводов в ВК. Уменьшите сумму и повторите платеж.")
                return false
            } else if (amount > limitPayVKOne) {
                println("Превышен разовый лимит переводов в ВК (${convertToRouble(limitPayVKOne)} руб.). Уменьшите сумму и повторите платеж.")
                return false
            } else {
                return true
            }
        }
        else -> return false
    }
}

fun getComission(typeCard: TypePay, amount: Int): Int {
    when (typeCard) {
        TypePay.MASTERCARD_MAESTRO -> {
            if (amount+moneyTotalMonthMasterCard <= limitLgotMasterCard) {
                println("Комиссия составила 0 руб.")
                return 0
            } else {
                val percent = (amount * comissPercentMastercardMaestro / 100_00).plus(comissFixRubleMasterCad)
                println("Комиссия составила ${convertToRouble(percent)} руб.")
                return percent
            }
        }
        TypePay.VISA_MIR -> {
            val percent = amount * comissPercentVisaMir / 100_00
            if (percent < comissMinRubleVIsa) {
                println("Комиссия составила $comissMinRubleVIsa руб.")
                return comissMinRubleVIsa
            } else {
                println("Комиссия составила ${convertToRouble(percent)} руб.")
                return percent
            }
        }
        TypePay.VKPAY -> {
            println("Комиссия составила 0 руб.")
            return 0
        }
    }
}

fun convertToRouble(rouble: Int): Double {
    return rouble.toDouble() / 100
}


fun transfer(typeCard: TypePay, amount: Int): Boolean {
    println("Сумма перевода: ${convertToRouble(amount)} руб.")
    if (isLimitPay(typeCard, amount)) { //если лимит позволяет
        val comissia = getComission(typeCard, amount)
        when (typeCard){
            TypePay.MASTERCARD_MAESTRO -> {
                moneyTotalMonthCard+= amount
                moneyTotalMonthMasterCard+=amount
            }
            TypePay.VISA_MIR -> moneyTotalMonthCard+= amount
            TypePay.VKPAY -> moneyTotalMonthVkPay+=amount
        }
        val summaPay = amount.plus(comissia)
        println("С вас списано: ${convertToRouble(summaPay)} руб.")
        return true
    } else {
        println("Лимит превышен!")
        println("Использованный лимит по картам: ${convertToRouble(moneyTotalMonthCard)} руб. из ${convertToRouble(limitPayCardMonth)} руб.")
        println("Лимит бесплатных переводов по MS/Maestro: ${convertToRouble(moneyTotalMonthMasterCard)} руб. из ${convertToRouble(limitLgotMasterCard)} руб.")
        println("Использованный лимит по VkPay: ${convertToRouble(moneyTotalMonthVkPay)} руб. из ${convertToRouble(limitPayVKMonth)} руб.")
        return false
    }
}

fun getTypePay(typeCard: Int): TypePay {
    return when (typeCard) {
        1 -> TypePay.MASTERCARD_MAESTRO
        2 -> TypePay.VISA_MIR
        3 -> TypePay.VKPAY
        else -> TypePay.VKPAY
    }
}

enum class TypePay {
    MASTERCARD_MAESTRO,
    VISA_MIR,
    VKPAY,
}