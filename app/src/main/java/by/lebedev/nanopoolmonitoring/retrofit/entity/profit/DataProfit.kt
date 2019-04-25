package by.lebedev.nanopoolmonitoring.retrofit.entity.profit

data class DataProfit(
    val minute: MinuteProfit,
    val hour: HourProfit,
    val day: DayProfit,
    val week: WeekProfit,
    val month: MonthProfit
)