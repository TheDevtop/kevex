/*
    Kotlin Evaluate Expression
    Version: 1.0
*/

import com.google.gson.Gson
import java.util.Scanner
import kotlin.system.exitProcess

var kevexMap = HashMap<String, Double>()
fun output() {
    val gson = Gson()
    print("${gson.toJson(kevexMap).toString()}\n")
}

fun evalList(sList: List<String>): ListResult {
    val dList = emptyList<Double>().toMutableList()
    sList.forEach { str ->
        try {
            dList.add(str.toDouble())
        } catch (ex: NumberFormatException) {
            if (kevexMap[str]?.let { dList.add(it) } == false) {
                return ListResult(dList, ex.toString())
            }
        }
    }
    return ListResult(dList, null)
}

fun evalFn(fnStr: String): FuncResult {
    // Add function
    fun add(dlist: List<Double>): Double {
        return dlist.sum()
    }

    // Count function
    fun count(dlist: List<Double>): Double {
        return dlist.size.toDouble()
    }

    // Highest value function
    fun high(dlist: List<Double>): Double {
        return dlist.max()
    }

    // Lowest value function
    fun low(dlist: List<Double>): Double {
        return dlist.min()
    }

    // None function
    fun none(dlist: List<Double>): Double {
        return 00.00
    }

    return when (fnStr) {
        ":=" -> FuncResult(::add, null)
        "#=" -> FuncResult(::count, null)
        "|=" -> FuncResult(::high, null)
        "&=" -> FuncResult(::low, null)
        else -> FuncResult(::none, "Error: Associated function not found!")
    }
}

fun parse(lines: List<String>): String? {
    val reg = "\\s+".toRegex()
    for (ln in lines) {
        if (ln.startsWith("#")) {
            continue
        }
        val tokens = ln.trim().split(reg)
        if (tokens.size < 3) {
            return "Error: Not enough tokens!"
        }
        val fr = evalFn(tokens[1])
        if (fr.err != null) {
            return fr.err
        }
        val lr = evalList(tokens.slice(2..tokens.lastIndex))
        if (lr.err != null) {
            return lr.err
        }
        kevexMap[tokens[0]] = fr.fn(lr.dlist)
    }
    return null
}

fun input(args: Array<String>): List<String> {
    if (args.isEmpty()) {
        val nargs = ArrayList<String>()
        val stdin = Scanner(System.`in`)
        while (stdin.hasNextLine()) {
            nargs.add(stdin.nextLine())
        }
        return nargs
    }
    return args.toList()
}

fun main(args: Array<String>) {
    val someErr = parse(input(args))
    if (!someErr.isNullOrEmpty()) {
        println(someErr); exitProcess(1)
    }
    output()
}