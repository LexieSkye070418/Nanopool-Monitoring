package by.lebedev.nanopoolmonitoringnoads.dagger

import by.lebedev.nanopoolmonitoringnoads.room.entity.Account
import javax.inject.Singleton

@Singleton
class AccountLocalList  private constructor() {
    var list = ArrayList<Account>()

    private object Holder {
        val INSTANCE = AccountLocalList()
    }

    companion object {
        val instance: AccountLocalList by lazy { Holder.INSTANCE }
    }
}