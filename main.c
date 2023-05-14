#include "lib/build/bin/native/debugShared/native_api.h"
#include "stdio.h"

int main(int argc, char** argv) {

    native_ExportedSymbols* lib = native_symbols();

    native_kref_account_ledger_library_native_Hello newInstance = lib->kotlin.root.account.ledger.library_native.Hello.Hello();
    const char* response = lib->kotlin.root.account.ledger.library_native.Hello.sayHello(newInstance);
    printf("sayHello returns %s", response);
    lib->DisposeString(response);

    return 0;
}
