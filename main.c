#include "lib/build/bin/mingwX64/debugShared/account_ledger_lib_api.h"
#include "stdio.h"
#include "stdbool.h"

int main(int argc, char **argv)
{

    account_ledger_lib_ExportedSymbols *lib = account_ledger_lib_symbols();

    account_ledger_lib_kref_account_ledger_library_utils_GistUtils newInstance = lib->kotlin.root.account_ledger_library.utils.GistUtils.GistUtils();
    const char *accountLedgerGistText = lib->kotlin.root.account_ledger_library.utils.GistUtils.processGistIdForTextData(newInstance, "USERNAME", "GITHUB_ACCESS_TOKEN", "GIST_ID", false, false);
    lib->DisposeStablePointer(newInstance.pinned);
    lib->DisposeString(accountLedgerGistText);

    printf("Gist Data : %s", accountLedgerGistText);

    return 0;
}
