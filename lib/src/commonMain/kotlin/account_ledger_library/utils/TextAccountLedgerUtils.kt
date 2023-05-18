package account_ledger_library.utils;

object TextAccountLedgerUtils {

//    @JvmStatic
    fun addLineToCurrentAccountLedger(

        ledgerToProcess: LinkedHashMap<UInt, MutableList<String>>,
        desiredAccountId: UInt,
        desiredLine: String

    ): LinkedHashMap<UInt, MutableList<String>> {

        val currentAccountLedgerLines: MutableList<String> =
            ledgerToProcess.getOrElse(
                key = desiredAccountId,
                defaultValue = fun(): MutableList<String> = mutableListOf(),
            )
        currentAccountLedgerLines.add(element = desiredLine)
        ledgerToProcess[desiredAccountId] = currentAccountLedgerLines
        return ledgerToProcess
    }
}
