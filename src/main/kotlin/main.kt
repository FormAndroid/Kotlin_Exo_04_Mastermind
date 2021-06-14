fun main(args: Array<String>) {
    val listMystere: List<Int> = generateMystere()
    println("-> ${listMystere.joinToString(" > ")}") //TODO Remove this ;)

    var isWin: Boolean = false
    val maxTentative: Int = 12
    var nbTentative: Int = 1
    do {
        val listUser: List<Int> = getTentativeUser(nbTentative)

        isWin = (listMystere == listUser)

        if(!isWin) {
            val nbWellPlaced: Int = checkWellPlaced(listMystere, listUser)
            val nbMisplaced: Int = checkMisplaced(listMystere, listUser)

            println("""
                Bien placé : $nbWellPlaced
                Mal placé: $nbMisplaced
            """.trimIndent())
        }

        println()
        nbTentative++
    } while (!isWin && nbTentative <= maxTentative)

    if(isWin) {
        println("Bravo! Vous avez mit $nbTentative tentative.")
    }
    else {
        println("Perdu. La réponse était \"${listMystere.joinToString(" ")}\"")
    }
}

fun generateMystere(): List<Int> = List(4) { (0..9).random() }

fun getTentativeUser(nbTentative: Int): List<Int> {
    val regexNb = Regex("^[0-9](\\s[0-9]){3}$")

    var saisie: String
    println("Entrer la proposition $nbTentative (Format: X X X X) :")
    do {
        print("> ")
        saisie = readLine()!!.trim()
    } while (!regexNb.matches(saisie))

    return saisie.split(Regex("\\s")).map() { it.toInt() }
}

fun checkWellPlaced(listMystere: List<Int>, listUser: List<Int>) : Int {
    var count = 0
    for(i in listMystere.indices) {
        if(listMystere[i] == listUser[i])
            count++
    }

    return count
}

fun checkMisplaced(listMystere: List<Int>, listUser: List<Int>): Int {

    val restMystere: MutableList<Int> = mutableListOf()
    val restUser: MutableList<Int> = mutableListOf()

    for(i in listMystere.indices) {
        if(listMystere[i] != listUser[i]) {
            restMystere.add(listMystere[i])
            restUser.add(listUser[i])
        }
    }

    var count = 0

    for(valMystere in restMystere) {

        var i = 0
        var isFind = false
        while(!isFind && i < restUser.size) {

            if(restUser[i] == valMystere) {
                isFind = true
                restUser.removeAt(i)
            }

            i++
        }

        if(isFind) {
            count++
        }
    }

    return count
}