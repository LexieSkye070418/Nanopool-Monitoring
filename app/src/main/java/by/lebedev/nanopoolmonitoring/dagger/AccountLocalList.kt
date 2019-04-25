package by.lebedev.nanopoolmonitoring.dagger

import by.lebedev.nanopoolmonitoring.room.entity.Account

class AccountLocalList  private constructor() {
    var list = ArrayList<Account>()

    private object Holder {
        val INSTANCE = AccountLocalList()
    }

    companion object {
        val instance: AccountLocalList by lazy { Holder.INSTANCE }
    }
}