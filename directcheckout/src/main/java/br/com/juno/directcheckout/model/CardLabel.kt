package br.com.juno.directcheckout.model

internal sealed class CardLabel @JvmOverloads constructor(
    val name:String,
    val detector: Regex,
    val cardLength:Int,
    val cvcLength:Int,
    val maskCC:String,
    val maskCVC:String,
    val order:Int ){

    object VISA : CardLabel(
        name= "visa",
        detector= "^4".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 99
    )

    object MASTERCARD : CardLabel(
        name= "mastercard",
        detector= "^(5[1-5]|2(2(2[1-9]|[3-9])|[3-6]|7([0-1]|20)))".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 99
    )

    object AMEX : CardLabel(
        name= "amex",
        detector= "^3[47]".toRegex(),
        cardLength= 15,
        cvcLength= 4,
        maskCC= "0000  000000  00000",
        maskCVC= "0000",
        order= 99
    )

    object DISCOVER: CardLabel(
        name= "discover",
        detector= "^6(?:011\\d{12}|5\\d{14}|4[4-9]\\d{13}|22(?:1(?:2[6-9]|[3-9]\\d)|[2-8]\\d{2}|9(?:[01]\\d|2[0-5]))\\d{10})".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 2
    )

    object HIPERCARD: CardLabel(
        name= "hipercard",
        detector= "^606282|384100|384140|384160".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 4
    )

    object DINERS: CardLabel(
        name= "diners",
        detector= "^(300|301|302|303|304|305|36|38)".toRegex(),
        cardLength= 14,
        cvcLength= 3,
        maskCC= "0000  000000  0000",
        maskCVC= DEFAULT_CVC_MASK,
        order= 5
    )


    object JCB_15: CardLabel(
        name= "jcb_15",
        detector= "^2131|1800".toRegex(),
        cardLength= 15,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 6
    )

    object JCB_16: CardLabel(
        name= "jcb_16",
        detector= "^35(?:2[89]|[3-8]\\d)".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 7
    )

    object ELO: CardLabel(
        name= "elo",
        detector= "^(4011(78|79)|43(1274|8935)|45(1416|7393|763(1|2))|50(4175|6699|67([0-6][0-9]|7[0-8])|9\\d{3})|627780|63(6297|6368)|650(03([^4])|04([0-9])|05(0|1)|4(0[5-9]|(1|2|3)[0-9]|8[5-9]|9[0-9])|5((3|9)[0-8]|4[1-9]|([0-2]|[5-8])\\d)|7(0\\d|1[0-8]|2[0-7])|9(0[1-9]|[1-6][0-9]|7[0-8]))|6516(5[2-9]|[6-7]\\d)|6550(2[1-9]|5[0-8]|(0|1|3|4)\\d))\\d*".toRegex(),
        cardLength= 16,
        cvcLength= 3,
        maskCC= DEFAULT_CC_MASK,
        maskCVC= DEFAULT_CVC_MASK,
        order= 1
    )

    object AURA: CardLabel(
        name= "aura",
        detector= "^((?!5066|5067|50900|504175|506699)50)".toRegex(),
        cardLength= 19,
        cvcLength= 3,
        maskCC= "0000000000000000000",
        maskCVC= DEFAULT_CVC_MASK,
        order= 3
    )

    companion object {

        const val DEFAULT_CC_MASK = "0000  0000  0000  0000"
        const val DEFAULT_CVC_MASK = "000"

        val listCardLabels = listOf(
            VISA,
            MASTERCARD,
            AMEX,
            DISCOVER,
            HIPERCARD,
            DINERS,
            JCB_15,
            JCB_16,
            ELO,
            AURA
        )

        fun getOrderedLabels() = listCardLabels.sortedBy { it.order }
    }
}