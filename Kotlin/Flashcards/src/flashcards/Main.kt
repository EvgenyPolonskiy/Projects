package flashcards

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.random.Random

fun main(args: Array<String>) {
    println(args.size)
    Flashcards(args).run()

}


class Flashcards(var args: Array<String>) {

    var termList = mutableListOf<String>()
    var descList = mutableListOf<String>()
    var statList = mutableListOf<Int>()
    var desc: String = ""
    var term: String = ""
    var logText = ""

    fun argsIncome() {
        if (args.contains("-import")) {
            var tempTermList = mutableListOf<String>()
            var tempDescList = mutableListOf<String>()
            var tempStatList = mutableListOf<Int>()
            var fileName = args[args.indexOf("-import") + 1]
            var linesCounter = 0
            try {
                BufferedReader(FileReader(fileName)).use { br ->
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        var (a, b, c) = line!!.split(":::")
                        tempTermList.add(a)
                        tempDescList.add(b)
                        tempStatList.add(c.toInt())
                        linesCounter++
                    }
                }
                for (i in 0 until tempTermList.size) {
                    if (termList.contains(tempTermList[i])) {
                        var index = termList.indexOf(tempTermList[i])
                        termList.removeAt(index)
                        descList.removeAt(index)
                        statList.removeAt(index)
                    }
                }
                termList.addAll(tempTermList)
                descList.addAll(tempDescList)
                statList.addAll(tempStatList)
                printAndLog("$linesCounter cards have been loaded.")
            } catch (e: Exception) {
                printAndLog("File not found.")
            }
        }
    }

    fun run() {
        argsIncome()
        var canWork = true
        while (canWork) {

            printAndLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
            var inputCmd = readln()
            printAndLog(inputCmd, 1)
            when (inputCmd) {
                "add" -> add()
                "remove" -> remove()
                "import" -> import()
                "export" -> export()
                "ask" -> ask()
                "log" -> log()
                "hardest card" -> hardestCard()
                "reset stats" -> resetStats()
                "test" -> test()
                "exit" -> {
                    exit()
                    canWork = false
                }
                else -> {
                    printAndLog("Wrong command")
                }
            }
        }
    }

    private fun exit() {
        if (args.contains("-export")){
            var fileName = args[args.indexOf("-export")+1]
            var text = ""
            for (i in 0 until termList.size) {
                text += "${termList[i]}:::${descList[i]}:::${statList[i]}\n"
                File(fileName).writeText(text)
                printAndLog("${termList.size} cards have been saved.")
            }
        }
        printAndLog("Bye bye!")
    }

    private fun add() {
        var term = addTerm()
        if (term == "") {
        } else {
            var desc = addDesc()
            if (desc == "") {
            } else {
                termList.add(term)
                descList.add(desc)
                statList.add(0)

                printAndLog("The pair (\"$term\":\"$desc\") has been added.")
            }
        }
    }

    private fun addTerm(): String {
        printAndLog("The card:")
        term = readln()
        printAndLog(term, 1)
        if (termList.contains(term)) {
            printAndLog("The card \"$term\" already exists.")
            return ""
        }
        return term
    }

    private fun addDesc(): String {
        printAndLog("The definition of the card:")
        desc = readln()
        printAndLog(desc, 1)
        if (descList.contains(desc)) {
            printAndLog("The definition \"$desc\" already exists.")
            return ""
        }
        return desc
    }

    private fun ask() {
        printAndLog("How many times to ask?")
        var target = readln().toInt()
        printAndLog(target.toString(), 1)
        for (i in 1..target) {
            var random = Random.nextInt(0, termList.size)
            var card = termList.elementAt(random)
            printAndLog("Print the definition of \"${card}\":")
            var answer = readln()
            printAndLog(answer, 1)
            if (descList.elementAt(random) == answer) {
                printAndLog("Correct!")
            } else if (descList.elementAt(random) != answer && descList.contains(answer)) {
                statList[random]++
                printAndLog(
                    "Wrong. The right answer is \"${descList.elementAt(random)}\", but your definition is correct for \"${
                        termList.elementAt(
                            descList.indexOf(answer)
                        )
                    }\"."
                )
            } else {
                statList[random]++
                printAndLog("Wrong. The right answer is \"${descList.elementAt(random)}\".")
            }
        }
    }

    private fun remove() {
        printAndLog("Which card?")
        term = readln()
        printAndLog(term, 1)
        if (termList.contains(term)) {
            var index = termList.indexOf(term)
            termList.removeAt(index)
            descList.removeAt(index)
            statList.removeAt(index)
            printAndLog("The card has been removed")
        } else {
            printAndLog("Can't remove \"$term\": there is no such card.")
        }
    }

    private fun export() {
        printAndLog("File name:")
        var fileName = readln()
        printAndLog(fileName, 1)
        var text = ""
        for (i in 0 until termList.size) {
            text += "${termList[i]}:::${descList[i]}:::${statList[i]}\n"

        }
        File(fileName).writeText(text)
        printAndLog("${termList.size} cards have been saved.")
    }

    private fun import() {
        var tempTermList = mutableListOf<String>()
        var tempDescList = mutableListOf<String>()
        var tempStatList = mutableListOf<Int>()
        printAndLog("File name:")
        var fileName = readln()
        printAndLog(fileName, 1)
        var linesCounter = 0
        try {
            BufferedReader(FileReader(fileName)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    var (a, b, c) = line!!.split(":::")
                    tempTermList.add(a)
                    tempDescList.add(b)
                    tempStatList.add(c.toInt())
                    linesCounter++
                }
            }
            for (i in 0 until tempTermList.size) {
                if (termList.contains(tempTermList[i])) {
                    var index = termList.indexOf(tempTermList[i])
                    termList.removeAt(index)
                    descList.removeAt(index)
                    statList.removeAt(index)
                }
            }
            termList.addAll(tempTermList)
            descList.addAll(tempDescList)
            statList.addAll(tempStatList)


            printAndLog("$linesCounter cards have been loaded.")
        } catch (e: Exception) {
            printAndLog("File not found.")
        }
    }

    private fun resetStats() {
        for (i in 0 until statList.size) {
            statList[i] = 0
        }
        printAndLog("Card statistics have been reset.")
    }

    private fun hardestCard() {
        var max = statList.maxOrNull() ?: 0
        var countOfMaxs = 0
        for (i in statList) {
            if (i == max && i != 0) {
                countOfMaxs += 1
            }
        }
        if (max == 0 && countOfMaxs == 0) {
            printAndLog("There are no cards with errors.")
        } else if (max != 0 && countOfMaxs == 1) {
            printAndLog("The hardest card is \"${termList.elementAt(statList.indexOf(max))}\". You have $max errors answering it")
        } else {
            var listOfTermsWithMistake = mutableListOf<String>()
            var listOfIndexes = mutableListOf<Int>()
            for (i in 0 until statList.size) {
                if (statList[i] == max) {
                    listOfIndexes.add(i)
                }
            }
            for (i in listOfIndexes) {
                listOfTermsWithMistake.add(termList.elementAt(i))
            }
            var text = ""
            for (i in listOfTermsWithMistake) {
                text = "$text\"$i\", "
            }
            var aa = text.dropLast(2)
            printAndLog("The hardest cards are $aa. You have $max errors answering them")
        }
    }

    private fun test() {
        println(termList)
        println(descList)
        println(statList)
    }

    private fun printAndLog(text: String, type: Int = 0) {
        if (type == 0) {
            println(text)
            logText += "$text\n"
        } else {
            logText += "$text\n"
        }
    }


    private fun log() {
        printAndLog("File name:")
        var fileName = readln()
        printAndLog(fileName, 1)
        printAndLog("The log has been saved.")
        File(fileName).writeText(logText)

    }
}




