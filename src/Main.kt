import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// Класс для представления расхода
class Expense(val amount: Double, val category: String, val date: LocalDate) {
    // Метод вывода информации о расходе
    fun printInfo() {
        println("Сумма: $amount, Категория: $category, Дата: $date")
    }
}

class ExpenseTracker { // Класс для представления списка расходов
    private val expenses = mutableListOf<Expense>()

    // Метод добавления нового расхода в список
    fun addExpense(amount: Double, category: String, date: LocalDate) {
        expenses.add(Expense(amount, category, date))
    }

    // Метод вывода всех расходов
    fun printAllExpenses() {
        if (expenses.isEmpty()) {
            println("Список расходов пуст.")
        }
        else
        {
            expenses.forEach { it.printInfo() }
        }
    }

    // Метод подсчета суммы всех расходов по каждой категории
    fun calculateExpensesByCategory(): Map<String, Double> {
        val expensesByCategory = mutableMapOf<String, Double>()
        expenses.forEach { expense ->
            expensesByCategory[expense.category] = expensesByCategory.getOrDefault(expense.category, 0.0) + expense.amount
        }
        //Метод getOrDefault в Java возвращает значение, соответствующее указанному ключу. Если такого ключа нет, метод вернёт значение по умолчанию, переданное в качестве параметра
        return expensesByCategory
    }
}

fun main() {
    val tracker = ExpenseTracker()

    while (true) {
        println("Выберите действие:")
        println("1. Добавить расход")
        println("2. Вывести все расходы")
        println("3. Вывести расходы по категориям")
        println("4. Выход")

        val choice = readLine()?.toIntOrNull()

        when (choice) {
            1 -> {
                println("Введите сумму расхода:")
                val amount = readLine()?.toDoubleOrNull() ?: 0.0
                println("Введите категорию расхода:")
                val category = readLine() ?: ""
                println("Введите дату расхода (ДД.ММ.ГГГГ):")
                val dateString = readLine() ?: ""
                val date = try {
                    LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy")) //Это статический метод класса `LocalDate`, который пытается преобразовать строку, представляющую дату, в объект `LocalDate`
                //DateTimeFormatter.ofPattern("dd.MM.yyyy")`: Это создает объект `DateTimeFormatter`, который задает ожидаемый формат даты.
                } catch (e: DateTimeParseException) {
                    println("Неверный формат даты. Пожалуйста, введите дату в формате ДД.ММ.ГГГГ.")
                    continue
                }
                tracker.addExpense(amount, category, date)
                println("Расход добавлен.")
            }
            2 -> {
                tracker.printAllExpenses()
            }
            3 -> {
                val expensesByCategory = tracker.calculateExpensesByCategory()
                println("Расходы по категориям:")
                expensesByCategory.forEach { (category, amount) ->
                    println("$category: $amount")
                }
            }
            4 -> {
                println("До свидания!")
                break
            }
            else -> {
                println("Неверный выбор. Пожалуйста, выберите действие из списка.")
            }
        }
    }
}
//ForEach — это цикл итерации, который позволяет обращаться к элементам коллекции или списка и выполнять действия над каждым элементом.
