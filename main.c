#include "lib/build/bin/native/debugShared/native_api.h"
#include "stdio.h"
#include "stdbool.h"

int main(int argc, char **argv)
{

    native_ExportedSymbols *lib = native_symbols();

    native_kref_account_ledger_library_utils_GistUtils newInstance = lib->kotlin.root.account_ledger_library.utils.GistUtils.GistUtils();
    lib->kotlin.root.account_ledger_library.utils.GistUtils.processGistId(newInstance, "USERNAME", "GITHUB_ACCESS_TOKEN", "GIST_ID", true, false);

    return 0;
}
